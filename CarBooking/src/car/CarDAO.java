package car;

import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    private static List<Car> cars;
    private static int nextAvailableSlot = 0;
    private static final int CAPACITY = 10;

    static {
        cars = new ArrayList<>(CAPACITY);
    }

    public void saveCar(Car car) {
        cars.add(car);
        nextAvailableSlot++;
    }

    public List<Car> getAllCars() {
        return cars;
    }

}
