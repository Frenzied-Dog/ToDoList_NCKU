package edu.ncku.todo.ui;

import java.io.IOException;
import javafx.fxml.FXML;

public class SceneController {

    @FXML
    private void switchToSecondary() throws IOException {
        GraphicUI.setRoot("secondary");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        GraphicUI.setRoot("primary");
    }
}
