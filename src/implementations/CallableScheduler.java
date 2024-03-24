package implementations;

import interfaces.CallablePeriodicTask;
import interfaces.CallableTask;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CallableScheduler extends TaskScheduler implements CallablePeriodicTask {
    @Override
    public <T> ScheduledFuture<?> schedulePeriodicTask(long initialDelay, long period, TimeUnit unit, CallableTask<T> task) {
        return super.scheduler.scheduleAtFixedRate(() -> {
            try {
                task.call();
            } catch (Exception e) {
                // Handle exception
                e.printStackTrace();
            }
        }, initialDelay, period, unit);
    }
}
