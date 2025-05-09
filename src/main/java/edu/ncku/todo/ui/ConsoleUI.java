package edu.ncku.todo.ui;

import java.util.Scanner;
import java.util.Date;

import edu.ncku.todo.model.CategoryMap;
import edu.ncku.todo.model.Config;
import edu.ncku.todo.util.Lang;
import edu.ncku.todo.model.Task;

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
            System.out.println("1." + Lang.get("ui.add"));
            System.out.println("2." + Lang.get("ui.list"));
            System.out.println("3." + Lang.get("ui.modify"));
            System.out.println("4." + Lang.get("ui.help"));
            System.out.println("5." + Lang.get("ui.setting"));
            System.out.println("6." + Lang.get("ui.exit"));

            int choice = getChoice(6);

            switch (choice) {
            case 1:
                // Add task / category
                System.out.print("\033[H\033[2J");
                System.out.println(Lang.get("ui.add"));
                System.out.println("=========================================");
                System.out.println("1." + Lang.get("ui.addCategory"));
                System.out.println("2." + Lang.get("ui.addTask"));
                choice = getChoice(2);

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
                // TODO: list tasks
                break;
            case 3:
                // Modify tasks / category
                System.out.print("\033[H\033[2J");
                System.out.println(Lang.get("ui.modify"));
                System.out.println("=========================================");
                System.out.println("1." + Lang.get("ui.modifyCategory"));
                System.out.println("2." + Lang.get("ui.modifyTask"));

                choice = getChoice(2);

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
                // TODO: 待補
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
                choice = getChoice(1);

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
                // TODO: save data (write file)
                System.exit(0);
                break;
            default:
                System.out.println(Lang.get("ui.invalidChoice"));
                break;
            }
        }

    }

    private static int getChoice(int max) {
        while (true) {
            try {
                System.out.print(Lang.get("ui.choice"));
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character
                if (choice < 1 || choice > max) {
                    System.out.println(Lang.get("ui.invalidChoice"));
                    continue;
                } else {
                    return choice;
                }
            } catch (Exception e) {
                System.out.println(Lang.get("ui.invalidChoice"));
                scanner.nextLine(); // consume the invalid input
                continue;
            }
        }
    }

    private static void addCategory() {
        System.out.print(Lang.get("ui.inputCategoryName"));
        String name = scanner.nextLine();

        if (name == null || name.isEmpty()) {
            System.out.println(Lang.get("ui.invalidCategoryName"));
            return;
        }

        boolean result = CategoryMap.addCategory(name);

        System.out.printf(Lang.get(result ? "ui.categoryAdded" : "ui.categoryExists"), name);
        sleep(1200);
    }

    private static void addTask() {
        System.out.print(Lang.get("ui.inputTaskName"));
        String name = scanner.nextLine();

        System.out.print(Lang.get("ui.inputTaskCategory"));
        String category = scanner.nextLine();

        System.out.print(Lang.get("ui.inputTaskDueDate"));
        String dueDateStr = scanner.nextLine();
        Date dueDate = Task.parseDate(dueDateStr);
        if (dueDateStr != "" && dueDate == null) {
            System.out.println(Lang.get("ui.invalidDateFormat"));
            return;
        }

        boolean result = CategoryMap.addTask(category, new Task(name, category, dueDate));

        System.out.printf(Lang.get(result ? "ui.taskAdded" : "ui.duplicateTaskOrCategory"), name, category, dueDateStr);
        sleep(1200);
    }

    private static void modifyCategory() {
        // System.out.print(Lang.get("ui.inputOldCategoryName"));
        // String oldName = scanner.nextLine();

        // System.out.print(Lang.get("ui.inputNewCategoryName"));
        // String newName = scanner.nextLine();

        // boolean result = CategoryMap.updateCategory(oldName, newName);

        // System.out.printf(Lang.get(result ? "ui.categoryModified" : "ui.categoryNotFound"), oldName, newName);
        // sleep(1200);
    }

    private static void modifyTask() {
        // System.out.print(Lang.get("ui.inputOldTaskName"));
        // String oldName = scanner.nextLine();

        // System.out.print(Lang.get("ui.inputNewTaskName"));
        // String newName = scanner.nextLine();

        // System.out.print(Lang.get("ui.inputNewTaskCategory"));
        // String newCategory = scanner.nextLine();

        // System.out.print(Lang.get("ui.inputNewTaskDueDate"));
        // String dueDateStr = scanner.nextLine();
        // Date dueDate = Task.parseDate(dueDateStr);
        // if (dueDateStr != "" && dueDate == null) {
        //     System.out.println(Lang.get("ui.invalidDateFormat"));
        //     return;
        // }

        // boolean result = CategoryMap.updateTask(oldName, newName, newCategory, dueDate);

        // System.out.printf(Lang.get(result ? "ui.taskModified" : "ui.taskNotFound"), oldName, newName, newCategory, dueDateStr);
        // sleep(1200);
    }

    private static void changeLanguage() {
        System.out.print("\033[H\033[2J");
        System.out.println(Lang.get("ui.setting"));
        System.out.println("=========================================");
        System.out.println("1. English");
        System.out.println("2. 繁體中文");
        System.out.println("3. 簡體中文");
        int choice = getChoice(3);

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
