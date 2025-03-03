package com.taskmanager;

import java.util.Date;

/**
 * Encapsulation and Inheritance
 *
 * Encapsulation: restricting direct access to an object's data and modifying through
 * controlled methods (getters, setters, or behaviors)
 *
 * Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * -> Parent: Task is parent and define attributes and methods
 * -> Subclass: WorkTask is a subclass that extends Task, inheriting its fields and methods while adding new
 * behavior
 * -> Reusability: Instead of rewriting the same logic, WorkTask will reuse Task's methods to enhance them.
 */
public class Task {

    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private boolean completed;

   public Task(int id, String title, String description, Date dueDate, boolean completed) {
       this.id = id;
       this.title = title;
       this.description = description;
       this.dueDate = dueDate;
       this.completed = false;
   }

    /**
     * Mark task as completed
     */
   public void markAsCompleted() {
       this.completed = true;
   }

    /**
     * Return if a task has been completed
     * @return
     */
   public boolean isCompleted() {
       return completed;
   }

    /**
     * Return all data from a task
     */
   public void displayTask() {
       System.out.println("ID: " + id + " | Title: " + title + " | Due: " + dueDate + " | Completed: " + completed);
   }

}
