package edu.ncku.todo.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;

import edu.ncku.todo.util.Lang;


public class GraphicUI extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //scene = new Scene(loadFXML("primary"), 640, 480);
        //scene = new Scene(loadFXML("MainView"), 819, 548);
        scene = new Scene(loadFXML("MainView"));
        stage.setScene(scene);
        stage.setTitle(Lang.get("app.title"));
        stage.setResizable(false);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        Stage stage = (Stage) scene.getWindow();
        
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.sizeToScene(); 
        scene = newScene; 
    }
    
    //彈出視窗邏輯
    public static void showDialog(String fxml, String title) throws IOException {
        Parent root = loadFXML(fxml);

        Stage owner = (Stage) scene.getWindow();

        Stage dialog = new Stage();
        dialog.setTitle(title);
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL); 
        dialog.setResizable(false);
        dialog.setScene(new Scene(root));
        dialog.sizeToScene();

        dialog.showAndWait();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicUI.class.getResource(fxml + ".fxml"), Lang.bundle);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}