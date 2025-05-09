package edu.ncku.todo.ui;

import java.util.Scanner;
import java.util.Date;

import edu.ncku.todo.model.CategoryMap;
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

            System.out.print(Lang.get("ui.choice"));
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
            case 1:
                // Add task / category
                System.out.print("\033[H\033[2J");
                System.out.println(Lang.get("ui.add"));
                System.out.println("=========================================");
                System.out.println("1." + Lang.get("ui.addCategory"));
                System.out.println("2." + Lang.get("ui.addTask"));
                System.out.print(Lang.get("ui.choice"));
                choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                switch (choice) {
                case 1: // Add category
                    addCategory();
                    break;
                case 2: // Add task
                    addTask();
                    break;
                default:
                    System.out.println(Lang.get("ui.invalidChoice"));
                    break;
                }
                break;
            case 2:

            case 3:

            case 4:

            case 5:

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

    private static void addCategory() {
        System.out.print(Lang.get("ui.inputCategoryName"));
        String name = scanner.nextLine();

        boolean result = CategoryMap.addCategory(name);

        System.out.printf(Lang.get(result ? "ui.categoryAdded" : "ui.categoryExists"), name);
        sleep(1500);
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
        sleep(1500);
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
