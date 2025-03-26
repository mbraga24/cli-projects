package com.taskmanager.domain.factory;

import com.taskmanager.domain.model.PersonalTask;
import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.domain.model.WorkTask;

import java.util.Date;

public class TaskFactory {

    public static Task createTask(TaskType type, int id, String title, String description, Date dueDate, String extraDetails) {
        switch(type) {
            case WORK:
                return new WorkTask(title, description, dueDate, extraDetails);
            case PERSONAL:
                return new PersonalTask(title, description, dueDate, extraDetails);
            default:
                throw new IllegalArgumentException("Invalid Task Type");
        }
    }

}
