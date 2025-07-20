/*
* За потребите на предметот Напредно програмирање потребно е да се имплементира систем за евидентирање на напредокот на студентите во текот на семестарот и за генерирање на извештаи за оценки.

Да се имплементира класа Student во која ќе се чува:

Индекс
Име на студентот
Поени на прв колоквиум (цел број со максимална вредност 100)
Поени на втор колоквиум (цел број со максимална вредност 100)
Поени од лабораториски вежби (цел број со максимална вредност 10)
За класата да се имплементира потребниот конструктор.

Да се напише класа AdvancedProgrammingCourse во која ќе се чува колекција од студенти кои го слушаат и полагаат предметот Напредно програмирање. Во ова класа да се имплементираат следните методи:

public void addStudent (Student s) - додавање на студент на предметот.
public void updateStudent (String idNumber, String activity, int points) - метод за ажурирање на поените на студентот со индекс idNumber во активноста activity со поените points. Методот да има комплекност О(1)! Можни вредности за activity се midterm1,midterm2 и labs. Со помош на исклучоци да се игнорираат додавања на поени кои се невалидни (не е потребно да се печати порака).
public List<Student> getFirstNStudents (int n) - ги враќа првите N најдобри положени студенти на предметот сортирани во опаѓачки редослед според вкупниот број на сумарни поени. Сумарните поени се пресметуваат по формулата: midterm1 * 0.45 + midterm2 * 0.45 + labs.
public Map<Integer,Integer> getGradeDistribuition() - враќа мапа од оценките (5,6,7,8,9,10) со бројот на студенти кои ја добиле соодветната оценка.
public void printStatistics() - печати основни статистики за вкупните поени (min,max,average,count) за сумарните поени на сите положени студенти.
* */

//package mk.ukim.finki.midterm;

import java.util.*;
import java.util.stream.Collectors;


public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}


//input

//addStudent 151020 Stefan
//updateStudent 151020 midterm1 78
//updateStudent 151020 midterm2 80
//updateStudent 151020 labs 8
//getFirstNStudents 1


//output:
//ID: 151020 Name: Stefan First midterm: 78 Second midterm 80 Labs: 8 Summary points: 79.10 Grade: 8
