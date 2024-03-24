package interfaces;

import java.util.concurrent.Callable;

public interface CallableTask<T> extends Callable<T> {
    T call() throws Exception;
}
