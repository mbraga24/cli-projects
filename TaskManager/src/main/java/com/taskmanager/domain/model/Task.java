package com.taskmanager.domain.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Encapsulation and Inheritance
 *
 * 🗒Encapsulation: Restricting direct access to an object's data and modifying it through
 * controlled methods (getters, setters, or behaviors)
 *
 * 🗒Inherirance: Allows a subclass to extend the properties and behaviors of a parent class.
 * 👉 Parent: com.taskmanager.domain.model.Task is parent and define attributes and methods
 * 👉 Subclass: com.taskmanager.domain.model.WorkTask is a subclass that extends
 *       com.taskmanager.domain.model.Task, inheriting its fields and methods while adding new
 *       behavior
 * 👉 Reusability: Instead of rewriting the same logic, com.taskmanager.domain.model.WorkTask
 *       will reuse com.taskmanager.domain.model.Task's methods to enhance them.
 */
public abstract class Task implements Serializable {

    private static int idCounter = 0;
    private UUID id;
    private String title;
    private String description;
    private Date dueDate;
    private TaskType type;
    private boolean completed;

    public Task(UUID id, String title, String description, Date dueDate, boolean completed, TaskType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.type = type;
    }

   public Task(UUID id, String title, String description, Date dueDate, TaskType type) {
       this.id = id;
        this.title = title;
       this.description = description;
       this.dueDate = dueDate;
       this.completed = false;
       this.type = type;
   }

    public UUID getId() {
        return this.id;
    }

    public void setTitle(String title) {
       this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String description) {
       this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDueDate(Date dueDate) {
       this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public TaskType getTaskType() {
       return this.type;
    }

    public abstract void setExtraDetails(String extraDetails);

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
     *  🗒Builder Pattern:
     *  Builder for constructing Task subclasses (WorkTask, PersonalTask) based on TaskType.
     *  Uses fluent setters for flexibility and a switch in build() to return the correct subclass.
     *  This approach decouples object creation logic from external classes and makes it
     *   easier to extend in the future (e.g., adding new Task types).
     *
     *  💡 Can be refactored to use a strategy map for better scalability.
     */
    public static class Builder {
        private UUID id;
        private String title;
        private String description;
        private Date dueDate;
        private boolean completed;
        private String extraDetail;
        private TaskType type;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder dueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder extraDetail(String extraDetail) {
            this.extraDetail = extraDetail;
            return this;
        }

        public Builder type(TaskType type) {
            this.type = type;
            return this;
        }

        public Task build() {
            switch (type) {
                case WORK:
                    return new WorkTask(id, title, description, dueDate, completed, extraDetail, type);
                case PERSONAL:
                    return new PersonalTask(id, title, description, dueDate, completed, extraDetail, type);
                default:
                    throw new IllegalArgumentException("Unsupported task type: " + type);
            }
        }
    }

    public void displayTask() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        System.out.format("Task Id: '%s' | Title: %s | Description: %s | DueDate: %s | Completed: %b", id, title, description, formatter.format(dueDate), completed);
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        return String.format(
                "Task{id='%s', Title='%s', Description='%s', Due Date='%s', Completed=%b, Type='%s'}",
                id, title, description, formatter.format(dueDate), completed, getTaskType()
        );
    }

}
