package Application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class UserInterface extends Application {
    private Database db = new Database();

    @Override
    public void start(Stage primaryStage) {
        selectOption(primaryStage);
        primaryStage.setTitle("Plan-It");
        primaryStage.show();
    }

    /** Initial page of application, allows user to select what they want to do next */
    public void selectOption(Stage primaryStage) {
        Button addTask = new Button("Add Task");
        Button viewTasks = new Button("View Tasks");
        Button deleteTaskId = new Button("Delete Task (By Task ID)");
        Button deleteTaskName = new Button("Delete Task (By Task name)");
        Button deleteAllTasks = new Button("Delete All Tasks");
        Button editTask = new Button("Edit Task");

        VBox initialStage = new VBox(10);
        initialStage.getChildren().addAll(addTask, viewTasks, deleteTaskId, deleteTaskName, deleteAllTasks, editTask);

        addTask.setOnAction(e -> {
            addTaskUI(primaryStage);
            initialStage.getChildren().clear();
        });

        viewTasks.setOnAction(e -> {
            displayAllTasks(primaryStage);
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

        editTask.setOnAction(e -> {
            editTask(primaryStage);
            initialStage.getChildren().clear();
        });

        Scene scene = new Scene(initialStage, 300, 250);
        primaryStage.setScene(scene);
    }

    /** Allows user to add item to list */
    public void addTaskUI(Stage primaryStage) {
        Label taskLabel = new Label("Task");
        TextField taskField = new TextField();
        Label descriptionLabel = new Label("Description");
        TextField descriptionField = new TextField();
        Label priorityLabel = new Label("Priority");
        // Creation of priority dropdown modal
        ComboBox<Integer> priorityField = new ComboBox<>();
        priorityField.getItems().addAll(1, 2, 3, 4, 5);
        // Setting a default value
        priorityField.setValue(5);

        Button submitToDo = new Button("Submit");
        Button cancelButton = new Button("Back");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(taskLabel, taskField, descriptionLabel, descriptionField, priorityLabel, priorityField, submitToDo, cancelButton);

        /** Handles submission button */
        submitToDo.setOnAction(e -> {
            String task = taskField.getText();
            String description = descriptionField.getText();
            int priority = priorityField.getValue();

            // Data validation - else we push into the database if data is valid
            if (task.isEmpty() || description.isEmpty()) {
                Label errorLabel = new Label("Please enter a valid task and description");
                vbox.getChildren().addAll(errorLabel);
            } else if (task.length() > 50) {
                Label errorLabel = new Label("Task name cannot be longer than 50 characters");
                vbox.getChildren().addAll(errorLabel);
            } else if (description.length() > 100) {
                Label errorLabel = new Label("Description cannot be longer than 100 characters");
                vbox.getChildren().addAll(errorLabel);
            } else {
                ToDo newToDo = new ToDo(task, description, priority);
                db.addTask(newToDo);
                Label successLabel = new Label("Task added successfully");
                vbox.getChildren().addAll(successLabel);

                // Clears UI fields when submission is made
                taskField.clear();
                descriptionField.clear();

                // Clears existing items in scene
                vbox.getChildren().clear();
                selectOption(primaryStage);
            }
        });

        cancelButton.setOnAction(e -> {
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
    }

    /** Handles deletion functionality */
    public void idDeleteTaskUI(Stage primaryStage) {
        Label idLabel = new Label("Delete a Task (By Task ID)");
        Label deleteLabel = new Label("Please enter the task ID to delete");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");
        Button cancelButton = new Button("Back");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(idLabel, deleteLabel, idField, deleteButton, cancelButton);

        deleteButton.setOnAction(e -> {
            // Handling invalid entries (expecting Integers)
            try {
                int id = Integer.parseInt(idField.getText());
                db.deleteTask(id);
                vbox.getChildren().clear();
                selectOption(primaryStage);
                Label successLabel = new Label("Action completed successfully");
                vbox.getChildren().addAll(successLabel);
                idField.clear();

            } catch (NumberFormatException numberError) {
                Label errorLabel = new Label("Please enter a valid task ID");
                vbox.getChildren().addAll(errorLabel);
            }
        });

        cancelButton.setOnAction(e -> {
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
    }

    /** Allows user to delete items based on the task name */
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

    /** Allows user to delete all items */
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

    /** Displays all to do items */
    public void displayAllTasks(Stage primaryStage) {
        // Using an arraylist to store DB entries
        ArrayList<String> tasks = db.fetchTasks();

        Label allTasks = new Label("Tasks:");
        Label printTasks = new Label();
        for (String task : tasks) {
            printTasks.setText(printTasks.getText() + task + "\n");
        }

        Button returnButton = new Button("Back");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(allTasks, printTasks, returnButton);

        returnButton.setOnAction(e -> {
            vbox.getChildren().clear();
            selectOption(primaryStage);
        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
    }

    /** Used to edit an existing task */
    public void editTask(Stage primaryStage) {
        Label title = new Label("Edit a Task (By Task ID)");
        Label taskLabel = new Label("Task ID");
        TextField taskField = new TextField();
        Label descriptionLabel = new Label("Description");
        TextField descriptionField = new TextField();

        Label priorityLabel = new Label("Priority");
        // Creation of priority dropdown modal
        ComboBox<Integer> priorityField = new ComboBox<>();
        priorityField.getItems().addAll(1, 2, 3, 4, 5);
        // Setting a default value
        priorityField.setValue(5);

        // Controls whether an item is completed or not
        Label completeLabel = new Label("Completed?");
        ComboBox<Boolean> completeField = new ComboBox<>();
        completeField.getItems().addAll(true, false);
        completeField.setValue(false);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Back");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(title, taskLabel, taskField, descriptionLabel, descriptionField, priorityLabel, priorityField,
                completeLabel, completeField, submitButton, cancelButton);

        submitButton.setOnAction(e -> {
            int taskId = Integer.parseInt(taskField.getText());
            String description = descriptionField.getText();
            int priority = priorityField.getValue();
            boolean complete = completeField.getValue();
            db.editTask(taskId, description, priority, complete);
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

    /** Used to launch the application */
    public void launchApp() {
        launch();
    }
}
