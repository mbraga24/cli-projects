package com.carbooking.controller;

import com.carbooking.actions.Actions;

public class BookingController {

    private static String userInput = "";

    public static void start() {
        do {
            userInput = Actions.mainMenuUserInput();
            Actions.triggerOption(userInput);
        } while (!userInput.equals("8"));
    }

}
