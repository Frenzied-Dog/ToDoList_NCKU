package edu.ncku.todo.ui;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import edu.ncku.todo.util.Lang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.time.format.TextStyle;
import java.util.Locale;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


public class MainViewController implements Initializable {

    // —— FXML 欄位
    // 以下參考自https://gist.github.com/Da9el00/f4340927b8ba6941eb7562a3306e93b6
    
    @FXML private FlowPane calendarPane;
    @FXML private Text yearText;
    @FXML private Text monthText;
    @FXML private TabPane categoryPane;
    @FXML private Button mainViewSettingButton;

    private ZonedDateTime today;
    private ZonedDateTime focusDate;
    private ContextMenu langMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initLanguageMenu();
        today     = ZonedDateTime.now();
        focusDate = today.withDayOfMonth(1);
        drawCalendar();

        // TODO: 這裡要改成讀取資料庫的任務資料
        try {
            // add category tabs
            Parent root = FXMLLoader.load(getClass().getResource("category.fxml"), Lang.bundle);
            Tab tab = new Tab("test", root);
            categoryPane.getTabs().add(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLanguageMenu() {
        //更換語言, 需再更改確認
        MenuItem ch = new MenuItem("中文");
        MenuItem en = new MenuItem("English");
        ch.setOnAction(e -> {
            //Lang.setLocale(Locale.chinese);
            reloadUI();
        });
        en.setOnAction(e -> {
            //Lang.setLocale(Locale.english);
            reloadUI();
        });
        
        langMenu = new ContextMenu(ch, en);
        
        mainViewSettingButton.setOnMouseClicked(e ->
            langMenu.show(mainViewSettingButton, Side.BOTTOM, 0, 0)
        );
    }
    
    private void reloadUI() {
        //更換語言後reload視窗
        try {
            GraphicUI.setRoot("mainView");  // 改成你自己的 FXML 名稱
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    // —— 畫出 6×7 日曆格子的函式
    private void drawCalendar() {
        // 更新上方的「年」「月」文字
        yearText .setText(String.valueOf(focusDate.getYear()));
        // monthText.setText(focusDate.getMonth().toString());
        monthText.setText(focusDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));

        // 算偏移：Java 的 DayOfWeek.getValue() 回傳 1(Monday)~7(Sunday)
        // 我們要讓它變成 0=Sunday,1=Monday,...6=Saturday
        int offset      = focusDate.getDayOfWeek().getValue() % 7;
        int daysInMonth = focusDate.getMonth()
                              .length(focusDate.toLocalDate().isLeapYear());

        // 動態產生 6×7 共 42 格
        for (int i = 0; i < 42; i++) {
            StackPane cell = new StackPane();
            cell.setPrefSize(60, 60);

            // 方塊
            Rectangle box = new Rectangle(60, 60);
            box.setFill(Color.TRANSPARENT);
            box.setStroke(Color.LIGHTGRAY);
            cell.getChildren().add(box);

            // 計算這一格應該顯示的日子
            int day = i - offset + 1;
            if (day >= 1 && day <= daysInMonth) {
                Text t = new Text(String.valueOf(day));
                cell.getChildren().add(t);

                // TODO: 如果有任務資料，就在這裡把該日任務用 Label/VBox 加到 cell
            }

            // 今天有籃框
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
        Button btn = (Button)e.getSource();   
        btn.setStyle("-fx-background-color: #8495c4;");
    }

    @FXML
    private void handleExit(MouseEvent e) {
        Button btn = (Button)e.getSource();
        btn.setStyle("-fx-background-color: #7190de;");
    }
    
    @FXML
    private void handlePress(MouseEvent e) {
        Button btn = (Button)e.getSource();   
        btn.setStyle("-fx-background-color: #3d4f7a;");
    }    
    
    @FXML
    private void switchToAddCategory(ActionEvent event) throws IOException {
        GraphicUI.showDialog("addCategory", "新增類別");
    }

    @FXML
    private void switchToAddTask(ActionEvent event) throws IOException {
        GraphicUI.showDialog("addTask", "新增任務");
    }

    @FXML
    private void switchToModifyCategory(ActionEvent event) throws IOException {
        GraphicUI.showDialog("ModifyCategory", "修改類別");
    }

    @FXML
    private void switchToModifyTask(ActionEvent event) throws IOException {
        GraphicUI.showDialog("ModifyTask", "修改任務");
    }
}
