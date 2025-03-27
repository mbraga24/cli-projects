package com.carbooking.actions;

import com.carbooking.service.CarBookingService;
import com.carbooking.service.CarService;
import com.carbooking.utils.BrandHelper;
import com.carbooking.service.UserService;
import com.carbooking.utils.SortByPrice;
import com.carbooking.utils.SortByYear;
import com.carbooking.utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.carbooking.utils.Utils.printErrorMessage;

public class Actions {

    private final static String CANCEL = "cancel";
    private static String userId = "";
    private static String carRegNumber = "";
    private static String userInput = "";
    private static UserService userService = new UserService();
    private static CarService carService = new CarService();
    private static CarBookingService carBookingService = new CarBookingService();
    private static Scanner scanner = new Scanner(System.in);
    private static String[] mainMenuTextOptions = {
            "Book Car",
            "View All Bookings",
            "View Cars",
            "Sort Cars",
            "View Users",
            "Exit"
    };
    private static String[] viewTextOptions = {
            "View Available Cars",
            "View Available Electric Cars",
            "View All Cars Booked by Users",
            "View Available Cars by Brand",
    };
    private static String[] sortMenuTextOptions = {
            "Sort by Year",
            "Sort by Price"
    };


    private static void displayOptions(String[] textOptions) {
        int OptNumber = 0;
        for (int option = 0; option < textOptions.length; option++) {
            OptNumber = (option + 1);
            System.out.println( OptNumber + ": " + textOptions[option]);
        }
        System.out.format("Enter your choice (1-%s):", OptNumber);
    }

    /**
     * Main menu user options:
     * 1) Book a Car
     * 2) View all Bookings
     * 3) View Options
     * 4) View Users
     * @param option
     */
    public static void triggerOption(String option) {
        switch (option) {
            case "1":
                Utils.printMessage("Book a Car");
                userId = collectUserInput("Enter an user ID: ", () -> userService.returnUsers(), Actions::validateUserId);
                if (userId.equals(CANCEL)) break;
                carRegNumber = provideCarRegNumber();
                if (carRegNumber.equals(CANCEL)) break;
                carBookingService.createCarBooking(userId, carRegNumber);
                break;
            case "2":
                Utils.printMessage("View all bookings");
                Utils.display(carBookingService.returnCarBookings());
                break;
            case "3":
                viewCarOptions();
                break;
            case "4":
                sortCarOptions();
                break;
            case "5":
                Utils.printMessage("View Users");
                Utils.display(userService.returnUsers());
                break;
            default:
                System.out.println("Exit.");
                break;
        }
    }

    public static String mainMenuUserInput() {
        int ascii = 0;
        char character = '\0';
        boolean isValidOption;
        do {
            isValidOption = true;
            displayOptions(mainMenuTextOptions);
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
            } else if (ascii < 49 || ascii > 56) {
                System.out.println();
                Utils.printMessage("Invalid option. Please use one of the options listed below:");
            }
        } while (userInput.isEmpty() || userInput.isBlank() || ascii < 49 || ascii > 56);
        return userInput;
    }

    private static void viewCarOptions() {
        Utils.printMessage("What would you like to view?");
        displayOptions(viewTextOptions);

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                // View Available Cars
                Utils.display(carService.returnAvailableCars());
                break;
            case 2:
                // View Electric Cars
                Utils.display(carService.returnElectricCars());
                break;
            case 3:
                Utils.printMessage("View All Cars Booked by Users");
                userId = collectUserInput("Enter an user ID: ", () -> userService.returnUsers(), Actions::validateUserId);
                if (userId.equals(CANCEL)) break;
                Utils.display(carBookingService.returnCarBookingsByUser(userId));
                break;
            case 4:
                Utils.printMessage("View Available Cars by Brand");
                String brandChoice = collectUserInput("Enter brand name: ", BrandHelper::returnBrandOptions, Actions::validateCarBrand);
                Utils.display(carService.returnAvailableCarsByBrand(brandChoice));
                break;
            default:
                printErrorMessage("Invalid option. Try again.");
        }
    }

    private static void sortCarOptions() {
        Utils.printMessage("How would you like to sort?");
        displayOptions(sortMenuTextOptions);

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                carService.sortCars(new SortByYear());
                Utils.display(carService.returnCars());
                break;
            case 2:
                carService.sortCars(new SortByPrice());
                Utils.display(carService.returnCars());
                break;
            default:
                printErrorMessage("Invalid option. Try again.");
        }
    }

    private static <T> String collectUserInput(String prompt, Supplier<List<T>> supplier, Function<String, Boolean> validator) {
        String userInput = "";
        boolean isValid = false;
        do {
            Utils.display(supplier.get()); // => supplier
            System.out.println(prompt);
            userInput = scanner.nextLine();
            isValid = validator.apply(userInput); // => validator
            if (userInput.equals("c") || userInput.equals("C")) {
                isValid = true;
                userInput = CANCEL;
                System.out.println();
                Utils.printMessage("Operation Cancelled");
            }
            if (!isValid) {
                printErrorMessage("Please, enter a valid input from the given list + \n" +
                        "provided below or press 'c' to cancel this operation:");
            }
        } while(userInput.isBlank() || userInput.isEmpty() || isValid == false);
        return userInput;
    }

    private static boolean validateUserId(String userId) {
        return userService.returnUsers()
                .stream()
                .anyMatch(u -> u.getId().equals(userId));
    }

    private static boolean validateCarBrand(String brand) {
        return BrandHelper.returnBrandOptions()
                .stream()
                .anyMatch(option -> option.equalsIgnoreCase(brand));
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