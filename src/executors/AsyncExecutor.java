package executors;

import java.util.concurrent.CompletableFuture;

import interfaces.AsyncTask;

public class AsyncExecutor {
    public <T> CompletableFuture<T> executeAsync(AsyncTask<T> asyncTask) {
        CompletableFuture<T> future = new CompletableFuture<>();
        CompletableFuture<T> taskFuture = asyncTask.executeAsync();

        // Handle task completion
        taskFuture.whenComplete((result, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable); // Propagate exception
            } else {
                future.complete(result); // Propagate result
            }
        });

        return future;
    }

    public void cancelAsync(CompletableFuture<?> future) {
        future.cancel(true); // Cancel the task if possible
    }
}
