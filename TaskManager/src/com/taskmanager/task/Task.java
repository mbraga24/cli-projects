package com.taskmanager.task;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulation and Inheritance
 *
 * 🗒Encapsulation: Restricting direct access to an object's data and modifying it through
 * controlled methods (getters, setters, or behaviors)
 *
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: com.taskmanager.task.Task is parent and define attributes and methods
 * 👉 Subclass: com.taskmanager.task.WorkTask is a subclass that extends com.taskmanager.task.Task, inheriting its fields and methods while adding new
 * behavior
 * 👉 Reusability: Instead of rewriting the same logic, com.taskmanager.task.WorkTask will reuse com.taskmanager.task.Task's methods to enhance them.
 */
public abstract class Task {

    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private TaskType type;
    private boolean completed;

   public Task(int id, String title, String description, Date dueDate, TaskType type) {
       this.id = id;
       this.title = title;
       this.description = description;
       this.dueDate = dueDate;
       this.completed = false;
       this.type = type;
   }

    public Task(int id, String title, String description, Date dueDate, boolean completed, TaskType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed; // for updating a task
        this.type = type;
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

    public TaskType getTaskType() {
       return this.type;
    }

    public abstract String getExtraDetails();

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
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        System.out.format("Task Id: %d | Title: %s | Description: %s | DueDate: %s | Completed: %b", id, title, description, formatter.format(dueDate), completed);
    }

}
