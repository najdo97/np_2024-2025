import java.util.*;

class Store {

    private String name;
    private int average_disc;


    public String toString() {
        /*
            [Store_name]
            Average discount: [заокружена вредност со едно децимално место]%
            Total discount: [вкупен апсолутен попуст]
            [процент во две места]% [цена на попуст]/[цена]
         */
    }

}

class Discounts {
    public int readStores(InputStream inputStream) {

    }

    public List<Store> byAverageDiscount() {

    }

    public List<Store> byTotalDiscount() {

    }

}


public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}