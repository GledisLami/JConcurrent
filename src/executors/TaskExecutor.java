package executors;

import interfaces.AsyncTask;
import interfaces.Task;

import java.util.concurrent.CompletableFuture;

public class TaskExecutor {
    private final ThreadPool threadPool;

    public TaskExecutor(int numThreads) {
        this.threadPool = new ThreadPool(numThreads);
    }

    public TaskExecutor() {
        this.threadPool = new ThreadPool(1);
    }

    public void submitTask(Task task) {
        try {
            threadPool.submitTask(task);
        } catch (Exception e){
            handleTaskError(task, e);
        }
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    private void handleTaskError(Task task, Exception e){
        System.err.println("Error occurred while executing task: " + task);
        e.printStackTrace();
    }

    public void submitTasks(Task... tasks){
        for (Task task: tasks){
            submitTask(task);
        }
    }

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
