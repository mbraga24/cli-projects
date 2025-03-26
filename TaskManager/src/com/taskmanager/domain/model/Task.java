package com.taskmanager.domain.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulation and Inheritance
 *
 * 🗒Encapsulation: Restricting direct access to an object's data and modifying it through
 * controlled methods (getters, setters, or behaviors)
 *
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: com.taskmanager.domain.model.Task is parent and define attributes and methods
 * 👉 Subclass: com.taskmanager.domain.model.WorkTask is a subclass that extends com.taskmanager.domain.model.Task, inheriting its fields and methods while adding new
 * behavior
 * 👉 Reusability: Instead of rewriting the same logic, com.taskmanager.domain.model.WorkTask will reuse com.taskmanager.domain.model.Task's methods to enhance them.
 */
public abstract class Task implements Serializable {

    private static int idCounter = 0;
    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private TaskType type;
    private boolean completed;

   public Task(String title, String description, Date dueDate, TaskType type) {
       this.id = ++idCounter;
       this.title = title;
       this.description = description;
       this.dueDate = dueDate;
       this.completed = false;
       this.type = type;
   }

    public Task(String title, String description, Date dueDate, boolean completed, TaskType type) {
        this.id = ++idCounter;
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

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        return String.format(
                "Task{id='%d', Title='%s', Description='%s', Due Date='%s', Completed=%b}",
                id, title, description, formatter.format(dueDate), completed
        );
    }
}
