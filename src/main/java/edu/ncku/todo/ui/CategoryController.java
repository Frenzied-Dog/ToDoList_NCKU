package edu.ncku.todo.ui;

import edu.ncku.todo.model.Category;
import edu.ncku.todo.model.Task;
import edu.ncku.todo.model.TaskStatus;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CategoryController {

    @FXML
    TableView<Task> categoryTable;
    @FXML
    TableColumn<Task, String> taskNameColumn;
    @FXML
    TableColumn<Task, LocalDate> taskDueDateColumn;
    @FXML
    TableColumn<Task, TaskStatus> taskStatusColumn;

    public void setTable(Category category) {
        categoryTable.getItems().clear();
        categoryTable.getItems().addAll(category.getTasks());

        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        taskDueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());
        taskStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        taskDueDateColumn.setCellFactory( _ -> new TableCell<Task, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
    }
}