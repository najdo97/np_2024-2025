/*За потребите на модулот за онлајн плаќања на системот iknow потребно е да напишете класа OnlinePayments со следните методи:

default конструктор
void readItems (InputStream is) - метод за вчитување на сите ставки кои се платени преку модулот. Секоја ставка е во нов ред и е во следниот формат STUDENT_IDX ITEM_NAME PRICE.
void printStudentReport (String index, OutputStream os) - метод за печатење на извештај за студентот со индекс id. Во извештајот треба да се испечати нето износот на сите платени ставки, наплатената банкарска провизија, вкупниот износ кој е наплатен од студентите, како и нумерирана листа од сите ставки кои се платени од студентите сортирани во опаѓачки редослед според цената.
Провизијата се пресметува врз вкупниот износ на ставките кои студентот ги плаќа и изнесува 1.14% (но најмалку 3 денари, а најмногу 300). Децималните износи се заокрузуваат со Math.round.
* */

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}

//Input:
//151020;Административно-материјални трошоци и осигурување;750
//        151020;Школарина за летен семестар 2022/2023;12300

//Output:
//Student: 151020 Net: 13050 Fee: 149 Total: 13199
//Items:
//1. Школарина за летен семестар 2022/2023 12300
//2. Административно-материјални трошоци и осигурување 750
//Student 151021 not found!
//Student 151022 not found!
//Student 151023 not found!
//Student 151024 not found!