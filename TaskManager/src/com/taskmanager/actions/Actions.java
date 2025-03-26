package com.taskmanager.actions;

import com.taskmanager.domain.factory.TaskFactory;
import com.taskmanager.domain.model.Task;
import com.taskmanager.service.TaskManagerService;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.utils.SortByDueDate;
import com.taskmanager.utils.SortById;
import com.taskmanager.utils.SortByTitle;
import com.taskmanager.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static com.taskmanager.utils.Utils.*;

public class Actions {

    private final static String CANCEL = "cancel";
    private static String userInput = "";
    private static Scanner scanner = new Scanner(System.in);
    private static TaskManagerService taskManagerService = new TaskManagerService();
    private static String[] mainMenuTextOptions = {
            "Add a New Task",
            "Update an Existing Task",
            "View All Tasks",
            "Mark a Task as Completed",
            "Remove a Task",
            "Exit"
    };
    private static String[] sortMenuTextOptions = {
            "Sort by Due Date",
            "Sort Alphabetically (Title)",
            "Sort by Task ID",
            "Sort by Task Category"
    };
    private static String[] categoryTextOptions = {
            "Work",
            "Personal"
    };

    public static String mainMenuUserInput() {
        int ascii = 0;
        char character = '\0';
        boolean isValidOption;
        do {
            isValidOption = true;
            displayOptions(mainMenuTextOptions);
            userInput = scanner.nextLine();

            if (userInput.length() > 1) isValidOption = false;
            if (isValidOption != false && userInput.length() > 0) {
                character = userInput.charAt(0);
                ascii = (int) character;
            }
            if (userInput.isEmpty() || userInput.isBlank()) {
                System.out.println();
                Utils.printErrorMessage("Input cannot be blank");
            } else if (ascii < 49 || ascii > 56) {
                System.out.println();
                Utils.printErrorMessage("Invalid option. Please use one of the options listed below:");
            }
        } while (userInput.isEmpty() || userInput.isBlank() || ascii < 49 || ascii > 56);
        return userInput;
    }

    private static void displayOptions(String[] textOptions) {
        int OptNumber = 0;
        for (int option = 0; option < textOptions.length; option++) {
            OptNumber = (option + 1);
            System.out.println( OptNumber + ": " + textOptions[option]);
        }
        System.out.format("Enter your choice (1-%s):", OptNumber);
    }

    public static void triggerOption(String choice) {
        switch (choice) {
            case "1":
                addTask();
                break;
            case "2":
                updateTask();
                break;
            case "3":
                viewTasks();
                break;
            case "4":
                markTaskCompleted();
                break;
            case "5":
                removeTask();
                break;
            case "6":
                System.out.println(0);
            default:
                Utils.printErrorMessage("Invalid option. Try again");
        }
    }

    private static void addTask() {
        printMessage("Enter Task Type (WORK/PERSONAL):");
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

        Task newTask = TaskFactory.createTask(type, taskManagerService.getTaskCount() + 1, title, description, dueDate, extraDetail);
        taskManagerService.addTask(newTask);
        Utils.printSuccessMessage("Task Created Successfully!");
    }

    private static void viewTasks() {
        System.out.println("Select how you would like to view your tasks:");
        displayOptions(sortMenuTextOptions);

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                taskManagerService.sortTasks(new SortByDueDate());
                display(taskManagerService.returnTasks());
                break;
            case 2:
                taskManagerService.sortTasks(new SortByTitle());
                display(taskManagerService.returnTasks());
                break;
            case 3:
                taskManagerService.sortTasks(new SortById());
                display(taskManagerService.returnTasks());
                break;
            case 4:
                chooseCategory();
                break;
            default:
                printErrorMessage("Invalid option. Try again.");
        }
    }

    private static void markTaskCompleted() {
        display(taskManagerService.returnTasks());
        printMessage("Enter task ID to complete: ");
        int id = Integer.parseInt(scanner.nextLine());
        Task task = taskManagerService.getTaskById(id);
        if (task != null) {
            task.markAsCompleted();
            System.out.println(taskManagerService.returnTask(id));
            Utils.printSuccessMessage("Task marked as completed.");
        } else {
            printErrorMessage("Task not found.");
        }
    }

    private static void removeTask() {
        display(taskManagerService.returnTasks());
        printMessage("Enter task ID to remove: ");
        int id = Integer.parseInt(scanner.nextLine());
        Task task = taskManagerService.getTaskById(id);
        if (task != null) {
            taskManagerService.removeTask(id);
            Utils.printSuccessMessage("Task removed successfully.");
        } else {
            printErrorMessage("Task not found.");
        }
    }

    static void chooseCategory() {
        printMessage("Choose category [WORK/PERSONAL]:");
        displayOptions(categoryTextOptions);
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                display(taskManagerService.displayByTaskType(TaskType.WORK));
                break;
            case 2:
                display(taskManagerService.displayByTaskType(TaskType.PERSONAL));
                break;
            default:
                printErrorMessage("Invalid option. Try again");
        }
    }

//================================================
//                                        UPDATE TASK
//================================================
        private static void updateTask() {
        String updateTitle;
        String updateDescription;
        String updateDueDate;
        String updateExtraDetail;
        boolean  updateCompleted = false;

        printMessage("Choose The Task To Update");
        display(taskManagerService.returnTasks());
        System.out.println("Enter Task ID: ");

        int choiceId = scanner.nextInt();
        scanner.nextLine();

        Task task = taskManagerService.returnTask(choiceId);

        checkIfTaskIsValid(task);

        printMessage("Original Task");
        task.displayTask();

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
            printErrorMessage("Invalid date format!");
            e.printStackTrace();
        }

        Task updatedTask = TaskFactory.createTask(task.getTaskType(), task.getId(), updateTitle, updateDescription, updatedDate, updateExtraDetail);
        updatedTask.setCompleted(updateCompleted);
        taskManagerService.updateTask(updatedTask);

        printSuccessMessage("Task Updated Successfully!");
    }

    private static void checkIfTaskIsValid(Task task) {
        if (task == null) {
            printErrorMessage("Task with ID: " + task.getId() + " not found.");
        }
    }

}
