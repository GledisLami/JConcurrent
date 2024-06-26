package executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final int numThreads;
    private final ExecutorService executor;
    private final BlockingQueue<Runnable> taskQueue;
    private volatile boolean running;

    protected ThreadPool(int numThreads){
        this.numThreads = numThreads;
        executor = Executors.newFixedThreadPool(this.numThreads);
        taskQueue = new LinkedBlockingQueue<>();
        running = true;
        // Start worker threads
        for (int i = 0; i < numThreads; i++){
            executor.execute(this::workerLoop);
        }
    }

    private void workerLoop(){
        while (running){
            try {
                /*
                * taskQueue.take() calls until a task becomes available in the queue.
                *  Once a task is available, the thread will retrieve it from the queue
                * and execute it. This ensures that worker threads are only active when
                * there are tasks to be executed, preventing them from blocking indefinitely
                *  when the queue is empty
                *
                 */
                Runnable task = taskQueue.take();
                
                try{
                    task.run();    
                } catch(RuntimeException e){
                    handleException(e);
                }
                
            }
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void handleException(Exception e){
        e.printStackTrace();
    }

    public void submitTask(Runnable task){
        try {
            taskQueue.put(task);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown(){
        running = false;
        executor.shutdown();
    }

}
