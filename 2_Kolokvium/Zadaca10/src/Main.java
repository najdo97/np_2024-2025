import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


class UserIdAlreadyExistsException extends Exception {

}

interface ILocation {
    double getLongitude();

    double getLatitude();

    LocalDateTime getTimestamp();
}

class Location implements ILocation {

    private double Longitude;
    private double Latitude;
    private LocalDateTime Timestamp;

    public Location(double longitude, double latitude, LocalDateTime timestamp) {
        Longitude = longitude;
        Latitude = latitude;
        Timestamp = timestamp;
    }

    @Override
    public double getLongitude() {
        return this.Longitude;
    }

    @Override
    public double getLatitude() {
        return this.Latitude;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return this.Timestamp;
    }
}

class User {
    private String name;
    private List<Location> knownLocations;

    private boolean isInfected;
    private LocalDateTime timeOfInfection;

    public User(String name) {
        this.name = name;
        this.knownLocations = new ArrayList<>();
    }

    public User(String name, List<Location> knownLocations) {
        this.name = name;
        this.knownLocations = knownLocations;
    }

    public void addLocation(ILocation iLocation) {
        Location location = new Location(iLocation.getLongitude(), iLocation.getLatitude(), iLocation.getTimestamp());
        this.knownLocations.add(location);
    }

    public void detectInfection(LocalDateTime timeOfInfection) {
        this.isInfected = true;
        this.timeOfInfection = timeOfInfection;
    }

    public List<Location> getKnownLocations() {
        return knownLocations;
    }

    public LocalDateTime getTimeOfInfection() {
        return timeOfInfection;
    }

    public boolean calculateProximity(User u) {
        for (int i = 0; i < this.knownLocations.size(); i++) {
            Location location1 = this.knownLocations.get(i);
            for (int j = 0; j < u.knownLocations.size(); j++) {
                Location location2 = u.knownLocations.get(j);
                double distance = Math.sqrt(Math.pow(location1.getLongitude() - location2.getLongitude(), 2) + Math.pow(location1.getLatitude() - location2.getLatitude(), 2));
                if (distance <= 2) {
                    return true;
                }
            }
        }
        return false;
    }
}

class StopCoronaApp {

    private Map<String, User> people;


    public StopCoronaApp() {
        this.people = new HashMap<>();
    }


    public void addUser(String name, String id) throws UserIdAlreadyExistsException {

        if (this.people.get(id) != null) {
            throw new UserIdAlreadyExistsException();
        }
        this.people.put(id, new User(name));
    }

    void addLocations(String id, List<ILocation> iLocations) {

        if (this.people.get(id) == null) {
            return;
        }
        User user = this.people.get(id);
        for (int i = 0; i < iLocations.size(); i++) {
            user.addLocation(iLocations.get(i));
        }


    }

    void detectNewCase(String id, LocalDateTime timestamp) {
        //што симулира пријавување на даден корисник дека е носител на вирусот.
        // Првиот аргумент е неговото ID, а вториот е времето кога корисникот пријавил дека е носител.

        if (this.people.get(id) == null) {
            return;
        }
        this.people.get(id).detectInfection(timestamp);

    }

    Map<User, Integer> getDirectContacts(User u) {
        // што ќе враќа мапа во која што клучеви ќе се сите блиски контакти на корисикот u,
        // а соодветните вредности во мапата ќе се колку пати се оствариле блиски контакти со корисникот u.

        List<User> closeContacts =
                this.people.values()
                        .stream()
                        .filter(user -> {
                            Duration duration = Duration.between(user.getTimeOfInfection(), u.getTimeOfInfection());
                            return Math.abs(duration.toMinutes()) < 5;
                        })
                        .filter(user -> {
                            return user.calculateProximity(u);
                        })
                        .collect(Collectors.toList());

        return closeContacts
                .stream()
                .collect(Collectors.groupingBy(
                        user -> user,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

    }

    Collection<User> getIndirectContacts(User u) {
        //што ќе враќа колекција од индиректните контакти на корисникот u. За индиректни контакти се сметаат блиските контакти на директните контакти на u,
        // при што еден корисник не може да биде и директен и индиректен контакт на некој друг корисник.
    }

    void createReport() {
        // што ќе креира и испечати извештај за МЗ во којшто за сите корисници-носители на вирусот ќе испечати информации во следниот формат.

        //[user_name] [user_id] [timestamp_detected]
        //Direct contacts:
        //[contact1_name] [contact1_first_five_letters_of_id] [number_of_detected_contacts1]
        //[contact2_name] [contact2_first_five_letters_of_id] [number_of_detected_contacts1]
        //...
        //[contactN_name] [contactN_first_five_letters_of_id] [number_of_detected_contactsN]
        //Count of direct contacts: [sum]
        //Indirect contacts:
        //[contact1_name] [contact1_first_five_letters_of_id]
        //[contact2_name] [contact2_first_five_letters_of_id]
        //...
        //[contactN_name] [contactN_first_five_letters_of_id]
        //Count of indirect contacts: [count]

        //Дополнително на крајот на извештајот да се испечати просечниот број на директни и индиректни контакти на корисниците што се носители на Корона вирусот.
    }


}


public class StopCoronaTest {

    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StopCoronaApp stopCoronaApp = new StopCoronaApp();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "REG": //register
                    String name = parts[1];
                    String id = parts[2];
                    try {
                        stopCoronaApp.addUser(name, id);
                    } catch (UserAlreadyExistException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "LOC": //add locations
                    id = parts[1];
                    List<ILocation> locations = new ArrayList<>();
                    for (int i = 2; i < parts.length; i += 3) {
                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
                    }
                    stopCoronaApp.addLocations(id, locations);

                    break;
                case "DET": //detect new cases
                    id = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    stopCoronaApp.detectNewCase(id, timestamp);

                    break;
                case "REP": //print report
                    stopCoronaApp.createReport();
                    break;
                default:
                    break;
            }
        }
    }

    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
        return new ILocation() {
            @Override
            public double getLongitude() {
                return Double.parseDouble(lon);
            }

            @Override
            public double getLatitude() {
                return Double.parseDouble(lat);
            }

            @Override
            public LocalDateTime getTimestamp() {
                return LocalDateTime.parse(timestamp);
            }
        };
    }
}
