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
import org.schors.eva.WaiterHandler;
import org.schors.eva.annotations.Facility;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FacilityManagerImpl<A extends AbstractFacility> implements FacilityManager<A> {

    private Map<String, A> facilities = new ConcurrentHashMap<>();
    private Map<String, WaiterHandler<A>> waiters = new ConcurrentHashMap<>();

    @Override
    public void addNewFacility(Class<?> facility) {
        System.out.println("addNewFacility:: " + facility);
        boolean ok = true;
        String name = null;
        FacilityAdapter facilityAdapter = null;
        if (facility.isAnnotationPresent(Facility.class)) {
            name = facility.getAnnotation(Facility.class).name();
        }
        if (name == null) {
            ok = false;
        }

        A o = null;
        try {
            Constructor constructor = facility.getConstructor(FacilityManager.class);
            o = (A) constructor.newInstance(this);
            o.start();
        } catch (Exception e) {
            ok = false;
        }

        if (ok) {
            System.out.println("addNewFacility:: done:: " + facilityAdapter);
            facilities.put(name, o);
        }
        WaiterHandler<A> future = waiters.remove(name);
        if (future != null) {
            future
        }
    }

    public void stop() {
        for (FacilityAdapter adapter : facilities.values()) {
            try {
                adapter.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public A getFacility(String name) {
        return facilities.get(name).getFacility();
    }

    @Override
    public Waiter<A> waitForFacility(String name) {
        if (facilities.containsKey(name)) {
            return new WaiterHandlerImpl.WaiterImp(facilities.get(name));
        }
        return null;
    }

    public class WaiterHandlerImpl<A> implements WaiterHandler<A> {

        private WaiterImp<A> item = new WaiterImp();

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public void setItem(A item) {
            this.item.item = item;
        }

        public class WaiterImp<A> implements Waiter<A> {

            private A item;

            public WaiterImp() {
            }

            public WaiterImp(A item) {
                this.item = item;
            }

            @Override
            public A get() {
                return null;
            }
        }
    }

}


