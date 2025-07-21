package Application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Database {

    private String dbURL;
    private String dbUsername;
    private String dbPassword;

    // Used to fetch database credentials from properties file
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

    // Used to create a table and input SQL as input
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

    // Used to actually create the table and the relevant fields
    public void createToDoTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ToDo (" +
                "Task_ID SERIAL PRIMARY KEY, " +
                "Task varchar(50) NOT NULL, " +
                "Description varchar(50) NOT NULL, " +
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
}
