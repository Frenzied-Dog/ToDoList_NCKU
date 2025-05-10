package edu.ncku.todo.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Category {
    private String name;
    private Color color;
    private List<Task> tasks;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category) {
            Category other = (Category) obj;
            return this.name.equals(other.name);
        }
        return super.equals(obj);
    }

    public Category(String name, Color color) {
        this.name = name;
        this.color = (color != null ? color : Color.WHITE);
        tasks = new ArrayList<>();
    }

    // for loading from file
    public void initialize() {
        tasks = new ArrayList<>();
    }

    public String getName() { return name; }
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    public void setName(String name) { this.name = name; }
    public List<Task> getTasks() { return tasks; }
    public void addTask(Task task) { tasks.add(task); }
    public void removeTask(Task task) { tasks.remove(task); }
}
