package edu.ncku.todo.ui;

import edu.ncku.todo.util.Lang;
import edu.ncku.todo.util.DataManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AddCategoryController extends ButtonBehavior {
    @FXML private TextField categoryInputField;
    @FXML private ColorPicker colorPicker;  


    @FXML
    private void onConfirm(ActionEvent e) {
        String newCategory = categoryInputField.getText();
        Color newColor = colorPicker.getValue();

        // 檢查是否為空
        if (newCategory == null || newCategory.isBlank()) {
            GraphicUI.showAlert(AlertType.WARNING, Lang.get("notify.invalidCategoryName"));
            return;
        } else {
            boolean result = DataManager.addCategory(newCategory, newColor);
            if (!result) {
                GraphicUI.showAlert(AlertType.WARNING, String.format(Lang.get("notify.existedCategory"), newCategory));
                return;
            }
        }

        // 關視窗
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

}
