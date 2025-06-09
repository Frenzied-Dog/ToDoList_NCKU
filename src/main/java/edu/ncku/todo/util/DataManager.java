package edu.ncku.todo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.ncku.todo.model.Task;
import edu.ncku.todo.model.Category;
import edu.ncku.todo.model.TaskStatus;

public abstract class DataManager {
    private static List<Category> data = new ArrayList<>();
    private static HashMap<LocalDate, List<Task>> taskMap = new HashMap<>();

    public static int getUnfinishedTaskCount() {
        int count = 0;
        for (Category category : data) {
            for (Task task : category.getTasks()) {
                if (task.getStatus() != TaskStatus.DONE) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public static void initialize(List<Category> categories) {
        if (categories == null) return;
        data = categories;
        for (Category category : data) {
            for (Task task : category.getTasks()) {
                LocalDate dueDate = task.getDueDate();
                if (dueDate == null) {
                    continue; // Skip tasks without due date
                }

                if (!taskMap.containsKey(dueDate)) {
                    taskMap.put(dueDate, new ArrayList<>(Collections.nCopies(1,task)));
                } else if (taskMap.get(dueDate) == null) {
                    taskMap.put(dueDate, new ArrayList<>(Collections.nCopies(1,task)));
                } else {
                    taskMap.get(dueDate).add(task);
                }
            }
        }
    }
    
    public static HashMap<LocalDate, List<Task>> getTaskMap() { return taskMap; }

    public static List<Task> getTasksByDate(LocalDate date) { return taskMap.get(date); }

    public static List<Category> getCategoryData() { return data; }

    public static List<String> getCategoryStrList() { 
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
            // Check if the category name matches the given name
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
        taskMap.entrySet().removeIf(entry -> {
            List<Task> tasks = entry.getValue();
            tasks.removeIf(task -> task.getCategoryName().equals(name));
            return tasks.isEmpty();
        });
    }

    public static void removeCategory(Category category) {
        data.remove(category);
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
        LocalDate dueDate = task.getDueDate();
        if (dueDate != null) {
            if (!taskMap.containsKey(dueDate)) {
                taskMap.put(dueDate, new ArrayList<>());
            } else if (taskMap.get(dueDate) == null) {
                taskMap.put(dueDate, new ArrayList<>());
            } else {
                taskMap.get(dueDate).add(task);
            }
        }
        return true;
    }

    public static boolean addTask(String categoryName, Task task) {
        return addTask(getCategory(categoryName), task);
    }

    public static void removeTask(Category category, Task task) {
        LocalDate dueDate = task.getDueDate();
        if (dueDate != null && taskMap.containsKey(dueDate)) {
            taskMap.get(dueDate).remove(task);
            if (taskMap.get(dueDate).isEmpty()) {
                taskMap.remove(dueDate); // Remove the entry if no tasks left for that date
            }
        }
        category.removeTask(task);
    }

    public static boolean updateTask(Task task, Category newCategory, String newTaskName, LocalDate newDueDate, TaskStatus newStatus) {
        Task tmp = new Task(newTaskName, newCategory.getName(), newDueDate);

        if (!task.equals(tmp) && newCategory.getTasks().contains(tmp)) {
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
        task.setUpdatedAt(LocalDateTime.now());
        return true; // Task updated successfully
    }

}
