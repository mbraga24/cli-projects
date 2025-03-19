package com.taskmanager.storage;

import com.taskmanager.task.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {

    private static final String DIRECTORY_PATH = System.getProperty("user.dir") + File.separator + "src/com/taskmanager/storage";
    private static final String FILE_NAME = DIRECTORY_PATH + "/tasks.txt";

    public static void saveTasks(List<Task> tasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static List<Task> loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved tasks found.");
            return new ArrayList<>();
        }

        try (ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return new ArrayList<>();
    }

}
