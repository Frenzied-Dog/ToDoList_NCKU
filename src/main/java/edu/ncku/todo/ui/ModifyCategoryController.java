package edu.ncku.todo.ui;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ncku.todo.model.Category;
import edu.ncku.todo.util.DataManager;
import edu.ncku.todo.util.Lang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyCategoryController extends ButtonBehavior implements Initializable {
    @FXML private ChoiceBox<String> pickCategoryList;
    @FXML private TextField newCategoryName;
    @FXML private ColorPicker newColorPicker;

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
        newColorPicker.setValue(DataManager.getCategory(pickCategoryList.getValue()).getColor());
    }

    @FXML
    private void onConfirm(ActionEvent e) {
        String oldName = pickCategoryList.getValue();
        String newName = newCategoryName.getText();
        Color newColor = newColorPicker.getValue();

        // 1.檢查cate有沒有選
        if (oldName == null) {
            GraphicUI.showAlert(AlertType.WARNING, Lang.get("notify.gui.noChooseCategory"));
            return;
        }

        if (newName.isBlank()) {
            GraphicUI.showAlert(AlertType.WARNING, Lang.get("notify.invalidCategoryName"));
            return;
        }

        // 如果新名稱和舊名稱相同，則不需要更新
        if (newName.equals(oldName) && newColor.equals(DataManager.getCategory(oldName).getColor())) {
            GraphicUI.showAlert(AlertType.INFORMATION, Lang.get("notify.unchanged"));
            
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
            return;
        }

        Category category = DataManager.getCategory(oldName);
        boolean result = DataManager.updateCategory(category, newName, newColor);

        if (!result) {
            GraphicUI.showAlert(AlertType.WARNING, String.format(Lang.get("notify.existedCategory"), newName));
        } else {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void onClickDelete(ActionEvent e) {
        String categoryName = pickCategoryList.getValue();

        if (categoryName == null) {
            GraphicUI.showAlert(AlertType.WARNING, Lang.get("notify.gui.noChooseCategory"));
            return;
        }

        ButtonType ret = GraphicUI.showAlert(AlertType.CONFIRMATION, Lang.get("notify.gui.confirmDeleteCategory"));

        if (ret == ButtonType.YES) {
            DataManager.removeCategory(categoryName);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}