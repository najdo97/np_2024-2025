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
            String[] inputLineWords = inputLine.split(" ");
            List<String> sentance = new ArrayList<>();
            for (int i = 0; i < inputLineWords.length; i++) {
                sentance = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                String word;
                for (int j = 0; j < inputLineWords[i].length(); j++) {
                    if (Character.isAlphabetic(inputLineWords[i].charAt(j))) {
                        sb.append(inputLineWords[i].toLowerCase().charAt(j));
                    }
                }
                word = sb.toString();
                sentance.add(word);
                this.dictionary.put(word, this.dictionary.getOrDefault(word, 0) + 1);
            }
            textLines.add(sentance);
        }
    }

    void printTextsVectors(OutputStream os) {

    }

    void printCorpus(OutputStream os, int n, boolean ascending) {

    }


    public void mostSimilarTexts(OutputStream os) {

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