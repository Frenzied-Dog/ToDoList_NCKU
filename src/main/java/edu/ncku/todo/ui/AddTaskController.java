/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ncku.todo.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AddTaskController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
        Button btn = (Button)e.getSource();   
        btn.setStyle("-fx-background-color: #8495c4;");
    }

    @FXML
    private void handlePress(MouseEvent e) {
        Button btn = (Button)e.getSource();   
        btn.setStyle("-fx-background-color: #3d4f7a;");
    }
    
    @FXML
    private void handleExit(MouseEvent e) {
        Button btn = (Button)e.getSource();
        btn.setStyle("-fx-background-color: #7190de;");
    }
    
    @FXML
    private void switchToMainView() throws IOException {
        GraphicUI.setRoot("mainView");
    }
    
    @FXML
    private void onConfirm(ActionEvent e) {
        if(!addTask())return;
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
        try {
           switchToMainView();  
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //處理介面
    @FXML   private ChoiceBox<String> pickCategoryList;
    @FXML   private TextField categoryInputField;
    @FXML   private TextField newTaskName;
    @FXML   private DatePicker dueDatePicker;

    @FXML
    private void chooseCategory(ActionEvent e){
        String selected = pickCategoryList.getValue();
        pickCategoryList.setValue(selected); 
    }
    @FXML
    private boolean addTask(){
        String newTask = newTaskName.getText();
        LocalDate dueDate = dueDatePicker.getValue();
        String mainCategory = (pickCategoryList.getValue());
        
        //1.檢查tas有沒有填
        if(newTask.isBlank())return true; //可能沒看到打叉的使用者可以用

        //2.檢查cate有沒有選
        if(mainCategory ==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("請選擇類別");
            alert.showAndWait();
            
            return false;
        }
        Category category = DataManager.getCategory(mainCategory);

        //3.檢查有沒有重複
        Task task = new Task(newTask, category.getName(), dueDate);
        boolean result = DataManager.addTask(category, task);
        if(!result){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("任務"+ newTask + " 已經存在");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    //Todo: 更新的時候月曆沒有同步更新，會慢一格
    // 沒有"完成任務"選項

}
