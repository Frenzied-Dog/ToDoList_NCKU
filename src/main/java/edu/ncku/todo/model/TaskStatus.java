package edu.ncku.todo.model;

public enum TaskStatus {
	TODO, IN_PROGRESS, DONE, DELETED;

	public static TaskStatus fromString(String status) {
		switch (status.toUpperCase()) {
			case "TODO":
				return TODO;
			case "IN_PROGRESS":
				return IN_PROGRESS;
			case "DONE":
				return DONE;
			case "DELETED":
				return DELETED;
			default:
				throw new IllegalArgumentException("Unknown status: " + status);
		}
	}
}
