package edu.ncku.todo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.paint.Color;

public class Category {
    private static HashMap<String, List<Task>> maps = new HashMap<>();
    
    String name;
    Color color = Color.WHITE;
    List<Task> tasks = new ArrayList<>();

    public static List<String> getCategories() { 
        Set<String> keys = maps.keySet();
        return new ArrayList<>(keys);
    }

    public static List<Task> getTasks(String categoryName) {
        return maps.get(categoryName);
    }

    public static boolean addCategory(String name) {
        if (maps.containsKey(name)) {
            return false; // Category already exists, do nothing
        }
        maps.put(name, new ArrayList<Task>());
        return true; // Category added successfully
    }

    public static void removeCategory(String name) {
        maps.remove(name);
    }

    public static boolean updateCategory(String oldName, String newName) {
        if (maps.containsKey(newName)) {
            return false; // Category already exists, do nothing
        }

        maps.put(newName, maps.get(oldName));
        maps.remove(oldName);
        // Update the category name for all tasks in the category
        for (Task task : maps.get(newName)) {
            task.setCategory(newName);
        }

        return true; // Category updated successfully
    }

    public static boolean addTask(String categoryName, Task task) {
        // Category does not exist, do nothing
        if (!maps.containsKey(categoryName)) {
            return false;
        }
        
        // Check if the task's category name matches the category name
        // just in case, should not happen
        if (task.getCategoryName() != categoryName) {
            removeTask(task);
        }

        // Task already exists in the category, do nothing
        if (maps.get(categoryName).contains(task)) {
            return false; 
        }

        maps.get(categoryName).add(task);
        return true;
    }

    public static void removeTask(Task task) {
        maps.get(task.getCategoryName()).remove(task);
    }

    public static boolean updateTask(Task task, String newCategory, String newTaskName, Date newDueDate, TaskStatus newStatus) {
        // If the category is changed
        if (newCategory != task.getCategoryName()) {
            if (maps.get(newCategory).contains(new Task(newTaskName, newCategory, newDueDate))) {
                return false; // Task already exists in the new category, do nothing
            }
            removeTask(task);
            task.setCategory(newCategory);
            addTask(newCategory, task);
        }


        if (maps.get(newCategory).contains(task)) {
            return false;
        }

        task.setName(newTaskName);
        task.setDueDate(newDueDate);
        task.setStatus(newStatus);
        task.setUpdatedAt(new Date());


        return true; // Task updated successfully
    }

}
