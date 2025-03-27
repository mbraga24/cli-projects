import com.carbooking.controller.BookingController;
import com.carbooking.config.SetupData;

public class Main {
    public static void main(String[] args) {
        SetupData.initialize();
        BookingController.start();
    }
}