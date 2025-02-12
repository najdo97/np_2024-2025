import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

class TextProcessor {

    List<List<String>> textLines;
    TreeMap<String, Integer> dictionary;

    public TextProcessor() {
        this.dictionary = new TreeMap<>();
        this.textLines = new ArrayList<>();
    }


    void readText(InputStream is) {
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            String inputLine = sc.nextLine();
            if (inputLine.isBlank()) {
                break;
            }
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
                //  if (!word.isBlank()) {
                sentance.add(word);
                this.dictionary.put(word, this.dictionary.getOrDefault(word, 0) + 1);
                //   }
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
        double mostSimilar = 0.0;
        double tmp = 0;
        CosineSimilarityCalculator calc = new CosineSimilarityCalculator();

        List<String> sentance1 = new ArrayList<>();
        List<String> sentance2 = new ArrayList<>();
        for (int i = 0; i < this.textLines.size(); i++) {
            for (int j = i + 1; j < this.textLines.size() - 1; j++) {

                tmp = calc.cosineSimilarity(getVector(this.textLines.get(i)), getVector(this.textLines.get(j)));
                if (tmp > mostSimilar) {
                    mostSimilar = tmp;
                    sentance1 = this.textLines.get(i);
                    sentance2 = this.textLines.get(j);
                }
            }
        }

        System.out.println(sentance1);
        System.out.println(sentance2);
        System.out.println(mostSimilar);

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