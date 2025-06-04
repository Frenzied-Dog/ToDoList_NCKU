package edu.ncku.todo.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.List;

import edu.ncku.todo.model.Task;
import edu.ncku.todo.util.Lang;


public class GraphicUI extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage; // 儲存主舞台的參考
        Scene scene = new Scene(getFXML("mainView").load());
        stage.setScene(scene);
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
    public static void showDialog(String fxml, String title, List<Task> tasks) throws IOException {
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
        dialog.sizeToScene();

        dialog.showAndWait();
    }

    public static FXMLLoader getFXML(String fxml) throws IOException {
        return new FXMLLoader(GraphicUI.class.getResource(fxml + ".fxml"), Lang.bundle);
    }

    public static void main(String[] args) {
        launch();
    }

}