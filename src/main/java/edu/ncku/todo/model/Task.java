package edu.ncku.todo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

import edu.ncku.todo.util.Lang;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy-M-d");

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
    private final ObjectProperty<LocalDate> dueDate = new SimpleObjectProperty<>(null);
    private final ObjectProperty<TaskStatus> status = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalDateTime> updatedAt = new SimpleObjectProperty<>(null);

    public Task(String name, String category, LocalDate dueDate) {
        this.name.set(name);
        this.category.set(category);
        this.dueDate.set(dueDate);
        this.status.set(TaskStatus.TODO);
        this.createdAt.set(LocalDateTime.now());
        this.updatedAt.set(LocalDateTime.now());
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
            return this.name.equals(other.name) && this.category.equals(other.category)
                    && this.dueDate.equals(other.dueDate);
        }

        return super.equals(obj);
    }

    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, dateParser);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Property getters
    public StringProperty nameProperty() { return name; }

    public StringProperty categoryProperty() { return category; }

    public ObjectProperty<LocalDate> dueDateProperty() { return dueDate; }

    public ObjectProperty<TaskStatus> statusProperty() { return status; }

    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }

    public ObjectProperty<LocalDateTime> updatedAtProperty() { return updatedAt; }

    // Normal getters/setters for convenience
    public String getName() { return name.get(); }

    public void setName(String value) { name.set(value); }

    public String getCategoryName() { return category.get(); }

    public void setCategory(String value) { category.set(value); }

    public LocalDate getDueDate() { return dueDate.get(); }

    public void setDueDate(LocalDate value) { dueDate.set(value); }

    public TaskStatus getStatus() { return status.get(); }

    public void setStatus(TaskStatus value) { status.set(value); }

    public LocalDateTime getCreatedAt() { return createdAt.get(); }

    public LocalDateTime getUpdatedAt() { return updatedAt.get(); }

    public void setUpdatedAt(LocalDateTime value) { updatedAt.set(value); }

    public String toString() {
        if (dueDate.get() == null) {
            return name.get() + " (" + status.get().toString() + ")";
        }
        return name.get() + " (" + status.get().toString() + ") " + Lang.get("ui.due") + dueDate.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
