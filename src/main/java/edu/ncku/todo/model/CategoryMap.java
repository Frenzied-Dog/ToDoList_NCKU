package edu.ncku.todo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class CategoryMap {
    private static HashMap<String, List<Task>> maps = new HashMap<>();

    public static boolean addCategory(String name) {
        if (maps.containsKey(name)) {
            return false; // Category already exists, do nothing
        }

        maps.put(name, new ArrayList<>());
        return true; // Category added successfully
    }

    public static void removeCategory(String name) {
        maps.remove(name);
    }

    public static void updateCategory(String oldName, String newName) {
        maps.put(newName, maps.get(oldName));
        maps.remove(oldName);
    }

    // 發現好像用不到
    // public static List<Task> getCategory(String categoryName) {
    //     if (maps.containsKey(categoryName)) {
    //         return maps.get(categoryName);
    //     } else {
    //         return null;
    //     }
    // }

    public static boolean addTask(String categoryName, Task task) {
        // Category does not exist, do nothing
        if (!maps.containsKey(categoryName)) {
            return false;
        }
        
        // Check if the task already exists in the category
        // just in case, should not happen
        if (task.getCategoryName() != categoryName) {
            removeTask(task.getCategoryName(), maps.get(task.getCategoryName()).indexOf(task));
        }

        // Task already exists in the category, do nothing
        if (maps.get(categoryName).contains(task)) {
            return false; 
        }

        maps.get(categoryName).add(task);
        return true;
    }

    public static void removeTask(String categoryName, int index) {
        maps.get(categoryName).remove(index);
    }

    public static void updateTask(String categoryName, int index, String newCategory, String newTaskName, Date newDueDate, TaskStatus newStatus) {
        Task task = maps.get(categoryName).get(index);
        task.setName(newTaskName);
        task.setDueDate(newDueDate);
        task.setStatus(newStatus);
        task.setUpdatedAt(new Date());

        // If the category is changed
        if (newCategory != null) { 
            task.setCategory(newCategory);
            addTask(newCategory, task);
            removeTask(categoryName, index);
        }
    }

}
