# Plan-it
- Plan-it is a To Do application built using Postgresql (for storage) and JavaFX (for a frontend/GUI).
- This project was built using version 21 of the Java SDK.

The application allows user to:
- Add new To Do items to the Database.
- Edit existing To Do items.
- Delete To Do items.
- Delete all items.
- Fetch a list of all existing items.

```Project aims and objectives```
- Use Postgresql to add, edit and remove entries.
- Use JavaFX for frontend/GUI.

```Installation steps and instructions```

```Frontend - JavaFX```
1. Install and then extract the JavaFX SDK for your OS: https://gluonhq.com/products/javafx/
2. Go to "File > Project Structure" (Intellij IDEA), add the jar files within the lib folder within the JavaFX SDK 
in "Libraries".
3. Then go to "Run > Edit Configurations > VM Options" and add ```--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.
fxml```

4. This will then run the application via JavaFX once configured.


```Postgresql integration```

- The project has been built using/integrating with a locally hosted Postgresql server.
- Create a db.properties file in the root of the project.
- Use the db.properties.template file and populate the relevant database credentials presented.

1. Install Postgresql on your device.
2. Install the Postgresql JDBC driver.
3. Add the relevant driver .jar files via: "File > Project Structure > Libraries".

From here, you'll be able to link and authenticate your local server (Once created in Postgres) via the properties file.
The code for authentication can be found in the Database.java class.

```Project Overview and what's possible```

The application can be executed by running the Main.java class.

Here's an example of how the application looks when executed:

<img width="402" height="462" alt="image" src="https://github.com/user-attachments/assets/dca0bdbc-4c95-4d53-aad5-9d80fc79f95a" />

From here, users can:
- Add tasks.
- View all tasks.
- Delete tasks (based on their assigned task ID).
- Delete tasks (based on their name - duplicate tasks will also be deleted if they have the same name).
- Delete all tasks.
- Edit existing tasks.
