package Application;

public class ToDo {
    private String task;
    private String description;
    private boolean complete;
    private int priority;

    public ToDo(String task, String description, int priority) {
        this.task = task;
        this.description = description;
        if (priority > 0 || priority < 5) {
            this.priority = priority;
        } else {
            System.out.println("Please enter a valid priority value");
        }
        this.complete = false;
    }

    public String getTask() {
        return task;
    }
    public String getDescription() {
        return description;
    }
    public int getPriority() {
        return priority;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        if (complete == true) {
            this.complete = complete;
        } else {
            System.out.println("Please enter a valid value");
        }
    }

    public void editTask(String description, int priority, boolean complete) {
        this.description = description;
        this.priority = priority;
        this.complete = complete;
    }
}
