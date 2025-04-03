package com.taskmanager.repository;

import com.github.javafaker.Faker;
import com.taskmanager.domain.model.PersonalTask;
import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.domain.model.WorkTask;
import com.taskmanager.io.ConsoleIO;
import com.taskmanager.service.TaskManagerService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TaskFakerDataAccessService implements TaskManagerDAO {

    Scanner scanner = new Scanner(System.in);
    ConsoleIO io = new ConsoleIO(scanner);
    TaskManagerDAO taskManagerDAO = new TaskDataAccessService();
    TaskManagerService taskManagerService = new TaskManagerService(taskManagerDAO);

    @Override
    public void addTasksFromFile() {
        Faker faker = new Faker();

        for (int index = 0; index < 5; index++) {
            LocalDate localDate = LocalDate.now().plusDays(index);
            int randomBit = (int) (Math.random() * 2);
            Task task = null;
            if (randomBit == 0) {
                task = new PersonalTask(UUID.randomUUID(), faker.book().title(), faker.lorem().sentence(), Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), faker.address().city(), TaskType.PERSONAL);
            } else {
                task = new WorkTask(UUID.randomUUID(), faker.book().title(), faker.lorem().sentence(), Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), faker.address().city(), TaskType.WORK);
            }
            taskManagerService.addTask(task);
            task.displayTask();
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
