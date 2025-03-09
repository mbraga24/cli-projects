package com.taskmanager.util;

import com.taskmanager.Task;

import java.util.List;

class SortByDueDate implements TaskSorter {

    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()));
    }

}
