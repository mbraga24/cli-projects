import com.carbooking.actions.Actions;
import com.carbooking.controller.BookingController;
import com.carbooking.utils.SetupData;

public class Main {
    public static void main(String[] args) {
        SetupData.initialize();
        BookingController.start();
    }
}