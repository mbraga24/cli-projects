package booking;

import car.Car;
import car.CarService;
import user.User;
import user.UserService;

import java.util.ArrayList;
import java.util.List;

public class CarBookingService {

    private CarBookingDAO carBookingDAO;
    private User user;
    private UserService userService;
    private CarService carService;

    public CarBookingService() {
        this.carBookingDAO = new CarBookingDAO();
        this.user = new User();
        this.carService = new CarService();
        this.userService = new UserService();
    }

    public void createCarBookingFromMain(CarBooking carBooking) {
        carService.setCarUnavailable(carBooking.getCar());
        carBookingDAO.saveCarBooking(carBooking);
    }

    public List<CarBooking> returnCarBookings() {
        return carBookingDAO.getAllCarBookings();
    }

    public void createCarBooking(String userId, String regNumber) {

        User user = userService.returnUsers()
                .stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElse(null);

        Car car = carService.returnCars()
                .stream()
                .filter(c -> c.getRegNumber().equals(regNumber))
                .findFirst()
                .orElse(null);

        CarBooking carBooking = new CarBooking(car, user);
        carService.setCarUnavailable(car);
        carBookingDAO.saveCarBooking(carBooking);
        this.printBookingConfirmation(car, user, carBooking);

    }

    public List<CarBooking> returnCarBookingsByUser(String userId) {
        List<CarBooking> bookingsByUser = new ArrayList<>(this.returnNumberOfTotalCarBookings());
        for (int i = 0; i < this.returnNumberOfTotalCarBookings(); i++) {
            if (this.returnCarBookings().get(i).getUser().getId().equals(userId)) {
                bookingsByUser.add(this.returnCarBookings().get(i));
            }
        }
        return bookingsByUser;
    }

    public int returnNumberOfTotalCarBookings() {
        int totalCarBooking = 0;
        for (CarBooking cb : carBookingDAO.getAllCarBookings()) {
            if (cb != null) totalCarBooking++;
        }
        return totalCarBooking;
    }

    private void printBookingConfirmation(Car car, User user, CarBooking carBooking) {
        System.out.println(String.format(
                "=> Car: %s%n" +
                        "--- Registration Number: %s%n" +
                        "=> User: %s%n" +
                        "--- User Id: %s%n" +
                        "=> Booking Details:%n" +
                        "--- Day/Time: %s%n" +
                        "--- Booking Confirmation: %s%n" +
                        "===============================================%n" +
                        "            SUCCESSFULLY BOOKED!%n" +
                        "===============================================",
                car.getBrand(), car.getRegNumber(),
                user.getFirstName(), user.getId(),
                carBooking.getBookingTime(), carBooking.getBookingId()
        ));
    }





}
