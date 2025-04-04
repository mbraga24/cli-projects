package com.taskmanager.integration;

import com.taskmanager.repository.TaskDataAccessService;
import com.taskmanager.repository.TaskFakerDataAccessService;
import com.taskmanager.repository.TaskManagerDAO;
import com.taskmanager.service.TaskManagerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskFakerDataAccessServiceIT {

//    Why is this maintainable?
//    Why is this scalable?
//    Why does it isolate tasks?

    TaskManagerDAO dao;
    TaskManagerService service;
    TaskManagerDAO fakerService;

    @BeforeEach
    void setup() {
        dao = new TaskDataAccessService();
        service = new TaskManagerService(dao);
        fakerService = new TaskFakerDataAccessService(service);
    }

    @Test
    void addTasksFromFile_addsFiveTasksToService() {
        fakerService.addTasksFromFile();

        assertEquals(5, service.getTaskCount());
    }

    @AfterEach
    void tearDown() {
        service.clearTasks();
    }

}
