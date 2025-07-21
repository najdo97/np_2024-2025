/*
Да се дефинира генеричка класа за правило (Rule) во која ќе се чуваат имплементации на интерфејсите Predicate и Function. Генеричката класа треба да има два генерички параметри - еден за влезниот тип (типот на објектите кои се спроведуваат низ правилото) и еден за излезниот тип (типот на објектите кои се резултат од правилото).

Во класата Rule да се дефинира метод apply кој прима еден аргумент input од влезниот тип, а враќа објект од генеричката класата Optional со генерички параметар ист како излезниот тип на класата Rule. Методот apply треба да врати Optional објект пополнет со резултатот добиен од Function имплементацијата применет на аргументот input само доколку е исполнет предикатот од правилото Rule. Доколку предикатот не е исполнет, методот apply враќа празен Optional.

Дополнително, да се дефинира класа RuleProcessor со еден генерички статички метод process кој ќе прими два аргументи:

Листа од влезни податоци (објекти од влезниот тип)
Листа од правила (објекти од класа Rule)
Методот потребно е врз секој елемент од листата на влезни податоци да го примени секое правило од листата на правила и на екран да го испечати резултатот од примената на правилото (доколку постои), а во спротивно да испечати порака Condition not met.

Во главната класа на местата означени со TODO да се дефинираат потребните објекти од класата Rule. Да се користат ламбда изрази за дефинирање на објекти од тип Predicate и Function.



Напомена: Решенијата кои нема да може да се извршат (не компајлираат) нема да бидат оценети. Дополнително, решенијата кои не се дизајнирани правилно според принципите на ООП ќе се оценети со најмногу 80% од поените.
* */

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Student {
    String id;
    List<Integer> grades;

    public Student(String id, List<Integer> grades) {
        this.id = id;
        this.grades = grades;
    }

    public static Student create(String line) {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Integer> grades = Arrays.stream(parts).skip(1).map(Integer::parseInt).collect(Collectors.toList());
        return new Student(id, grades);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", grades=" + grades +
                '}';
    }
}

public class RuleTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { //Test for String,Integer
            List<Rule<String, Integer>> rules = new ArrayList<>();

            /*
            TODO: Add a rule where if the string contains the string "NP", the result would be index of the first occurrence of the string "NP"
            * */


            /*
            TODO: Add a rule where if the string starts with the string "NP", the result would be length of the string
            * */


            List<String> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(sc.nextLine());
            }

            RuleProcessor.process(inputs, rules);


        } else { //Test for Student, Double
            List<Rule<Student, Double>> rules = new ArrayList<>();

            //TODO Add a rule where if the student has at least 3 grades, the result would be the max grade of the student


            //TODO Add a rule where if the student has an ID that starts with 20, the result would be the average grade of the student
            //If the student doesn't have any grades, the average is 5.0


            List<Student> students = new ArrayList<>();
            while (sc.hasNext()){
                students.add(Student.create(sc.nextLine()));
            }

            RuleProcessor.process(students, rules);
        }
    }
}


//1
//Gjorgji
//Stefan
//Ana
//Napredno programiranje
//NP2022/2023
//Ova e ispit po NP



//Input: Gjorgji
//Condition not met
//Condition not met
//Input: Stefan
//Condition not met
//Condition not met
//Input: Ana
//Condition not met
//Condition not met
//Input: Napredno programiranje
//Condition not met
//Condition not met
//Input: NP2022/2023
//Result: 0
//Result: 11
//Input: Ova e ispit po NP
//Result: 15
//Condition not met