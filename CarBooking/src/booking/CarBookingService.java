package booking;

import car.Car;
import car.CarService;
import user.User;
import user.UserService;

import java.util.List;
import java.util.stream.Collectors;

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
        return returnCarBookings()
                .stream()
                .filter(b -> b.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    private void printBookingConfirmation(Car car, User user, CarBooking carBooking) {
        System.out.println(String.format(
                "\uD83D\uDE97 Car: %s%n" +
                        "    ✅  Registration Number: %s%n" +
                        "\uD83E\uDDD1 User: %s%n" +
                        "    ✅ User Id: %s%n" +
                        "\uD83E\uDDFE Booking Details:%n" +
                        "    ✅ Day/Time: %s%n" +
                        "    ✅ Booking Confirmation: %s%n" +
                        "====================================%n" +
                        "           ✅ SUCCESSFULLY BOOKED!%n" +
                        "====================================",
                car.getBrand(), car.getRegNumber(),
                user.getFirstName(), user.getId(),
                carBooking.getBookingTime(), carBooking.getBookingId()
        ));
    }
}
