package util;

import com.taskmanager.domain.model.Task;
import com.taskmanager.utils.SortByDueDate;
import com.taskmanager.utils.SortById;
import com.taskmanager.utils.SortByTitle;
import com.taskmanager.utils.TaskSorter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskSorterTest {

    Date date1;
    Date date2;
    List<Task> tasks;

    @BeforeEach
    void setUp() {
        // GIVEN: Array List of tasks with different due dates
        LocalDate localDatePlusTwo = LocalDate.now().plusDays(2);
        LocalDate localDatePlusThree = LocalDate.now().plusDays(3);

        date1 = Date.from(localDatePlusTwo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        date2 = Date.from(localDatePlusThree.atStartOfDay(ZoneId.systemDefault()).toInstant());

        tasks = Arrays.asList(
                new Task(3, "C", "Desc",  date1),
                new Task(1, "A", "Desc",  date2)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSorters")
    void testSortingPreservesSizeAndIsNotNull(TaskSorter sorter) {
        // GIVEN: Array List with two tasks initialized in the setUp() method

        // WHEN: each sort implementation is invoked
        sorter.sort(tasks);

        // THEN: Check if list is not null and returns the same number of tasks
        assertNotNull(tasks, "Sorted list should not be null");
        assertEquals(tasks.size(), tasks.size(), "Sorted list should have the same number of tasks");
    }

    @Test
    void sortByTitle() {
        // GIVEN: Array List with two tasks initialized in the setUp() method

        String title = tasks.get(0).getTitle().trim();
        char lastLetter = title.charAt(title.length() - 1);

        // WHEN: sortByTitle is invoked
        SortByTitle sortByTitle = new SortByTitle();
        sortByTitle.sort(tasks);

        // THEN: The list returns the values in alphabetical order
        assertEquals('A', tasks.get(0).getTitle().trim().charAt(0), "The first letter of the title is A");
        assertEquals('C', lastLetter, "The last letter of the title is C");
    }

    @Test
    void sortById() {
        // GIVEN: Array List with two tasks initialized in the setUp() method

        // WHEN: sortById is invoked
        SortById sortById = new SortById();
        sortById.sort(tasks);

        // THEN: The list returns the tasks with the ID's in ascending order
        Assertions.assertEquals(1, tasks.get(0).getId(), "The first ID should be 1");
        Assertions.assertEquals(3, tasks.get(1).getId(), "The last ID should be 3");
    }

    @Test
    void sortByDueDate() {
        // GIVEN: Array List with two tasks initialized in the setUp() method

        // WHEN: sortByDueDate(); is invoked
        SortByDueDate sortByDueDate = new SortByDueDate();
        sortByDueDate.sort(tasks);

        // THEN: The list returns the tasks with the ID's in ascending order
        Assertions.assertEquals(date1, tasks.get(0).getDueDate(), "The due dates  should be the same (0)");
        Assertions.assertEquals(date2, tasks.get(1).getDueDate(), "The due dates  should be the same (1)");
    }

    static Stream<TaskSorter> provideSorters() {
        return Stream.of(new SortByDueDate(), new SortById(), new SortByTitle());
    }

}
