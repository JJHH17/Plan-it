package Application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.ArrayList;

public class Database {
    private String dbURL;
    private String dbUsername;
    private String dbPassword;

    /** Used to fetch database credentials from properties file */
    public Database() {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("db.properties")) {
            prop.load(input);
        } catch (Exception e) {
            System.out.println("There was a problem fetching database credentials");
            e.printStackTrace();
        }

        this.dbURL = prop.getProperty("db.url");
        this.dbUsername = prop.getProperty("db.username");
        this.dbPassword = prop.getProperty("db.password");
    }

    /** Used to create a table and input SQL as input */
    public void tableCreationHelper(String sql) {
        // Establishes connection with database
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            Statement statement = connection.createStatement();
            statement.execute(sql);
            System.out.println("Table created successfully");
            connection.close();

        } catch (Exception e) {
            System.out.println("There was a problem creating the database table");
            e.printStackTrace();
        }
    }

    /** Used to actually create the table and the relevant fields */
    public void createToDoTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ToDo (" +
                "Task_ID SERIAL PRIMARY KEY, " +
                "Task varchar(50) NOT NULL, " +
                "Description varchar(100) NOT NULL, " +
                "Complete bool NOT NULL, " +
                "Priority int NOT NULL);";

        tableCreationHelper(sql);
    }

    public void addTask(ToDo task) {
        String sql = "INSERT INTO todo (Task, Description, Complete, Priority) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement prepared = connection.prepareStatement(sql)) {

            prepared.setString(1, task.getTask());
            prepared.setString(2, task.getDescription());
            prepared.setBoolean(3, task.isComplete());
            prepared.setInt(4, task.getPriority());
            prepared.execute();

            System.out.println("Task added successfully");

        } catch (SQLException e) {
            System.out.println("There was an error adding the task to the database");
            e.printStackTrace();
        }
    }

    public ArrayList<String> fetchTasks() {
        ArrayList<String> response = new ArrayList<>();
        String sql = "SELECT * FROM todo";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement prepared = connection.prepareStatement(sql)) {

            ResultSet result = prepared.executeQuery();

            while (result.next()) {
                int taskId = result.getInt("Task_ID");
                String task = result.getString("Task");
                String description = result.getString("Description");
                boolean isComplete = result.getBoolean("Complete");
                int priority = result.getInt("Priority");

                response.add("ID: " + taskId + " Task: " + task + " Description: " + description + " Priority: " + priority + " Complete: " + isComplete);
            }

        } catch (SQLException e) {
            System.out.println("There was an error fetching tasks from the database");
            e.printStackTrace();
        }

        return response;
    }

    /** Deletes task based on given task ID */
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM todo WHERE Task_ID = ?";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement prepared = connection.prepareStatement(sql)) {

            prepared.setInt(1, taskId);
            int rowsDeleted = prepared.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Task deleted successfully");
            } else {
                System.out.println("Task with ID: " + taskId + " not found");
            }

        } catch (SQLException e) {
            System.out.println("There was an error deleting the task from the database");
            e.printStackTrace();
        }
    }

    /** Deletes task based on a given task name. Deletes all instances of that task name */
    public void deleteTask(String task) {
        String sql = "DELETE FROM todo WHERE Task = ?";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement prepared = connection.prepareStatement(sql)) {

            prepared.setString(1, task);
            int rowsDelete = prepared.executeUpdate();
            if (rowsDelete > 0) {
                System.out.println("Task deleted successfully");
            } else {
                System.out.println("Task with name: " + task + " not found");
            }

        } catch (SQLException e) {
            System.out.println("There was an error deleting the task from the database");
            e.printStackTrace();
        }
    }

    public void deleteAllTasks() {
        String sql = "DELETE FROM todo;";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement prepared = connection.prepareStatement(sql)) {

            int rowsDelete = prepared.executeUpdate();
            if (rowsDelete > 0) {
                System.out.println("All tasks deleted successfully");
            } else {
                System.out.println("No tasks found");
            }

        } catch (SQLException e) {
            System.out.println("There was an error deleting the tasks from the database");
            e.printStackTrace();
        }
    }

    public void editTask(int taskId, String newDescription, int priority, boolean complete) {
        String sql = "UPDATE todo SET Description = ?, Priority = ?, Complete = ? WHERE Task_ID = ?";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement prepared = connection.prepareStatement(sql)) {

            prepared.setString(1, newDescription);
            prepared.setInt(2, priority);
            prepared.setBoolean(3, complete);
            prepared.setInt(4, taskId);

            int rowsUpdated = prepared.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Task updated successfully");
            } else {
                System.out.println("Task with ID: " + taskId + " not found");
            }

        } catch (SQLException e) {
            System.out.println("There was an error updating the task in the database");
            e.printStackTrace();
        }
    }
}
