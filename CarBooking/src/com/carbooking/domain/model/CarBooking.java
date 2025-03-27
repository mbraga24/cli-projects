package com.carbooking.booking;

import com.carbooking.car.Car;
import com.carbooking.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class CarBooking {

    private String bookingId;
    private User user;
    private Car car;
    private String bookingTime;
    private boolean isCanceled;
    private static AtomicInteger seq;

    static {
        seq = new AtomicInteger();
    }

    public CarBooking(String bookingId, User user, Car car, String bookingTime, boolean isCanceled) {
        this.bookingId = bookingId;
        this.user = user;
        this.car = car;
        this.bookingTime = bookingTime;
        this.isCanceled = isCanceled;
    }

    public CarBooking(String bookingId, User user, Car car, String bookingTime) {
        this(bookingId, user, car, bookingTime, false);
    }

    public CarBooking(Car car, User user) {
        this.bookingId = String.valueOf(1000 + seq.incrementAndGet());
        this.car = car;
        this.user = user;
        this.bookingTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/dd/yyyy | HH:mm:ss a"));
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public String toString() {
        return String.format(
                "CarBooking{bookingId=%s, com.carbooking.user=%s, com.carbooking.car=%s, bookingTime=%s, isCanceled=%b}",
                bookingId, user, car, bookingTime, isCanceled
        );
    }

}
