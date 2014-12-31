/*
 * The MIT License (MIT)
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.schors.eva.facility;


import org.apache.log4j.Logger;
import org.schors.eva.Application;
import org.schors.eva.Version;
import redis.clients.jedis.Jedis;

@Facility(name = "redis", version = @Version(major = 1, minor = 0), dependsOn = {})
public class Redis extends AbstractFacility {
    private static final Logger log = Logger.getLogger(Redis.class);
    private Jedis jedis;

    public Redis(Application application) {
        super(application);
        application.getConfigurationManager().addListener(RedisConfiguration.class, this);
    }


    @Override
    public void onConfigurationChange() {
        super.onConfigurationChange();
        stop();
        start();
    }

    @Override
    public void start() {
        log.debug("start");
        status = FacilityStatus.STARTING;
        try {
            RedisConfiguration cfg = getConfiguration(RedisConfiguration.class);
            jedis = new Jedis(cfg.getHost());
            status = FacilityStatus.STARTED;
        } catch (Exception e) {
            status = FacilityStatus.ERROR;
            log.error("Unable to start", e);
        }
    }

    @Override
    public void stop() {
        log.debug("stop");
        status = FacilityStatus.STOPPING;
        jedis.close();
        jedis = null;
        status = FacilityStatus.STOPPED;
    }

    public Jedis getJedis() {
        return jedis;
    }

}
