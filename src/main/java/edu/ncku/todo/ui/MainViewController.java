package edu.ncku.todo.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Locale;

import edu.ncku.todo.util.DataManager;
import edu.ncku.todo.model.Config;
import edu.ncku.todo.util.Lang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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

    private LocalDate today;
    private LocalDate focusMonth;
    private ArrayList<CellController> cellControllers = new ArrayList<>();
    private ContextMenu langMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initLanguageMenu();
        today = LocalDate.now();
        focusMonth = today.withDayOfMonth(1);
        drawCalendar();

        DataManager.getCategoryData().forEach(c -> {
            try {
                // add category tabs
                FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"), Lang.bundle);
                AnchorPane root = loader.load();
                TableController controller = loader.getController();

                controller.setTable(c);
                Tab tab = new Tab(c.getName(), root);
                tab.setStyle("-fx-background-color:rgba(208, 222, 255, 0.8);");

                categoryPane.getTabs().add(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    private void initLanguageMenu() {
        MenuItem en = new MenuItem("English");
        MenuItem zh_TW = new MenuItem("繁體中文");
        MenuItem zh_CN = new MenuItem("简体中文");
        en.setOnAction(_ -> {
            Config.set("lang", "en");
            Lang.setLocale(Config.getLocale());
            reloadUI();
        });

        zh_TW.setOnAction(_ -> {
            Config.set("lang", "zh-TW");
            Lang.setLocale(Config.getLocale());
            reloadUI();
        });

        zh_CN.setOnAction(_ -> {
            Config.set("lang", "zh-CN");
            Lang.setLocale(Config.getLocale());
            reloadUI();
        });

        langMenu = new ContextMenu(en, zh_TW, zh_CN);
        
        mainViewSettingButton.setOnMouseClicked(_ ->
            langMenu.show(mainViewSettingButton, Side.BOTTOM, 0, 0)
        );
    }
    
    private void reloadUI() {
        //更換語言後reload視窗
        try {
            GraphicUI.setRoot("mainView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // —— 上一月按鈕的處理
    @FXML
    private void onPrevMonth(ActionEvent e) {
        focusMonth = focusMonth.minusMonths(1);
        // 更新上方的「年」「月」文字
        yearText.setText(String.valueOf(focusMonth.getYear()));
        monthText.setText(focusMonth.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        updateCalendar();
    }

    // —— 下一月按鈕的處理
    @FXML
    private void onNextMonth(ActionEvent e) {
        focusMonth = focusMonth.plusMonths(1);
        // 更新上方的「年」「月」文字
        yearText.setText(String.valueOf(focusMonth.getYear()));
        monthText.setText(focusMonth.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        updateCalendar();
    }

    // —— 畫出 6×7 日曆格子的函式
    private void drawCalendar() {
        // 更新上方的「年」「月」文字
        yearText.setText(String.valueOf(focusMonth.getYear()));
        // monthText.setText(focusDate.getMonth().toString());
        monthText.setText(focusMonth.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));

        // 算偏移：Java 的 DayOfWeek.getValue() 回傳 1(Monday)~7(Sunday)
        // 我們要讓它變成 0=Sunday, 1=Monday, ...6=Saturday
        int offset = focusMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = focusMonth.getMonth().length(focusMonth.isLeapYear());

        // 動態產生 6×7 共 42 格
        for (int i = 0; i < 42; i++) {
            try {
                // add category tabs
                FXMLLoader loader = new FXMLLoader(getClass().getResource("calendarCell.fxml"), Lang.bundle);
                StackPane cell = loader.load();
                CellController controller = loader.getController();
                cellControllers.add(controller);
                updateCell(controller, i, offset, daysInMonth);
                calendarPane.getChildren().add(cell);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCalendar() {
        // 算偏移：Java 的 DayOfWeek.getValue() 回傳 1(Monday) ~ 7(Sunday)
        // 我們要讓它變成 0=Sunday, 1=Monday, ...6=Saturday
        int offset = focusMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = focusMonth.getMonth().length(focusMonth.isLeapYear());

        int i = 0;
        for (CellController controller : cellControllers) {
            updateCell(controller, i, offset, daysInMonth);
            i++;
        }
    }

    private void updateCell(CellController controller, int i, int offset, int daysInMonth) {
        // 計算這一格應該顯示的日子
        int day = i - offset + 1;
        if (day >= 1 && day <= daysInMonth) {
            LocalDate date = focusMonth.plusDays(day - 1);
            controller.set(date);

            // 今天有藍框
            if (focusMonth.getYear() == today.getYear() && focusMonth.getMonth() == today.getMonth()
                    && day == today.getDayOfMonth()) {
                controller.setTodayIndicator(true);
            } else {
                controller.setTodayIndicator(false);
            }
        } else {
            controller.set(); // 清空格子
        }
    }

    @FXML
    private void handleHover(MouseEvent e) {
        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #8495c4;");
    }

    @FXML
    private void handleExit(MouseEvent e) {
        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #7190de;");
    }
    
    @FXML
    private void handlePress(MouseEvent e) {
        Button btn = (Button)e.getSource();   
        btn.setStyle("-fx-background-color: #3d4f7a;");
    }    
    
    @FXML
    private void popupAddCategory(ActionEvent event) throws IOException {
        GraphicUI.showDialog("addCategory", "新增類別", null);
    }

    @FXML
    private void popupAddTask(ActionEvent event) throws IOException {
        GraphicUI.showDialog("addTask", "新增任務", null);
    }

    @FXML
    private void popupModifyCategory(ActionEvent event) throws IOException {
        GraphicUI.showDialog("ModifyCategory", "修改類別", null);
    }

    @FXML
    private void popupModifyTask(ActionEvent event) throws IOException {
        GraphicUI.showDialog("ModifyTask", "修改任務", null);
    }
}
