package com.taskmanager.domain.model;

import java.util.Date;
import java.util.UUID;

/**
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: com.taskmanager.domain.model.Task is parent and define attributes and methods
 * 👉 Subclass: com.taskmanager.domain.model.WorkTask is a subclass that extends com.taskmanager.domain.model.Task, inheriting its fields and methods while adding new
 * behavior
 * 👉 Reusability: Instead of rewriting the same logic, com.taskmanager.domain.model.WorkTask will reuse com.taskmanager.domain.model.Task's methods to enhance them.
 */
public class PersonalTask extends Task {

    private String location;

    public PersonalTask(UUID id, String title, String description, Date dueDate, String location, TaskType type) {
        super(id, title, description, dueDate, type); // constructor chaining
        this.location = location;
    }

    public PersonalTask(UUID id, String title, String description, Date dueDate, Boolean completed, String location, TaskType type) {
        super(id, title, description, dueDate, completed, type); // constructor chaining
        this.location = location;
    }

    @Override
    public void setExtraDetails(String extraDetail) {
        this.location = extraDetail;
    }

    @Override
    public String getExtraDetails() {
        return this.location;
    }

    @Override
    public void displayTask() {
        super.displayTask();
        System.out.format(" | Location: %s | Type: %s\n", location, String.valueOf(TaskType.PERSONAL));
    }

}
