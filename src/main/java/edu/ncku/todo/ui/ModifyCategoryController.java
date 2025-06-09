/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ncku.todo.ui;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ncku.todo.model.Category;
import edu.ncku.todo.util.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyCategoryController extends ButtonBehavior implements Initializable {
    @FXML private TextField newCategoryName;
    @FXML private ChoiceBox<String> pickCategoryList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 將category名字載進來
        pickCategoryList.getItems().clear();
        DataManager.getCategoryData().forEach(c -> {
            pickCategoryList.getItems().add(c.getName());
        });
    }

    @FXML
    private void fillOldCategoryName() {
        newCategoryName.setText(pickCategoryList.getValue());
    }

    @FXML
    private void onConfirm(ActionEvent e) {
        String newName = newCategoryName.getText();
        String oldName = pickCategoryList.getValue();

        // 1.檢查cate有沒有選
        if (oldName == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("請選擇類別");
            alert.showAndWait();
            return;
        }

        if (newName.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("類別名稱不得為空");
            alert.showAndWait();
            return;
        }

        // 如果新名稱和舊名稱相同，則不需要更新
        if (newName.equals(oldName)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("未修改");
            alert.showAndWait();
            
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
            return;
        }

        Category category = DataManager.getCategory(oldName);
        boolean result = DataManager.updateCategory(category, newName);

        if (!result) {
            // 跳警告
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("類別" + newName + "已經存在");
            alert.showAndWait();
            return;
        } else {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

}
