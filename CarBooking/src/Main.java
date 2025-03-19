import actions.Actions;
import utils.SetupData;

public class Main {
    private static String userInput = "";

    public static void main(String[] args) {
        SetupData.initialize();
        do {
            userInput = Actions.collectUserInput();
            Actions.triggerOption(userInput);
        } while (!userInput.equals("7"));
    }
}