package com.taskmanager.service;

import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.repository.TaskManagerDAO;
import com.taskmanager.utils.TaskSorter;

import java.util.List;
import java.util.UUID;
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

    public TaskManagerService() {
        taskManagerDAO = new TaskManagerDAO();
    }

    public void addTask(Task task) {
        taskManagerDAO.saveTask(task);
    }

    public List<Task> returnTasks() {
        return taskManagerDAO.getAllTasks();
    }

    public Task getTaskById(UUID taskId) {
        return returnTasks()
                .stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

    public void updateTask(Task updatedTask) {
        Task taskToUpdate = taskManagerDAO.getAllTasks()
                .stream()
                .filter(t -> t.getId() == updatedTask.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + updatedTask.getId() + " not found."));

                taskToUpdate.setTitle(updatedTask.getTitle());
                taskToUpdate.setDescription(updatedTask.getDescription());
                taskToUpdate.setDueDate(updatedTask.getDueDate());
                taskToUpdate.setExtraDetails(updatedTask.getExtraDetails());
                taskToUpdate.setCompleted(updatedTask.getCompleted());
    }

    public void removeTask(UUID taskId) {
        taskManagerDAO.removeTask(taskId);
    }

    public void clearTasks() {
        taskManagerDAO.removeAllTasks();
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
