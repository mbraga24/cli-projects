package com.taskmanager;

import java.util.ArrayList;
import java.util.List;

/**
 * 🧩 Singleton Pattern | Creational Pattern
 * Ensures only one instance of a class exists globally.
 * TaskManager is implemented as a Singleton so that all tasks are stored in a single, shared instance.
 *
 * The taskManagerInstance variable in TaskManager is declared as static, ensuring that only one
 * instance of TaskManager exists throughout the application. The variable taskManagerInstance
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
     * Private constructor prevents direct instantiation
     */
    private TaskManager() {
        tasks = new ArrayList<>();
    }

    /**
     * Statis method is the only way to access the instance of TaskManager.
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
    }

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

    public void removeTask(int taskId) {
        tasks.removeIf(task -> task.getId() == taskId);
    }

}
