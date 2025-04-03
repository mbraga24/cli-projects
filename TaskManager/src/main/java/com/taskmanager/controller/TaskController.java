package com.taskmanager.controller;

import com.taskmanager.actions.Actions;
import com.taskmanager.repository.TaskFakerDataAccessService;

import static com.taskmanager.utils.Utils.printMessageHeader;


public class TaskController {

    private final Actions actions;
    private final TaskFakerDataAccessService taskFakerDataAccessService;
    private int userInput;

    public TaskController(Actions actions, TaskFakerDataAccessService taskFakerDataAccessService) {
        this.actions = actions;
        this.taskFakerDataAccessService = taskFakerDataAccessService;
    }

    public  void start() {
        taskFakerDataAccessService.addTasksFromFile();
        do {
            printMessageHeader("Task Manager CLI");
            userInput = actions.mainMenuUserInput();
            actions.triggerOption(userInput);
        } while (!(userInput == 6));
    }

}
