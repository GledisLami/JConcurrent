package implementations;

import interfaces.CallablePeriodicTask;
import interfaces.CallableTask;
import interfaces.PeriodicTask;
import interfaces.Task;

import java.util.concurrent.*;

public class TaskScheduler implements PeriodicTask{
    protected final ScheduledExecutorService scheduler;

    public TaskScheduler() {
        // Create a ScheduledExecutorService with 1 thread
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public TaskScheduler(int numThreads) {
        // Create a ScheduledExecutorService with the specified number of threads
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
    }

    @Override
    public ScheduledFuture<?> schedulePeriodicTask(long initialDelay, long period, TimeUnit unit, Task task) {
        return scheduler.scheduleAtFixedRate(() -> {
            task.run();
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
