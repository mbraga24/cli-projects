package com.carbooking.utils;

import com.carbooking.domain.model.Car;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByYear implements CarSorter{

    @Override
    public void sort(List<Car> cars) {
        //🗒Note: Can't combine method references with '&&'. Opt for multilevel, chaining, sorting
        Collections.sort(cars, Comparator.comparing(Car::getYear));
    }

}
