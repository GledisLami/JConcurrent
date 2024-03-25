package executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class CallableThreadPool<T> {
    private final int numThreads;
    private final ExecutorService executor;
    private final BlockingQueue<Callable<T>> taskQueue;
    private final BlockingQueue<T> resultsQueue;
    private volatile boolean running;

    protected CallableThreadPool(int numThreads){
        this.numThreads = numThreads;
        executor = Executors.newFixedThreadPool(this.numThreads);
        taskQueue = new LinkedBlockingQueue<>();
        resultsQueue = new LinkedBlockingQueue<>();
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
                Callable<T> task = taskQueue.take();
                T result = task.call();  
                resultsQueue.put(result);                 
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void submitTask(Callable<T> task){
        try {
            taskQueue.put(task);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public T getResult() throws InterruptedException{
        return resultsQueue.take();
    }

    public void shutdown(){
        running = false;
        executor.shutdown();
    }

}
