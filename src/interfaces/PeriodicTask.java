package interfaces;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface PeriodicTask {
    /**
     * Schedules the task to execute periodically with the given initial delay, period,
     * and task logic.
     *
     * @param initialDelay the initial delay before the first execution
     * @param period the period between successive executions
     * @param unit the time unit of the initial delay and period
     * @param task the logic of the task to execute
     * @return a ScheduledFuture representing the periodic task
     */
    ScheduledFuture<?> schedulePeriodicTask(long initialDelay, long period, TimeUnit unit, Runnable task);

}
