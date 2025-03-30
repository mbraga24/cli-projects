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
import java.util.UUID;

import static com.taskmanager.utils.Utils.display;
import static com.taskmanager.utils.Utils.printMessageHeader;

public class Actions {

    private Scanner scanner = new Scanner(System.in);
    private final ConsoleIO io = new ConsoleIO(scanner);
    private TaskManagerService taskManagerService = new TaskManagerService();
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

    public int mainMenuUserInput() {
        return displayMenuAndGetChoice(mainMenuTextOptions);
    }

    private int displayMenuAndGetChoice(String[] textOptions) {
        int OptNumber = 0;
        for (int option = 0; option < textOptions.length; option++) {
            OptNumber = (option + 1);
            io.print( OptNumber + ": " + textOptions[option]);
        }
        io.print("Enter your choice: (1-" + OptNumber +")");
        int choice = scanner.nextInt();
        io.readLineString(); // clear Buffer
        return choice;
    }

    public void triggerOption(int choice) {
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
                io.print("\uD83D\uDC4B Exiting program. Goodbye!");
                break;
            default:
                io.printError("Not a valid option. Try again");
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

        try {
            Task newTask = TaskFactory.createTask(type, title, description, dueDate, extraDetail);
            taskManagerService.addTask(newTask);
            io.skipALine();
            io.printSuccess("Task Created Successfully!");
            io.skipALine();
        } catch (Exception e) {
            io.printError(e.getMessage());
        }
    }

    private void viewTasks() {
        io.print("Select how you would like to view your tasks:");
        int choice = displayMenuAndGetChoice(sortMenuTextOptions);

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
        String taskId = io.readLineString();
        try {
            Task task = taskManagerService.getTaskById(UUID.fromString(taskId));
            if (task != null) {
                task.markAsCompleted();
                io.skipALine();
                io.printSuccess("Task [" + task.getTitle() + "] marked as completed.");
                io.skipALine();
            } else {
                io.printError("Task not found.");
            }
        } catch (Exception e) {
            io.printError(String.valueOf(e));
        }
    }

    private void removeTask() {
        display(taskManagerService.returnTasks());
        printMessageHeader("Remove Task ID");
        io.print("Enter task ID to remove: ");
        String taskId = io.readLineString();
        Task task = taskManagerService.getTaskById(UUID.fromString(taskId));
        if (task != null) {
            taskManagerService.removeTask(task.getId());
            io.skipALine();
            io.printSuccess("Task [" + task.getTitle() + "] removed successfully.");
            io.skipALine();
        } else {
            io.printError("Task not found.");
        }
    }

    public void chooseCategory() {
        printMessageHeader("Choose Category [WORK/PERSONAL]");
        int choice = displayMenuAndGetChoice(categoryTextOptions);
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
        String updateDueDateStr;
        String updateExtraDetail;
        boolean updateCompleted = false;

        printMessageHeader("Update Task");
        io.print("Choose The Task To Update");
        display(taskManagerService.returnTasks());

        io.print("Enter Task ID: ");
        String taskId = io.readLineString();

        // Retrieve task to update
        Task originalTask = taskManagerService.getTaskById(UUID.fromString(taskId));

        if (originalTask == null) {
            io.printError("Task with ID: " + taskId + " not found.");
            return;
        }

        printMessageHeader("Original Task");
        originalTask.displayTask();

        updateTitle = promptOrKeep("title", originalTask.getTitle());

        updateDescription = promptOrKeep("description", originalTask.getDescription());

        updateDueDateStr = promptOrKeep("Due Date", formatDate(originalTask.getDueDate()));

        Date updateDate = parseDateOrDefault(updateDueDateStr, originalTask.getDueDate());

        updateCompleted  = promptCompleted(originalTask.getCompleted());

        String extraDetail = originalTask.getTaskType() == TaskType.WORK ? "Project Name" : "Location";

        updateExtraDetail = promptOrKeep(extraDetail, originalTask.getExtraDetails());

        try {
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
            io.skipALine();
            io.printSuccess("Task Updated Successfully!");
            io.skipALine();
        } catch (Exception e) {
            io.printError(e.getMessage());
        }
    }

    private void ifTaskIsNull(Task task, String userId) {
        if (task == null) {
            io.printError("Task with ID: " + userId + " not found.");
        }
    }

    private String promptOrKeep(String label, String currentValue) {
        io.print(String.format("Enter new %s (press Enter to keep [%s]):", label, currentValue));
        String input = io.readLineString();
        return input.isEmpty() ? currentValue : input;
    }

    private boolean promptCompleted(boolean currentStatus) {
        io.print(String.format("Mark as completed? (y/n, current: %s):", currentStatus));
        return io.readLineString().equalsIgnoreCase("y");
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("MMM dd yyyy").format(date);
    }

    private Date parseDateOrDefault(String input, Date fallback) {
        if (input.isEmpty()) return fallback;
        try {
            return new SimpleDateFormat("MMM dd yyyy").parse(input);
        } catch (ParseException e) {
            io.printError("Invalid date format! Keeping original date.");
            return fallback;
        }
    }

}
