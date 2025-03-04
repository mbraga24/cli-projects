package com.taskmanager;
import java.util.Date;

/**
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: Task is parent and define attributes and methods
 * 👉 Subclass: WorkTask is a subclass that extends Task, inheriting its fields and methods while adding new
 * behavior
 * 👉 Reusability: Instead of rewriting the same logic, WorkTask will reuse Task's methods to enhance them.
 */
public class WorkTask extends Task {

    private String projectName;

    public WorkTask(int id, String title, String description, Date dueDate, String projectName) {
        super(id, title, description, dueDate); // constructor chaining
        this.projectName = projectName;
    }

    @Override
    public void displayTask() {
        super.displayTask();
        System.out.println("Project: " + projectName);
    }
}
