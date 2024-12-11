/*

Да се имплементира класа за аудиција Audition со следните методи:

void addParticpant(String city, String code, String name, int age)

додава нов кандидат со код code, име и возраст за аудиција во даден град city.
Во ист град не се дозволува додавање на кандидат со ист код како некој претходно додаден кандидат
(додавањето се игнорира, а комплексноста на овој метод треба да биде $O(1)$)

void listByCity(String city)
ги печати сите кандидати од даден град подредени според името,
а ако е исто според возраста (комплексноста на овој метод не треба да надминува $O(n*log_2(n))$,
каде $n$ е бројот на кандидати во дадениот град).
* */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Audition {
    void addParticpant(String city, String code, String name, int age) {

    }

    void listByCity(String city) {

    }

}


public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}