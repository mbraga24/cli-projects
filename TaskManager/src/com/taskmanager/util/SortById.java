package com.taskmanager.util;

import com.taskmanager.task.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortById implements TaskSorter {

    @Override
    public void sort(List<Task> tasks) {
        Collections.sort(tasks, Comparator.comparing(Task::getId));
    }

}
