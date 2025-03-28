package com.taskmanager.io;

import java.util.Scanner;

public class ConsoleIO {

    private final Scanner scanner;

    public ConsoleIO(Scanner scanner) {
        this.scanner = scanner;
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.err.println("❌ [ERROR] " + message);
    }

    public void printSuccess(String message) {
        System.out.println("✅ Success: " + message);
    }

    public void printNextLine() {
        System.out.println();
    }

    public String readLineString() {
        return scanner.nextLine();
    }

    public int readLineInt() {
        return scanner.nextInt();
    }

    public int readInt() {
        return Integer.parseInt(scanner.nextLine());
    }
}
