package com.taskmanager.controller;

import com.taskmanager.actions.Actions;

import static com.taskmanager.utils.Utils.printMessage;

public class TaskController {

    private static String userInput = "";

    public  void start() {
        do {
            printMessage("Task Manager CLI");
            userInput = Actions.mainMenuUserInput();
            Actions.triggerOption(userInput);
        } while (!userInput.equals("7"));
    }

}
