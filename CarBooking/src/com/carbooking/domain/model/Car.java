package com.carbooking.domain.model;

import java.math.BigDecimal;

public class Car {
    private String regNumber;
    private BigDecimal rentalPricePerDay;
    private Brand brand;
    private String year;
    private boolean isElectric;
    private Boolean isAvailable;

    public Car(String regNumber, BigDecimal rentalPricePerDay, Brand brand, String year, boolean isElectric) {
        this.regNumber = regNumber;
        this.rentalPricePerDay = rentalPricePerDay;
        this.brand = brand;
        this.isElectric = isElectric;
        this.year = year;
        this.isAvailable = true;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public String getYear() {
        return this.year;
    }

    public boolean isElectric() {
        return isElectric;
    }

    @Override
    public String toString() {
        return String.format(
                "Car{regNumber='%s', rentalPricePerDay=%.2f, brand='%s', year='%s', isElectric=%b, isAvailable=%b}",
                regNumber, rentalPricePerDay, brand, year, isElectric, isAvailable
        );
    }

}
