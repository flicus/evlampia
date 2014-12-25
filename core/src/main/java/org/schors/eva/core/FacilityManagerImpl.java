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

package org.schors.eva.core;

import org.schors.eva.AbstractFacility;
import org.schors.eva.FacilityManager;
import org.schors.eva.Waiter;
import org.schors.eva.annotations.Facility;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FacilityManagerImpl<A extends AbstractFacility> implements FacilityManager<A> {

    private Map<String, A> facilities = new ConcurrentHashMap<>();
    private Map<String, WaiterImp<A>> waiters = new ConcurrentHashMap<>();
    private Executor pool = Executors.newCachedThreadPool();

    @Override
    public void addNewFacility(Class<?> facility) {
        System.out.println("FacilityManagerImpl::addNewFacility::" + facility);
        boolean ok = true;
        String name = null;
        if (facility.isAnnotationPresent(Facility.class)) {
            name = facility.getAnnotation(Facility.class).name();
        }
        if (name == null) {
            ok = false;
        }

        A object = null;
        try {
            Constructor constructor = facility.getConstructor(FacilityManager.class);
            object = (A) constructor.newInstance(this);
        } catch (Exception e) {
            ok = false;
        }
        if (ok) {
            final A theSameObject = object;
            final String fname = name;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    theSameObject.start();
                    facilities.put(fname, theSameObject);
                    WaiterImp<A> future = waiters.remove(fname);
                    if (future != null) {
                        System.out.println("FacilityManagerImpl::waiterDone::" + fname);
                        future.done(theSameObject);
                    }
                }
            });
            System.out.println("FacilityManagerImpl::addNewFacility::done::" + object);
        }
    }

    public void stop() {
        for (AbstractFacility facility : facilities.values()) {
            try {
                facility.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public A getFacility(String name) {
        return facilities.get(name);
    }

    @Override
    public Waiter<A> waitForFacility(String name) {
        if (facilities.containsKey(name)) {
            System.out.println("FacilityManagerImpl::waitForFacility:: Already here:: " + name);
            return new WaiterImp(facilities.get(name), name);
        } else {
            System.out.println("FacilityManagerImpl::waitForFacility:: Start waiting for facility: " + name);
            WaiterImp waiter = new WaiterImp(name);
            waiters.put(name, waiter);
            return waiter;
        }
    }

    public class WaiterImp<A> implements Waiter<A> {

        private A item;
        private String what;

        public WaiterImp(String what) {
            this.what = what;
        }

        public WaiterImp(A item, String what) {
            this.item = item;
            this.what = what;
        }

        @Override
        public A get() {
            if (item != null) {
                return item;
            }
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean done = false;
                    while (!done) {
                        System.out.println("Waiter:: waiting for:: " + what);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            done = true;
                        }
                        if (item != null) {
                            done = true;
                            System.out.println("Waiter:: got it:: " + item);
                        }
                    }
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return item;
        }

        public void done(A item) {
            this.item = item;
        }
    }

}


