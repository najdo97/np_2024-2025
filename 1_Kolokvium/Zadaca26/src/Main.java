/*
Да се дефинира генеричка класа за равенка (Equation) во која ќе се чуваат имплементации на интерфејсите Supplier и Function. Генеричката класа треба да има два генерички параметри - еден за влезниот тип (типот на аргументите на равенката) и еден за излезниот тип (типот на резултатите од равенката).

Во класата Equation да се дефинира метод calculate кој не прима агрументи, а враќа објект од генеричката класата Optional со генерички параметар ист како излезниот тип на класата Equation. Методот треба да врати Optional објект пополнет со резултатот добиен од Function имплементацијата применет на аргументот добиен со Supplier имплементацијата.

Дополнително, да се дефинира класа EqationProcessor со еден генерички статички метод process кој ќе прими два аргументи:

Листа од влезни податоци (објекти од влезниот тип)
Листа од равенки (објекти од класа Equation)
Методот потребно е за секој елемент од листата на влезни податоци да го испечати тој елемент, да направи пресметка на равенката и да го испечати резултатот. Доколку равенката се евалуира исто на сите елементи од листата на влезни податоци, тогаш испечатете го резултатот од секоја равенка само еднаш, на крај.

Во главната класа на местата означени со TODO да се дефинираат потребните објекти од класата Equation. Да се користат ламбда изрази за дефинирање на објекти од тип Supplier и Function.



Напомена: Решенијата кои нема да може да се извршат (не компајлираат) нема да бидат оценети.
* */

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


class Line {
    Double coeficient;
    Double x;
    Double intercept;

    public Line(Double coeficient, Double x, Double intercept) {
        this.coeficient = coeficient;
        this.x = x;
        this.intercept = intercept;
    }

    public static Line createLine(String line) {
        String[] parts = line.split("\\s+");
        return new Line(
                Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2])
        );
    }

    public double calculateLine() {
        return coeficient * x + intercept;
    }

    @Override
    public String toString() {
        return String.format("%.2f * %.2f + %.2f", coeficient, x, intercept);
    }
}

public class EquationTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { // Testing with Integer, Integer
            List<Equation<Integer, Integer>> equations1 = new ArrayList<>();
            List<Integer> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(Integer.parseInt(sc.nextLine()));
            }

            // TODO: Add an equation where you get the 3rd integer from the inputs list, and the result is the sum of that number and the number 1000.


            // TODO: Add an equation where you get the 4th integer from the inputs list, and the result is the maximum of that number and the number 100.

            EquationProcessor.process(inputs, equations1);

        } else { // Testing with Line, Integer
            List<Equation<Line, Double>> equations2 = new ArrayList<>();
            List<Line> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(Line.createLine(sc.nextLine()));
            }

            //TODO Add an equation where you get the 2nd line, and the result is the value of y in the line equation.


            //TODO Add an equation where you get the 1st line, and the result is the sum of all y values for all lines that have a greater y value than that equation.

            EquationProcessor.process(inputs, equations2);
        }
    }
}


//1
//2
//3
//4
//56
//34
//44



//Input: 2
//Input: 3
//Input: 4
//Input: 56
//Input: 34
//Input: 44
//Result: 1004
//Result: 100

//////////////////////////////

//2
//30 40 50
//29 29 20
//1 2 5
//60 40 50
//80 90 9

//Input: 30.00 * 40.00 + 50.00
//Input: 29.00 * 29.00 + 20.00
//Input: 1.00 * 2.00 + 5.00
//Input: 60.00 * 40.00 + 50.00
//Input: 80.00 * 90.00 + 9.00
//Result: 861.0
//Result: 9659.0