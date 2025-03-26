package com.taskmanager.repository;

import com.taskmanager.domain.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerDAO {

    private static List<Task> tasks;
    private static int nextAvailableSlot = 0;
    private static final int CAPACITY = 100;

    static {
        tasks = new ArrayList<>(CAPACITY);
    }

    public void saveTask(Task task) {
        tasks.add(task);
        nextAvailableSlot++;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void removeTask(int taskId) {
        tasks.removeIf(t -> t.getId() == taskId);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public int getTaskCount() {
        return tasks.size();
    }

}
