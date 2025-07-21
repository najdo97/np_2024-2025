/*
Да се имплемнтира генеричка класа Triple (тројка) од нумерички вредности (три броја). За класата да се имплементираат:

конструктор со 3 аргументи,
double max() - го враќа најголемиот од трите броја
double average() - кој враќа просек на трите броја
void sort() - кој ги сортира елементите во растечки редослед
да се преоптовари методот toString() кој враќа форматиран стринг со две децимални места за секој елемент и празно место помеѓу нив.
*/

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple




//3 7 5
//8 8 9
//2 1 4


//7.00
//5.00
//3.00 5.00 7.00
//9.00
//8.33
//8.00 8.00 9.00
//4.00
//2.33
//1.00 2.00 4.00