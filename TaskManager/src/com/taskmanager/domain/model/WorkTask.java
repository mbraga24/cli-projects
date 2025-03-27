package com.taskmanager.domain.model;

import java.util.Date;

/**
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: com.taskmanager.domain.model.Task is parent and define attributes and methods
 * 👉 Subclass: com.taskmanager.domain.model.WorkTask is a subclass that extends com.taskmanager.domain.model.Task, inheriting its fields and methods while adding new
 * behavior
 * 👉 Reusability: Instead of rewriting the same logic, com.taskmanager.domain.model.WorkTask will reuse com.taskmanager.domain.model.Task's methods to enhance them.
 */
public class WorkTask extends Task {

    private String projectName;

    public WorkTask(String title, String description, Date dueDate, String projectName) {
        super(title, description, dueDate, TaskType.WORK); // constructor chaining
        this.projectName = projectName;
    }

    @Override
    public void setExtraDetails(String extraDetail) {
        this.projectName = extraDetail;
    }

    @Override
    public String getExtraDetails() {
        return this.projectName;
    }

    @Override
    public void displayTask() {
        super.displayTask();
        System.out.format(" | Project: %s\n", projectName);
    }

}
