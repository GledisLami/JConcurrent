import interfaces.Task;

public class TaskExecutor {
    private final ThreadPool threadPool;

    public TaskExecutor(int numThreads) {
        this.threadPool = new ThreadPool(numThreads);
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
}
