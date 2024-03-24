package implementations;

import interfaces.CallablePeriodicTask;
import interfaces.CallableTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CallableScheduler implements CallablePeriodicTask {
    protected final ScheduledExecutorService scheduler;

    public CallableScheduler() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public CallableScheduler(int numThreads) {
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
    }

    @Override
    public <T> ScheduledFuture<?> schedulePeriodicTask(long initialDelay, long period, TimeUnit unit, CallableTask<T> task) {
        return scheduler.scheduleAtFixedRate(() -> {
            try {
                task.call();
            } catch (Exception e) {
                // Handle exception
                e.printStackTrace();
            }
        }, initialDelay, period, unit);
    }
}
