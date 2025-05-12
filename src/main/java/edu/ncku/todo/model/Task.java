package edu.ncku.todo.model;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncku.todo.util.Lang;

public class Task {
    private String name;
    private String category;
    private Date dueDate;
    private TaskStatus status;
    private Date createdAt;
    private Date updatedAt;

    public Task(String name, String category, Date dueDate) {
        this.name = name;
        this.category = category;
        this.dueDate = dueDate;
        this.status = TaskStatus.TODO;
        this.createdAt = new Date(); // Set createdAt to current date
        this.updatedAt = new Date(); // Set updatedAt to current date
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            Task other = (Task) obj;
            if (this.dueDate == null && other.dueDate == null) {
                return this.name.equals(other.name) && this.category.equals(other.category);
            } else if (this.dueDate == null || other.dueDate == null) {
                return false;
            }
            return this.name.equals(other.name) && this.category.equals(other.category) && this.dueDate.equals(other.dueDate);
        }

        return super.equals(obj);
    }

    public static Date parseDate(String dateString) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
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
        // TODO: 臨時方案，可以更改 (?
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (dueDate == null) {
            return name + " (" + Lang.get("status." + status.toString()) + ")";
        }
        return name + " (" + Lang.get("status."+status.toString()) + ") " + Lang.get("ui.due") + format.format(dueDate);
    }
}
