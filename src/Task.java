import java.time.LocalDate;

public class Task {

    private int taskID;
    private String taskText;
    private String taskDueDate;
    private String taskImportance;
    private String taskStatus;

    private int userID;

    public int getUserID() {
        return userID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
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
