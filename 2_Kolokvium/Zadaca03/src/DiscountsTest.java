import java.util.*;
import java.util.stream.Collectors;

class Store {

    private String name;
    private List<String> prices;

    public Store() {
        this.prices = new ArrayList<>();
    }

    public Store(String name, List<String> prices) {
        this.name = name;
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPrices() {
        return prices;
    }

    public void setPrices(List<String> prices) {
        this.prices = prices;
    }

    public float calculateAverageDiscount() {
        float answer = 0;

        for (String price : this.prices) {
            String[] prices = price.split(":");
            int discounted = Integer.parseInt(prices[0]);
            int regular = Integer.parseInt(prices[1]);

            answer += 100 - (((float) discounted / regular) * 100);

        }

        return answer / this.prices.size();
    }


    public float calculateTotalDiscount() {
        float answer = 0;

        for (String price : this.prices) {
            String[] prices = price.split(":");
            int discounted = Integer.parseInt(prices[0]);
            int regular = Integer.parseInt(prices[1]);

            answer += (regular - discounted);

        }

        return answer;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.prices.size(); i++) {

            String[] prices = this.prices.get(i).split(":");
            int discounted = Integer.parseInt(prices[0]);
            int regular = Integer.parseInt(prices[1]);
            sb.append(100 - (((float) discounted / regular) * 100) + " " + discounted + "/" + regular);
            if (i < this.prices.size()-1) {
                sb.append("\n");
            }

        }

        return this.name
                + '\n' +
                "Average discount: " + this.calculateAverageDiscount() + "%\n" +
                "Total discount: " + (int) this.calculateTotalDiscount() + "\n"
                + sb.toString();

    }
}

class Discounts {
    private List<Store> stores;

    public Discounts() {
        this.stores = new ArrayList<>();
    }

    public Discounts(List<Store> stores) {
        this.stores = stores;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public int readStores(Scanner in) {

        while (in.hasNext()) {


            String line = in.nextLine().trim();

            String[] storeInput = line.split(" ");

            String store_name = storeInput[0];

            ArrayList<String> store_prices = new ArrayList<>();
            for (int i = 1; i < storeInput.length; i++) {
                if (!storeInput[i].equals("")) {
                    store_prices.add(storeInput[i]);
                }
            }

            this.stores.add(new Store(store_name, store_prices));
        }
        return this.stores.size();
    }

    public List<Store> byAverageDiscount() {
        List<Store> topDiscountStores = this.stores
                .stream()
                .sorted(
                        Comparator
                                .comparing(Store::calculateAverageDiscount).reversed()
                                .thenComparing(Store::getName)
                )
                .limit(3)
                .collect(Collectors.toCollection(ArrayList::new));

        return topDiscountStores;
    }

    public List<Store> byTotalDiscount() {

        return this.stores
                .stream()
                .sorted(Comparator
                        .comparing(Store::calculateTotalDiscount)
                        .thenComparing(Store::getName)
                )
                .limit(3)
                .collect(Collectors.toCollection(ArrayList::new));

    }

}


public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(new Scanner(System.in));
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}