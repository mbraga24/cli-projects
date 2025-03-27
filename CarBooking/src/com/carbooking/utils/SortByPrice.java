package com.carbooking.utils;

import com.carbooking.domain.model.Car;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByPrice implements CarSorter {

    @Override
    public void sort(List<Car> cars) {
        Collections.sort(cars, Comparator.comparing(Car::getRentalPricePerDay));
    }

}
