import com.taskmanager.factory.TaskFactory;
import com.taskmanager.storage.TaskStorage;
import com.taskmanager.task.PersonalTask;
import com.taskmanager.task.Task;
import com.taskmanager.service.TaskManager;
import com.taskmanager.task.TaskType;
import com.taskmanager.task.WorkTask;
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
        System.out.println("Enter Task Type (WORK/PERSONAL):");
        String taskTypeInput = scanner.nextLine().toUpperCase();
        TaskType type = TaskType.valueOf(taskTypeInput);

        System.out.println("Enter Task Title: ");
        String title = scanner.nextLine();
        System.out.println("Enter Task Description: ");
        String description = scanner.nextLine();

        System.out.println(type == TaskType.WORK ? "Enter Project Name: " : "Enter Location: ");
        String extraDetail  = scanner.nextLine();

        System.out.println("Enter Due Date (yyyy-mm-dd): ");
        Date dueDate = new Date();

        Task newTask = TaskFactory.createTask(type, taskManager.getTaskCount() + 1, title, description, dueDate, extraDetail);
        taskManager.addTask(newTask);
        System.out.println("====================");
        System.out.println("Task Created Successfully!");
        System.out.println("====================");
    }

    private static void updateTask() {
        String updateTitle;
        String updateDescription;
        String updateDueDate;
        String updateExtraDetail;
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

        System.out.format("Enter new title (press Enter to keep  [ %s ]):\n", task.getTitle());
        updateTitle = scanner.nextLine();
        if (updateTitle.isEmpty()) {
            updateTitle = task.getTitle();
        }

        System.out.format("Enter new description (press Enter to keep [ %s ]):\n", task.getDescription());
        updateDescription = scanner.nextLine();
        if (updateDescription.isEmpty()) {
            updateDescription = task.getDescription();
        }

        String extraDetail = task.getTaskType() == TaskType.WORK ? "Project Name" : "Location";

        System.out.format("Enter new %s (press Enter to keep [%s]):\n", extraDetail, extraDetail);
        updateExtraDetail = scanner.nextLine();
        if (updateExtraDetail.isEmpty()) {
            updateExtraDetail = task.getExtraDetails();
        }

        System.out.format("Enter new Due Date (press Enter to keep [ %s ]):\n", task.getDueDate());
        updateDueDate = scanner.nextLine();
        if (updateDueDate.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
            updateDueDate = formatter.format(task.getDueDate());
        }

        System.out.format("Mark as completed? (y/n, current: %s):\n", task.getCompleted());
        String completedInput = scanner.nextLine();
        if (completedInput.equalsIgnoreCase("y")) {
            updateCompleted = true;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        Date updatedDate = null;

        try {
            // Convert String to Date
            updatedDate = formatter.parse(updateDueDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format!");
            e.printStackTrace();
        }

        Task updatedTask = TaskFactory.createTask(task.getTaskType(), task.getId(), updateTitle, updateDescription, updatedDate, updateExtraDetail);
        updatedTask.setCompleted(updateCompleted);
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
        System.out.println("  4. Sort by Task Category");
        System.out.print("Enter your choice (1-4): ");

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
            case 4:
                chooseCategory();
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

    static void chooseCategory() {
        System.out.println("Choose category [WORK/PERSONAL]:");
        System.out.println("1. Work");
        System.out.println("2. Personal");
        System.out.print("Enter your choice (1-2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                taskManager.displayByTaskType(TaskType.WORK);
                break;
            case 2:
                taskManager.displayByTaskType(TaskType.PERSONAL);
                break;
            default:
                System.out.println("Invalid option. Try again");
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

        Task defaultPersonalTask1 = new PersonalTask("Make your bed", "Accomplish first task of the day.", date1, "home");
        Task defaultPersonalTask2 = new PersonalTask("Wash dishes", "Wash dishes after lunch.", date2, "home");
        Task defaultPersonalTask3 = new PersonalTask("Clean bathtub", "Clean bathtub once a week.", date3, "home");
        Task defaultWorkTask1 = new WorkTask("Work on meeting documents", "Review Thursday 3pm meeting.", date3, "Project Name1");

        taskManager.addTask(defaultPersonalTask1);
        taskManager.addTask(defaultPersonalTask2);
        taskManager.addTask(defaultPersonalTask3);
        taskManager.addTask(defaultWorkTask1);
    }
}