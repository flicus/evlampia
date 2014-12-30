/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
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
import org.schors.eva.Application;
import org.schors.eva.facility.AbstractFacility;
import org.schors.eva.facility.Facility;
import org.schors.eva.facility.FacilityManager;
import org.schors.eva.facility.FacilityStatus;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FacilityManagerImpl implements FacilityManager {
    private static final Logger log = Logger.getLogger(FacilityManagerImpl.class);
    private Map<Class<? extends AbstractFacility>, AbstractFacility> facilities = new ConcurrentHashMap<>();
    private Application application;

    public FacilityManagerImpl(Application application) {
        this.application = application;
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
            Constructor constructor = facilityClass.getConstructor(Application.class);
            facilityObject = facilityClass.cast(constructor.newInstance(application));
        } catch (Exception e) {
            log.error("Unable to instantiate facility: ", e);
            return null;
        }
        facilities.put(facilityClass, facilityObject);
        facilityObject.setStatus(FacilityStatus.RESOLVING);
        if (depends != null && depends.length > 0) {
            Future future = application.getDependencyResolver().resolve(depends);
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
    public void startFacility(final Class<? extends AbstractFacility> facility) {
        final AbstractFacility f = facilities.get(facility);
        if (facility != null &&
                (FacilityStatus.READY.equals(f.getStatus())
                        || FacilityStatus.STOPPED.equals(f.getStatus()))) {
            application.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    f.start();
                    application.getDependencyResolver().facilityResolved(facility);
                }
            });
        }
    }

    @Override
    public void stopFacility(Class<? extends AbstractFacility> facilityClass) {
        final AbstractFacility facility = facilities.get(facilityClass);
        if (facility != null && !FacilityStatus.STOPPED.equals(facility.getStatus())) {
            application.getThreadPool().execute(new Runnable() {
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
}


