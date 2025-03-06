package com.taskmanager.util;

import com.taskmanager.Task;

import java.util.List;

/**
 * 🧩 Strategy Pattern | Behavioral Pattern
 * Allows selecting an algorithm dynamically at runtime.
 * The TaskSorter uses the Strategy Pattern to allow sorting by different criteria (due date, title, etc.) dynamically.
 *
 * Separating classes for each strategy:
 *  ✅ Pros:
 * Scalability: Easy to add new strategies.
 * Open/Closed Principle (OOP Best Practice): New sorting strategies don't require modifying existing code/class.
 * Encapsulation: Each class does one job, keeping the code clean.
 * Polymorphism: Easily to swap sorting strategies at runtime using TaskSorter instances
 * ❎ Cos:
 * More files: Creates multiple small classes
 * Slightly More Boilerplate: each strategy needs its own file
 *
 */
interface TaskSorter {

    /**
     * sort() - Interface-Based Polymorphism
     * Calls the correct sorting method based on user assigned strategy
     * @param tasks
     */
    void sort(List<Task> tasks);

}
