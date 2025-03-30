package com.taskmanager.repository;

import com.taskmanager.domain.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskManagerDAO {

    private static List<Task> tasks;
    private static final int CAPACITY = 5;

    static {
        tasks = new ArrayList<>(CAPACITY); // no need for CAPACITY - the list will dynamically grow as elements are added to it
    }

    public void saveTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void removeTask(UUID taskId) {
        tasks.removeIf(t -> t.getId().equals(taskId));
    }

    public void removeAllTasks() {
        tasks.clear();
    }

}
