package com.taskmanager.config;

import com.taskmanager.domain.model.PersonalTask;
import com.taskmanager.domain.model.Task;
import com.taskmanager.service.TaskManagerService;
import com.taskmanager.domain.model.WorkTask;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SetupData {

    public static void initialize() {
        LocalDate localDatePlusTwo = LocalDate.now().plusDays(2);
        LocalDate localDatePlusThree = LocalDate.now().plusDays(3);
        LocalDate localDatePlusFive = LocalDate.now().plusDays(5);

        Date date1 = Date.from(localDatePlusThree.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from(localDatePlusTwo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date3 = Date.from(localDatePlusFive.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Task defaultPersonalTask1 = new PersonalTask("Make your bed", "Accomplish first task of the day.", date1, "home");
        Task defaultPersonalTask2 = new PersonalTask("Wash dishes", "Wash dishes after lunch.", date2, "home");
        Task defaultPersonalTask3 = new PersonalTask("Clean bathtub", "Clean bathtub once a week.", date3, "home");
        Task defaultWorkTask1 = new WorkTask("Work on meeting documents", "Review Thursday 3pm meeting.", date3, "Project Name1");

        TaskManagerService taskManagerService = new TaskManagerService();

        taskManagerService.addTask(defaultPersonalTask1);
        taskManagerService.addTask(defaultPersonalTask2);
        taskManagerService.addTask(defaultPersonalTask3);
        taskManagerService.addTask(defaultWorkTask1);
    }

}
