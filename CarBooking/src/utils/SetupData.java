package utils;

import booking.CarBooking;
import booking.CarBookingService;
import car.Car;
import car.CarService;
import model.Brand;
import user.User;

import user.UserService;

import java.math.BigDecimal;

public class SetupData {

    public static void initialize() {
        // CARS
        Car teslaO = new Car("111", BigDecimal.valueOf(89.00), Brand.TESLA, true);
        Car teslaTw = new Car("222", BigDecimal.valueOf(109.00), Brand.TESLA, true);
        Car teslaTh = new Car("333", BigDecimal.valueOf(99.00), Brand.TESLA, true);
        Car audiO = new Car("0101", BigDecimal.valueOf(50.00), Brand.AUDI, false);
        Car audiTw = new Car("0202", BigDecimal.valueOf(44.00), Brand.AUDI, false);
        Car mercedes = new Car("0001", BigDecimal.valueOf(77.00), Brand.MERCEDES, false);
        // CARS END

        // USERS
        User tom = new User("100", "Tom", 24);
        User leon = new User("200", "Leon", 36);
        User betty = new User("300", "Betty", 28);
        // USERS END

        // CAR BOOKING
        CarBooking tomBookingO = new CarBooking(teslaO, tom);
        CarBooking tomBookingTw = new CarBooking(mercedes, tom);
        CarBooking leonBooking = new CarBooking(teslaTw, leon);
        CarBooking bettyBooking = new CarBooking(audiO, betty);
        // CAR BOOKING END

        // CLASS INSTANCES
        CarService carService = new CarService();
        CarBookingService carBookingService = new CarBookingService();
        UserService userService = new UserService();
        // CLASS INSTANCES END

        // CREATE CARS
        carService.createCar(teslaO);
        carService.createCar(teslaTw);
        carService.createCar(teslaTh);
        carService.createCar(audiO);
        carService.createCar(audiTw);
        carService.createCar(mercedes);
        // CREATE CARS END

        // CREATE USERS
        userService.createUser(tom);
        userService.createUser(betty);
        userService.createUser(leon);
        // CREATE USERS END

        // CREATE CAR BOOKING
        carBookingService.createCarBookingFromMain(tomBookingO);
        carBookingService.createCarBookingFromMain(tomBookingTw);
        carBookingService.createCarBookingFromMain(bettyBooking);
        carBookingService.createCarBookingFromMain(leonBooking);
        // CREATE CAR BOOKING END

    }

}