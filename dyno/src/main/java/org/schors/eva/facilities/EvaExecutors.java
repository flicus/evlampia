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
import org.schors.eva.annotations.Facility;
import org.schors.eva.annotations.Version;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Facility(name = "executors", version = @Version(major = 1, minor = 0))
public class EvaExecutors extends AbstractFacility {

    private ScheduledExecutorService scheduler;
    private ExecutorService executor;

    public EvaExecutors(FacilityManager facilityManager) {
        super(facilityManager);
    }

    public static String getName() {
        return EvaExecutors.class.getAnnotation(Facility.class).name();
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void start() {
        System.out.println("EvaExecutors:: start");
        status = FacilityStatus.STARTING;
        scheduler = Executors.newScheduledThreadPool(3);
        executor = Executors.newCachedThreadPool();
        status = FacilityStatus.STARTED;
    }

    @Override
    public void stop() {
        System.out.println("EvaExecutors:: stop");
        status = FacilityStatus.STOPPING;
        scheduler.shutdown();
        executor.shutdown();
        status = FacilityStatus.STOPPED;
    }

}
