package Application;

public class ToDo {
    private String task;
    private String description;
    private boolean complete;
    private int priority;

    public ToDo(String task, String description, int priority) {
        this.task = task;
        this.description = description;
        this.priority = priority;
        this.complete = false;
    }
}
