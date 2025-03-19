package actions;

import booking.CarBookingService;
import car.Car;
import car.CarService;
import user.User;
import user.UserService;
import utils.Utils;

import java.util.Scanner;

public class Actions {

    private final static String CANCEL = "cancel";
    private static String userId = "";
    private static String carRegNumber = "";
    private static String userInput = "";
    private static UserService userService = new UserService();
    private static CarService carService = new CarService();
    private static CarBookingService carBookingService = new CarBookingService();
    private static Scanner scanner = new Scanner(System.in);

    private static void displayOptions() {
        Integer[] numberOptions = {1, 2, 3, 4, 5, 6, 7};
        String[] textOptions = {"Book Car",
                "View All Cars Booked by Users",
                "View All Bookings",
                "View Available Cars",
                "View Available Electric Cars",
                "View All Users",
                "Exit"};

        for (int option = 0; option < 7; option++) {
            System.out.println(numberOptions[option] + ": " + textOptions[option]);
        }
    }

    public static void triggerOption(String option) {
        switch (option) {
            case "1":
                Utils.printMessage("Book a car");
                userId = provideUserId();
                if (userId.equals(CANCEL)) break;
                carRegNumber = provideCarRegNumber();
                if (carRegNumber.equals(CANCEL)) break;
                carBookingService.createCarBooking(userId, carRegNumber);
                break;
            case "2":
                Utils.printMessage("View All Cars Booked by Users");
                userId = provideUserId();
                if (userId.equals(CANCEL)) break;
                Utils.display(carBookingService.returnCarBookingsByUser(userId));
                break;
            case "3":
                Utils.printMessage("View all bookings");
                Utils.display(carBookingService.returnCarBookings());
                break;
            case "4":
                Utils.printMessage("View available cars");
                Utils.display(carService.returnAvailableCars());
                break;
            case "5":
                Utils.printMessage("View Available Electric Cars");
                Utils.display(carService.returnElectricCars());
                break;
            case "6":
                Utils.printMessage("View All Users");
                Utils.display(userService.returnUsers());
                break;
            default:
                System.out.println("Exit.");
                break;
        }
    }

    public static String collectUserInput() {
        int ascii = 0;
        char character = '\0';
        boolean isValidOption;
        do {
            isValidOption = true;
            displayOptions();
            System.out.println("Make a selection:");
            userInput = scanner.nextLine();

            if (userInput.length() > 1) isValidOption = false;
            if (isValidOption != false && userInput.length() > 0) {
                character = userInput.charAt(0);
                ascii = (int) character;
            }
            if (userInput.isEmpty() || userInput.isBlank()) {
                System.out.println();
                Utils.printMessage("Input cannot be blank");
            } else if (ascii < 49 || ascii > 55) {
                System.out.println();
                Utils.printMessage("Invalid option. Please use one of the options listed below:");
            }
        } while (userInput.isEmpty() || userInput.isBlank() || ascii < 49 || ascii > 55);
        return userInput;
    }

    private static String provideUserId() {
        String userId = "";
        boolean isValid = false;
        do {
            Utils.display(userService.returnUsers());
            System.out.println("Enter an user ID: ");
            userId = scanner.nextLine();
            isValid = validateUserId(userId);
            if (userId.equals("c") || userId.equals("C")) {
                isValid = true;
                userId = CANCEL;
                System.out.println();
                Utils.printMessage("Operation Cancelled");
            }
            if (!isValid) {
                Utils.printMessage("Please, enter a valid user Id from the list of users + \n" +
                        "provided below or press 'c' to cancel this operation:");
            }
        } while(userId.isBlank() || userId.isEmpty() || isValid == false);
        return userId;
    }

    private static boolean validateUserId(String userId) {
        return userService.returnUsers()
                .stream()
                .anyMatch(u -> u.getId().equals(userId));
    }

    private static String provideCarRegNumber() {
        String carRegNumber = "";
        boolean isValid = false;
        do {
            Utils.display(carService.returnAvailableCars());
            System.out.println("Enter car's registration number:");
            carRegNumber = scanner.nextLine();
            isValid = validateCarRegNumber(carRegNumber);
            if (carRegNumber.equals("c") || carRegNumber.equals("C")) {
                isValid = true;
                carRegNumber = CANCEL;
                System.out.println();
                Utils.printMessage("Operation Cancelled");
            }
            if (!isValid) {
                Utils.printMessage("Please, enter a valid car registration number from the list + \n" +
                        "of available cars provided below or press 'c' to cancel this + \n" +
                        "operation:");
            }

        } while(carRegNumber.isEmpty() || carRegNumber.isBlank() || isValid == false);
        return carRegNumber;
    }

    private static boolean validateCarRegNumber(String carRegNumber) {
        return carService.returnAvailableCars()
                .stream()
                .anyMatch(c -> c.getRegNumber().equals(carRegNumber));
    }

}