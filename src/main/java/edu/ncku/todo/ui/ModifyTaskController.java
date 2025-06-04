/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ncku.todo.ui;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ModifyTaskController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private ChoiceBox<String> pickCategoryList;
    @FXML private ChoiceBox<String> newCategoryList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // 將category名字載進來
        pickCategoryList.getItems().clear();
        newCategoryList.getItems().clear();
        DataManager.getCategoryData().forEach(c -> {
            pickCategoryList.getItems().add(c.getName());
            newCategoryList.getItems().add(c.getName());
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
    // private void switchToMainView() throws IOException {
    //     GraphicUI.setRoot("mainView");
    // }

    @FXML
    private void onConfirm(ActionEvent e) {
        if (!modifyTask()) return;
        
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private ChoiceBox<Task> pickTaskList;

    @FXML
    private void chooseCategory() {
        pickTaskList.getItems().clear(); // 清空任務列表

        String selectedCategoryName = pickCategoryList.getValue();
        if (selectedCategoryName == null || selectedCategoryName.isEmpty())
            return;

        Category category = DataManager.getCategory(selectedCategoryName);
        if (category == null)
            return;

        // 加入 Task 物件本身
        pickTaskList.getItems().addAll(category.getTasks());
    }

    // 修改task
    
    @FXML    private TextField newTaskName;
    @FXML    private DatePicker dueDatePicker;
    
    @FXML
    private void pickTaskListClicked() {
        newCategoryList.setValue(pickCategoryList.getValue());
        Task selectedTask = pickTaskList.getValue();  
            if (selectedTask != null) {
            newTaskName.setText(selectedTask.getName());  
            dueDatePicker.setValue(selectedTask.getDueDate()); 
        }
    }

    @FXML
    private boolean modifyTask() {
        String newName = newTaskName.getText();
        LocalDate newDueDate = dueDatePicker.getValue();
        String newCategory = (newCategoryList.getValue());
        Task oldTask = pickTaskList.getValue();

        // 1.檢查task有沒有填
        if (newName.isBlank())
            return true;

        // 2.檢查cate有沒有選
        if (newCategory == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("請選擇類別");
            alert.showAndWait();

            return false;
        }
        Category category = DataManager.getCategory(newCategory);

        //3.檢查有沒有改
        Task task = new Task(newName, category.getName(), newDueDate);
        if(task.equals(pickTaskList.getValue())) return true;

        // 4.檢查有沒有重複
        boolean result = DataManager.addTask(category, task);
        if (!result) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("任務" + newName + " 已經存在於類別" + category.getName());
            alert.showAndWait();
            return false;
        }
        DataManager.removeTask(DataManager.getCategory(pickCategoryList.getValue()), oldTask); // 移除原本的task
        return true;
    }
}

// TODO: 沒有"完成任務"選項