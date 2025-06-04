package edu.ncku.todo.ui;

import edu.ncku.todo.model.Task;
import edu.ncku.todo.util.DataManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CellController {
    @FXML private Rectangle calendarBox;
    @FXML private Ellipse notificationSpot;
    @FXML private Label notificationLabel;
    @FXML private Tooltip notificationTips;
    @FXML private Text dayText;
    private LocalDate cellDate;

    @FXML
    private void popupTable(MouseEvent event) throws IOException {
        GraphicUI.showDialog("table", cellDate.format(Task.dateFormat), DataManager.getTasksByDate(cellDate));
    }


    public void set() {
        cellDate = null;
        setTodayIndicator(false);
        dayText.setText("");
        notificationSpot.setVisible(false);
        notificationLabel.setVisible(false);
        notificationTips.setText("");
    }

    public void set(LocalDate date) {
        this.cellDate = date;
        dayText.setText(String.valueOf(date.getDayOfMonth()));
        List<Task> tasks = DataManager.getTasksByDate(date);
        if (tasks != null && !tasks.isEmpty()) {
            notificationSpot.setVisible(true);
            notificationLabel.setVisible(true);
            notificationLabel.setText(String.valueOf(tasks.size()));
            String tips = tasks.stream()
                    .map(task -> task.getName() + " (" + task.getCategoryName() + ")")
                    .reduce((a, b) -> a + "\n" + b)
                    .orElse("");
            notificationTips.setText(tips);
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
