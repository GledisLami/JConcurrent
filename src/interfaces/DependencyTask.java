package interfaces;

public interface DependencyTask extends Task{
    void addDependency(DependencyTask dependency);
    void notifyCompletion();
}
