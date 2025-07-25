/*
* Да се имплементира класа QuizProcessor со единствен метод Map<String, Double> processAnswers(InputStream is).

Методот потребно е од влезниот поток is да ги прочита одговорите на студентите на еден квиз. Информациите за квизовите се дадени во посебни редови и се во следниот формат:

ID; C1, C2, C3, C4, C5, … ,Cn; A1, A2, A3, A4, A5, …,An.
каде што ID е индексот на студентот, Ci е точниот одговор на i-то прашање, а Ai е одговорот на студентот на i-то прашање. Студентот добива по 1 поен за точен одговор, а по -0.25 за секој неточен одговор. Бројот на прашања n може да биде различен во секој квиз.

Со помош на исклучоци да се игнорира квиз во кој бројот на точни одговори е различен од бројот на одговорите на студентот.

Во резултантната мапа, клучеви се индексите на студентите, а вредности се поените кои студентот ги освоил. Пример ако студентот на квиз со 6 прашања, има точни 3 прашања, а неточни 3 прашања, студентот ќе освои 3*1 - 3*0.25 = 2.25.
* */

import java.util.*;

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}
//input:
//        151020;A, B, C;A, C, C

//output:
//151020 -> 1.75


//input:
//200000;C, D, D, D, A, C, B, D, D;C, D, D, D, D, B, C, D, A
//200001;A, B, D, D, D, A, C, B;B, D, C, C, C, D, C, D
//200002;D, D, C, D, D, B, C;D, B, B, C, B, B, C
//200003;D, C, D, A, D, D;A, C, C, B, A, D
//200004;A, A, C, A, B, D, C, C, C, D;A, C, C, D, A, C, C, C, A, C
//output:
//200000 -> 4.00
//200001 -> -0.75
//200002 -> 2.00
//200003 -> 1.00
//200004 -> 2.50