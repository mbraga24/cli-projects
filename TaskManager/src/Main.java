import com.taskmanager.actions.Actions;
import com.taskmanager.controller.TaskController;
import com.taskmanager.repository.TaskFileDataAccessService;

public class Main {

    public static void main(String[] args) {

        TaskFileDataAccessService taskFileDataAccessService = new TaskFileDataAccessService();
        Actions actions = new Actions();

        new TaskController(actions, taskFileDataAccessService).start();

    }
}