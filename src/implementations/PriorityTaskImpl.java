package implementations;

import interfaces.PriorityTask;
import utility.PriorityTaskSorter;

import java.util.List;

public class PriorityTaskImpl implements PriorityTask {
    private final Runnable task;
    private final int priority;

    public PriorityTaskImpl(Runnable task, int priority) {
        this.task = task;
        this.priority = priority;
    }



    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void run() {
        task.run();
    }

    public static void runTasks(List<PriorityTask> tasks){
        //Runs a list of PriorityTasks based on their priority
        PriorityTaskSorter.sortByPriority(tasks);
        for (PriorityTask task1: tasks){
            task1.run();
        }
    }

    public static void runTasks(PriorityTask... tasks){
        //Runs a list of PriorityTasks based on their priority
        PriorityTaskSorter.sortByPriority(tasks);
        for (PriorityTask task1: tasks){
            task1.run();
        }
    }
}
