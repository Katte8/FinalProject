import java.time.LocalDate;

public class Task {

    private int taskID;
    private String taskText;
    private LocalDate taskDueDate;
    private String taskImportance;
    private String taskStatus;

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getTaskImportance() {
        return taskImportance;
    }

    public void setTaskImportance(String taskImportance) {
        this.taskImportance = taskImportance;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
