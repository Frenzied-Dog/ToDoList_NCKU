package edu.ncku.todo.model;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncku.todo.util.Lang;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
    private final ObjectProperty<Date> dueDate = new SimpleObjectProperty<>(null);
    private final ObjectProperty<TaskStatus> status = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Date> createdAt = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Date> updatedAt = new SimpleObjectProperty<>(null);


    public Task(String name, String category, Date dueDate) {
        this.name.set(name);
        this.category.set(category);
        this.dueDate.set(dueDate);
        this.status.set(TaskStatus.TODO);
        this.createdAt.set(new Date());
        this.updatedAt.set(new Date());
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

    // Property getters
    public StringProperty nameProperty() { return name; }

    public StringProperty categoryProperty() { return category; }

    public ObjectProperty<Date> dueDateProperty() { return dueDate; }

    public ObjectProperty<TaskStatus> statusProperty() { return status; }

    public ObjectProperty<Date> createdAtProperty() { return createdAt; }

    public ObjectProperty<Date> updatedAtProperty() { return updatedAt; }

    // Normal getters/setters for convenience
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }

    public String getCategoryName() { return category.get(); }
    public void setCategory(String value) { category.set(value); }

    public Date getDueDate() { return dueDate.get(); }
    public void setDueDate(Date value) { dueDate.set(value); }

    public TaskStatus getStatus() { return status.get(); }
    public void setStatus(TaskStatus value) { status.set(value); }

    public Date getCreatedAt() { return createdAt.get(); }
    public Date getUpdatedAt() { return updatedAt.get(); }
    public void setUpdatedAt(Date value) { updatedAt.set(value); }

    public String toString() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (dueDate.get() == null) {
            return name.get() + " (" + status.get().toString() + ")";
        }
        return name.get() + " (" + status.get().toString() + ") " + Lang.get("ui.due") + format.format(dueDate.get());
    }
}
