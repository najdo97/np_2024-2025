/*
* Да се имплементира апликација за чување на продукти во една online продавница. За таа цел, дефинирајте класа OnlineShop во која што ќе ги чувате сите продукти во продавниците и ќе нуди функционалности за листање на продуктите и нивно купување. За класата да се имплементираат:

Default конструктор OnlineShop()
Метод void addProduct(String category, String id, String name, LocalDateTime createdAt, double price) - метод за додавање на производ во онлајн продавницата. Секој производ е дефиниран со категорија, ИД, име, датум кога се додава во продавницата и негова цена.
метод double buyProduct(String id, int quantity) - што ќе имплементира купување на quantity количина на производот со ИД id. Методот да врати колку пари се потрошени за оваа трансакција. Да се фрли исклучок од тип ProductNotFoundException доколку не постои производот. Методот да има комплексност О(1).
метод List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) којшто ќе ги излиста сите производи од категоријата category сортирани според компараторот comparatorType групирани во страници со големина pageSize (пагинација). Category може да биде и null па во тој случај се листаат сите продукти во онлајн продавницата.
COMPARATOR_TYPE е еnum којшто ви е даден во почетниот код. За печатење на продуктите користете ја вградената toString нотација во IDE-то (запазете го редоследот и имињата на променливите).
* */

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(message);
    }
}


class Product {

}


class OnlineShop {


    OnlineShop() {

    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){

    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException{
        throw new ProductNotFoundException("");
        //return 0.0;
    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<List<Product>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        return result;
    }

}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category=null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}


//Input
//addProduct C 050be27b product0 2019-01-14T23:17:46.715710 2913.14
//addProduct B 6aad1b88 product1 2019-06-18T02:18:33.715710 1524.04
//addProduct A 9d663742 product2 2017-12-31T09:18:12.715710 1110.18
//addProduct B 963792ab product3 2018-11-20T07:42:18.715710 2438.84
//addProduct A b5afbeec product4 2017-01-11T03:14:41.715710 1303.61
//addProduct A 53bf96af product5 2019-11-14T07:02:57.715710 1731.04
//addProduct B 712fcb57 product6 2019-11-10T22:54:40.715710 2148.59
//addProduct A 1718af53 product7 2018-09-22T10:13:57.715710 2832.58
//addProduct C 389bf07c product8 2019-03-29T05:43:51.715710 1287.56
//addProduct C 930a8e1e product9 2019-05-06T02:10:20.715710 1160.62
//buyProduct 050be27b 14
//buyProduct 6aad1b88 1
//buyProduct 9d663742 2
//buyProduct 963792ab 5
//buyProduct b5afbeec 1
//buyProduct 53bf96af 4
//buyProduct 712fcb57 1
//buyProduct 1718af53 5
//buyProduct 389bf07c 11
//buyProduct 930a8e1e 10
//listProducts null HIGHEST_PRICE_FIRST 4


//Output

//PAGE 1
//Product{id='050be27b', name='product0', createdAt=2019-01-14T23:17:46.715710, price=2913.14, quantitySold=14}
//Product{id='1718af53', name='product7', createdAt=2018-09-22T10:13:57.715710, price=2832.58, quantitySold=5}
//Product{id='963792ab', name='product3', createdAt=2018-11-20T07:42:18.715710, price=2438.84, quantitySold=5}
//Product{id='712fcb57', name='product6', createdAt=2019-11-10T22:54:40.715710, price=2148.59, quantitySold=1}
//PAGE 2
//Product{id='53bf96af', name='product5', createdAt=2019-11-14T07:02:57.715710, price=1731.04, quantitySold=4}
//Product{id='6aad1b88', name='product1', createdAt=2019-06-18T02:18:33.715710, price=1524.04, quantitySold=1}
//Product{id='b5afbeec', name='product4', createdAt=2017-01-11T03:14:41.715710, price=1303.61, quantitySold=1}
//Product{id='389bf07c', name='product8', createdAt=2019-03-29T05:43:51.715710, price=1287.56, quantitySold=11}
//PAGE 3
//Product{id='930a8e1e', name='product9', createdAt=2019-05-06T02:10:20.715710, price=1160.62, quantitySold=10}
//Product{id='9d663742', name='product2', createdAt=2017-12-31T09:18:12.715710, price=1110.18, quantitySold=2}
//Total revenue of the online shop is: 107031.18