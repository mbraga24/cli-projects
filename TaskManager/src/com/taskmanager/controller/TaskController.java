package com.taskmanager.controller;

import com.taskmanager.actions.Actions;
import com.taskmanager.repository.TaskFileDataAccessService;

import static com.taskmanager.utils.Utils.printMessageHeader;


public class TaskController {

    Actions actions = new Actions();
    TaskFileDataAccessService taskFileDataAccessService = new TaskFileDataAccessService();
    private int  userInput;

    public  void start() {
        taskFileDataAccessService.addTasksFromFile();
        do {
            printMessageHeader("Task Manager CLI");
            userInput = actions.mainMenuUserInput();
            actions.triggerOption(userInput);
        } while (!(userInput == 6));
    }

}
