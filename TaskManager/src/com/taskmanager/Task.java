package com.taskmanager;

import java.util.Date;

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
