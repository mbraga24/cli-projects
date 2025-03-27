package service;

import com.taskmanager.domain.model.Task;
import com.taskmanager.domain.model.WorkTask;
import com.taskmanager.service.TaskManagerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//✅ Write unit tests for all public methods.

public class TaskManagerServiceTest {

    private TaskManagerService taskManagerService1;
    private TaskManagerService taskManagerService2;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture printer output
        System.setOut(new PrintStream(outputStream));

        // GIVEN: A TaskManager instance is initialized before each test
        taskManagerService1 = new TaskManagerService();
        taskManagerService2 = new TaskManagerService();
    }

    @AfterEach
    void restoreSystemOut() {
        // Restore original System.out after each test
        System.setOut(originalOut);
        // Remove all tasks from TaskManager
        taskManagerService1.clearTasks();
        taskManagerService2.clearTasks();
    }

    @Test
    void testAddTaskAndReturnTask() {
        // GIVEN: A TaskManager instance with an initial task count
        Task task = new WorkTask("Work Task Title", "Work Task Description", new Date(), "Project Name");

        // WHEN: A new task is added
        taskManagerService1.addTask(task);
        List<Task> tasks = taskManagerService1.returnTasks();

        // THEN: The task count should increase by 1
        assertNotNull(tasks);
        assertEquals(1, tasks.size(), "Task count should increase.");
        assertEquals("Work Task Title", tasks.getFirst().getTitle(), "Task title should be the same.");
    }

    @Test
    void testGetTaskCountReturnsZero() {
        // GIVEN: A new  TaskManager instance (initialized in setup) with an initial task count

        // WHEN: We retrieve the current task count
        int  initialCount = taskManagerService1.getTaskCount();

        // THEN: The task count should be 0
        assertEquals(0, initialCount, "A new TaskManager instance should have 0 tasks");
    }

    @Test
    void testListTasksReturnZeroTasks() {
        // GIVEN: A TaskManager with one task
        Task task1 = new WorkTask("Task Title 1", "Task Description 1", new Date(), "Project Name 1");
        Task task2 = new WorkTask("Work Task Title 2", "Work Task Description 2", new Date(), "Project Name 2");
        taskManagerService1.addTask(task1);
        taskManagerService1.addTask(task2);

        // WHEN: listTasks() is called
        List<Task> tasks = taskManagerService1.returnTasks();

        assertNotNull(tasks);
        assertNotNull(tasks, "listTasks() should print correct task details");
        assertEquals("Task Title 1", tasks.getFirst().getTitle(), "Task 1 title should be the same.");
        assertEquals("Work Task Title 2", tasks.getLast().getTitle(), "Task 2 title should be the same.");
    }

    @Test
    void testClearTasks() {
        // GIVEN: A TaskManager with two tasks
        Task task1 = new WorkTask("Task Title 1", "Task Description 1", new Date(), "Project Name 1");
        Task task2 = new WorkTask("Work Task Title 2", "Work Task Description 2", new Date(), "Project Name 2");
        taskManagerService1.addTask(task1);
        taskManagerService1.addTask(task2);

        // WHEN: clearTasks() is called
        taskManagerService1.clearTasks();

        // THEN: All tasks should be deleted from the list of tasks
        assertEquals(0, taskManagerService1.getTaskCount(), "clearTasks() should remove all tasks from TaskManager list");
    }

    @Test
    void testRemoveTask() {
        // GIVEN: A TaskManager with one task
        Task task1 = new WorkTask("Task Title 1", "Task Description 1", new Date(), "Project Name 1");
        taskManagerService1.addTask(task1);

        assertEquals(1, taskManagerService1.getTaskCount(), "TaskManager should contain 1 task before removal.");

        // WHEN: removeTask() is called
        taskManagerService1.removeTask(task1.getId());

        // THEN: Task count should be 0
        assertEquals(0, taskManagerService1.getTaskCount(), "removeTask() should remove the task");
    }

    @Test
    void testUpdateTaskUpdatesAllProperties() {
        // GIVEN: A TaskManager with one task and a new updated task
        String updateTitle = "UpdateTitle";
        String updateDescription = "updateDescription";
        String updateProjectName = "updateProjectName";
        Date updateDueDate = new Date();

        // Existing task
        Task task1 = new WorkTask("Task Title 1", "Task Description 1", new Date(), "Project Name 1");
        taskManagerService1.addTask(task1);

        // Return task
        Task updatedTask = taskManagerService1.returnTask(task1.getId());

        // Update properties
        updatedTask.setTitle(updateTitle);
        updatedTask.setDescription(updateDescription);
        updatedTask.setDueDate(updateDueDate);
        updatedTask.setExtraDetails(updateProjectName);
        updatedTask.markAsCompleted();

        // WHEN: updateTask() is called
        taskManagerService1.updateTask(updatedTask);

        // THEN: Retrieve the task again and verify it was updated in storage
        Task storedTask = taskManagerService1.returnTask(task1.getId());

        assertEquals(task1.getId(), storedTask.getId(), "Task should have same ID.");
        assertEquals(updateTitle, storedTask.getTitle(), "Task should have same title.");
        assertEquals(updateDescription, storedTask.getDescription(), "Task should have same description.");
        assertEquals(updateDueDate, storedTask.getDueDate(), "Task should have same DueDate .");
        assertEquals(updateProjectName, storedTask.getExtraDetails(), "Task should have same Project Name .");
        assertTrue(storedTask.getCompleted(), "Task should be marked as completed.");
    }

    @Test
    void testUpdateTaskFailsOnId() {
        // GIVEN: A task added to the service
        Task task1 = new WorkTask("Task Title 1", "Task Description 1", new Date(), "Project Name 1");

        // WHEN: Expect an IllegalArgumentException when trying to update
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskManagerService1.updateTask(task1);
        });

        // THEN: The exception thrown is the same
        String expectedMessage = "Task with ID " + task1.getId() + " not found.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
