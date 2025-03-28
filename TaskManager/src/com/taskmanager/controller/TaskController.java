package com.taskmanager.controller;

import com.taskmanager.actions.Actions;

import static com.taskmanager.utils.Utils.printMessageHeader;


public class TaskController {

    Actions actions = new Actions();
    private String userInput = "";

    public  void start() {
        do {
            printMessageHeader("Task Manager CLI");
            userInput = actions.mainMenuUserInput();
            actions.triggerOption(userInput);
        } while (!userInput.equals("7"));
    }

}
