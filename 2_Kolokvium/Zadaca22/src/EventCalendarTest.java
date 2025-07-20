/*
* Да се имплементира класа за календар на настани EventCalendar. Секој настан е дефиниран со:

име
локација
време (Date).
Класата треба да ги овозможува следните функционалности:

public EventCalendar(int year) - конструктор со еден аргумент годината на календарот
public void addEvent(String name, String location, Date date) - додава нов настан зададен со име, локација и време. Ако годината на настанот не се совпаѓа со годината на календарот да се фрли исклучок од вид WrongDateException со порака Wrong date: [date].
public void listEvents(Date date) - ги печати сите настани на одреден датум (ден) подредени според времето на одржување во растечки редослед (ако два настани имаат исто време на одржување, се подредуваат лексикографски според името). Добивањето колекција од настани на одреден датум треба да биде во константно време $O(1)$, а печатењето во линеарно време $O(n)$ (без сортирање, само изминување)! Форматот на печатење настан е dd MMM, YYY HH:mm at [location], [name].
public void listByMonth() - ги печати сите месеци (1-12) со бројот на настани во тој месец.
* */


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}
//input
//10
//2012
//Taksirat 15;Skopje;15.12.2012 22:00
//Rush Premiera;Cineplex;14.02.2013 11:00
//Vinoskop;Vero Centar;01.02.2012 21:00
//Skopje Gori;Kale;19.04.2012 15:30
//Brucoshka Zabava;FINKI;19.04.2012 15:30
//Bela Nokj;Skopje;12.06.2012 18:30
//Stand Up;Expo Center;15.04.2012 19:30
//Cinedays;Kino Milenium;13.01.2012 14:00
//Koncert Arhangel;Boris Trajkovski;19.02.2012 19:45
//Skopje Jazz Festival;Univerzalna Sala;18.08.2012 22:30
//19.04.2012 00:00

//Output
//Wrong date: Thu Feb 14 11:00:00 UTC 2013
//19 Apr, 2012 15:30 at FINKI, Brucoshka Zabava
//19 Apr, 2012 15:30 at Kale, Skopje Gori
//1 : 1
//2 : 2
//3 : 0
//4 : 3
//5 : 0
//6 : 1
//7 : 0
//8 : 1
//9 : 0
//10 : 0
//11 : 0
//12 : 1