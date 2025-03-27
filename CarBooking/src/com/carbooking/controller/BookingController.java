package com.carbooking.controller;

import com.carbooking.actions.Actions;
import com.carbooking.utils.Utils;

public class BookingController {

    private static String userInput = "";

    public static void start() {
        do {
            Utils.printMessage("Car Booking App");
            userInput = Actions.mainMenuUserInput();
            Actions.triggerOption(userInput);
        } while (!userInput.equals("6"));
    }

}
