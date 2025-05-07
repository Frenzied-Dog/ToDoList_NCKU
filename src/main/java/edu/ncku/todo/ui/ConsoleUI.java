package edu.ncku.todo.ui;

import java.util.Scanner;

import edu.ncku.todo.storage.FileManager;
import edu.ncku.todo.model.Config;
import edu.ncku.todo.util.Lang;

public class ConsoleUI {
    public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("CLI mode");

        Config config = new Config();

        if (FileManager.loadConfig(config)) {
            Lang.setLocale(config.getLocale());
            System.out.println(Lang.get("notify.langLoaded"));
        } else {
            // System.out.println("Config file not found, creating a new one.");
            // System.out.println("Default language is set to English.");
        }
	}
}
