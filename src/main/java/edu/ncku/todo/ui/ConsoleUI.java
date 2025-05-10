package edu.ncku.todo.ui;

import java.util.Scanner;
import java.util.Date;
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
            // clear the console
            System.out.print("\033[H\033[2J");
            System.out.println(Lang.get("ui.welcome"));
            System.out.println("=========================================");
            System.out.printf(Lang.get("ui.hintTaskCount"), DataManager.getUnfinishedTaskCount());
            System.out.println("=========================================");
            System.out.println("1." + Lang.get("ui.add"));
            System.out.println("2." + Lang.get("ui.list"));
            System.out.println("3." + Lang.get("ui.modify"));
            System.out.println("4." + Lang.get("ui.help"));
            System.out.println("5." + Lang.get("ui.setting"));
            System.out.println("6." + Lang.get("ui.exit"));

            int choice = getChoice(6, "ui.choice", false);

            switch (choice) {
            case 1:
                // Add task / category
                System.out.print("\033[H\033[2J");
                System.out.println(Lang.get("ui.add"));
                System.out.println("=========================================");
                System.out.println("1." + Lang.get("ui.addCategory"));
                System.out.println("2." + Lang.get("ui.addTask"));
                choice = getChoice(2, "ui.choice", false);

                switch (choice) {
                case 1: // Add category
                    addCategory();
                    break;
                case 2: // Add task
                    addTask();
                    break;
                default: // should not happen
                    System.out.println(Lang.get("ui.invalidChoice"));
                    break;
                }
                break;
            case 2:
                // List tasks
                System.out.print("\033[H\033[2J");
                System.out.println(Lang.get("ui.list"));
                System.out.println("=========================================");
                listTasks();
                System.out.println(Lang.get("ui.pressEnter"));
                scanner.nextLine(); // wait for user input
                break;
            case 3:
                // Modify tasks / category
                System.out.print("\033[H\033[2J");
                System.out.println(Lang.get("ui.modify"));
                System.out.println("=========================================");
                System.out.println("1." + Lang.get("ui.modifyCategory"));
                System.out.println("2." + Lang.get("ui.modifyTask"));

                choice = getChoice(2, "ui.choice", false);

                switch (choice) {
                case 1: // Modify category
                    modifyCategory();
                    break;
                case 2: // Modify task
                    modifyTask();
                    break;
                default: // should not happen
                    System.out.println(Lang.get("ui.invalidChoice"));
                    break;
                }

                break;
            case 4:
                // Help text
                System.out.print("\033[H\033[2J");
                System.out.println(Lang.get("ui.help"));
                System.out.println("=========================================");
                // TODO: 待補helpContent內容
                System.out.println(Lang.get("ui.helpContent"));
                System.out.println(Lang.get("ui.helpContent2"));
                System.out.println(Lang.get("ui.pressEnter"));
                scanner.nextLine(); // wait for user input
                break;
            case 5:
                // Settings
                System.out.print("\033[H\033[2J");
                System.out.println(Lang.get("ui.setting"));
                System.out.println("=========================================");
                System.out.println("1." + Lang.get("ui.changeLanguage"));
                choice = getChoice(1, "ui.choice", false);

                switch (choice) {
                case 1: // Change language
                    changeLanguage();
                    break;
                default: // should not happen
                    System.out.println(Lang.get("ui.invalidChoice"));
                    break;
                }
                break;
            case 6:
                // Exit
                return;
            default:
                System.out.println(Lang.get("ui.invalidChoice"));
                break;
            }
        }

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
                    return 0; // allow null input
                }
                int choice = Integer.parseInt(input);
                
                // check if the choice is in the range
                if (choice < 1 || choice > max) {
                    System.out.println(Lang.get("ui.invalidChoice"));
                    continue;
                } else {
                    return choice;
                }
            } catch (Exception e) {
                System.out.println(Lang.get("ui.invalidChoice"));
                continue;
            }
        }
    }

    private static void listTasks() {
        // check if there are any categories
        List<Category> categories = DataManager.getCategoryData();
        if (categories.isEmpty()) {
            System.out.println(Lang.get("ui.noCategory"));
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
        System.out.print("\033[H\033[2J");
        System.out.println(Lang.get("ui.addCategory"));
        System.out.println("=========================================");
        System.out.print(Lang.get("ui.inputCategoryName"));
        String name = scanner.nextLine();

        // Check if the name is empty
        if (name == null || name.isEmpty()) {
            System.out.println(Lang.get("ui.invalidCategoryName"));
            sleep(1500);
            return;
        }

        // Check if the name is already in use
        boolean result = DataManager.addCategory(name);
        System.out.printf(Lang.get(result ? "ui.categoryAdded" : "ui.categoryExists"), name);
        sleep(1500);
    }

    private static void addTask() {
        System.out.print("\033[H\033[2J");
        System.out.println(Lang.get("ui.addTask"));
        System.out.println("=========================================");
        // check if there are any categories
        List<String> categories = DataManager.getCategoryList();
        if (categories.isEmpty()) {
            System.out.println(Lang.get("ui.noCategory"));
            sleep(1500);
            return;
        }

        // choose a category
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, categories.get(i));
        }
        int choice = getChoice(categories.size(), "ui.pickCategoryToAdd", false) - 1; // -1 to convert to index
        Category category = DataManager.getCategory(choice);

        System.out.print(Lang.get("ui.inputTaskName"));
        String name = scanner.nextLine();
        // Check if the name is empty
        if (name == null || name.isEmpty()) {
            System.out.println(Lang.get("ui.invalidTaskName"));
            sleep(1500);
            return;
        }

        System.out.print(Lang.get("ui.inputTaskDueDate"));
        String dueDateStr = scanner.nextLine();
        // Check if the due date is valid
        Date dueDate = Task.parseDate(dueDateStr);
        // -1 / empty means no due date
        if (dueDateStr != "" && dueDateStr != "-1" && dueDate == null) {
            System.out.println(Lang.get("ui.invalidDateFormat"));
            sleep(1500);
            return;
        }

        boolean result = DataManager.addTask(category, new Task(name, category.getName(), dueDate));
        System.out.printf(Lang.get(result ? "ui.taskAdded" : "ui.duplicateTask"), name, category.getName());
        sleep(1500);
    }

    private static void modifyCategory() {
        System.out.print("\033[H\033[2J");
        System.out.println(Lang.get("ui.modifyCategory"));
        System.out.println("=========================================");
        List<String> categories = DataManager.getCategoryList();

        // Check if there are any categories
        if (categories.isEmpty()) {
            System.out.println(Lang.get("ui.noCategory"));
            sleep(1500);
            return;
        }

        // choose a category
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, categories.get(i));
        }
        int choice = getChoice(categories.size(), "ui.pickCategoryToModify", false) - 1; // -1 to convert to index
        Category category = DataManager.getCategory(choice);
        String oldName = categories.get(choice);

        System.out.print(Lang.get("ui.inputNewCategoryName"));
        String newName = scanner.nextLine();

        // Check if the name is empty
        if (newName == null || newName.isEmpty()) {
            System.out.println(Lang.get("ui.invalidCategoryName"));
            sleep(1500);
            return;
        }

        // Check if the new name is already in use
        boolean result = DataManager.updateCategory(category, newName);
        if (result) {
            System.out.printf(Lang.get("ui.categoryModified"), oldName, newName);
        } else {
            System.out.printf(Lang.get("ui.categoryExists"), newName);
        }
        sleep(1500);
    }

    private static void modifyTask() {
        System.out.print("\033[H\033[2J");
        System.out.println(Lang.get("ui.modifyTask"));
        System.out.println("=========================================");

        // check if there are any categories
        List<String> categories = DataManager.getCategoryList();
        if (categories.isEmpty()) {
            System.out.println(Lang.get("ui.noCategory"));
            sleep(1500);
            return;
        }
        // choose a category
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, categories.get(i));
        }
        int choice = getChoice(categories.size(), "ui.pickCategoryOfTask", false) - 1; // -1 to convert to index
        Category category = DataManager.getCategory(choice);
        

        // check if there are any tasks in the category
        List<Task> tasks = category.getTasks();
        if (tasks.isEmpty()) {
            System.out.println(Lang.get("ui.noTaskInCategory"));
            sleep(1500);
            return;
        }
        // choose a task
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, tasks.get(i).getName());
        }
        choice = getChoice(tasks.size(), "ui.pickTaskToModify", false) - 1; // -1 to convert to index
        Task task = tasks.get(choice);


        // pick a new category
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, categories.get(i));
        }
        choice = getChoice(categories.size(), "ui.pickNewCategory", true) - 1; // -1 to convert to index
        Category newCategory = (choice == -1 ? category : DataManager.getCategory(choice));

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
        Date newDueDate = Task.parseDate(dueDateStr);
        if (dueDateStr == "") { // empty means no change, -1 means no due date
            newDueDate = task.getDueDate();
        } else if (dueDateStr != "-1" && newDueDate == null) { // invalid date format
            System.out.println(Lang.get("ui.invalidDateFormat"));
            sleep(1500);
            return;
        }

        // list all TaskStatus
        for (TaskStatus status : TaskStatus.values()) {
            System.out.printf("%d.%s\n", status.ordinal(), status);
        }
        choice = getChoice(categories.size(), "ui.pickNewStatus", true) - 1; // -1 to convert to index
        TaskStatus newStatus;
        if (choice == -1) {
            newStatus = task.getStatus(); // no change
        } else {
            newStatus = TaskStatus.values()[choice];
        }

        boolean result = DataManager.updateTask(task, newCategory, newName, newDueDate, newStatus);
        System.out.printf(Lang.get(result ? "ui.taskModified" : "ui.duplicateTask"), task.getName());
        sleep(1200);
    }

    private static void changeLanguage() {
        System.out.print("\033[H\033[2J");
        System.out.println(Lang.get("ui.setting"));
        System.out.println("=========================================");
        System.out.println("1. English");
        System.out.println("2. 繁體中文");
        System.out.println("3. 簡體中文");
        int choice = getChoice(3, "ui.pickLanguage", false);

        switch (choice) {
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
            System.out.println(Lang.get("ui.invalidChoice"));
            sleep(1000);
            return;
        }
        Lang.setLocale(Config.getLocale());
        System.out.println(Lang.get("ui.languageChanged"));
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
