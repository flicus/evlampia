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

package org.schors.eva.facilities;


import org.schors.eva.AbstractConfiguration;
import org.schors.eva.AbstractFacility;
import org.schors.eva.FacilityManager;
import org.schors.eva.FacilityStatus;
import org.schors.eva.annotations.ConfigurationSection;
import org.schors.eva.annotations.Facility;
import org.schors.eva.annotations.Version;

import javax.xml.bind.annotation.XmlRootElement;

@Facility(name = "jedis", version = @Version(major = 1, minor = 0), dependsOn = {})
public class Jedis extends AbstractFacility {

    private redis.clients.jedis.Jedis jedis;
    private Configuration configuration;

    public Jedis(FacilityManager facilityManager) {
        super(facilityManager);
        this.configuration = facilityManager.getConfiguration().getSection(Configuration.class);
    }


    @Override
    public void start() {
        status = FacilityStatus.STARTING;
        jedis = new redis.clients.jedis.Jedis(configuration.getHost());
        status = FacilityStatus.STARTED;
    }

    @Override
    public void stop() {
        status = FacilityStatus.STOPPING;
        jedis.close();
        jedis = null;
        status = FacilityStatus.STOPPED;
    }

    public redis.clients.jedis.Jedis getJedis() {
        return jedis;
    }

    @XmlRootElement(name = "jedis")
    @ConfigurationSection
    public class Configuration extends AbstractConfiguration {

        private String host;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }
}
