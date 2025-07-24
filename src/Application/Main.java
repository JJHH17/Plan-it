package Application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Database db = new Database();

    @Override
    public void start(Stage primaryStage) {
        selectOption(primaryStage);

        primaryStage.setTitle("Plan-It");
        primaryStage.show();
    }

    public void selectOption(Stage primaryStage) {
        Button addTask = new Button("Add Task");
        Button viewTasks = new Button("View Tasks");
        Button deleteTaskId = new Button("Delete Task (By Task ID)");
        Button deleteTaskName = new Button("Delete Task (By Task name)");
        Button deleteAllTasks = new Button("Delete All Tasks");

        VBox initialStage = new VBox(10);
        initialStage.getChildren().addAll(addTask, viewTasks, deleteTaskId, deleteTaskName, deleteAllTasks);

        addTask.setOnAction(e -> {
            addTaskUI(primaryStage);
            initialStage.getChildren().clear();
        });

        deleteTaskId.setOnAction(e -> {
            idDeleteTaskUI(primaryStage);
            initialStage.getChildren().clear();
        });

        deleteTaskName.setOnAction(e -> {
            nameDeleteTaskUI(primaryStage);
            initialStage.getChildren().clear();
        });

        deleteAllTasks.setOnAction(e -> {
            deleteAllTasksUI(primaryStage);
            initialStage.getChildren().clear();
        });

        Scene scene = new Scene(initialStage, 300, 250);
        primaryStage.setScene(scene);
    }

    public void addTaskUI(Stage primaryStage) {
        // TODO: Allow user to go back to initial stage from this page

        Label taskLabel = new Label("Task");
        TextField taskField = new TextField();
        Label descriptionLabel = new Label("Description");
        TextField descriptionField = new TextField();
        Label priorityLabel = new Label("Priority");
        TextField priorityField = new TextField();

        Button submitToDo = new Button("Submit");

        VBox vbox = new VBox(10); // 10 = spacing between the elements
        vbox.getChildren().addAll(taskLabel, taskField, descriptionLabel, descriptionField, priorityLabel, priorityField, submitToDo);

        // Handles submission button
        submitToDo.setOnAction(e -> {
            String task = taskField.getText();
            String description = descriptionField.getText();
            int priority = priorityField.getText().isEmpty() ? 0 : Integer.parseInt(priorityField.getText());

            // Feeds into DB function
            ToDo newToDo = new ToDo(task, description, priority);
            db.addTask(newToDo);

            // Clears UI fields when submission is made
            taskField.clear();
            descriptionField.clear();
            priorityField.clear();
            // Clears existing items in scene
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
    }

    public void idDeleteTaskUI(Stage primaryStage) {
        Label idLabel = new Label("Delete a Task (By Task ID)");
        Label deleteLabel = new Label("Please enter the task ID to delete");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");
        Button cancelButton = new Button("Back");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(idLabel, deleteLabel, idField, deleteButton, cancelButton);

        deleteButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            db.deleteTask(id);
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        cancelButton.setOnAction(e -> {
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
    }

    public void nameDeleteTaskUI(Stage primaryStage) {
        Label nameLabel = new Label("Delete a Task (By Task name)");
        Label deleteLabel = new Label("Please enter the task name to delete");
        TextField nameField = new TextField();
        Button deleteButton = new Button("Delete");
        Button cancelButton = new Button("Back");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(nameLabel, deleteLabel, nameField, deleteButton, cancelButton);

        deleteButton.setOnAction(e -> {
            String name = nameField.getText();
            db.deleteTask(name);
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        cancelButton.setOnAction(e -> {
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
    }

    public void deleteAllTasksUI(Stage primaryStage) {
        Label deleteAll = new Label("Are you sure you want to delete all tasks?");
        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Back");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(deleteAll, confirmButton, cancelButton);

        confirmButton.setOnAction(e -> {
            db.deleteAllTasks();
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        cancelButton.setOnAction(e -> {
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
        // UserInterface ui = new UserInterface();
        // ui.start();
    }
}
