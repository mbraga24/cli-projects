package com.taskmanager.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    UUID id;
    String title;
    String description;
    Date dueDate;
    boolean completed;
    String location;
    TaskType personal;
    TaskType work;

    @BeforeEach
    void setup() {
        id = UUID.fromString("91c290cb-f86e-4213-b8f5-6ed7d48f7e3f");
        title = "Buy groceries";
        description = "Milk, eggs, meat";
        dueDate = new Date();
        completed = false;
        location = "home";
        personal = TaskType.PERSONAL;
        work = TaskType.WORK;
    }

    @Test
    void constructor_inPersonalTask_withoutCompletedField_initializesAllTaskFieldsCorrectly() {
        // Given: variables assigned in the setup() method

        // When:
        Task task = new PersonalTask(id, title, description, dueDate, location, personal);

        // Then:
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(location, task.getExtraDetails());
        assertEquals(personal, task.getTaskType());
    }

    @Test
    void constructor_inPersonalTask_withCompletedField_initializesAllTaskFieldsCorrectly() {
        // Given: variables assigned in the setup() method

        // When:
        Task task = new PersonalTask(id, title, description, dueDate, completed, location, personal);

        // Then:
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(completed, task.getCompleted());
        assertEquals(location, task.getExtraDetails());
        assertEquals(personal, task.getTaskType());
    }

    @Test
    void equals_sameValues_returnsTrue() {
        // Given: variables assigned in the setup() method

        // When:
        Task task1 = new PersonalTask(id, title, description, dueDate, completed, location, personal);
        Task task2 = new PersonalTask(id, title, description, dueDate, completed, location, personal);

        boolean isEquals = task1.equals(task2);

        // Then:
        assertTrue(isEquals);
    }

    @Test
    void equals_null_returnsFalse() {
        // Given: variables assigned in the setup() method

        // When:
        Task task = new PersonalTask(id, title, description, dueDate, completed, location, personal);

        boolean isEquals = task.equals(null);

        // Then:
        assertFalse(isEquals);
    }

    @Test
    void equals_differentClass_returnsFalse() {
        // Given: variables assigned in the setup() method

        // When:
        Task task1 = new PersonalTask(id, title, description, dueDate, completed, location, personal);
        Task task2 = new WorkTask(id, title, description, dueDate, completed, "Project Title", personal);

        boolean isEquals = task1.equals(task2);

        // Then:
        assertFalse(isEquals);
    }

    @Test
    void equals_sameReference_returnsTrue() {
        // Given: variables assigned in the setup() method

        // When:
        Task task = new PersonalTask(id, title, description, dueDate, completed, location, personal);

        boolean isEquals = task.equals(task);

        // Then:
        assertTrue(isEquals);
    }

    @Test
    void equals_differentValues_returnsFalse() {
        // Given: variables assigned in the setup() method

        // When:
        Task task1 = new PersonalTask(id, title, description, dueDate, completed, location, personal);
        Task task2 = new PersonalTask(UUID.fromString("91c290cb-f86e-4213-b8f5-6ed7d481111"), "Different Title", description, dueDate, completed, location, personal);

        boolean isEquals = task1.equals(task2);

        // Then:
        assertFalse(isEquals);
    }

    @Test
    void toString_returnsFormattedString() {
        // Given: variables assigned in the setup() method

        Task task = new PersonalTask(id, title, description, dueDate, completed, location, personal);
        // When:
        String result = task.toString();

        // Then:
        assertTrue(result.contains("Task{id='" + id.toString()));
        assertTrue(result.contains("Title='Buy groceries'"));
        assertTrue(result.contains("Description='Milk, eggs, meat'"));
        assertTrue(result.contains("Completed=false"));
        assertTrue(result.contains("Type='PERSONAL'"));
    }
}
