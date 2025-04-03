import com.taskmanager.actions.Actions;
import com.taskmanager.controller.TaskController;
import com.taskmanager.repository.TaskFakerDataAccessService;

public class Main {

    public static void main(String[] args) {

        TaskFakerDataAccessService taskFakerDataAccessService = new TaskFakerDataAccessService();
        Actions actions = new Actions();

        new TaskController(actions, taskFakerDataAccessService).start();

    }
}