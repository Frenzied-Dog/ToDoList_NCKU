/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ncku.todo.ui;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ncku.todo.util.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AddCategoryController implements Initializable {
    @FXML private TextField categoryInputField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleHover(MouseEvent e) {
        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #8495c4;");
    }

    @FXML
    private void handlePress(MouseEvent e) {
        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #3d4f7a;");
    }

    @FXML
    private void handleExit(MouseEvent e) {
        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #7190de;");
    }

    // @FXML
    // private void switchToMainView() throws IOException {
    //     GraphicUI.setRoot("mainView");
    // }

    

    @FXML
    private void onConfirm(ActionEvent e) {
        // 新增
        String newCategory = categoryInputField.getText();
        // 檢查有沒有東西
        if (!(newCategory == null || newCategory.isEmpty())) {
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
