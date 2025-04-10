import java.util.*;
import java.util.stream.Collectors;

class Car {

    private String manufacturer;
    private String model;
    private int price;
    private float power;

    public Car(String manufacturer, String model, int price, float power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return manufacturer + " " + model + " (" + (int)power + "KW) " + price;
    }
}

class CarCollection {

    private List<Car> carCollection;

    public CarCollection() {
        this.carCollection = new ArrayList<>();
    }

    public void addCar(Car car) {
        this.carCollection.add(car);
    }

    public void sortByPrice(boolean ascending) {
        if (ascending) {
            this.carCollection =
                    this.carCollection
                            .stream()
                            .sorted(Comparator.comparing(Car::getPrice).thenComparing(Car::getPower))
                            .collect(Collectors.toList());
        } else {
            this.carCollection =
                    this.carCollection
                            .stream()
                            .sorted(Comparator.comparing(Car::getPrice).thenComparing(Car::getPower).reversed())
                            .collect(Collectors.toList());
        }
    }

    public List<Car> filterByManufacturer(String manufacturer) {
        return this.carCollection
                .stream()
                .filter(car -> car.getManufacturer().toLowerCase().equals(manufacturer.toLowerCase()))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }

    public List<Car> getList() {
        return this.carCollection;
    }
}

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length < 4) return parts[0];
            Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]));
            cc.addCar(car);
        }
        scanner.close();
        return "";
    }
}