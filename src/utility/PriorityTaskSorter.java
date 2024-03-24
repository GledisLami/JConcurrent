package utility;

import interfaces.PriorityTask;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriorityTaskSorter {
    public static void sortByPriority(List<PriorityTask> tasks) {
        // Define a custom Comparator to compare PriorityTask objects based on their priorities
        Comparator<PriorityTask> priorityComparator = (task1, task2) -> task2.getPriority() - task1.getPriority();

        // Sort the list of tasks using the custom Comparator
        Collections.sort(tasks, priorityComparator);
    }
}
