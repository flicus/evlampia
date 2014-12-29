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

package org.schors.eva.core;

import org.apache.log4j.Logger;
import org.schors.eva.AbstractFacility;
import org.schors.eva.ConfigurationManager;
import org.schors.eva.FacilityManager;
import org.schors.eva.FacilityStatus;
import org.schors.eva.annotations.Facility;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class FacilityManagerImpl implements FacilityManager {
    private static final Logger log = Logger.getLogger(FacilityManagerImpl.class);
    private Map<Class, AbstractFacility> facilities = new ConcurrentHashMap<>();
    private Map<Class, List<DependencyListener>> listeners = new ConcurrentHashMap<>();
    private ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
    private ConfigurationManager configuration;

    public FacilityManagerImpl(ConfigurationManager configuration) {
        this.configuration = configuration;
    }

    @Override
    public void stop() {
        if (log.isDebugEnabled()) {
            log.debug("stop");
        }
        for (AbstractFacility facility : facilities.values()) {
            try {
                facility.stop();
            } catch (Exception e) {
                log.warn("Unable to stop facility", e);
            }
        }
    }

    @Override
    public ConfigurationManager getConfigurationManager() {
        return configuration;
    }

    @Override
    public String registerFacility(Class<? extends AbstractFacility> facilityClass) {
        if (log.isDebugEnabled()) {
            log.debug("registerFacility::" + facilityClass);
        }
        String name = null;
        Class<? extends AbstractFacility>[] depends = null;
        if (facilityClass.isAnnotationPresent(Facility.class)) {
            name = facilityClass.getAnnotation(Facility.class).name();
            depends = facilityClass.getAnnotation(Facility.class).dependsOn();
        }
        if (name == null) {
            log.error("Cannot find Name of the facility: " + facilityClass);
            return null;
        }
        AbstractFacility facilityObject = null;
        try {
            Constructor constructor = facilityClass.getConstructor(FacilityManager.class);
            facilityObject = facilityClass.cast(constructor.newInstance(this));
        } catch (Exception e) {
            log.error("Unable to instantiate facility: ", e);
            return null;
        }
        facilities.put(facilityClass, facilityObject);
        facilityObject.setStatus(FacilityStatus.RESOLVING);
        if (depends != null && depends.length > 0) {
            Future future = pool.submit(new DependencyResolver(depends, this));
            try {
                future.get();
                if (log.isDebugEnabled()) {
                    log.debug("Resolved dependencies: " + depends);
                }
            } catch (InterruptedException e) {
                log.error(e, e);
                return null;
            } catch (ExecutionException e) {
                log.error(e, e);
                return null;
            }
        }
        facilityObject.setStatus(FacilityStatus.READY);
        return name;
    }

    @Override
    public void startFacility(final Class<? extends AbstractFacility> facilityClass) {
        final AbstractFacility facility = facilities.get(facilityClass);
        if (facility != null &&
                (FacilityStatus.READY.equals(facility.getStatus())
                        || FacilityStatus.STOPPED.equals(facility.getStatus()))) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    facility.start();
                    facilityResolved(facilityClass);
                }
            });
        }
    }

    private void facilityResolved(Class<? extends AbstractFacility> facility) {
        List<DependencyListener> list = listeners.remove(facility);
        if (list != null && list.size() > 0) {
            for (DependencyListener listener : list) {
                listener.onResolve(facility);
            }
        }
    }

    @Override
    public void stopFacility(Class<? extends AbstractFacility> facilityClass) {
        final AbstractFacility facility = facilities.get(facilityClass);
        if (facility != null && !FacilityStatus.STOPPED.equals(facility.getStatus())) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    facility.stop();
                }
            });
        }
    }

    @Override
    public <T extends AbstractFacility> T getFacility(Class<T> type) {
        return type.cast(facilities.get(type));
    }

    @Override
    public <T extends AbstractFacility> T getFacilityForUsing(Class<T> type) {
        T facility = type.cast(facilities.get(type));
        return (facility != null && FacilityStatus.STARTED.equals(facility.getStatus())) ? facility : null;
    }

    public Map<Class, List<DependencyListener>> getListeners() {
        return listeners;
    }
}


