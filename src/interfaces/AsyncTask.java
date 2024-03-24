package interfaces;

import java.util.concurrent.CompletableFuture;

public interface AsyncTask<T> {
    /**
     * Executes the asynchronous task and returns a CompletableFuture representing
     * the result of the task.
     *
     * @return a CompletableFuture representing the result of the task
     */
    CompletableFuture<T> executeAsync();
}
