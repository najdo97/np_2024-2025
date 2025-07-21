/*
Во една метеролошка станица на секои 5 минути пристигнуваат податоци за временските услови (температура, влажност на воздухот, ветар, видливост, време). Пример за вакви податоци:

температура: 13 степени
влажност: 98%
ветар: 11.2 km/h
видливост: 14 km
време: 28.12.2013 14:37:55 (dd.MM.yyyy HH:mm:ss).
Потребно е да се имплементира класа WeatherStation која ќе ги чува податоците за временските услови за последните x денови (при додавање на податоци за ново мерење, сите мерења чие што време е постаро за x денови од новото се бришат ). Исто така ако времето на новото мерење кое се додава се разликува за помалку од 2.5 минути од времето на некое претходно додадено мерење, тоа треба да се игнорира (не се додава).

Да се имплементираат следните методи на класата WeatherStation:

WeatherStation(int days) - конструктор со аргумент бројот на денови за кои се чуваат мерења
public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) - додавање на податоци за ново мерење
public int total() - го враќа вкупниот број на мерења кои се чуваат
public void status(Date from, Date to) - ги печати сите мерења во периодот од from до to подредени според датумот во растечки редослед и на крај ја печати просечната температура во овој период. Ако не постојат мерења во овој период се фрла исклучок од тип RuntimeException (вграден во Јава).
Пример за форматот на излезот:

24.6 80.2 km/h 28.7% 51.7 km Tue Dec 17 23:40:15 CET 2013
23.5 32.2 km/h 16.5% 187.2 km Tue Dec 17 23:45:15 CET 2013
13.2 67.1 km/h 18.9% 135.4 km Tue Dec 17 23:50:15 CET 2013
Avarage temperature: 20.43
* */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde


//4
//22.1 18.9 1.3 24.6
//10.12.2013 21:30:15
//22.1 18.9 1.3 24.6
//11.12.2013 22:30:15
//22.1 18.9 1.3 24.6
//17.12.2013 23:30:15
//41.8 9.4 40.8 20.7
//17.12.2013 23:35:15
//24.6 28.7 80.2 51.7
//17.12.2013 23:40:15
//24.6 28.7 80.2 51.7
//17.12.2013 23:40:25
//24.6 28.7 80.2 51.7
//17.12.2013 23:39:36
//23.5 16.5 32.2 187.2
//17.12.2013 23:45:15
//13.2 18.9 67.1 135.4
//17.12.2013 23:50:15
//13.2 18.9 67.1 135.4
//17.12.2013 23:51:12
//31.6 48.3 6.0 86.6
//17.12.2013 23:55:15
//18.0 37.6 15.9 37.7
//18.12.2013 00:00:15
//24.6 28.7 80.2 51.7
//19.12.2013 23:39:36
//23.5 16.5 32.2 187.2
//19.12.2013 23:45:15
//13.2 18.9 67.1 135.4
//19.12.2013 23:50:15
//13.2 18.9 67.1 135.4
//19.12.2013 23:51:12
//31.6 48.3 6.0 86.6
//19.12.2013 23:55:15
//18.0 37.6 15.9 37.7
//20.12.2013 00:00:15
//18.0 37.6 15.9 37.7
//20.12.2013 00:05:15
//18.0 37.6 15.9 37.7
//20.12.2013 00:05:35
//=====
//11.12.2013 22:30:15
//19.12.2013 23:39:36



//13
//22.1 18.9 km/h 1.3% 24.6 km Tue Dec 17 23:30:15 GMT 2013
//41.8 9.4 km/h 40.8% 20.7 km Tue Dec 17 23:35:15 GMT 2013
//24.6 28.7 km/h 80.2% 51.7 km Tue Dec 17 23:40:15 GMT 2013
//23.5 16.5 km/h 32.2% 187.2 km Tue Dec 17 23:45:15 GMT 2013
//13.2 18.9 km/h 67.1% 135.4 km Tue Dec 17 23:50:15 GMT 2013
//31.6 48.3 km/h 6.0% 86.6 km Tue Dec 17 23:55:15 GMT 2013
//18.0 37.6 km/h 15.9% 37.7 km Wed Dec 18 00:00:15 GMT 2013
//24.6 28.7 km/h 80.2% 51.7 km Thu Dec 19 23:39:36 GMT 2013
//Average temperature: 24.93
