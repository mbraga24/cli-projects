import com.taskmanager.actions.Actions;
import com.taskmanager.controller.TaskController;
import com.taskmanager.repository.TaskDataAccessService;
import com.taskmanager.repository.TaskFakerDataAccessService;
import com.taskmanager.repository.TaskManagerDAO;
import com.taskmanager.service.TaskManagerService;

public class Main {

    public static void main(String[] args) {

        TaskManagerDAO taskManagerDAO = new TaskDataAccessService();
        TaskManagerService taskManagerService = new TaskManagerService(taskManagerDAO);
        TaskFakerDataAccessService taskFakerDataAccessService = new TaskFakerDataAccessService(taskManagerService);
        Actions actions = new Actions();

        new TaskController(actions, taskFakerDataAccessService).start();
    }
}