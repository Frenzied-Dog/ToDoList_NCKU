/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.ncku.todo.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
        // TODO
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
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
