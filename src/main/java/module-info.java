module edu.ncku.todo {
    requires transitive com.google.gson;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires org.hildan.fxgson;
    requires java.base;

    opens edu.ncku.todo.ui to javafx.graphics, javafx.fxml;
    opens edu.ncku.todo.model to com.google.gson;

    exports edu.ncku.todo;
    exports edu.ncku.todo.util;
    exports edu.ncku.todo.model;
}