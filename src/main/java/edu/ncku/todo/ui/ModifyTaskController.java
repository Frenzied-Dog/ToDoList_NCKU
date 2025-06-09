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
import edu.ncku.todo.model.TaskStatus;
import edu.ncku.todo.util.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyTaskController extends ButtonBehavior implements Initializable {
    @FXML private ChoiceBox<String> pickCategoryList;
    @FXML private ChoiceBox<String> newCategoryList;
    @FXML private ChoiceBox<Task> pickTaskList;
    @FXML private TextField newTaskName;
    @FXML private DatePicker dueDatePicker;
    @FXML private ChoiceBox<TaskStatus> newStatusList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 將category名字載進來
        pickCategoryList.getItems().clear();
        newCategoryList.getItems().clear();
        DataManager.getCategoryData().forEach(c -> {
            pickCategoryList.getItems().add(c.getName());
            newCategoryList.getItems().add(c.getName());
        });

        newStatusList.getItems().addAll(TaskStatus.values());
    }

    @FXML
    private void fillTaskList() {
        pickTaskList.setValue(null);
        pickTaskList.getItems().clear(); // 清空任務列表

        String selectedCategoryName = pickCategoryList.getValue();
        if (selectedCategoryName == null || selectedCategoryName.isBlank())
            return;

        Category category = DataManager.getCategory(selectedCategoryName);
        if (category == null)
            return;

        // 加入 Task 物件本身
        pickTaskList.getItems().addAll(category.getTasks());
    }

    @FXML
    private void fillOldTaskProperty() {
        Task selectedTask = pickTaskList.getValue();  
        
        if (selectedTask != null) {
            newCategoryList.setValue(pickCategoryList.getValue());
            newTaskName.setText(selectedTask.getName());  
            dueDatePicker.setValue(selectedTask.getDueDate()); 
            newStatusList.setValue(selectedTask.getStatus());
        } else {
            newCategoryList.setValue(null);
            newTaskName.clear();
            dueDatePicker.setValue(null);
            newStatusList.setValue(null);
        }
    }

    @FXML
    private void onConfirm(ActionEvent e) {
        String oldCategory = pickCategoryList.getValue();
        Task oldTask = pickTaskList.getValue();
        String newCategory = newCategoryList.getValue();
        String newName = newTaskName.getText();
        LocalDate newDueDate = dueDatePicker.getValue();
        TaskStatus newStatus = newStatusList.getValue();

        // 1.檢查task有沒有填
        if (oldTask == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("請選擇要修改的任務");
            alert.showAndWait();
            return;
        }

        if (newName.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("任務名不得為空");
            alert.showAndWait();
            return;
        }

        // 2.檢查cate有沒有選
        if (newCategory == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("請選擇類別");
            alert.showAndWait();
            return;
        }

        if (newCategory.equals(oldCategory)
                && newName.equals(oldTask.getName()) 
                && newDueDate.equals(oldTask.getDueDate()) 
                && newStatus.equals(oldTask.getStatus())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("未修改");
            alert.showAndWait();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
            return;
        }

        // 3.檢查有沒有重複
        Category category = DataManager.getCategory(newCategory);
        boolean result = DataManager.updateTask(oldTask, category, newName, newDueDate, newStatus);
        if (!result) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("任務" + newName + " 已經存在於類別" + category.getName());
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void onClickDelete(ActionEvent e) {
        String categoryName = pickCategoryList.getValue();
        Task taskName = pickTaskList.getValue();

        if (taskName == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("請選擇要刪除的任務");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText(null);
        alert.setContentText("這將刪除任務" + taskName.getName() + "，確認是否刪除？");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            DataManager.removeTask(categoryName, taskName);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}