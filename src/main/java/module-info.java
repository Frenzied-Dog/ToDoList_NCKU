module edu.ncku {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens edu.ncku to javafx.fxml;
    exports edu.ncku;
}
