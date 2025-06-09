/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ncku.todo.ui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.ncku.todo.model.Category;
import edu.ncku.todo.model.Task;
import edu.ncku.todo.util.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTaskController extends ButtonBehavior implements Initializable {
    @FXML private ChoiceBox<String> pickCategoryList;
    @FXML private TextField categoryInputField;
    @FXML private TextField newTaskName;
    @FXML private DatePicker dueDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        // 將category名字載進來
        pickCategoryList.getItems().clear();
        DataManager.getCategoryData().forEach(c -> {
            pickCategoryList.getItems().add(c.getName());
        });
    }
    
    @FXML
    private void onConfirm(ActionEvent e) {
        String mainCategory = pickCategoryList.getValue();
        String newTask = newTaskName.getText();
        LocalDate dueDate = dueDatePicker.getValue();

        // 1.檢查cate有沒有選
        if (mainCategory == null || mainCategory.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("請選擇類別");
            alert.showAndWait();
            return;
        }

        // 2.檢查task有沒有填
        if (newTask == null || newTask.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("任務名不得為空");
            alert.showAndWait();
            return;
        }

        Category category = DataManager.getCategory(mainCategory);

        // 3.檢查有沒有重複
        Task task = new Task(newTask, category.getName(), dueDate);
        boolean result = DataManager.addTask(category, task);

        if (!result) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("任務" + newTask + " 已經存在");
            alert.showAndWait();
            return;
        }

        // 關視窗
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
