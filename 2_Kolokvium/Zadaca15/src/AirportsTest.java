/*Да се имплементира класа Airports со следните методи:

public void addAirport(String name, String country, String code, int passengers) - метод за додавање нов аеродром (име, држава, код и број на патници кои ги превезува годишно)
public void addFlights(String from, String to, int time, int duration) - метод за додавање летови (код на аеродром за полетување, код на аеродром за слетување, време на тргнување во минути поминати од 0:00 часот, времетраење на летот во минути). Од аеродром А до аеродром Б може да има повеќе летови.
public void showFlightsFromAirport(String code) - метод кои ги прикажува сите летови од аеродромот со код code. Прво се печати името на аеродромот (формат во пример излезот), потоа се печатат сите летови (формат во пример излезот) подредени најпрво лексикографски според кодот на аеродромот дестинација, а потоа летовите кон тој аеродром според времето на полетување (целосно точна имплементација се смета без повикување на sort методи).
public void showDirectFlightsFromTo(String from, String to) - метод кој ги прикажува сите директни летови од аеродромот со код from до аеродромот со код to.
public void showDirectFlightsTo(String to) - метод кои ги прикажува сите директни летови до аеродромот со код to.
Сите летови треба да бидат сортирани според времето на полетување (целосно точна имплементација се смета без повикување на sort методи).
*/

import java.util.*;


class Airport {
    private String airportName;
    private String country;
    private String airportCode;
    private int passengers;

    public Airport(String airportName, String country, String airportCode, int passengers) {
        this.airportName = airportName;
        this.country = country;
        this.airportCode = airportCode;
        this.passengers = passengers;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
}

class Flight {
    private Airport from;
    private Airport to;
    private int time;
    private int duration;
}


class Airports {

    private List<Airport> airports;


    public Airports() {
        this.airports = new ArrayList<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        this.airports.add(new Airport(name, country, code, passengers));
    }

    public void addFlights(String from, String to, int time, int duration) {

    }

    public void showFlightsFromAirport(String code) {

    }

    ;

    public void showDirectFlightsFromTo(String from, String to) {

    }

    public void showDirectFlightsTo(String to) {

    }
}

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}