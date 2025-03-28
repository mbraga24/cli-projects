package com.taskmanager.actions;

import com.taskmanager.domain.factory.TaskFactory;
import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.io.ConsoleIO;
import com.taskmanager.service.TaskManagerService;
import com.taskmanager.utils.SortByDueDate;
import com.taskmanager.utils.SortById;
import com.taskmanager.utils.SortByTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static com.taskmanager.utils.Utils.display;
import static com.taskmanager.utils.Utils.printMessageHeader;

public class Actions {

    private Scanner scanner = new Scanner(System.in);
    private final ConsoleIO io = new ConsoleIO(scanner);
    private TaskManagerService taskManagerService = new TaskManagerService();
    private String userInput = "";
    private String[] mainMenuTextOptions = {
            "Add a New Task",
            "Update an Existing Task",
            "View All Tasks",
            "Mark a Task as Completed",
            "Remove a Task",
            "Exit"
    };
    private String[] sortMenuTextOptions = {
            "Sort by Due Date",
            "Sort Alphabetically (Title)",
            "Sort by Task ID",
            "Sort by Task Category"
    };
    private String[] categoryTextOptions = {
            "Work",
            "Personal"
    };

    public String mainMenuUserInput() {
        int ascii = 0;
        char character = '\0';
        boolean isValidOption;
        do {
            isValidOption = true;
            displayOptions(mainMenuTextOptions);
            userInput = io.readLineString();

            if (userInput.length() > 1) isValidOption = false;
            if (isValidOption != false && userInput.length() > 0) {
                character = userInput.charAt(0);
                ascii = (int) character;
            }
            if (userInput.isEmpty() || userInput.isBlank()) {
                io.printError("Input cannot be blank");
            } else if (ascii < 49 || ascii > 56) {
                io.printError("Invalid option. Please use one of the options listed above.");
            }
        } while (userInput.isEmpty() || userInput.isBlank() || ascii < 49 || ascii > 56);
        return userInput;
    }

    private void displayOptions(String[] textOptions) {
        int OptNumber = 0;
        for (int option = 0; option < textOptions.length; option++) {
            OptNumber = (option + 1);
            io.print( OptNumber + ": " + textOptions[option]);
        }
        io.print("Enter your choice: (1-" + OptNumber +")");
    }

    public void triggerOption(String choice) {
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
                break;
            default:
                io.printError("Invalid option. Try again");
        }
    }

    private void addTask() {
        printMessageHeader("Create Task Type");
        io.print("Enter Task Type (WORK/PERSONAL):");
        String taskTypeInput = io.readLineString().toUpperCase();
        TaskType type = TaskType.valueOf(taskTypeInput);

        io.print("Enter Task Title: ");
        String title = io.readLineString();
        io.print("Enter Task Description: ");
        String description = io.readLineString();

        io.print(type == TaskType.WORK ? "Enter Project Name: " : "Enter Location: ");
        String extraDetail  = io.readLineString();

        io.print("Enter Due Date (yyyy-mm-dd): ");
        Date dueDate = new Date();

        Task newTask = TaskFactory.createTask(type, taskManagerService.getTaskCount() + 1, title, description, dueDate, extraDetail);
        taskManagerService.addTask(newTask);
        io.printSuccess("Task Created Successfully!");
    }

    private void viewTasks() {
        io.print("Select how you would like to view your tasks:");
        displayOptions(sortMenuTextOptions);

        int choice = scanner.nextInt();
        io.readLineString();

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
                io.printError("Invalid option. Try again.");
        }
    }

    private void markTaskCompleted() {
        display(taskManagerService.returnTasks());
        io.print("Enter task ID to complete: ");
        int id = Integer.parseInt(io.readLineString());
        Task task = taskManagerService.getTaskById(id);
        if (task != null) {
            task.markAsCompleted();
            io.printSuccess("Task [" + task.getTitle() + "] marked as completed.");
        } else {
            io.printError("Task not found.");
        }
    }

    private void removeTask() {
        display(taskManagerService.returnTasks());
        printMessageHeader("Remove Task ID");
        io.print("Enter task ID to remove: ");
        int id = io.readInt();
        Task task = taskManagerService.getTaskById(id);
        if (task != null) {
            taskManagerService.removeTask(id);
            io.printSuccess("Task [" + task.getTitle() + "] removed successfully.");
        } else {
            io.printError("Task not found.");
        }
    }

    public void chooseCategory() {
        printMessageHeader("Choose Category [WORK/PERSONAL]");
        displayOptions(categoryTextOptions);
        int choice = io.readLineInt();
        io.readLineString();

        switch(choice) {
            case 1:
                display(taskManagerService.displayByTaskType(TaskType.WORK));
                break;
            case 2:
                display(taskManagerService.displayByTaskType(TaskType.PERSONAL));
                break;
            default:
                io.printError("Invalid option. Try again");
        }
    }

    private void updateTask() {
        String updateTitle;
        String updateDescription;
        String updateDueDate;
        String updateExtraDetail;
        boolean  updateCompleted = false;

        printMessageHeader("Update Task");
        io.print("Choose The Task To Update");
        display(taskManagerService.returnTasks());
        System.out.println("Enter Task ID: ");

        int choiceId = scanner.nextInt();
        io.readLineString();

        // Retrieve task to update
        Task originalTask = taskManagerService.returnTask(choiceId);

        checkIfTaskIsValid(originalTask);

        // Print original Task for reference
        printMessageHeader("Original Task");
        originalTask.displayTask();

        System.out.format("Enter new title (press Enter to keep  [ %s ]):\n", originalTask.getTitle());
        updateTitle = io.readLineString();
        if (updateTitle.isEmpty()) {
            updateTitle = originalTask.getTitle();
        }

        System.out.format("Enter new description (press Enter to keep [ %s ]):\n", originalTask.getDescription());
        updateDescription = io.readLineString();
        if (updateDescription.isEmpty()) {
            updateDescription = originalTask.getDescription();
        }

        String extraDetail = originalTask.getTaskType() == TaskType.WORK ? "Project Name" : "Location";

        System.out.format("Enter new %s (press Enter to keep [%s]):\n", extraDetail, extraDetail);
        updateExtraDetail = io.readLineString();
        if (updateExtraDetail.isEmpty()) {
            updateExtraDetail = originalTask.getExtraDetails();
        }

        System.out.format("Enter new Due Date (press Enter to keep [ %s ]):\n", originalTask.getDueDate());
        updateDueDate = io.readLineString();
        if (updateDueDate.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
            updateDueDate = formatter.format(originalTask.getDueDate());
        }

        System.out.format("Mark as completed? (y/n, current: %s):\n", originalTask.getCompleted());
        String completedInput = io.readLineString();
        if (completedInput.equalsIgnoreCase("y")) {
            updateCompleted = true;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        Date updateDate = null;

        try {
            // Convert String to Date
            updateDate = formatter.parse(updateDueDate);
        } catch (ParseException e) {
            io.printError("Invalid date format!");
            e.printStackTrace();
        }

        Task updatedTask = new Task.Builder()
                .id(originalTask.getId())
                .title(updateTitle)
                .description(updateDescription)
                .dueDate(updateDate)
                .completed(updateCompleted)
                .extraDetail(updateExtraDetail)
                .type(originalTask.getTaskType())
                .build();
        taskManagerService.updateTask(updatedTask);

        io.printSuccess("Task Updated Successfully!");
    }

    private void checkIfTaskIsValid(Task task) {
        if (task == null) {
            io.printError("Task with ID: " + task.getId() + " not found.");
        }
    }

}
