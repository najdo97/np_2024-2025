import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Observer {
    void update(float temperature, float humidity, float pressure);
}

class WeatherDispatcher {

    private float temperature;
    private float humidity;
    private float pressure;

    private List<Observer> observers;

    public WeatherDispatcher() {
        this.observers = new ArrayList<>();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;

        for (Observer observer : this.observers) {
            observer.update(temperature, humidity, pressure);
            System.out.println(observer.toString());
        }
    }

    public void register(Observer o) {
        if (!this.observers.contains(o)) {
            observers.add(o);
        }
    }

    public void remove(Observer o) {
        this.observers.remove(o);
    }

}

class CurrentConditionsDisplay implements Observer {
    private float temperature;
    private float humidity;

    public CurrentConditionsDisplay(WeatherDispatcher weatherDispatcher) {
        weatherDispatcher.register(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Temperature: " + temperature + "F" +
                "\n" + "Humidity: " + humidity + "%";
    }
}

class ForecastDisplay implements Observer {
    private float current_pressure = 0.00F;
    private float past_pressure = 0.00F;


    public ForecastDisplay(WeatherDispatcher weatherDispatcher) {
        weatherDispatcher.register(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.past_pressure = this.current_pressure;
        this.current_pressure = pressure;
    }

    @Override
    public String toString() {
        if (this.current_pressure > this.past_pressure) {
            return "Forecast: Improving \n";
        } else if (this.current_pressure < this.past_pressure) {
            return "Forecast: Cooler \n";
        } else return "Forecast: Same \n";
    }
}

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if (parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if (operation == 1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if (operation == 2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if (operation == 3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if (operation == 4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}