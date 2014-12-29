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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Facility(
        name = "token",
        version = @Version(major = 1, minor = 0),
        dependsOn = {EvaExecutors.class}
)
public class TokenManager extends AbstractFacility {

    private Map<String, String> tokens = new ConcurrentHashMap<>();
    private Configuration configuration;

    public TokenManager(FacilityManager facilityManager) {
        super(facilityManager);
        this.configuration = facilityManager.getConfiguration().getSection(Configuration.class);
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
        System.out.println("TokenManager::start");
        status = FacilityStatus.STARTING;
        facilityManager
                .getFacility(EvaExecutors.class)
                .getScheduler()
                .scheduleAtFixedRate(
                        new ClearTokensTask(),
                        configuration.getClearTaskPeriod(),
                        configuration.getClearTaskPeriod(),
                        TimeUnit.MINUTES);
        status = FacilityStatus.STARTED;
    }

    @Override
    public void stop() {
        System.out.println("TokenManager stop");
        status = FacilityStatus.STOPPED;
    }

    public class ClearTokensTask implements Runnable {

        @Override
        public void run() {
            try {
                List<String> toRemove = new ArrayList<>();

                long now = System.currentTimeMillis();
                for (Map.Entry<String, String> entry : tokens.entrySet()) {
                    long itemTime = Long.decode("#".concat(entry.getKey()));
                    if ((now - itemTime) > 1000 * 60 * configuration.getTokenTTL())
                        toRemove.add(entry.getKey());    //24 hours
                }
                for (String key : toRemove) {
                    tokens.remove(key);
                }
            } catch (Exception e) {

            }
        }
    }

    @XmlRootElement(name = "token-manager")
    @ConfigurationSection
    public class Configuration extends AbstractConfiguration {

        private int clearTaskPeriod;
        private int tokenTTL;

        public int getClearTaskPeriod() {
            return clearTaskPeriod;
        }

        public void setClearTaskPeriod(int clearTaskPeriod) {
            this.clearTaskPeriod = clearTaskPeriod;
        }

        public int getTokenTTL() {
            return tokenTTL;
        }

        public void setTokenTTL(int tokenTTL) {
            this.tokenTTL = tokenTTL;
        }
    }

}

