package com.carbooking.service;

import com.carbooking.domain.model.Brand;
import com.carbooking.domain.model.Car;
import com.carbooking.repository.CarDAO;
import com.carbooking.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class CarService {

    private CarDAO carDAO;

    public CarService() {
        this.carDAO = new CarDAO();
    }

    public void createCar(Car car) {
        carDAO.saveCar(car);
    }

    public void setCarUnavailable(Car car) {
        car.setAvailable(false);
    }

    public List<Car> getAllCars() {
        return carDAO.getAllCars();
    }

    public Car getCar(String regNumber) {
        return carDAO.getAllCars()
                .stream()
                .filter(car -> car.getRegNumber().equals(regNumber))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(String.format("Car with the registration number %s not found", regNumber))
                );
    }

    public List<Car> returnCars() {
        return carDAO.getAllCars();
    }

    public List<Car> returnAvailableCars() {
        return returnCars()
                .stream()
                .filter(Car::getAvailable)
                .collect(Collectors.toList());
    }

    public List<Car> returnElectricCars() {
        return carDAO.getAllCars()
                .stream()
                .filter(c -> c.isElectric() && c.getAvailable())
                .collect(Collectors.toList());
    }

    public List<Car> returnAvailableCarsByBrand(String brandChoice){
        Brand brand = Brand.valueOf(brandChoice.toUpperCase());
        List<Car> filtered = returnCars()
                .stream()
                .filter(c -> c.getAvailable() && c.getBrand().equals(brand))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            Utils.printErrorMessage("No cars found");
        }
        return filtered;
    }

}
