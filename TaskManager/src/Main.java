import com.taskmanager.Task;
import com.taskmanager.TaskManager;
import com.taskmanager.util.SortByDueDate;
import com.taskmanager.util.SortById;
import com.taskmanager.util.SortByTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static TaskManager taskManager = TaskManager.getTaskManagerInstance();

    public static void main(String[] args) {

        addDemoTasks();

        while (true) {
            System.out.println("\n===== Task Manager CLI =====");
            System.out.println("Select an option:");
            System.out.println("  1. Add a New Task");
            System.out.println("  2. Update an Existing Task");
            System.out.println("  3. View All Tasks");
            System.out.println("  4. Mark a Task as Completed");
            System.out.println("  5. Remove a Task");
            System.out.println("  6. Exit");
            System.out.print("Enter your choice (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    updateTask();
                    break;
                case 3:
                    viewTasks();
                    break;
                case 4:
                    markTaskCompleted();
                    break;
                case 5:
                    removeTask();
                    break;
                case 6:
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

    private static void updateTask() {
        String updateTitle;
        String updateDescription;
        String updateDueDate;
        boolean  updateCompleted = false;

        System.out.println("===== Choose The Task You Would Like To Update =====");
        taskManager.listTasks();
        System.out.println("Enter Task ID: ");

        int choiceId = scanner.nextInt();
        scanner.nextLine();

        Task task = taskManager.returnTask(choiceId);

        checkIfTaskIsValid(task);

        System.out.println("=== Original Task ===");
        task.displayTask();
        System.out.println("===============");

        System.out.println("Enter new title (press Enter to keep  [" + task.getTitle() + "]): ");
        updateTitle = scanner.nextLine();
        if (updateTitle.isEmpty()) {
            updateTitle = task.getTitle();
        }

        System.out.println("Enter new description (press Enter to keep [" + task.getDescription() + "])");
        updateDescription = scanner.nextLine();
        if (updateDescription.isEmpty()) {
            updateDescription = task.getDescription();
        }

        System.out.println("Enter new Due Date (press Enter to keep [" + task.getDueDate() + "])");
        updateDueDate = scanner.nextLine();
        if (updateDueDate.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            updateDueDate = formatter.format(task.getDueDate());
        }

        System.out.print("Mark as completed? (y/n, current: " + task.getCompleted() + "): ");
        String completedInput = scanner.nextLine();
        if (completedInput.equalsIgnoreCase("y")) {
            updateCompleted = true;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date updatedDate = null;

        try {
            // Convert String to Date
            updatedDate = formatter.parse(updateDueDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format!");
            e.printStackTrace();
        }

        Task updatedTask = new Task(task.getId(), updateTitle, updateDescription, updatedDate, updateCompleted);
        taskManager.updateTask(updatedTask);

        System.out.println("====================");
        System.out.println("Task Updated Successfully!");
        System.out.println("====================");
    }

    private static void viewTasks() {
        System.out.println("\n===== Task Manager CLI =====");
        System.out.println("Select how you would like to view your tasks:");
        System.out.println("  1. Sort by Due Date");
        System.out.println("  2. Sort Alphabetically (Title)");
        System.out.println("  3. Sort by Task ID");
        System.out.print("Enter your choice (1-3): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                taskManager.sortTasks(new SortByDueDate());
                taskManager.listTasks();
                break;
            case 2:
                taskManager.sortTasks(new SortByTitle());
                taskManager.listTasks();
                break;
            case 3:
                taskManager.sortTasks(new SortById());
                taskManager.listTasks();
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
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

    private static void checkIfTaskIsValid(Task task) {
        if (task == null) {
            System.out.println("Task with ID: " + task.getId() + " not found.");
        }
    }

    private static void addDemoTasks() {
        LocalDate localDatePlusTwo = LocalDate.now().plusDays(2);
        LocalDate localDatePlusThree = LocalDate.now().plusDays(3);
        LocalDate localDatePlusFive = LocalDate.now().plusDays(5);

        Date date1 = Date.from(localDatePlusThree.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from(localDatePlusTwo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date3 = Date.from(localDatePlusFive.atStartOfDay(ZoneId.systemDefault()).toInstant());

        int firstTaskId = taskManager.getTaskCount() + 1;

        Task defaultTask1 = new Task(firstTaskId, "Make your bed", "Accomplish first task of the day.", date1);
        Task defaultTask2 = new Task(firstTaskId + 1, "Wash dishes", "Wash dishes after lunch.", date2);
        Task defaultTask3 = new Task(firstTaskId + 2, "Clean bathtub", "Clean bathtub once a week.", date3);

        taskManager.addTask(defaultTask1);
        taskManager.addTask(defaultTask2);
        taskManager.addTask(defaultTask3);
    }
}