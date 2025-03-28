package com.taskmanager.utils;

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
    public static void printMessageHeader(String message) {
        System.out.println("=".repeat(message.length()));
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

}
