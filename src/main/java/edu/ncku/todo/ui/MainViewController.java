package edu.ncku.todo.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Locale;

import edu.ncku.todo.model.Config;
import edu.ncku.todo.util.DataManager;
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
import javafx.scene.text.Text;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


public class MainViewController extends ButtonBehavior implements Initializable {
    @FXML private FlowPane calendarPane;
    @FXML private Text yearText;
    @FXML private Text monthText;
    @FXML private TabPane categoryPane;
    @FXML private Button settingButton;

    private LocalDate today;
    private LocalDate focusMonth;
    private ArrayList<CellController> cellControllers = new ArrayList<>();
    private ArrayList<TableController> tableControllers = new ArrayList<>();
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
                tableControllers.add(controller);
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
        
        settingButton.setOnMouseClicked(_ ->
            langMenu.show(settingButton, Side.BOTTOM, 0, 0)
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
                FXMLLoader loader = GraphicUI.getFXML("calendarCell");
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
    private void popupAddCategory(ActionEvent event) throws IOException {
        GraphicUI.showDialog("addCategory", Lang.get("ui.addCategory"), null);
        reloadUI();
    }

    @FXML
    private void popupAddTask(ActionEvent event) throws IOException {
        GraphicUI.showDialog("addTask", Lang.get("ui.addTask"), null);
        reloadUI();
    }

    @FXML
    private void popupModifyCategory(ActionEvent event) throws IOException {
        int index = categoryPane.getSelectionModel().getSelectedIndex();
        GraphicUI.showDialog("modifyCategory", Lang.get("ui.modifyCategory"), DataManager.getCategoryStrList().get(index));
        reloadUI();
    }

    @FXML
    private void popupModifyTask(ActionEvent event) throws IOException {
        int index = categoryPane.getSelectionModel().getSelectedIndex();
        GraphicUI.showDialog("modifyTask", Lang.get("ui.modifyTask"), tableControllers.get(index).getNowSelectedTask());
        reloadUI();
    }
}
