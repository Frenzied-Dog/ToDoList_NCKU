package edu.ncku.todo.ui;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

import edu.ncku.todo.model.Config;
import edu.ncku.todo.model.Task;
import edu.ncku.todo.model.TaskStatus;
import edu.ncku.todo.model.Category;
import edu.ncku.todo.util.Lang;
import edu.ncku.todo.util.DataManager;

public class ConsoleUI {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("CLI mode");

        // main menu
        while (true) {
            // clear the console and print the menu
            printTitle("ui.welcome");
            System.out.printf(Lang.get("ui.hintTaskCount") + "\n", DataManager.getUnfinishedTaskCount());
            System.out.println("=========================================");
            System.out.println("1. " + Lang.get("ui.add"));
            System.out.println("2. " + Lang.get("ui.listTasks"));
            System.out.println("3. " + Lang.get("ui.modify"));
            System.out.println("4. " + Lang.get("ui.delete"));
            System.out.println("5. " + Lang.get("ui.help"));
            System.out.println("6. " + Lang.get("ui.setting"));
            System.out.println("7. " + Lang.get("ui.exit"));

            int choice = getChoice(7, "ui.choice", false);

            switch (choice) {
            case 0:
                break;
            case 1:
                // Add task / category
                printTitle("ui.add");
                System.out.println("0. " + Lang.get("ui.back"));
                System.out.println("1. " + Lang.get("ui.addCategory"));
                System.out.println("2. " + Lang.get("ui.addTask"));
                choice = getChoice(2, "ui.choice", false);

                switch (choice) {
                case 0:
                    break;
                case 1: // Add category
                    addCategory();
                    break;
                case 2: // Add task
                    addTask();
                    break;
                default: // should not happen
                    System.out.println(Lang.get("notify.invalidChoice"));
                    break;
                }
                break;
            case 2:
                // List tasks
                printTitle("ui.listTasks");
                listTasks();
                System.out.println(Lang.get("ui.pressEnter"));
                scanner.nextLine(); // wait for user input
                break;
            case 3:
                // Modify tasks / category
                printTitle("ui.modify");
                System.out.println("0. " + Lang.get("ui.back"));
                System.out.println("1. " + Lang.get("ui.modifyCategory"));
                System.out.println("2. " + Lang.get("ui.modifyTask"));

                choice = getChoice(2, "ui.choice", false);

                switch (choice) {
                case 0:
                    break;
                case 1: // Modify category
                    modifyCategory();
                    break;
                case 2: // Modify task
                    modifyTask();
                    break;
                default: // should not happen
                    System.out.println(Lang.get("notify.invalidChoice"));
                    break;
                }

                break;
            case 4:
                // Delete tasks / category
                printTitle("ui.delete");
                System.out.println("0. " + Lang.get("ui.back"));
                System.out.println("1. " + Lang.get("ui.deleteCategory"));
                System.out.println("2. " + Lang.get("ui.deleteTask"));

                choice = getChoice(2, "ui.choice", false);

                switch (choice) {
                case 0:
                    break;
                case 1: // Delete category
                    deleteCategory();
                    break;
                case 2: // Delete task
                    deleteTask();
                    break;
                default: // should not happen
                    System.out.println(Lang.get("notify.invalidChoice"));
                    break;
                }

                break;
            case 5:
                // Help text
                printTitle("ui.help");
                System.out.println(Lang.get("ui.helpContent"));
                System.out.println(Lang.get("ui.helpContent2"));
                System.out.println(Lang.get("ui.helpContent3"));
                System.out.println(Lang.get("ui.credit"));
                System.out.println(Lang.get("ui.pressEnter"));
                scanner.nextLine(); // wait for user input
                break;
            case 6:
                // Settings
                printTitle("ui.setting");
                System.out.println("0. " + Lang.get("ui.back"));
                System.out.println("1. " + Lang.get("ui.changeLanguage"));
                choice = getChoice(1, "ui.choice", false);

                switch (choice) {
                case 0:
                    break;
                case 1: // Change language
                    changeLanguage();
                    break;
                default: // should not happen
                    System.out.println(Lang.get("notify.invalidChoice"));
                    break;
                }
                break;
            case 7:
                // Exit
                return;
            default:
                System.out.println(Lang.get("notify.invalidChoice"));
                break;
            }
        }

    }

    private static void printTitle(String title) {
        System.out.print("\033[H\033[2J");
        System.out.println(Lang.get(title));
        System.out.println("=========================================");
    }

    // foolproof
    private static int getChoice(int max, String hint, boolean allowNull) {
        while (true) {
            try {
                // print the hint
                System.out.print(Lang.get(hint));

                // get the input
                String input = scanner.nextLine(); // consume the newline character
                if (allowNull && input.isEmpty()) {
                    return -1; // allow null input
                }
                int choice = Integer.parseInt(input);

                // check if the choice is in the range
                if (choice < 0 || choice > max) {
                    System.out.println(Lang.get("notify.invalidChoice"));
                    continue;
                } else {
                    return choice;
                }
            } catch (Exception e) {
                System.out.println(Lang.get("notify.invalidChoice"));
                continue;
            }
        }
    }

    private static void listTasks() {
        // check if there are any categories
        List<Category> categories = DataManager.getCategoryData();
        if (categories.isEmpty()) {
            System.out.println(Lang.get("notify.noCategory"));
            return;
        }

        // list all categories
        for (Category category : categories) {
            System.out.println(category.getName() + ":");
            List<Task> tasks = category.getTasks();
            if (tasks.isEmpty()) {
                System.out.println("    " + Lang.get("ui.emptyCategory"));
                continue;
            }

            // list all tasks in the category
            for (Task task : tasks) {
                System.out.printf("    %s\n", task.toString());
            }
        }
    }

    private static void addCategory() {
        printTitle("ui.addCategory");
        System.out.print(Lang.get("ui.inputCategoryName"));
        String name = scanner.nextLine();

        // Check if the name is empty
        if (name == null || name.isEmpty()) {
            System.out.println(Lang.get("notify.invalidCategoryName"));
            sleep(1500);
            return;
        }

        // Check if the name is already in use
        boolean result = DataManager.addCategory(name, null);
        System.out.printf(Lang.get(result ? "notify.categoryAdded" : "notify.existedCategory"), name);
        sleep(1500);
    }

    private static void addTask() {
        printTitle("ui.addTask");
        // check if there are any categories
        List<String> categories = DataManager.getCategoryStrList();
        if (categories.isEmpty()) {
            System.out.println(Lang.get("notify.noCategory"));
            sleep(1500);
            return;
        }

        // choose a category
        System.out.println("0. " + Lang.get("ui.back"));
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i));
        }
        int choice = getChoice(categories.size(), "ui.pickCategoryToAdd", false) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }
        Category category = DataManager.getCategory(choice);

        // input task name
        System.out.print(Lang.get("ui.inputTaskName"));
        String name = scanner.nextLine();
        // Check if the name is empty
        if (name == null || name.isEmpty()) {
            System.out.println(Lang.get("notify.invalidTaskName"));
            sleep(1500);
            return;
        }

        // input due date
        System.out.print(Lang.get("ui.inputDueDate"));
        String dueDateStr = scanner.nextLine();
        // Check if the due date is valid
        LocalDate dueDate = Task.parseDate(dueDateStr);
        // -1 / empty means no due date
        if (dueDateStr != "" && dueDateStr != "-1" && dueDate == null) {
            System.out.println(Lang.get("notify.invalidDateFormat"));
            sleep(1500);
            return;
        }

        boolean result = DataManager.addTask(category, new Task(name, category.getName(), dueDate));
        System.out.printf(Lang.get(result ? "notify.taskAdded" : "notify.duplicateNewTask"), name, category.getName());
        sleep(1500);
    }

    private static void modifyCategory() {
        printTitle("ui.modifyCategory");
        List<String> categories = DataManager.getCategoryStrList();

        // Check if there are any categories
        if (categories.isEmpty()) {
            System.out.println(Lang.get("notify.noCategory"));
            sleep(1500);
            return;
        }

        // choose a category
        System.out.println("0. " + Lang.get("ui.back"));
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i));
        }
        int choice = getChoice(categories.size(), "ui.pickCategoryToModify", false) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }
        Category category = DataManager.getCategory(choice);
        String oldName = categories.get(choice);

        // input new name
        System.out.print(Lang.get("ui.inputNewCategoryName"));
        String newName = scanner.nextLine();

        // Check if the name is empty
        if (newName == null || newName.isEmpty()) {
            System.out.println(Lang.get("notify.invalidCategoryName"));
            sleep(1500);
            return;
        }

        boolean result = DataManager.updateCategory(category, newName, null);
        if (result) {
            System.out.printf(Lang.get("notify.categoryModified"), oldName, newName);
        } else {
            System.out.printf(Lang.get("notify.existedCategory"), newName);
        }
        sleep(1500);
    }

    private static void modifyTask() {
        printTitle("ui.modifyTask");

        // check if there are any categories
        List<String> categories = DataManager.getCategoryStrList();
        if (categories.isEmpty()) {
            System.out.println(Lang.get("notify.noCategory"));
            sleep(1500);
            return;
        }
        // choose a category
        System.out.println("0. " + Lang.get("ui.back"));
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i));
        }
        int choice = getChoice(categories.size(), "ui.pickCategoryOfTaskToModify", false) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }
        Category category = DataManager.getCategory(choice);

        // check if there are any tasks in the category
        List<Task> tasks = category.getTasks();
        if (tasks.isEmpty()) {
            System.out.println(Lang.get("notify.noTaskInCategory"));
            sleep(1500);
            return;
        }
        // choose a task
        printTitle("ui.modifyTask");
        System.out.println("0. " + Lang.get("ui.back"));
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, tasks.get(i).getName());
        }
        choice = getChoice(tasks.size(), "ui.pickTaskToModify", false) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }
        Task task = tasks.get(choice);

        // pick a new category
        printTitle("ui.modifyTask");
        System.out.printf(Lang.get("ui.modifyingTask") + "\n", task.getCategoryName(), task.getName());
        System.out.println("=========================================");
        System.out.println("0. " + Lang.get("ui.back"));
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i));
        }
        choice = getChoice(categories.size(), "ui.pickNewCategory", true) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }
        Category newCategory = (choice == -2 ? category : DataManager.getCategory(choice));

        // input new name
        System.out.print(Lang.get("ui.inputNewTaskName"));
        String newName = scanner.nextLine();
        // Check if the name is empty
        if (newName.isEmpty()) {
            newName = task.getName(); // no change
        }

        // input new due date
        System.out.print(Lang.get("ui.inputNewDueDate"));
        String dueDateStr = scanner.nextLine();
        LocalDate newDueDate = Task.parseDate(dueDateStr);
        if (dueDateStr == "") { // empty means no change, -1 means no due date
            newDueDate = task.getDueDate();
        } else if (!dueDateStr.equals("-1") && newDueDate == null) { // invalid date format
            System.out.println(Lang.get("ui.invalidDateFormat"));
            sleep(1500);
            return;
        }

        // pick a TaskStatus
        System.out.println("0. " + Lang.get("ui.back"));
        for (TaskStatus status : TaskStatus.values()) {
            System.out.printf("%d. %s\n", status.ordinal() + 1, status);
        }
        choice = getChoice(categories.size(), "ui.pickNewStatus", true) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }

        TaskStatus newStatus = (choice == -2 ? task.getStatus() : TaskStatus.values()[choice]);

        boolean result = DataManager.updateTask(task, newCategory, newName, newDueDate, newStatus);
        System.out.printf(Lang.get(result ? "notify.taskModified" : "notify.duplicateModifiedTask"), task.getName());
        sleep(1200);
    }

    private static void deleteCategory() {
        printTitle("ui.deleteCategory");
        List<String> categories = DataManager.getCategoryStrList();

        // Check if there are any categories
        if (categories.isEmpty()) {
            System.out.println(Lang.get("notify.noCategory"));
            sleep(1500);
            return;
        }

        // choose a category
        System.out.println("0. " + Lang.get("ui.back"));
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i));
        }
        int choice = getChoice(categories.size(), "ui.pickCategoryToDelete", false) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }
        Category category = DataManager.getCategory(choice);

        
        System.out.printf(Lang.get("ui.confirmDeleteCategory"), category.getName());
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                break; // proceed to delete
            } else if (input.equals("n") || input.equals("no")) {
                System.out.println(Lang.get("ui.cancelOperation"));
                sleep(1500);
                return; // cancel deletion
            } else {
                System.out.println(Lang.get("notify.invalidChoice"));
            }
            System.out.printf(Lang.get("ui.confirmDeleteCategory2"), category.getName());
        }

        DataManager.removeCategory(category);
        System.out.printf(Lang.get("notify.categoryDeleted"), category.getName());
        sleep(1200);
    }

    private static void deleteTask() {
        printTitle("ui.deleteTask");

        // check if there are any categories
        List<String> categories = DataManager.getCategoryStrList();
        if (categories.isEmpty()) {
            System.out.println(Lang.get("notify.noCategory"));
            sleep(1500);
            return;
        }
        // choose a category
        System.out.println("0. " + Lang.get("ui.back"));
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categories.get(i));
        }
        int choice = getChoice(categories.size(), "ui.pickCategoryOfTaskToDelete", false) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }
        Category category = DataManager.getCategory(choice);

        // check if there are any tasks in the category
        List<Task> tasks = category.getTasks();
        if (tasks.isEmpty()) {
            System.out.println(Lang.get("notify.noTaskInCategory"));
            sleep(1500);
            return;
        }
        
        // choose a task
        printTitle("ui.deleteTask");
        System.out.println("0. " + Lang.get("ui.back"));
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, tasks.get(i).getName());
        }
        choice = getChoice(tasks.size(), "ui.pickTaskToDelete", false) - 1; // -1 to convert to index
        if (choice == -1) {
            return; // back to main menu
        }

        Task task = tasks.get(choice);

        System.out.printf(Lang.get("ui.confirmDeleteTask"), task.getName());
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                break; // proceed to delete
            } else if (input.equals("n") || input.equals("no")) {
                System.out.println(Lang.get("ui.cancelOperation"));
                sleep(1500);
                return; // cancel deletion
            } else {
                System.out.println(Lang.get("notify.invalidChoice"));
            }
            System.out.printf(Lang.get("ui.confirmDeleteTask"), task.getName());
        }

        DataManager.removeTask(category, task);
        System.out.printf(Lang.get("notify.taskDeleted"), task.getName());
        sleep(1200);
    }

    private static void changeLanguage() {
        printTitle("ui.setting");
        System.out.println("0. " + Lang.get("ui.back"));
        System.out.println("1. English");
        System.out.println("2. 繁體中文");
        System.out.println("3. 簡體中文");
        int choice = getChoice(3, "ui.pickLanguage", false);
        if (choice == -1) {
            return; // back to main menu
        }

        switch (choice) {
        case 0:
            return; // back to main menu
        case 1:
            Config.set("lang", "en");
            break;
        case 2:
            Config.set("lang", "zh-TW");
            break;
        case 3:
            Config.set("lang", "zh-CN");
            break;
        default: // should not happen
            System.out.println(Lang.get("notify.invalidChoice"));
            sleep(1000);
            return;
        }
        Lang.setLocale(Config.getLocale());
        System.out.println(Lang.get("notify.languageChanged"));
        sleep(1000);
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
