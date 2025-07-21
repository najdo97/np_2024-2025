/*
Да се имплементира класа TimeTable која ќе чита од влезен тек (стандарден влез, датотека, ...) податоци за времиња во 24-часовен формат. Сите времиња се разделени со едно празно место, а во самото време часот и минутите може да бидат разделени со : или .. Пример за форматот на податоците:

11:15 0.45 23:12 15:29 18.46

Ваша задача е да ги имплементирате методите:

TimeTable() - default конструктор
void readTimes(InputStream inputStream) - метод за читање на податоците
void writeTimes(OutputStream outputStream, TimeFormat format) - метод кој ги печати сите времиња сортирани во растечки редослед во зададениот формат (24 часовен или AM/PM).
Методот за читање readTimes фрла исклучоци од тип UnsupportedFormatException ако времињата се разделени со нешто друго што не е : или . и InvalidTimeException ако времето (часот или минутите) е надвор од дозволениот опсег (0-23, 0-59). И двата исклучоци во пораката getMessage() треба да го вратат влезниот податок кој го предизвикал исклучокот. Сите времиња до моментот кога ќе се фрли некој од овие два исклучоци треба да си останат вчитани.
Правила за конверзија од 24-часовен формат во AM/PM:

за првиот час од денот (0:00 - 0:59), додадете 12 и направете го "AM"
од 1:00 до 11:59, само направето го "AM"
од 12:00 до 12:59, само направето го "PM"
од 13:00 до 23:59 одземете 12 и направете го "PM"
* */

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

//10.10
//11.11
//12.12
//0.15
//1:10
//20:20
//13:15
//17:20
//23:30
//19.19
//17:21
//19.59
//12:00
//11:59


//24 HOUR FORMAT
// 0:15
// 1:10
//10:10
//11:11
//11:59
//12:00
//12:12
//13:15
//17:20
//17:21
//19:19
//19:59
//20:20
//23:30
//AM/PM FORMAT
//12:15 AM
// 1:10 AM
//10:10 AM
//11:11 AM
//11:59 AM
//12:00 PM
//12:12 PM
// 1:15 PM
// 5:20 PM
// 5:21 PM
// 7:19 PM
// 7:59 PM
// 8:20 PM
//11:30 PM