package com.taskmanager.service;

import com.taskmanager.storage.TaskStorage;
import com.taskmanager.task.Task;
import com.taskmanager.task.TaskType;
import com.taskmanager.util.TaskSorter;

import java.util.List;

/**
 * 🧩 Singleton Pattern | Creational Pattern
 * Ensures only one instance of a class exists globally.
 * com.taskmanager.service.TaskManager is implemented as a Singleton so that all tasks are stored in a single, shared instance.
 *
 * The taskManagerInstance variable in com.taskmanager.service.TaskManager is declared as static, ensuring that only one
 * instance of com.taskmanager.service.TaskManager exists throughout the application. The variable taskManagerInstance
 * belongs to the class itself, not to any specific object.
 *
 * ☑ Good to use cases:
 * 👉 Managing a single shared resource (task management, logging, database connection)
 * 👉 Avoiding duplicate objects that should remain unique (config managers).
 *
 */

public class TaskManager {

    private static TaskManager taskManagerInstance; // Static instance
    private List<Task> tasks;

    /**
     * Private constructor prevents direct instantiation / Loads tasks from storage
     */
    private TaskManager() {
        this.tasks = TaskStorage.loadTasks();
    }

    /**
     * Statis method is the only way to access the instance of com.taskmanager.service.TaskManager.
     * Static method provides global access to the single instance.
     * @return
     */
    public static TaskManager getTaskManagerInstance() {
        if (taskManagerInstance == null) {
            taskManagerInstance = new TaskManager();
        }
        return taskManagerInstance;
    }

    public void addTask(Task task) {
            tasks.add(task);
            TaskStorage.saveTasks(tasks); // Saving tasks to storage after adding a task
    }

    /**
     * listTasks() - Method Overriding (Inheritance-Based Polymorphism)
     * Calls the appropriate overridden method dynamically
     */
    public void listTasks() {
        for (Task task : tasks) {
            task.displayTask();
        }
    }

    public Task getTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) return task;
        }
        return null;
    }

    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                TaskStorage.saveTasks(tasks); // Save tasks in storage after updating a task
                return;
            }
        }
        System.out.println("xxxxxxxxxxxxxxx");
        System.out.println("Task Not Found");
        System.out.println("xxxxxxxxxxxxxxx");
    }

    public void removeTask(int taskId) {
        tasks.removeIf(task -> task.getId() == taskId);
        TaskStorage.saveTasks(tasks); // Save tasks in storage after removing a task
    }

    public void clearTasks() {
        tasks.clear();
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public Task returnTask(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    public void displayByTaskType(TaskType type) {
        for (Task task : tasks) {
            if (task.getTaskType().equals(type)) {
                task.displayTask();
            }
        }
    }

    public void sortTasks(TaskSorter sorter) {
        sorter.sort(tasks);
    }

}
