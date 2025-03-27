package com.carbooking.utils;

import java.util.List;

public class Utils {

    /**
     * Method uses generics to display objects from any given array.
     * @param <T>
     */
    public static <T> void display(List<T> aList) {
        aList.forEach(item -> {
            System.out.println(item.toString());
        });
        System.out.println();
    }

    /**
     * Helps build message and separator header to avoid repetition.
     * @param message
     */
    public static void printMessage(String message) {
        int plusIndex = message.indexOf('+');
        String display = message.replace("+", "").trim();

        System.out.println("=".repeat(plusIndex != -1 ? plusIndex : message.length()));
        System.out.println(display);
        System.out.println("=".repeat(plusIndex != -1 ? plusIndex : message.length()));
    }

    public static void printErrorMessage(String message) {
        int plusIndex = message.indexOf('+');
        String display = message.replace("+", "").trim();

        System.out.println("-".repeat(plusIndex != -1 ? plusIndex : message.length()));
        System.out.println(display);
        System.out.println("-".repeat(plusIndex != -1 ? plusIndex : message.length()));
    }

}
