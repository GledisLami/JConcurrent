package executors;

public class TaskExecutor {
    /*
     * TaskExecutor class that instatiates a ThreadPool object with a specific or default number of threads
     * Works as an abstraction of the ThreadPool, to keep concerns separate
     * Can submit a Task or an array of Tasks
     */
    private final ThreadPool threadPool;

    public TaskExecutor(int numThreads) {
        this.threadPool = new ThreadPool(numThreads);
    }

    public TaskExecutor() {
        this.threadPool = new ThreadPool(1);
    }

    public void submitTask(Runnable task) {
        try {
            threadPool.submitTask(task);
        } catch (Exception e){
            handleTaskError(task, e);
        }
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    private void handleTaskError(Runnable task, Exception e){
        System.err.println("Error occurred while executing task: " + task);
        e.printStackTrace();
    }

    public void submitTasks(Runnable... tasks){
        for (Runnable task: tasks){
            submitTask(task);
        }
    }
}
