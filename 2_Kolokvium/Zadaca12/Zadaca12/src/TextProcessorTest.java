/*
* Да се креира класа TextProcessor за процесирање на текстови која и ќе дава информации за распределбата на зборовите во текстовите и нивната векторска репрезентација. За класата да се имплементираат:

TextProcessor() - конструктор
readText (InputStream is) - метод за читање на текст од влезен поток на податоци. Секој текст е во нов ред. Од секој текст треба да се елимираат сите непотребни интерпукциски знаци и бројки, така што ќе останат само зборови и празните места меѓу нив.
printTextsVectors (OutputStream os) - метод којшто на излезен поток за секој прочитан текст од влез ќе испечати негова векторска репрезентација, каде што векторот е од тип: [frequency1, frequency2 ....]. Во векторот се наоѓаат фреквенциите на сите зборови (лексикографски сортирани) коишто се појавиле во сите текстови прочитани од влезниот поток. Да се игнорира големината на буквите.
Пример нека се дадени текстовите Napredno programiranje и napredno rabotenje. Тогаш векторската репрезентација на првиот текст ќе биде [1, 1, 0], a на вториот текст ќе биде [1, 0, 1], бидејќи сите зборови коишто се појавиле во сите текстови (лексикографски сортирани се: [napredno, programiranje, rabotenje].
printCorpus(OutputStream os, int n, boolean ascending) - метод што ги печати првите n зборови од секоја текст според фреквенцијата на појавување. Ако ascending аргументот e true, се печатат во растечки редослед, а во спротивно во опаѓачки.
public void mostSimilarTexts (OutputStream os) - метод којшто на излезен поток ги печати двата најслични текстови добиени од влезен поток. Како мерка за сличност на текстовите се користи косинусна сличност меѓу фреквенциите во векторите на документите. Да се искористи статичката функцијата cosineSimilarity од класата CosineSimilarityCalculator.
* */

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

class TextProcessor {

    List<String> originalTextLines;
    List<List<String>> textLines;
    TreeMap<String, Integer> dictionary;

    public TextProcessor() {
        this.dictionary = new TreeMap<>();
        this.textLines = new ArrayList<>();
        this.originalTextLines = new ArrayList<>();

    }


    void readText(InputStream is) {
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            String inputLine = sc.nextLine();
            if (inputLine.isBlank()) {
                break;
            }
            originalTextLines.add(inputLine);
            String[] inputLineWords = inputLine.split(" ");
            List<String> sentance = new ArrayList<>();
            for (int i = 0; i < inputLineWords.length; i++) {

                StringBuilder sb = new StringBuilder();
                String word;
                for (int j = 0; j < inputLineWords[i].length(); j++) {
                    if (Character.isAlphabetic(inputLineWords[i].charAt(j))) {
                        sb.append(inputLineWords[i].toLowerCase().charAt(j));
                    }
                }
                word = sb.toString();
                if (!word.isBlank()) {
                    sentance.add(word);
                    this.dictionary.put(word, this.dictionary.getOrDefault(word, 0) + 1);
                }
            }
            textLines.add(sentance);
        }
    }


    public List<Integer> getVector(List<String> words) {

        HashMap<String, Integer> wordsInSentence = new HashMap<>();
        for (int j = 0; j < words.size(); j++) {
            wordsInSentence.put(
                    words.get(j),
                    wordsInSentence.getOrDefault(words.get(j), 0) + 1);
        }
        List<Integer> vector = new ArrayList<>();
        this.dictionary.forEach((key, value) -> {
            if (wordsInSentence.containsKey(key)) {
                vector.add(wordsInSentence.get(key));
            } else {
                vector.add(0);
            }
        });
        return vector;
    }

    void printTextsVectors(OutputStream os) {
        for (int i = 0; i < this.textLines.size(); i++) {

            System.out.println(getVector(this.textLines.get(i)));

//            HashMap<String, Integer> wordsInSentence = new HashMap<>();
//            for (int j = 0; j < this.textLines.get(i).size(); j++) {
//                wordsInSentence.put(
//                        this.textLines.get(i).get(j),
//                        wordsInSentence.getOrDefault(this.textLines.get(i).get(j), 0) + 1);
//            }
//            List<Integer> vector = new ArrayList<>();
//            this.dictionary.forEach((key, value) -> {
//                if (wordsInSentence.containsKey(key)) {
//                    vector.add(wordsInSentence.get(key));
//                } else {
//                    vector.add(0);
//                }
//            });
//            System.out.println(vector);
//
        }

    }

    void printCorpus(OutputStream os, int n, boolean ascending) {

        if (ascending) {
            this.dictionary.entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue))
                    .limit(n)
                    .forEach(entry -> {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    });
        } else {
            this.dictionary.entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed())
                    .limit(n)
                    .forEach(entry -> {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    });
        }
    }


    public void mostSimilarTexts(OutputStream os) {
        CosineSimilarityCalculator calc = new CosineSimilarityCalculator();
        double mostSimilar = calc.cosineSimilarity(getVector(this.textLines.get(0)), getVector(this.textLines.get(1)));
        double tmp = 0;
        String sentance1 = this.originalTextLines.get(0);
        String sentance2 = this.originalTextLines.get(1);
        for (int i = 0; i < this.textLines.size(); i++) {
            for (int j = i + 1; j < this.textLines.size() - 1; j++) {

                tmp = calc.cosineSimilarity(getVector(this.textLines.get(i)), getVector(this.textLines.get(j)));
                if (tmp > mostSimilar) {
                    mostSimilar = tmp;
                    sentance1 = this.originalTextLines.get(i);
                    sentance2 = this.originalTextLines.get(j);
                }
            }
        }

        System.out.println(sentance1);
        System.out.println(sentance2);
        System.out.println(String.format("%.10f",mostSimilar));

    }
}


class CosineSimilarityCalculator {
    public static double cosineSimilarity(Collection<Integer> c1, Collection<Integer> c2) {
        int[] array1;
        int[] array2;
        array1 = c1.stream().mapToInt(i -> i).toArray();
        array2 = c2.stream().mapToInt(i -> i).toArray();
        double up = 0.0;
        double down1 = 0, down2 = 0;

        for (int i = 0; i < c1.size(); i++) {
            up += (array1[i] * array2[i]);
        }

        for (int i = 0; i < c1.size(); i++) {
            down1 += (array1[i] * array1[i]);
        }

        for (int i = 0; i < c1.size(); i++) {
            down2 += (array2[i] * array2[i]);
        }

        return up / (Math.sqrt(down1) * Math.sqrt(down2));
    }
}

public class TextProcessorTest {

    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor();

        textProcessor.readText(System.in);

        System.out.println("===PRINT VECTORS===");
        textProcessor.printTextsVectors(System.out);

        System.out.println("PRINT FIRST 20 WORDS SORTED ASCENDING BY FREQUENCY ");
        textProcessor.printCorpus(System.out, 20, true);

        System.out.println("PRINT FIRST 20 WORDS SORTED DESCENDING BY FREQUENCY");
        textProcessor.printCorpus(System.out, 20, false);

        System.out.println("===MOST SIMILAR TEXTS===");
        textProcessor.mostSimilarTexts(System.out);
    }
}