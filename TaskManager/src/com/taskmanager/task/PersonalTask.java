package com.taskmanager.task;

import java.util.Date;

/**
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: com.taskmanager.task.Task is parent and define attributes and methods
 * 👉 Subclass: com.taskmanager.task.WorkTask is a subclass that extends com.taskmanager.task.Task, inheriting its fields and methods while adding new
 * behavior
 * 👉 Reusability: Instead of rewriting the same logic, com.taskmanager.task.WorkTask will reuse com.taskmanager.task.Task's methods to enhance them.
 */
public class PersonalTask extends Task {

    private String location;

    public PersonalTask(String title, String description, Date dueDate, String location) {
        super(title, description, dueDate, TaskType.PERSONAL); // constructor chaining
        this.location = location;
    }

    @Override
    public String getExtraDetails() {
        return this.location;
    }

    @Override
    public void displayTask() {
        super.displayTask();
        System.out.format(" | Location: %s\n", location);
    }

}
