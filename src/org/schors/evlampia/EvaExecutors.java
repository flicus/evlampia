package org.schors.evlampia;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ExecutorService;

public class EvaExecutors {

    private ScheduledExecutorService scheduler;
    private ExecutorService executor;

    public EvaExecutors() {

        scheduler = Executors.newScheduledThreadPool(3);
        executor = Executors.newCachedThreadPool();
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    private static class Singleton {
        public static EvaExecutors instance = new EvaExecutors();
    }

    public static EvaExecutors getInstance() {
        return Singleton.instance;
    }
}
