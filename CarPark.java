import java.util.*;
import java.util.stream.Collectors;

public class CarPark {

    public static void main(String[] args)
    {
        // Массивы (Работа с парком машин)
        int[] years = new int[50];
        Random random = new Random();
        for (int i = 0; i < 50; i++)
        {
            years[i] = random.nextInt(2026 - 2000) + 2000;
        }

        System.out.println("Годы выпуска машин: " + Arrays.toString(years));

        // Вывод машин, выпущенных после 2015 года
        System.out.print("Машины, выпущенные после 2015 года: ");
        for (int year : years)
        {
            if (year > 2015)
            {
                System.out.print(year + " ");
            }
        }
        System.out.println();

        // Посчитать средний возраст авто
        double sum = 0;
        for (int year : years)
        {
            sum += year;
        }
        double averageAge = sum / years.length;
        System.out.printf("Средний год выпуска: %.2f%n", averageAge);
        
        // Коллекции (Управление моделями)
        List<String> models = Arrays.asList(
                "Toyota Camry", "BMW X5", "Toyota Camry", "Mercedes C-Class", "BMW X5", "Audi A4", "Tesla Model S");

        System.out.println("\nСписок моделей: " + models);

        // Удалить дубликаты, отсортировать и вывести
        Set<String> uniqueModels = new HashSet<>(models);
        List<String> sortedModels = new ArrayList<>(uniqueModels);
        Collections.sort(sortedModels, Collections.reverseOrder());

        System.out.print("Уникальные модели (отсортированные в обратном алфавитном порядке): ");
        for (String model : sortedModels)
        {
            System.out.print(model + " ");
        }
        System.out.println();

        // Заменить Tesla на ELECTRO_CAR
        uniqueModels.forEach(model ->
        {
            if (model.contains("Tesla"))
            {
                System.out.println("Замена модели: " + model + " на ELECTRO_CAR");
            }
        });
        
        // equals/hashCode (Сравнение автомобилей)
        HashSet<Car> carSet = new HashSet<>();
        carSet.add(new Car("VIN123", "Toyota Camry", 2020, "Automatic", 25000));
        carSet.add(new Car("VIN456", "BMW X5", 2022, "Automatic", 40000));
        carSet.add(new Car("VIN123", "Toyota Camry", 2020, "Automatic", 25000)); // Дубликат по VIN

        System.out.println("\nКоличество машин в HashSet: " + carSet.size());
        
        // Stream API (Анализ автопарка)
        List<Car> cars = Arrays.asList(
                new Car("VIN1", "Toyota Camry", 2020, "Automatic", 25000),
                new Car("VIN2", "BMW X5", 2022, "Automatic", 40000),
                new Car("VIN3", "Mercedes C-Class", 2018, "Manual", 30000),
                new Car("VIN4", "Audi A4", 2021, "Automatic", 35000),
                new Car("VIN5", "Tesla Model S", 2023, "Automatic", 60000)
        );

        // Добавить поле mileage (предположим, что пробег случайный от 0 до 100000)
        cars.forEach(car -> car.setMileage(new Random().nextInt(100001)));

        System.out.println("\nСписок машин с пробегом: " + cars);

        // Отфильтровать машины с пробегом меньше 50_000 км
        List<Car> filteredCars = cars.stream()
                .filter(car -> car.getMileage() < 50000)
                .collect(Collectors.toList());
        System.out.println("Машины с пробегом меньше 50000: " + filteredCars);

        // Отсортировать по цене (по убыванию)
        List<Car> sortedByPrice = cars.stream()
                .sorted(Comparator.comparingDouble(Car::getPrice).reversed())
                .collect(Collectors.toList());
        System.out.println("Машины отсортированные по цене (убывание): " + sortedByPrice);

        // Вывести топ-3 самые дорогие машины
        List<Car> top3Expensive = sortedByPrice.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Топ 3 самых дорогих машин: " + top3Expensive);

        // Посчитать средний пробег всех машин
        double averageMileage = cars.stream()
                .mapToDouble(Car::getMileage)
                .average()
                .orElse(0);
        System.out.printf("Средний пробег: %.2f%n", averageMileage);

        // Сгруппировать машины по производителю
        Map<String, List<Car>> groupedByManufacturer = cars.stream()
                .collect(Collectors.groupingBy(Car::getManufacturer));
        System.out.println("Машины сгруппированные по производителю: " + groupedByManufacturer);
    }
}

class Car
{
    private String vin;
    private String model;
    private String manufacturer;
    private int year;
    private String transmission;
    private double price;
    private int mileage;

    public Car(String vin, String model, int year, String transmission, double price)
    {
        this.vin = vin;
        this.model = model;
        this.manufacturer = getManufacturer(model); // Извлекаем производителя из модели
        this.year = year;
        this.transmission = transmission;
        this.price = price;
    }

    public String getManufacturer(String model)
    {
        String[] parts = model.split(" ");
        return parts[0];
    }
    
    public String getVin()
    {
        return vin;
    }

    public String getModel()
    {
        return model;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public int getYear()
    {
        return year;
    }

    public String getTransmission()
    {
        return transmission;
    }

    public double getPrice()
    {
        return price;
    }

    public int getMileage()
    {
        return mileage;
    }

    public void setMileage(int mileage)
    {
        this.mileage = mileage;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(vin, car.vin);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(vin);
    }

    @Override
    public String toString()
    {
        return "Car{" + "vin='" + vin + '\'' + ", model='" + model + '\'' + ", manufacturer='" + manufacturer + '\'' + ", year=" + year + ", transmission='" + transmission + '\'' + ", price=" + price + ", mileage=" + mileage + '}';
    }
}
