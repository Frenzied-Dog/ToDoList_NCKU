package edu.ncku.todo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ncku.todo.model.Task;
import edu.ncku.todo.model.Category;
import edu.ncku.todo.model.TaskStatus;

public class DataManager {
    private static List<Category> data = new ArrayList<>();

    public static void initialize(List<Category> categories) {
        data = categories;
        data.forEach(c -> {c.initialize();});
    }

    public static List<String> getCategoryList() { 
        List<String> list = new ArrayList<>();
        data.forEach(c -> list.add(c.getName()));
        return list;
    }

    public static Category getCategory(int index) {
        if (index < 0 || index >= data.size()) {
            return null;
        }
        return data.get(index);
    }

    public static Category getCategory(String name) {
        for (Category category : data) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public static boolean addCategory(String name) {
        Category category = new Category(name, null);
        if (data.contains(category)) {
            return false; // Category already exists, do nothing
        }
        data.add(category);
        return true; // Category added successfully
    }

    public static void removeCategory(String name) {
        data.removeIf(category -> category.getName().equals(name));
    }

    public static boolean updateCategory(Category category, String newName) {
        if (data.contains(new Category(newName, null))) {
            return false; // Category already exists, do nothing
        }

        category.setName(newName);
        // Update the category name for all tasks in the category
        for (Task task : category.getTasks()) {
            task.setCategory(newName);
        }

        return true; // Category updated successfully
    }

    public static boolean addTask(Category category, Task task) {
        // Category does not exist, do nothing
        if (category == null) {
            return false;
        }

        // Check if the task's category name matches the category name
        // just in case, should not happen
        if (!category.getName().equals(task.getCategoryName())) {
            removeTask(category, task);
        }
        
        if (category.getTasks().contains(task)) {
            return false; // Task already exists in the category, do nothing
        }
        category.addTask(task);
        return true;
    }

    public static boolean addTask(String categoryName, Task task) {
        return addTask(getCategory(categoryName), task);
    }

    public static void removeTask(Category category, Task task) {
        category.removeTask(task);
    }

    public static boolean updateTask(Task task, Category newCategory, String newTaskName, Date newDueDate, TaskStatus newStatus) {
        if (newCategory.getTasks().contains(new Task(newTaskName, newCategory.getName(), newDueDate))) {
            return false; // Task already exists in the new category, do nothing
        }
        
        // If the category is changed
        if (newCategory.getName() != task.getCategoryName()) {
            removeTask(getCategory(task.getCategoryName()), task);
            task.setCategory(newCategory.getName());
            addTask(newCategory, task);
        }

        task.setName(newTaskName);
        task.setDueDate(newDueDate);
        task.setStatus(newStatus);
        task.setUpdatedAt(new Date());
        return true; // Task updated successfully
    }

}
