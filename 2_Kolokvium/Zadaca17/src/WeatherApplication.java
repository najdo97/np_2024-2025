/*
* Да се имплементира решение со кое ќе биде овозможено прикажување на временските услови во реално време измерени од некоја мерна станица. За потребите на ова решение потребно е да се имплементира класа WeatherDispatcher која ќе има за цел да ги прибере податоците за температура, влажност и притисок од мерната станица и да ги дистрибуира до сите оние ентитети кои сакаат истите да ги прикажат. Во случајот треба да се имплементираат два такви ентитети CurrentConditionsDisplay и ForecastDisplay. Овие класи во конструкторот го примаат диспечерот чии податоци сакаат да ги прикажуваат и истите ги прикажуваат податоците кои диспечерот ги прибрал преку методот public void setMeasurements(float temperature, float humidity, float pressure).

За ForecastDisplay форматот е:

Forecast: [Improving, Same, Cooler], Improving се печати доколку моменталниот притисок е поголем од претходно прикажаниот. Same се печати доколку моменталниот притисок е еднаков на претходно прикажаниот. Cooler се печати доколку моменталниот притисок е помал од претходно прикажаниот. Првичниот притисок е поставен на вредност 0.0.

За CurrentConditionsDisplay форматот е -

Temperature: [тековна температура]F

Humidity: [тековна влажност]%

Дополнително, диспечерот имплементира и методи за додавање register и бришење remove на ентитети кои ќе ги прикажуваат неговите податоци.
* */

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
            if(parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if(operation==1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if(operation==2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if(operation==3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if(operation==4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}