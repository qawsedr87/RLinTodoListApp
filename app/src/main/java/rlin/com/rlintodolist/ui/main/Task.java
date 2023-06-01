package rlin.com.rlintodolist.ui.main;

public class Task {
    public final static String HIGH = "high";
    public final static String MEDIUM = "medium"; // default
    public final static String LOW = "low";

    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private long id = 0;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public long getId() {
        return (id);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return String.format("Task: id#{0}, title {1}, priority is {2} at {3}", id, title, priority, dueDate);
    }
}
