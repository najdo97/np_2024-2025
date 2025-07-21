/*
Да се дефинира класа Quiz за репрезентација на еден квиз во кој може да се наоѓаат повеќе прашања од 2 типа (true/false прашање или прашање со избор на еден точен одговор од 5 понудени одговори A/B/C/D/E). За класата Quiz да се имплементираат следните методи:

конструктор
void addQuestion(String questionData) - метод за додавање на прашање во квизот. Податоците за прашањето се дадени во текстуална форма и може да бидат во следните два формати согласно типот на прашањето:
TF;text;points;answer (answer може да биде true или false)
MC;text;points;answer (answer е каракатер кој може да ја има вредноста A/B/C/D/E)
Со помош на исклучок од тип InvalidOperationException да се спречи додавање на прашање со повеќе понудени одговори во кое како точен одговор се наоѓа некоја друга опција освен опциите A/B/C/D/E.
void printQuiz(OutputStream os) - метод за печатење на квизот на излезен поток. Потребно е да се испечатат сите прашања од квизот подредени според бројот на поени на прашањата во опаѓачки редослед.
void answerQuiz (List<String> answers, OutputStream os) - метод којшто ќе ги одговори сите прашања од квизот (во редоследот во којшто се внесени) со помош на одговорите од листата answers. Методот треба да испечати извештај по колку поени се освоени од секое прашање и колку вкупно поени се освоени од квизот (види тест пример). Да се фрли исклучок од тип InvalidOperationException доколку бројот на одговорите во `answers е различен од број на прашања во квизот.
За точен одговор на true/false прашање се добиваат сите предвидени поени, а за неточен одговор се добиваат 0 поени
За точен одговор на прашање со повеќе понудени одговори се добиваат сите предвидени поени, а за неточен одговор се добиваат негативни поени (20% од предвидените поени).


Напомена: Решенијата кои нема да може да се извршат (не компајлираат) нема да бидат оценети. Дополнително, решенијата кои не се дизајнирани правилно според принципите на ООП ќе се оценети со најмногу 80% од поените.
* */

public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            quiz.addQuestion(sc.nextLine());
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            quiz.answerQuiz(answers, System.out);
        } else {
            System.out.println("Invalid test case");
        }
    }
}


//3
//MC;Question1;3;E
//MC;Question2;4;E
//TF;Question3;2;false
//3
//D
//B
//false
//1



//Multiple Choice Question: Question2 Points 4 Answer: E
//Multiple Choice Question: Question1 Points 3 Answer: E
//True/False Question: Question3 Points: 2 Answer: false