package car;

import java.util.ArrayList;
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
        List<Car> availableCars = new ArrayList<>(returnCars().size());
        for (int i = 0; i < returnCars().size(); i++) {
            if (returnCars().get(i).getAvailable() == true) {
                availableCars.add(returnCars().get(i));
            }
        }
        return availableCars;
    }

    public List<Car> returnElectricCars() {
        return carDAO.getAllCars()
                .stream()
                .filter(Car::isElectric)
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

    public List<Car> returnAvailableCarsByBrand (Enum brand){
        int countCars = countCarsByBrand(brand);
        List<Car> carsByBrand = new ArrayList<>();
        for (int i = 0; i < countCars; i++) {
            if (carDAO.getAllCars().get(i).getBrand().equals(brand) && carDAO.getAllCars().get(i).getAvailable() == true) {
                carsByBrand.add(carDAO.getAllCars().get(i));
            }
        }
        return carsByBrand;
    }

    private int countCarsByBrand (Enum brand){
        int countCars = 0;
        for (Car car : carDAO.getAllCars()) {
            if (car.getBrand().equals(brand)) countCars++;
        }
        return countCars;
    }
}
