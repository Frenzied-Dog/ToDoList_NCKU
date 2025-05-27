package edu.ncku.todo.ui;

import edu.ncku.todo.model.Task;
import edu.ncku.todo.util.DataManager;
import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CellController {
    @FXML
    private Rectangle calendarBox;
    @FXML
    private Ellipse notificationSpot;
    @FXML
    private Label notificationLabel;
    @FXML
    private Tooltip notificationTips;
    @FXML
    private Text dayText;

    public void set() { 
        setTodayIndicator(false);
        dayText.setText("");
        notificationSpot.setVisible(false);
        notificationLabel.setVisible(false);
        notificationTips.setText("");
    }

    public void set(LocalDate date) {
        dayText.setText(String.valueOf(date.getDayOfMonth())); 
        List<Task> tasks = DataManager.getTasksByDate(date);
        if (tasks != null && !tasks.isEmpty()) {
            notificationSpot.setVisible(true);
            notificationLabel.setVisible(true);
            notificationLabel.setText(String.valueOf(tasks.size()));
            notificationTips.setText("Tasks: " + tasks.size());
        } else {
            notificationSpot.setVisible(false);
            notificationLabel.setVisible(false);
            notificationTips.setText("");
        }
    }

    public void setTodayIndicator(Boolean isToday) {
        if (isToday) {
            calendarBox.setStroke(Color.BLUE);
            calendarBox.setStrokeWidth(2);
        } else {
            calendarBox.setStroke(Color.LIGHTGRAY);
            calendarBox.setStrokeWidth(1);
        }
    }
}
