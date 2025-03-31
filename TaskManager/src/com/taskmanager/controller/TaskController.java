package com.taskmanager.controller;

import com.taskmanager.actions.Actions;
import com.taskmanager.repository.TaskFileDataAccessService;

import static com.taskmanager.utils.Utils.printMessageHeader;


public class TaskController {

    private final Actions actions;
    private final TaskFileDataAccessService taskFileDataAccessService;
    private int userInput;

    public TaskController(Actions actions, TaskFileDataAccessService taskFileDataAccessService) {
        this.actions = actions;
        this.taskFileDataAccessService = taskFileDataAccessService;
    }

    public  void start() {
        taskFileDataAccessService.addTasksFromFile();
        do {
            printMessageHeader("Task Manager CLI");
            userInput = actions.mainMenuUserInput();
            actions.triggerOption(userInput);
        } while (!(userInput == 6));
    }

}
