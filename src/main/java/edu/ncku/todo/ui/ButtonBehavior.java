package edu.ncku.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

public abstract class ButtonBehavior {
    @FXML
    private void handleHover(MouseEvent e) {
        if (!(e.getSource() instanceof Button)) {
            return; // 如果不是按鈕，則不處理
        }

        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #8495c4;");
    }

    
    @FXML
    private void handleExit(MouseEvent e) {
        if (!(e.getSource() instanceof Button)) {
            return; // 如果不是按鈕，則不處理
        }

        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #7190de;");
    }
    
    @FXML
    private void handlePress(MouseEvent e) {
        if (!(e.getSource() instanceof Button)) {
            return; // 如果不是按鈕，則不處理
        }

        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #3d4f7a;");
    }

    @FXML
    private void handleRelease(MouseEvent e) {    
        if (!(e.getSource() instanceof Button)) {
            return; // 如果不是按鈕，則不處理
        }
        
        // 判斷滑鼠當前座標是否在按鈕上
        Button btn = (Button) e.getSource();
        double mouseX = e.getSceneX();
        double mouseY = e.getSceneY();
        double btnMinX = btn.localToScene(btn.getBoundsInLocal()).getMinX();
        double btnMaxX = btn.localToScene(btn.getBoundsInLocal()).getMaxX();
        double btnMinY = btn.localToScene(btn.getBoundsInLocal()).getMinY();
        double btnMaxY = btn.localToScene(btn.getBoundsInLocal()).getMaxY();

        if (mouseX < btnMinX || mouseX > btnMaxX || mouseY < btnMinY || mouseY > btnMaxY) {
            // 如果在按鈕上，則恢復原來的樣式
            btn.setStyle("-fx-background-color: #7190de;");
        } else {
            btn.setStyle("-fx-background-color: #8495c4;");
        }        
    }
}
