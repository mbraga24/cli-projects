package car;

import model.Brand;

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

    public int returnNumberOfTotalCars() {
        int totalCars = 0;
        for (Car car : carDAO.getAllCars()) {
            if (car != null) totalCars++;
        }
        return totalCars;
    }

    public Car returnCarByRegNumber(String regNumber) {
        return carDAO.getAllCars()
                .stream()
                .filter(c -> c.getRegNumber().equals(regNumber))
                .findFirst()
                .orElse(null);
    }

    public List<Car> returnAvailableCarsByBrand(String brandChoice){
        Brand brand = Brand.valueOf(brandChoice.toUpperCase());
        List<Car> filtered = returnCars()
                .stream()
                .filter(c -> c.getAvailable() && c.getBrand().equals(brand))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("----------------");
            System.out.println("No cars found");
            System.out.println("----------------");
        }
        return filtered;
    }

    private int countCarsByBrand (Enum brand){
        int countCars = 0;
        for (Car car : carDAO.getAllCars()) {
            if (car.getBrand().equals(brand)) countCars++;
        }
        return countCars;
    }
}
