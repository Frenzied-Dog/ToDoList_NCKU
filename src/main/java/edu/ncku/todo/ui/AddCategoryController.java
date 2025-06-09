/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ncku.todo.ui;

import edu.ncku.todo.util.DataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AddCategoryController extends ButtonBehavior {
    @FXML private TextField categoryInputField;  

    @FXML
    private void onConfirm(ActionEvent e) {
        String newCategory = categoryInputField.getText();

        // 檢查是否為空
        if (newCategory == null || newCategory.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("類別名稱不得為空");
            alert.showAndWait();
            return;
        } else {
            boolean result = DataManager.addCategory(newCategory);

            if (!result) {
                System.err.println(newCategory);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("警告");
                alert.setHeaderText(null);
                alert.setContentText("類別" + newCategory + " 已經存在");
                alert.showAndWait();
                return;
            }    
        }

        // 關視窗
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

}
