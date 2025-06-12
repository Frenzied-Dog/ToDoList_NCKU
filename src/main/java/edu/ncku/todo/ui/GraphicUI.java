package edu.ncku.todo.ui;

import java.io.IOException;
import java.util.List;

import edu.ncku.todo.model.Task;
import edu.ncku.todo.util.Lang;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Modality;

public class GraphicUI extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage; // 儲存主舞台的參考
        Scene scene = new Scene(getFXML("mainView").load());
        stage.setScene(scene);
        stage.getIcons().add(new Image(GraphicUI.class.getResourceAsStream("icon.png")));
        stage.setTitle(Lang.get("app.title"));
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        Parent root = getFXML(fxml).load();
        Scene newScene = new Scene(root);
        mainStage.setScene(newScene);
        mainStage.sizeToScene();
    }
    
    //彈出視窗邏輯
    static void showDialog(String fxml, String title, List<Task> tasks) throws IOException {
        FXMLLoader loader = getFXML(fxml);
        Parent root = loader.load();

        if (tasks != null && !tasks.isEmpty()) {
            TableController controller = loader.getController();
            controller.setTable(tasks);
        }

        Stage dialog = new Stage();
        dialog.setTitle(title);
        dialog.initOwner(mainStage);
        dialog.initModality(Modality.WINDOW_MODAL); 
        dialog.setResizable(false);
        dialog.setScene(new Scene(root));
        dialog.getIcons().add(new Image(GraphicUI.class.getResourceAsStream("icon.png")));
        dialog.sizeToScene();

        dialog.showAndWait();
    }

    static ButtonType showAlert(AlertType type, String content) {
        Alert alert = new Alert(type);
        ((Stage) alert.getDialogPane().getScene().getWindow())
            .getIcons().add(new Image(GraphicUI.class.getResourceAsStream("icon.png")));

        if (type == AlertType.WARNING) {
            alert.setTitle(Lang.get("notify.warning"));
        } else if (type == AlertType.INFORMATION) {
            alert.setTitle(Lang.get("notify.info"));
        } else if (type == AlertType.CONFIRMATION) {
            alert.setTitle(Lang.get("notify.confirm"));
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        } else {
            alert.setTitle(Lang.get("notify.error"));
        }
    
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();

        return alert.getResult();
    }

    static FXMLLoader getFXML(String fxml) throws IOException {
        return new FXMLLoader(GraphicUI.class.getResource(fxml + ".fxml"), Lang.bundle);
    }

    public static void main(String[] args) {
        launch();
    }
}