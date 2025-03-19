package com.taskmanager.factory;

import com.taskmanager.task.PersonalTask;
import com.taskmanager.task.Task;
import com.taskmanager.task.TaskType;
import com.taskmanager.task.WorkTask;

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
