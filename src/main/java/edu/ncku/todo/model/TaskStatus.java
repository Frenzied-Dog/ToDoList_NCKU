package edu.ncku.todo.model;

import edu.ncku.todo.util.Lang;

public enum TaskStatus {
    // DELETED is for future use
	TODO, IN_PROGRESS, DONE, DELETED;

	public static TaskStatus fromString(String status) {
		switch (status.toUpperCase()) {
			case "TODO":
				return TODO;
			case "IN_PROGRESS":
				return IN_PROGRESS;
			case "DONE":
				return DONE;
			default:
				throw new IllegalArgumentException("Unknown status: " + status);
		}
	}

    public String toString() {
        try {
            return Lang.get("status." + this.name());
        } catch (Exception e) {
            return this.name();
        }
    }
}
