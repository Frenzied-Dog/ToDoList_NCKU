package edu.ncku.todo.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String name;
    private Date dueDate;
    private Date createdAt;
    private Date updatedAt;
    private TaskStatus status;
    private String category;

    public Task(String name, Date dueDate, TaskStatus status, String category) {
        this.name = name;
        this.dueDate = dueDate;
        this.status = status;
        this.category = category;
        this.createdAt = new Date(); // Set createdAt to current date
        this.updatedAt = new Date(); // Set updatedAt to current date
    }

    public String getName() { return name; }
    public Date getDueDate() { return dueDate; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public TaskStatus getStatus() { return status; }
    public String getCategoryName() { return category; }

    public void setName(String name) { this.name = name; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public void setCategory(String category) { this.category = category; }


    
    public String toString() {
        // TODO: 臨時方案，待更改
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return name + " " + format.format(dueDate) + " " + status + " " + category;
    }
}
