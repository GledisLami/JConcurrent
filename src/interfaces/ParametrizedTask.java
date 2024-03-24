package interfaces;

public interface ParametrizedTask<T> {
    /**
     * Executes the task with the given parameters.
     *
     * @param params the parameters required for task execution
     */
    void execute(T params);
}
