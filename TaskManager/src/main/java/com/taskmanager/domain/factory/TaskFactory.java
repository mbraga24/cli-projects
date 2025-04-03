package com.taskmanager.domain.factory;

import com.taskmanager.domain.model.PersonalTask;
import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.domain.model.WorkTask;

import java.util.Date;
import java.util.UUID;

public class TaskFactory {

    public static Task createTask(TaskType type, String title, String description, Date dueDate, String extraDetails) {
        switch(type) {
            case WORK:
                return new WorkTask(UUID.randomUUID(), title, description, dueDate, extraDetails, TaskType.WORK);
            case PERSONAL:
                return new PersonalTask(UUID.randomUUID(), title, description, dueDate, extraDetails, TaskType.PERSONAL);
            default:
                throw new IllegalArgumentException("Invalid Task Type");
        }
    }

}
