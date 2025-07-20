/*
 * Да се имплементира класа Discounts за обработка на информации за цени и цени на попуст на одредени производи во неколку продавници (објекти од класа Store). Потребно е да се имплементираат следните методи:
 *
 * public int readStores(InputStream inputStream) - метод за вчитување на податоците за продавниците и цените на производите. Податоците за секоја продавница се во посебен ред во формат [ime] [cena_na_popust1:cena1] [cena_na_popust2:cena2] ... (погледнете пример). Методот враќа колку продавници се вчитани.
 * public List<Store> byAverageDiscount() - метод кој враќа листа од 3-те продавници со најголем просечен попуст (просечна вредност на попустот за секој производ од таа продавница). Попустот (намалувањето на цената) е изразен во цел број (проценти) и треба да се пресмета од намалената цена и оригиналната цена. Ако две продавници имаат ист попуст, се подредуваат според името лексикографски.
 * public List<Store> byTotalDiscount() - метод кој враќа листа од 3-те продавници со намал вкупен попуст (сума на апсолутен попуст од сите производи). Апсолутен попуст е разликата од цената и цената на попуст. Ако две продавници имаат ист попуст, се подредуваат според името лексикографски.
 * Дополнително за класата Store да се имплементира стринг репрезентација, односно методот:
 *
 * public String toString() кој ќе враќа репрезентација во следниот формат:
 *
 * [Store_name]
 * Average discount: [заокружена вредност со едно децимално место]%
 * Total discount: [вкупен апсолутен попуст]
 * [процент во две места]% [цена на попуст]/[цена]
 * ...
 * при што продуктите се подредени според процентот на попуст (ако е ист, според апсолутниот попуст) во опаѓачки редослед.
 * */

import java.util.*;
import java.util.stream.Collectors;

class Store {

    private String name;
    private List<ArrayList<Integer>> prices;


    public Store(String name, List<ArrayList<Integer>> prices) {
        this.name = name;
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArrayList<Integer>> getPrices() {
        return prices;
    }

    public void setPrices(List<ArrayList<Integer>> prices) {
        this.prices = prices;
    }

    public float calculateAverageDiscount() {
        float answer = 0;

        for (ArrayList<Integer> price : this.prices) {
            answer += price.get(0);
        }
        return answer / this.prices.size();
    }


    public float calculateTotalDiscount() {
        float answer = 0;

        for (ArrayList<Integer> price : this.prices) {
            answer += (price.get(2) - price.get(1));
        }
        return answer;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<ArrayList<Integer>> sortedList = new ArrayList<>();


        sortedList = this.prices
                .stream()
                .sorted(
                        Comparator
                                .<List<Integer>, Integer>comparing(subList -> subList.get(0))
                                .thenComparing(subList -> (subList.get(2) - subList.get(1))).reversed()
                )

                .collect(Collectors.toList());

        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).get(0) < 10) {
                sb.append(" "+sortedList.get(i).get(0) + "% " + sortedList.get(i).get(1) + "/" + sortedList.get(i).get(2));

            } else {
                sb.append(sortedList.get(i).get(0) + "% " + sortedList.get(i).get(1) + "/" + sortedList.get(i).get(2));
            }
            if (i < this.prices.size() - 1) {
                sb.append("\n");
            }
        }

        return this.name
                + '\n' +
                "Average discount: " + String.format("%.1f", this.calculateAverageDiscount()) + "%\n" +
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

            ArrayList<ArrayList<Integer>> store_prices = new ArrayList<>();
            for (int i = 1; i < storeInput.length; i++) {
                if (!storeInput[i].equals("")) {

                    String[] price = storeInput[i].split(":");
                    int discounted_price = Integer.parseInt(price[0]);
                    int regular_price = Integer.parseInt(price[1]);

                    ArrayList<Integer> pom = new ArrayList<>();
                    pom.add((int) (100 - (((float) discounted_price / regular_price) * 100)));
                    pom.add(discounted_price);
                    pom.add(regular_price);

                    store_prices.add(pom);
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