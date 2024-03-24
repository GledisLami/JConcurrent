package implementations;

import interfaces.CallableTask;
import interfaces.PeriodicTask;
import interfaces.Task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PeriodicTaskScheduler implements PeriodicTask {
    private final ScheduledExecutorService scheduler;

    public PeriodicTaskScheduler() {
        // Create a ScheduledExecutorService with 1 thread
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public PeriodicTaskScheduler(int numThreads) {
        // Create a ScheduledExecutorService with the specified number of threads
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
    }

    @Override
    public ScheduledFuture<?> schedulePeriodicTask(long initialDelay, long period, TimeUnit unit, Task task) {
        return scheduler.scheduleAtFixedRate(() -> {
            task.run();
        }, initialDelay, period, unit);
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

    public void shutdown(){
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)){
                // Scheduler did not terminate in time, force shutdown
                scheduler.shutdown();
            }
        }catch (InterruptedException e){
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
