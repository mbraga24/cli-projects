package com.taskmanager.util;
import com.taskmanager.Task;

import java.util.List;

class SortByTitle implements TaskSorter {

    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((t1, t2) -> t1.getTitle().compareToIgnoreCase(t2.getTitle()));
    }

}
