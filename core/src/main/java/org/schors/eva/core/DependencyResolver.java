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

import org.schors.eva.AbstractFacility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DependencyResolver implements Runnable {

    private List<Class<? extends AbstractFacility>> dependencies = new CopyOnWriteArrayList<>();

    public DependencyResolver(Class<? extends AbstractFacility>[] depends, FacilityManagerImpl facilityManager) {
        for (Class<? extends AbstractFacility> dep : depends) {
            dependencies.add(dep);
            List<DependencyListener> list = facilityManager.getListeners().get(dep);
            if (list == null) {
                list = new ArrayList<>();
                facilityManager.getListeners().put(dep, list);
            }
            list.add(new DependencyListener() {
                @Override
                public void onResolve(Class<? extends AbstractFacility> facility) {
                    dependencies.remove(facility);
                }
            });
        }
    }

    @Override
    public void run() {
        boolean done = false;
        while (!done) {
            try {
                done = dependencies.isEmpty();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                done = true;
            }
        }
    }
}
