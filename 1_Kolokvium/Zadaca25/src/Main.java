/*
Да се дефинира класа ShoppingCart за репрезентација на една потрошувачка кошничка во која може да се наоѓаат ставки од 2 типа (ставка која содржи продукт кој се купува во целост, или ставка која содржи продукт кој се купува на грам).

За класата ShoppingCart да се имплементираат следните методи:

конструктор
void addItem(String itemData) - метод за додавање на ставка во кошничката. Податоците за ставката се дадени во текстуална форма и може да бидат во следните два формати согласно типот на ставката:
WS;productID;productName;productPrice;quantity (quantity е цел број, productPrice се однесува на цена на 1 продукт)
PS;productID;productName;productPrice;quantity (quantity е децимален број - во грамови, productPrice се однесува на цена на 1 кг продукт)
Со помош на исклучок од тип InvalidOperationException да се спречи додавање на ставка со quantity 0.
void printShoppingCart(OutputStream os) - метод за печатење на кошничката на излезен поток. Потребно е да се испечатат сите ставки од кошничката подредени според вкупната цена во опаѓачки редослед. Вкупната цена е производ на цената на продуктот кој е во ставката и квантитетот кој е купен по таа цена.
void blackFridayOffer(List<Integer> discountItems, OutputStream os) - метод којшто ќе ја намали цената за 10% на сите продукти чиј што productID се наоѓа во листата discountItems. Потоа, треба да се испечати извештај за вкупната заштеда на секоја ставка каде има продукт на попуст (види тест пример). Да се фрли исклучок од тип InvalidOperationException доколку листата  со продукти на попуст е празна.


Напомена: Решенијата кои нема да може да се извршат (не компајлираат) нема да бидат оценети. Дополнително, решенијата кои не се дизајнирани правилно според принципите на ООП ќе се оценети со најмногу 80% од поените.
* */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

//3
//PS;107965;Flour;409;800.78
//WS;101569;Coca Cola;970;64
//WS;108375;Fanta;496;56
//2
//107965
//108375
//1


//101569 - 62080.00
//108375 - 27776.00
//107965 - 327.52