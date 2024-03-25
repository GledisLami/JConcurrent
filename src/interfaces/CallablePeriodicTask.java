package interfaces;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface CallablePeriodicTask{
    <T> ScheduledFuture<?> schedulePeriodicTask(long initialDelay, long period, TimeUnit unit, Callable<T> task);

}
