package implementations;

import interfaces.DependencyTask;

import java.util.ArrayList;
import java.util.List;

public class SequentialTask implements DependencyTask {
    private final List<DependencyTask> dependencies;
    private int completedDependencies;
    private boolean isCompleted;
    private final Runnable task;

    public SequentialTask(Runnable task) {
        this.dependencies = new ArrayList<>();
        this.completedDependencies = 0;
        this.isCompleted = false;
        this.task = task;
    }

    @Override
    public void addDependency(DependencyTask dependency) {
        dependencies.add(dependency);
    }

    @Override
    public synchronized void notifyCompletion(){
        completedDependencies++;
        if (completedDependencies == dependencies.size()){
            isCompleted = true;
            notify(); // Notify any waiting threads
        }
    }

    @Override
    public void run() {
        synchronized (this) {
            while (!isCompleted){
                try {
                    wait();
                    /*
                    Inside the while loop, the wait() method is called.
                    This causes the current thread to wait until another thread calls notify()
                     or notifyAll() on the same object (this). In this case,
                     it waits until the notifyCompletion() method is called by all dependencies,
                     indicating that they have completed.
                     */
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        task.run();
        notifyCompletion();
    }

}
