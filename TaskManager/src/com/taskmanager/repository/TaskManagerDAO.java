package com.taskmanager.repository;

import com.taskmanager.domain.model.Task;

import java.util.List;
import java.util.UUID;

public interface TaskManagerDAO {

    void addTasksFromFile();

    void saveTask(Task task);

    List<Task> getAllTasks();

    void removeTask(UUID taskId);

    void removeAllTasks();

}
