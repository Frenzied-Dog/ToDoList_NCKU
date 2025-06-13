package edu.ncku.todo.ui;

import edu.ncku.todo.model.Category;
import edu.ncku.todo.model.Task;
import edu.ncku.todo.model.TaskStatus;
import edu.ncku.todo.util.Lang;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    @FXML private TableView<Task> table;
    @FXML private TableColumn<Task, String> taskNameColumn;
    @FXML private TableColumn<Task, LocalDate> taskDueDateColumn;
    @FXML private TableColumn<Task, TaskStatus> taskStatusColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        taskNameColumn.setReorderable(false);
        taskDueDateColumn.setReorderable(false);
        taskStatusColumn.setReorderable(false);
        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        taskDueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());
        taskStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        table.getSelectionModel().setCellSelectionEnabled(false);
        table.setStyle("-fx-background-color: #b9cceb;");
        table.setPlaceholder(new Label(Lang.get("gui.emptyCategory")));


        taskNameColumn.setCellFactory(_ -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setStyle("-fx-background-color: transparent; " + 
                            "-fx-border-color: gray; " +
                            "-fx-border-width: 0 1 0 0;");

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                    setTextFill(Color.BLACK);
                }
            }
        );

        taskDueDateColumn.setCellFactory(_ -> new TableCell<>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    setStyle("-fx-background-color: transparent; " +
                            "-fx-border-color: gray; " +
                            "-fx-border-width: 0 1 0 0;");

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.format(Task.dateFormat));
                    }
                    setTextFill(Color.BLACK);
                }
            }
        );

        taskStatusColumn.setCellFactory(_ -> new TableCell<>() {
                @Override
                protected void updateItem(TaskStatus item, boolean empty) {
                    super.updateItem(item, empty);

                    setStyle("-fx-background-color: transparent; " +
                            "-fx-border-color: gray; " +
                            "-fx-border-width: 0 1 0 0;");

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                    setTextFill(Color.BLACK);
                }
            }
        );

        table.setRowFactory(_ -> new TableRow<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setStyle("-fx-background-color: #b9cceb;");
                } else if (isSelected()) {
                    setStyle("-fx-background-color: #7ca5d4;");
                } else {
                    setStyle("-fx-background-color: #b9cceb;");
                }
            }
        });
    }

    public void setTable(Category category) {
        table.getItems().clear();
        table.getItems().addAll(category.getTasks());
    }

    public void setTable(List<Task> tasks) {
        table.getItems().clear();
        table.getItems().addAll(tasks);
    }

    public Task getNowSelectedTask() {
        return table.getSelectionModel().getSelectedItem();
    }

}