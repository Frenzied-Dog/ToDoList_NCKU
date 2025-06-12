package edu.ncku.todo.ui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.ncku.todo.model.Task;
import edu.ncku.todo.util.DataManager;
import edu.ncku.todo.util.Lang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
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
        String categoryName = pickCategoryList.getValue();
        String newTask = newTaskName.getText();
        LocalDate dueDate = dueDatePicker.getValue();

        // 1.檢查cate有沒有選
        if (categoryName == null || categoryName.isBlank()) {
            GraphicUI.showAlert(AlertType.WARNING, Lang.get("notify.gui.noChooseCategory"));
            return;
        }

        // 2.檢查task有沒有填
        if (newTask == null || newTask.isBlank()) {
            GraphicUI.showAlert(AlertType.WARNING, Lang.get("notify.invalidTaskName"));
            return;
        }

        // 3.檢查有沒有重複
        Task task = new Task(newTask, categoryName, dueDate);
        boolean result = DataManager.addTask(categoryName, task);

        if (!result) {
            GraphicUI.showAlert(AlertType.WARNING, String.format(Lang.get("notify.gui.existedTask"), newTask, categoryName));
            return;
        }

        // 關視窗
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}