import com.taskmanager.config.SetupData;
import com.taskmanager.controller.TaskController;

public class Main {

    public static void main(String[] args) {

//        SetupData.initialize();

        new TaskController().start();

    }
}