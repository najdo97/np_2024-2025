/*
* Да се имплементира класа DeliveryApp која ќе моделира една апликација за нарачки и достава на храна од ресторани. Во класата да се имплементираат следните методи:

Конструктор DeliveryApp (String name)
Метод void registerDeliveryPerson (String id, String name, Location currentLocation) - методот за регистрирање на слободен доставувач кој сака да работи за апликацијата.
Метод void addRestaurant (String id, String name, Location location) - метод за додавање на ресторан кој сака да овозможи достава на ставките од своето мени
Метод void addUser (String id, String name) - метод за регистрирање на корисник кој сака да ја користи апликацијата за нарачка и достава на храна
Метод void addAddress (String id, String addressName, Location location) - метод за додавање на адреса на корисникот со ИД id. Еден корисник може да има повеќе адреси (пр. Дома, работа и сл.)
метод void orderFood(String userId, String userAddressName, String restaurantId, float cost) - метод за нарачка на храна на корисникот со ID userID на неговата адреса userAddressName од ресторантот со ID restaurantId.

При процесирање на нарачката потребно е прво да се најде доставувач кој ќе ја достави нарачката до клиентот. Нарачката се доделува на доставувачот кој е најблиску до ресторанот. Во случај да има повеќе доставувачи кои се најблиску до ресторанот - се избира доставувачот со најмалку извршени достави досега.
По доделување на нарачката на определен доставувач, се менува неговата моментална локација во локацијата на клиентот кому му се доставува нарачката.
Доставувачот заработува од нарачката така што добива 90 денари за секоја нарачка, и дополнителни 10 денари на секои10 единици растојание од ресторанот до клиентот (пр. ако растојанието е 35 единици = 90+3х10 = 120)
метод void printUsers() - метод кој ги печати сите корисници на апликацијата сортирани во опаѓачки редослед според потрошениот износ за нарачка на храна преку апликацијата
метод void printRestaurants() - метод кој ги печати сите регистрирани ресторани во апликацијата, сортирани во опаѓачки редослед според просечната цена на нарачките наплатени преку апликацијата
метод void printDeliveryPeople() - метод кој ги печати сите регистрирани доставувачи сортирани во опачки редослед според заработениот износ од извршените достави.*/

import java.util.*;

/*
YOUR CODE HERE
DO NOT MODIFY THE interfaces and classes below!!!
*/

interface Location {
    int getX();

    int getY();

    default int distance(Location other) {
        int xDiff = Math.abs(getX() - other.getX());
        int yDiff = Math.abs(getY() - other.getY());
        return xDiff + yDiff;
    }
}

class LocationCreator {
    public static Location create(int x, int y) {

        return new Location() {
            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }
        };
    }
}

public class DeliveryAppTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String appName = sc.nextLine();
        DeliveryApp app = new DeliveryApp(appName);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            if (parts[0].equals("addUser")) {
                String id = parts[1];
                String name = parts[2];
                app.addUser(id, name);
            } else if (parts[0].equals("registerDeliveryPerson")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.registerDeliveryPerson(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addRestaurant")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addRestaurant(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addAddress")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addAddress(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("orderFood")) {
                String userId = parts[1];
                String userAddressName = parts[2];
                String restaurantId = parts[3];
                float cost = Float.parseFloat(parts[4]);
                app.orderFood(userId, userAddressName, restaurantId, cost);
            } else if (parts[0].equals("printUsers")) {
                app.printUsers();
            } else if (parts[0].equals("printRestaurants")) {
                app.printRestaurants();
            } else {
                app.printDeliveryPeople();
            }

        }
    }
}


//Input:

//Korpa
//addUser 1 stefan
//addRestaurant 1 Morino 2 4
//addAddress 1 doma 2 3
//registerDeliveryPerson 1 Petko 1 2
//registerDeliveryPerson 2 Riste 2 3
//orderFood 1 doma 1 450
//printUsers


//output:
//ID: 1 Name: stefan Total orders: 1 Total amount spent: 450.00 Average amount spent: 450.00