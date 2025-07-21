package Application;

import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Database db;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.db = new Database();
        db.createToDoTable();
    }

    public void start() {
        boolean active = true;
        System.out.println("Welcome to Plan-It!");

        while (active) {
            System.out.println("What would you like to do?");
            System.out.println("1. Add a new task");
            System.out.println("2. View all tasks");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Please enter the task name");
                    String taskName = scanner.nextLine();
                    System.out.println("Please enter the task description");
                    String taskDescription = scanner.nextLine();
                    System.out.println("Please enter the task priority (1-5)");
                    int taskPriority = scanner.nextInt();
                    ToDo task = new ToDo(taskName, taskDescription, taskPriority);
                    db.addTask(task);
                    break;

                case 2:
                    db.fetchTasks();
                    break;

                case 3:
                    active = false;
                    break;
            }
        }
    }
}
