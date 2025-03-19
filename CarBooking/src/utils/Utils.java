package utils;

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
        int countChars = 0;
        char[] chars = message.toCharArray();
        String[] messageArr = message.split(" ");

        for (int i = 0; i < messageArr.length; i++) {
            if (messageArr[i].equals("+")) {
                System.out.print("");
            }
            else {
                System.out.print(messageArr[i] + " ");
            }
        }

        for (int i = 0; i < chars.length; i++) {
            countChars++;
            if (chars[i] == '+') break;
        }

        System.out.println();
        for (int i = 0; i < countChars; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

}
