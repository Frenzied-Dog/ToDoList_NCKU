package edu.ncku.todo.ui;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainViewController implements Initializable {

    // —— FXML 欄位，對應到 MainView.fxml 裡的 fx:id
    @FXML private FlowPane calendarPane;
    @FXML private Text yearText;
    @FXML private Text monthText;

    //—— 私有欄位，用來記錄今天和當前聚焦的月份
    private ZonedDateTime today;
    private ZonedDateTime focusDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        today     = ZonedDateTime.now();
        focusDate = today.withDayOfMonth(1);
        drawCalendar();
    }

    // —— 上一月按鈕的處理
    @FXML
    private void onPrevMonth(ActionEvent e) {
        focusDate = focusDate.minusMonths(1);
        calendarPane.getChildren().clear();
        drawCalendar();
    }

    // —— 下一月按鈕的處理
    @FXML
    private void onNextMonth(ActionEvent e) {
        focusDate = focusDate.plusMonths(1);
        calendarPane.getChildren().clear();
        drawCalendar();
    }

    // —— 真正畫出 6×7 日曆格子的函式
    private void drawCalendar() {
        // 更新上方的「年」「月」文字
        yearText .setText(String.valueOf(focusDate.getYear()));
        monthText.setText(focusDate.getMonth().toString());

        // 算偏移：Java 的 DayOfWeek.getValue() 回傳 1(Monday)~7(Sunday)
        // 我們要讓它變成 0=Sunday,1=Monday,...6=Saturday
        int offset      = focusDate.getDayOfWeek().getValue() % 7;
        int daysInMonth = focusDate.getMonth()
                              .length(focusDate.toLocalDate().isLeapYear());

        // 動態產生 6×7 共 42 格
        for (int i = 0; i < 42; i++) {
            StackPane cell = new StackPane();
            cell.setPrefSize(60, 60);

            // a) 純框線的方塊
            Rectangle box = new Rectangle(60, 60);
            box.setFill(Color.TRANSPARENT);
            box.setStroke(Color.LIGHTGRAY);
            cell.getChildren().add(box);

            // b) 計算這一格應該顯示的日子
            int day = i - offset + 1;
            if (day >= 1 && day <= daysInMonth) {
                Text t = new Text(String.valueOf(day));
                cell.getChildren().add(t);

                // TODO: 如果有任務資料，就在這裡把該日任務用 Label/VBox 加到 cell
            }

            // c) 今天高亮
            if (focusDate.getYear()  == today.getYear() &&
                focusDate.getMonth() == today.getMonth() &&
                day == today.getDayOfMonth()) {
                box.setStroke(Color.BLUE);
                box.setStrokeWidth(2);
            }

            calendarPane.getChildren().add(cell);
        }
    }
    
    //@FXML private Button mainViewAddBotton;
    //@FXML private Button mainViewModBotton;
    //@FXML private Button mainViewSetBotton;

    @FXML
    private void handleHover(MouseEvent e) {
        Button btn = (Button)e.getSource();    // 取得滑到的那顆按鈕
        btn.setStyle("-fx-background-color: #8495c4;");
    }

    @FXML
    private void handleExit(MouseEvent e) {
        Button btn = (Button)e.getSource();
        btn.setStyle("-fx-background-color: #7190de;");
    }
}
