package com.carbooking.booking;

import com.carbooking.domain.model.CarBooking;

import java.util.ArrayList;
import java.util.List;

public class CarBookingDAO {

    private static final int CAPACITY = 100;
    private static int nextAvailableSlot = 0;
    private static List<CarBooking> carBookings;

    static {
        carBookings = new ArrayList<>(CAPACITY);
    }

    public void saveCarBooking(CarBooking carBooking) {
        carBookings.add(carBooking);
        nextAvailableSlot++;
    }

    public List<CarBooking> getAllCarBookings() {
        return carBookings;
    }

}
