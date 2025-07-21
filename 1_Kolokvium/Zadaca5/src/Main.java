//Да се имплемнтира генеричка класа MinMax од два споредливи објекти (минимум/максимум). За класата да се имплементираат:
//
//MinMax()-default конструктор
//void update(T element) - метод за ажурирање на тековните минимум/максимум.
//T max() - го враќа најголемиот елемент
//T min() - го враќа најмалиот елемент
//да се преоптовари методот toString() кој враќа стринг составен од минималниот и максималниот елемент и бројот на елементи обработени во методот update кои се различни од тековниот минимум/максимум, разделени со празно место.
//Во класата не смеат да се чуваат елементите кои се обработуваат во методот update, освен тековниот минимум/максимум.

import java.util.Scanner;

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}
//6
//abc
//xyz
//abc
//xyz
//zzz
//blatr
//12
//8
//8
//5
//55
//13
//55


//abc zzz 3
//
//5 55 4