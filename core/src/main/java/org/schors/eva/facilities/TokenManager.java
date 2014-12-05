/*
 * The MIT License (MIT)
 *
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


import org.schors.eva.AbstractFacility;
import org.schors.eva.FacilityManager;
import org.schors.eva.FacilityStatus;
import org.schors.eva.Waiter;
import org.schors.eva.annotations.Facility;
import org.schors.eva.annotations.Version;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Facility(name = "token", version = @Version(major = 1, minor = 0))
public class TokenManager extends AbstractFacility {

    private Map<String, String> tokens = new ConcurrentHashMap<>();

    public TokenManager(FacilityManager facilityManager) {
        super(facilityManager);
    }

    public static String getName() {
        return TokenManager.class.getAnnotation(Facility.class).name();
    }

    public String makeNewToken(String username) {
        String token = Long.toHexString(System.currentTimeMillis());
        tokens.put(token, username);
        return token;
    }

    public String checkToken(String token) {
        return tokens.get(token);
    }

    @Override
    public void start() {
        status = FacilityStatus.STARTING;
        Waiter<EvaExecutors> item = facilityManager.waitForFacility("executors");
        EvaExecutors evaExecutors = item.get();
        evaExecutors.getScheduler().scheduleAtFixedRate(new ClearTokensTask(), 1, 2, TimeUnit.HOURS);
        status = FacilityStatus.STARTED;
    }

    @Override
    public void stop() {

    }

    public class ClearTokensTask implements Runnable {

        @Override
        public void run() {
            try {
                List<String> toRemove = new ArrayList<>();

                long now = System.currentTimeMillis();
                for (Map.Entry<String, String> entry : tokens.entrySet()) {
                    long itemTime = Long.decode("#".concat(entry.getKey()));
                    if ((now - itemTime) > 1000 * 60 * 60 * 24) toRemove.add(entry.getKey());    //24 hours
                }
                for (String key : toRemove) {
                    tokens.remove(key);
                }
            } catch (Exception e) {

            }
        }
    }

}

