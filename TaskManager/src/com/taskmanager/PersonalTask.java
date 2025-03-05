package com.taskmanager;

import java.util.Date;
import java.util.List;

/**
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: Task is parent and define attributes and methods
 * 👉 Subclass: WorkTask is a subclass that extends Task, inheriting its fields and methods while adding new
 * behavior
 * 👉 Reusability: Instead of rewriting the same logic, WorkTask will reuse Task's methods to enhance them.
 */
public class PersonalTask extends Task {

    private String location;

    public PersonalTask(int id, String title, String description, Date dueDate, String location) {
        super(id, title, description, dueDate); // constructor chaining
        this.location = location;
    }

    @Override
    public void displayTask() {
        super.displayTask();
        System.out.println("Location: " + location);
    }

}
