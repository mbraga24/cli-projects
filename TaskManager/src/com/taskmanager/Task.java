package com.taskmanager;

import java.util.Date;

/**
 * Encapsulation and Inheritance
 *
 * 🗒Encapsulation: Restricting direct access to an object's data and modifying it through
 * controlled methods (getters, setters, or behaviors)
 *
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: com.taskmanager.Task is parent and define attributes and methods
 * 👉 Subclass: com.taskmanager.WorkTask is a subclass that extends com.taskmanager.Task, inheriting its fields and methods while adding new
 * behavior
 * 👉 Reusability: Instead of rewriting the same logic, com.taskmanager.WorkTask will reuse com.taskmanager.Task's methods to enhance them.
 */
public class Task {

    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private boolean completed;

   public Task(int id, String title, String description, Date dueDate) {
       this.id = id;
       this.title = title;
       this.description = description;
       this.dueDate = dueDate;
       this.completed = false;
   }

    public Task(int id, String title, String description, Date dueDate, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed; // for updating a task
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setCompleted(boolean isComplete) {
        this.completed = isComplete;
    }

    public boolean getCompleted() {
        return this.completed;
    }

   public void markAsCompleted() {
       this.completed = true;
   }

    /**
     * Return all data from a task
     */
    public void displayTask() {
        System.out.println(String.format("- Task Id: %d | Title: %s | Description: %s | DueDate: %s | Completed: %b", id, title, description, dueDate, completed));
    }

}
