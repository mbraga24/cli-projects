package com.taskmanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TaskManagerTest {

    private TaskManager taskManager1;
    private TaskManager taskManager2;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture printer output
        System.setOut(new PrintStream(outputStream));
        // GIVEN: A TaskManager instance is initialized before each test
         taskManager1 = TaskManager.getTaskManagerInstance();
         taskManager2 = TaskManager.getTaskManagerInstance();
    }

    @AfterEach
    void restoreSystemOut() {
        // Restore original System.out after each test
        System.setOut(originalOut);
        // Remove all tasks from TaskManager
        taskManager1.clearTasks();
        taskManager2.clearTasks();
    }

    /**
     * 🔍Notes:
     * 👉 This test does not modify the singleton – No reflection by resetting instance
     * 👉 Since singletons share state, running multiple tests in parallel may cause side effects.
     * 👉 Consider using factories or dependency injection frameworks like Spring
     */
    @Test
    public void testSingletonInstance() {
        // WHEN: We can getInstance() multiple times (handled in setup)

        // THEN: Both instances should be the same
        assertSame(taskManager1, taskManager2, "TaskManager should return the same instance");
    }

    @Test
    void testAddTaskDoesNotAffectOtherInstances() {
        // GIVEN: A TaskManager instance with an initial task count
        int initialCount = taskManager1.getTaskCount();

        // WHEN: A new task is added
        Task task = new Task(1, "Task", "" , new Date());
        taskManager1.addTask(task);

        // THEN: The task count should increase by 1
        assertEquals(initialCount + 1, taskManager1.getTaskCount(), "Task count should increase by 1.");
    }

    @Test
    void testgetTaskCountReturnsZero() {
        // GIVEN: A new  TaskManager instance (initialized in setup) with an initial task count

        // WHEN: We retrieve the current task count
        int  initialCount = taskManager1.getTaskCount();

        // THEN: The task count should be 0
        assertEquals(0, initialCount, "A new TaskManager instance should have 0 tasks");
    }

    @Test
    void testgetTaskCountIncreasesAfterAddingATask() {
        // GIVEN: A new TaskManager instance (initialized in setup)

        // WHEN: A new task is added
        Task task = new Task(1, "Task", "" , new Date());
        taskManager1.addTask(task);

        // THEN: The task count should be 1
        assertEquals(1, taskManager1.getTaskCount(), "Task count should be 1 after adding a task");
    }

    @Test
    void testListTasksReturnZeroTasks() {
        // GIVEN: A TaskManager with one task
        Task task = new Task(1, "Task", "My task" , null);
        taskManager1.addTask(task);

        // WHEN: listTasks() is called
        taskManager1.listTasks();

        // Capture the output
        String printedOutput = outputStream.toString().trim();

        // THEN: The printed output should match expected task details
        String expectedOutput = "- ID: 1\n- Title: Task\n- Description: My task\n- Due: null\n- Completed: false";

        assertEquals(expectedOutput, printedOutput, "listTasks() should print correct task details");
    }
}
