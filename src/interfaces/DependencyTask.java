package interfaces;

public interface DependencyTask extends Runnable{
    void addDependency(DependencyTask dependency);
    void notifyCompletion();
}
