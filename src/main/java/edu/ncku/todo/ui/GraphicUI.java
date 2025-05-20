package edu.ncku.todo.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GraphicUI extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //scene = new Scene(loadFXML("primary"), 640, 480);
        //scene = new Scene(loadFXML("MainView"), 819, 548);
        scene = new Scene(loadFXML("MainView"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        Stage stage = (Stage) scene.getWindow();
        
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.sizeToScene(); 
        scene = newScene; 
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicUI.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}