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

import java.util.*;
import java.util.stream.Collectors;


class Person {
    private String name;
    private int age;
    private String code;

    public Person() {
    }

    public Person(String name, int age, String code) {
        this.name = name;
        this.age = age;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class Audition {


    private HashMap<String, HashMap<String, Person>> hash_map;


    public Audition() {
        this.hash_map = new HashMap<>();
    }

    void addParticpant(String city, String code, String name, int age) {

        if (hash_map.get(city) == null) {
            hash_map.put(city, new HashMap<String, Person>());
        }

        if (!hash_map.get(city).containsKey(code)) {
            hash_map.get(city).put(code, new Person(name, age, code));
        }


    }

    void listByCity(String city) {
        HashMap<String, Person> kandidati = new HashMap<>();

        kandidati = this.hash_map.get(city);


        List<Person> sortedCandidates = kandidati.values()
                .stream()
                .sorted(
                        Comparator.comparing(Person::getName)
                                .thenComparing(Person::getAge)
                ).collect(Collectors.toList());

        for (Person sortedCandidate : sortedCandidates) {
            System.out.println(sortedCandidate.getCode() + " " + sortedCandidate.getName() + " " + sortedCandidate.getAge());
        }

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