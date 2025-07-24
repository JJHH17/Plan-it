package Application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Database db = new Database();

    @Override
    public void start(Stage primaryStage) {
        addTaskUI(primaryStage);


        primaryStage.setTitle("Plan-It");
        primaryStage.show();
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
