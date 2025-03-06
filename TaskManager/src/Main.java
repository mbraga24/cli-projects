import com.taskmanager.Task;
import com.taskmanager.TaskManager;

import java.util.Date;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static TaskManager taskManager = TaskManager.getTaskManagerInstance();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nTask Manager CLI");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Remove Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    taskManager.listTasks();
                    /*
                        Display sorting options to user
                     */
                    break;
                case 3:
                    markTaskCompleted();
                    break;
                case 4:
                    removeTask();
                    break;
                case 5:
                    System.out.println(0);
                default:
                    System.out.println("Invalid option. Try again");
            }
        }
    }

    private static void addTask() {
        System.out.println("Enter Task Title: ");
        String title = scanner.nextLine();
        System.out.println("Enter Task Description: ");
        String description = scanner.nextLine();
        System.out.println("Enter Due Date (yyyy-mm-dd): ");
        Date dueDate = new Date();
        Task newTask = new Task(taskManager.getTaskCount() + 1, title, description, dueDate);
        taskManager.addTask(newTask);
        System.out.println("Task added successfully!");
    }

    private static void markTaskCompleted() {
        System.out.println("Enter task ID to complete: ");
        int id = Integer.parseInt(scanner.nextLine());
        Task task = taskManager.getTaskById(id);
        if (task != null) {
            task.markAsCompleted();
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void removeTask() {
        System.out.println("Enter task ID to remove: ");
        int id = Integer.parseInt(scanner.nextLine());
        Task task = taskManager.getTaskById(id);
        if (task != null) {
            taskManager.removeTask(id);
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }
}