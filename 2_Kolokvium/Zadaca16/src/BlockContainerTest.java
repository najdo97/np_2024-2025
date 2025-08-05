/*
* Да се имплементира генеричка класа за блок контејнер BlockContainer. Контејнерот треба да има блоковска структура,
*  со тоа што секој блок содржи N елементи.
*

Контејнерот треба да ги задоволува следните услови:
константно време на пристап до секој блок $O(1)$
логаритамско време на пристап до елементите во блокот $O(log N)$
елементите во секој блок треба да бидат сортирани.
Класата треба да ги имплементира следните методи:

public BlockContainer(int n) - конструктор со еден аргумент, максималниот број на елементи во блокот
public void add(T a) - метод за додавање елемент во последниот блок од контејнерот (ако блокот е полн, се додава нов блок)
public boolean remove(T a) - метод за бришње на елемент од последниот блок (ако се избришат сите елементи од еден блок, тогаш и блокот се брише)
public void sort() - метод за сортирање на сите елементи во контејнерот
public String toString() - препокривање на методот да враќа String во следниот формат: пример: [7, 8, 9],[1, 2, 3],[5, 6, 12],[4, 10, 8]
* */


import java.util.*;

class Block<T> {

    ArrayList<T> data;

    public Block() {
        this.data = new ArrayList<>();
    }

    int getDataSize() {
        return data.size();
    }

    void addData(T value) {
        this.data.add(value);
    }

}

class BlockContainer<T> {
    int max_size;
    ArrayList<Block<T>> container;

    public BlockContainer(int n) {
        this.max_size = n;
        this.container = new ArrayList<>();
    } //- конструктор со еден аргумент, максималниот број на елементи во блокот

    public void add(T a) {
        if (container.get(container.size() - 1).getDataSize() < max_size) {
            container.get(container.size() - 1).addData(a);
        }else{
            container.add(new Block<T>());
            container.get(container.size() - 1).addData(a);
        }

    }//- метод за додавање елемент во последниот блок од контејнерот (ако блокот е полн, се додава нов блок)

    public boolean remove(T a) {

    }// - метод за бришње на елемент од последниот блок (ако се избришат сите елементи од еден блок, тогаш и блокот се брише)

    public void sort() {

    }// -    метод за сортирање на сите елементи во контејнерот

    public String toString() {

    } // -    препокривање на методот да враќа String во следниот формат:пример:[7,8,9],[1,2,3],[5,6,12],[4,10,8]


}

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for (int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for (int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}

// Вашиот код овде



