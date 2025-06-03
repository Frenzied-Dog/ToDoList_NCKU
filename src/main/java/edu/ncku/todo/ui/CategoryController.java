package edu.ncku.todo.ui;

import edu.ncku.todo.model.Category;
import edu.ncku.todo.model.Task;
import edu.ncku.todo.model.TaskStatus;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CategoryController {

    @FXML
    private TableView<Task> categoryTable;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private TableColumn<Task, LocalDate> taskDueDateColumn;

    @FXML
    private TableColumn<Task, TaskStatus> taskStatusColumn;

    public void setTable(Category category) {
        categoryTable.getItems().clear();
        categoryTable.getItems().addAll(category.getTasks());

        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        taskDueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());
        taskStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        categoryTable.getSelectionModel().setCellSelectionEnabled(false);

        taskNameColumn.setCellFactory(_ -> {
            TableCell<Task, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setStyle("-fx-background-color: transparent;  " +
                            "-fx-border-color: gray;  " +
                            "-fx-border-width: 0 1 0 0;");

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                    setTextFill(Color.BLACK);
                }
            };

            return cell;
        });

        taskDueDateColumn.setCellFactory(_ -> {
            TableCell<Task, LocalDate> cell = new TableCell<>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    setStyle("-fx-background-color: transparent;  " +
                            "-fx-border-color: gray;  " +
                            "-fx-border-width: 0 1 0 0;");

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.format(formatter));
                    }
                    setTextFill(Color.BLACK);
                }
            };

            return cell;
        });

        taskStatusColumn.setCellFactory(_ -> {
            TableCell<Task, TaskStatus> cell = new TableCell<>() {
                @Override
                protected void updateItem(TaskStatus item, boolean empty) {
                    super.updateItem(item, empty);

                    setStyle("-fx-background-color: transparent;  " +
                            "-fx-border-color: gray;  " +
                            "-fx-border-width: 0 1 0 0;");

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                    setTextFill(Color.BLACK);
                }
            };

            return cell;
        });

        categoryTable.setRowFactory(_ -> new TableRow<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setStyle("-fx-background-color: #b9cceb;");
                } else if (isSelected()) {
                    setStyle("-fx-background-color:rgb(124, 165, 212);");
                } else {
                    setStyle("-fx-background-color: #b9cceb;");
                }
            }
        });
    }
}
