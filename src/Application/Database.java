package Application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

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
}
