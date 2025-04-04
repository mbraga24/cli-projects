package com.taskmanager.unit.service;

import com.taskmanager.domain.model.PersonalTask;
import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.TaskType;
import com.taskmanager.repository.TaskManagerDAO;
import com.taskmanager.service.TaskManagerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TaskManagerServiceTest {

    TaskManagerDAO daoMock;
    TaskManagerService taskManagerService1;
    TaskManagerService taskManagerService2;

    UUID id;
    String title;
    String description;
    Date dueDate;
    boolean completed;
    String location;
    TaskType personal;
    TaskType work;

    @BeforeEach
    void setUp() {
        daoMock = mock(TaskManagerDAO.class);
        taskManagerService1 = new TaskManagerService(daoMock);
        taskManagerService2 = new TaskManagerService(daoMock);

        id = UUID.fromString("91c290cb-f86e-4213-b8f5-6ed7d48f7e3f");
        title = "Buy groceries";
        description = "Milk, eggs, meat";
        dueDate = new Date();
        completed = false;
        location = "home";
        personal = TaskType.PERSONAL;
        work = TaskType.WORK;
    }

    @AfterEach
    void restoreSystemOut() {
        // Remove all tasks from TaskManager
        taskManagerService1.clearTasks();
        taskManagerService2.clearTasks();
    }

    @Test
    void test_addTask() {
        // GIVEN: A TaskManager instance with an initial task count
        Task task1 = new PersonalTask(id, title, description, dueDate, location, personal);

        // WHEN: A new task is added
        taskManagerService1.addTask(task1);
        List<Task> tasks = taskManagerService1.returnTasks();

        // THEN: The task count should increase by 1
        assertNotNull(tasks);
        assertEquals(1, tasks.size(), "Task count should increase.");
        assertEquals(title, tasks.get(0).getTitle(), "Task title should be the same.");
    }

    @Test
    void test_listTasks_returnZeroTasks() {
        // GIVEN: A TaskManager with two tasks
        String title2 = "Wash Dishes";
        Task task1 = new PersonalTask(id, title, description, dueDate, location, personal);
        Task task2 = new PersonalTask(id, title2, description, dueDate, location, personal);
        taskManagerService1.addTask(task1);
        taskManagerService1.addTask(task2);

        // WHEN: listTasks() is called
        List<Task> tasks = taskManagerService1.returnTasks();

        assertNotNull(tasks);
        assertNotNull(tasks, "listTasks() should print correct task details");
        assertEquals(title, tasks.get(0).getTitle(), "Task 1 title should be the same.");
        assertEquals(title2, tasks.get(1).getTitle(), "Task 2 title should be the same.");
    }

    @Test
    void test_getTaskById() {
        // GIVEN: A TaskManager with one task
        Task task1 = new PersonalTask(id, title, description, dueDate, location, personal);
        taskManagerService1.addTask(task1);

        // WHEN: listTasks() is called
        Task returnedTask = taskManagerService1.getTaskById(id);

        assertNotNull(returnedTask);
        assertEquals(id, returnedTask.getId(), "Task 1 id should be the same.");
    }

    @Test
    void test_getTaskCount() {
        // GIVEN: A TaskManager with one task
        Task task1 = new PersonalTask(id, title, description, dueDate, location, personal);

        // WHEN: A task is added
        taskManagerService1.addTask(task1);

        // THEN: Task count should be 1
        assertEquals(1, taskManagerService1.getTaskCount(), "TaskManager should contain 1 task before removal.");
    }

    @Test
    void test_removeTask() {
        // GIVEN: A TaskManager with one task
        Task task1 = new PersonalTask(id, title, description, dueDate, location, personal);
        taskManagerService1.addTask(task1);

        assertEquals(1, taskManagerService1.getTaskCount(), "TaskManager should contain 1 task before removal.");

        // WHEN: removeTask() is called
        taskManagerService1.removeTask(task1.getId());

        // THEN: Task count should be 0
        assertEquals(0, taskManagerService1.getTaskCount(), "removeTask() should remove the task");
    }

    @Test
    void test_clearTasks() {
        // GIVEN: A TaskManager with two tasks
        String title2 = "Wash Dishes";
        Task task1 = new PersonalTask(id, title, description, dueDate, location, personal);
        Task task2 = new PersonalTask(id, title2, description, dueDate, location, personal);
        taskManagerService1.addTask(task1);
        taskManagerService1.addTask(task2);

        // WHEN: clearTasks() is called
        taskManagerService1.clearTasks();

        // THEN: All tasks should be deleted from the list of tasks
        assertEquals(0, taskManagerService1.getTaskCount(), "clearTasks() should remove all tasks from TaskManager list");
    }

    @Test
    void test_updateTask_updatesAllProperties() {
        // GIVEN: A TaskManager with one task and a new updated task
        String updateTitle = "Update title";
        String updateDescription = "Update description";
        String location = "Update location";
        Date updateDueDate = new Date();

        // Existing task
        Task task1 = new PersonalTask(id, title, description, dueDate, location, personal);
        taskManagerService1.addTask(task1);

        // Return task
        Task taskToUpdate = taskManagerService1.getTaskById(task1.getId());

        // Update properties
        taskToUpdate.setTitle(updateTitle);
        taskToUpdate.setDescription(updateDescription);
        taskToUpdate.setDueDate(updateDueDate);
        taskToUpdate.setExtraDetails(location);
        taskToUpdate.markAsCompleted();

        // WHEN: updateTask() is called
        taskManagerService1.updateTask(taskToUpdate);

        // THEN: Retrieve the task again and verify it was updated in storage
        Task updatedTask = taskManagerService1.getTaskById(task1.getId());

        assertEquals(task1.getId(), updatedTask.getId(), "Task should have same ID.");
        assertEquals(updateTitle, updatedTask.getTitle(), "Task should have same title.");
        assertEquals(updateDescription, updatedTask.getDescription(), "Task should have same description.");
        assertEquals(updateDueDate, updatedTask.getDueDate(), "Task should have same DueDate .");
        assertEquals(location, updatedTask.getExtraDetails(), "Task should have same Project Name .");
        assertTrue(updatedTask.getCompleted(), "Task should be marked as completed.");
    }

    @Test
    void testUpdateTaskFailsOnId() {
        // GIVEN: A task added to the service
        Task task1 = new PersonalTask(id, title, description, dueDate, location, personal);

        // WHEN: Expect an IllegalArgumentException when trying to update
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskManagerService1.updateTask(task1);
        });

        // THEN: The exception thrown is the same
        String expectedMessage = "Task with ID " + task1.getId() + " not found.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
