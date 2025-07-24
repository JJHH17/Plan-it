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
        // Todo: allow user to select what they wish to do on UI before presenting new scene
        selectOption(primaryStage);

        primaryStage.setTitle("Plan-It");
        primaryStage.show();
    }

    public void selectOption(Stage primaryStage) {
        Button addTask = new Button("Add Task");
        Button viewTasks = new Button("View Tasks");
        Button deleteTask = new Button("Delete Task");
        Button deleteAllTasks = new Button("Delete All Tasks");

        VBox initialStage = new VBox(10);
        initialStage.getChildren().addAll(addTask, viewTasks, deleteTask, deleteAllTasks);

        addTask.setOnAction(e -> {
            addTaskUI(primaryStage);
            initialStage.getChildren().clear();
        });

        Scene scene = new Scene(initialStage, 300, 250);
        primaryStage.setScene(scene);
    }

    public void addTaskUI(Stage primaryStage) {
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
