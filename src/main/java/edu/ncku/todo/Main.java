package edu.ncku.todo;

import edu.ncku.todo.ui.GraphicUI;
import edu.ncku.todo.util.FileManager;
import edu.ncku.todo.util.Lang;
import edu.ncku.todo.util.SqlClient;
import edu.ncku.todo.model.Config;
import edu.ncku.todo.ui.ConsoleUI;

public class Main {
    public static SqlClient client;

    public static void main(String[] args) {
        // Initialize the SqlClient (in plan)
        // client = new SqlClient();

        System.out.println("Loading configuration...");
        // Load the configuration file and set the locale
        if (FileManager.loadConfig()) {
            Lang.setLocale(Config.getLocale());
            System.out.println(Lang.get("notify.langLoaded"));
        } else {
            System.out.println("Default language is set to English.");
        }

        // Load tasks & categories files
        System.out.println(Lang.get("notify.loadingData"));
        FileManager.loadData();

        // Check if the first argument is "--cli"
        // If it is, run the ConsoleUI, otherwise run the GraphicUI
        // Example usage:
        // java -jar yourapp.jar --cli
        if (args.length > 0 && args[0].equals("--cli")) {
            ConsoleUI.main(args);
        } else {
            GraphicUI.main(args);
        }

        FileManager.saveData();
    }
}