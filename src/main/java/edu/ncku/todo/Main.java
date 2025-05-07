package edu.ncku.todo;

import edu.ncku.todo.ui.GraphicUI;
import edu.ncku.todo.storage.SqlClient;
import edu.ncku.todo.ui.ConsoleUI;

public class Main {
	public static SqlClient client;

	public static void main(String[] args) {
		client = new SqlClient();

		// Check if the first argument is "--cli"
		// If it is, run the ConsoleUI, otherwise run the GraphicUI
		// Example usage:
		// java -jar yourapp.jar --cli
		if (args.length > 0 && args[0].equals("--cli")) {
			ConsoleUI.main(args);
		} else {
			GraphicUI.main(args);
		}
	}
}