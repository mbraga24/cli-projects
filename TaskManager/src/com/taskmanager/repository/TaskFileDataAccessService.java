package com.taskmanager.repository;

import com.taskmanager.domain.model.PersonalTask;
import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.domain.model.WorkTask;
import com.taskmanager.io.ConsoleIO;
import com.taskmanager.service.TaskManagerService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TaskFileDataAccessService implements TaskManagerDAO {

    Scanner scannerIn = new Scanner(System.in);;

    ConsoleIO io = new ConsoleIO(scannerIn);
    TaskManagerDAO taskManagerDAO = new TaskDataAccessService();
    TaskManagerService taskManagerService = new TaskManagerService(taskManagerDAO);

    public TaskFileDataAccessService() {}

    @Override
    public void addTasksFromFile() {

        File file = new File("src/tasks.csv");

        try {
            int index = 0;
            Scanner scannerFile = new Scanner(file);
            while (scannerFile.hasNext()) {
                String[] split = scannerFile.nextLine().split(",");
                LocalDate localDate = LocalDate.now().plusDays(index);
                Task task = null;
                if (split[5].equals("PERSONAL")) {
                    task = new PersonalTask(UUID.fromString(split[0]), split[1], split[2], Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), split[4], TaskType.valueOf(split[5]));
                } else {
                    task = new WorkTask(UUID.fromString(split[0]), split[1], split[2], Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), split[4], TaskType.valueOf(split[5]));
                }
                taskManagerService.addTask(task);
                task.displayTask();
                index++;
            }

        } catch (IOException e) {
            io.printError(String.valueOf(e));
        }
    }

    @Override
    public void saveTask(Task task) {

    }

    @Override
    public List<Task> getAllTasks() {
        return List.of();
    }

    @Override
    public void removeTask(UUID taskId) {

    }

    @Override
    public void removeAllTasks() {

    }

}
