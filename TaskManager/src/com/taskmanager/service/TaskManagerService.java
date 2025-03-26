package com.taskmanager.service;

import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.repository.TaskManagerDAO;
import com.taskmanager.utils.TaskSorter;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 🧟 Used Previously
 *
 * 🧩 Demonstrates method overriding via inheritance-based polymorphism.
 * Calls the appropriate displayTask() implementation based on the actual Task subclass at runtime.
 *
 * 🧩 Singleton Pattern | Creational Pattern
 * Ensures only one instance of a class exists globally.
 * com.taskmanager.task.TaskManager is implemented as a Singleton so that all tasks are stored in a single, shared instance.
 *
 * The taskManagerInstance variable in com.taskmanager.task.TaskManager is declared as static, ensuring that only one
 * instance of com.taskmanager.task.TaskManager exists throughout the application. The variable taskManagerInstance
 * belongs to the class itself, not to any specific object.
 *
 * ☑ Good to use cases:
 * 👉 Managing a single shared resource (task management, logging, database connection)
 * 👉 Avoiding duplicate objects that should remain unique (config managers).
 *
 */

public class TaskManagerService {

    private TaskManagerDAO taskManagerDAO;
    private static TaskManagerService taskManagerServiceInstance; // Static instance
    private List<Task> tasks;

    public TaskManagerService() {
        taskManagerDAO = new TaskManagerDAO();
    }

    public void addTask(Task task) {
        taskManagerDAO.saveTask(task);
    }

    public List<Task> returnTasks() {
        return taskManagerDAO.getAllTasks();
    }

    public Task getTaskById(int taskId) {
        return returnTasks()
                .stream()
                .filter(t -> t.getId() == taskId)
                .findFirst()
                .orElse(null);
    }

    /*
        ===========
        REFACTOR LAST
        ===========

    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                return;
            }
        }
        System.out.println("xxxxxxxxxxxxxxx");
        System.out.println("Task Not Found");
        System.out.println("xxxxxxxxxxxxxxx");
    }
   */


    public void removeTask(int taskId) {
        taskManagerDAO.removeTask(taskId);
    }

    public void clearTasks() {
        taskManagerDAO.removeAllTasks();
    }

    public int getTaskCount() {
        return taskManagerDAO.getTaskCount();
    }

    public Task returnTask(int taskId) {
        return returnTasks()
                .stream()
                .filter(c -> c.getId() == taskId)
                .findFirst()
                .orElse(null);
    }

    public List<Task> displayByTaskType(TaskType type) {
        return returnTasks()
                .stream()
                .filter(t -> t.getTaskType().equals(type))
                .collect(Collectors.toList());
    }

    public void sortTasks(TaskSorter sorter) {
        sorter.sort(returnTasks());
    }

}
