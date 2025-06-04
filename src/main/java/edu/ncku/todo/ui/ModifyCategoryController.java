/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ncku.todo.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.ncku.todo.model.Category;
import edu.ncku.todo.util.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ModifyCategoryController implements Initializable {
    @FXML
    private TextField newCategoryName;
    /**
     * Initializes the controller class.
     */
    @FXML
    private ChoiceBox<String> pickCategoryList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 將category名字載進來
        pickCategoryList.getItems().clear();
        DataManager.getCategoryData().forEach(c -> {
            pickCategoryList.getItems().add(c.getName());
        });
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
    // private void switchToMainView() throws IOException { GraphicUI.setRoot("mainView"); }

    @FXML
    private void onConfirm(ActionEvent e) {
        if (!modifyCategory())
            return;

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private boolean modifyCategory() {
        String newName = newCategoryName.getText();
        String oldName = pickCategoryList.getValue();

        // 1.檢查cate有沒有選
        if (oldName == null) {
            if (newName.isBlank()) {
                return true;
            } // 可能沒看到打叉的使用者可以用

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("請選擇類別");
            alert.showAndWait();
            return false;
        }

        // 可能沒看到打叉的使用者可以用
        if (newName.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("未修改");
            alert.showAndWait();
            return true;
        }

        // Category category = DataManager.getCategory(newName); // 先檢查重複
        // if (category == null) {
        // category = DataManager.getCategory(oldName);
        // category.setName(newName);
        // return true;
        // }
        Category category = DataManager.getCategory(oldName);
        boolean result = DataManager.updateCategory(category, newName);

        if (!result) {
            // 跳警告
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("類別" + newName + "已經存在");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }
}
