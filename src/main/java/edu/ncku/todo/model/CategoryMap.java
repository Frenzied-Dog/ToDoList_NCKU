package edu.ncku.todo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class CategoryMap {
    private static HashMap<String, List<Task>> maps = new HashMap<>();

    public static void addCategory(String name) {
        maps.put(name, new ArrayList<>());
    }

    public static void removeCategory(String name) {
        maps.remove(name);
    }

    public static void updateCategory(String oldName, String newName) {
        maps.put(newName, maps.get(oldName));
        maps.remove(oldName);
    }

    public static List<Task> getCategory(String categoryName) {
        if (maps.containsKey(categoryName)) {
            return maps.get(categoryName);
        } else {
            return null;
        }
    }

    public static void addTask(String categoryName, Task task) {
        maps.get(categoryName).add(task);
    }

    public static void removeTask(String categoryName, int index) {
        maps.get(categoryName).remove(index);
    }

    public static void updateTask(String categoryName, int index, String newCategory, String newTaskName, Date newDueDate, TaskStatus newStatus) {
        Task task = maps.get(categoryName).get(index);
        task.setName(newTaskName);
        task.setDueDate(newDueDate);
        task.setStatus(newStatus);
        task.setUpdatedAt(new Date()); // Update the updatedAt field

        if (newCategory != null) { // If the category is changed
            task.setCategory(newCategory);
            addTask(newCategory, task);
            removeTask(categoryName, index);
        }
    }

}
