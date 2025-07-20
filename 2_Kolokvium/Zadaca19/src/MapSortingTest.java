/*
 * Да се напише генеричка метода entriesSortedByValues за сортирање на елементи (парови од клуч и вредност) на една мапа според вредноста во опаѓачки редослед.
 * Доколку постојат две или повеќе исти вредности, да се задржи редоследот дефиниран во мапата.
 * Сортираните елементи на мапата да бидат да бидат вратени како SortedSet<Map.Entry<, >>.*/

import java.util.*;

public class MapSortingTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        List<String> l = readMapPairs(scanner);
        if (n == 1) {
            Map<String, Integer> map = new HashMap<>();
            fillStringIntegerMap(l, map);
            SortedSet<Map.Entry<String, Integer>> s = entriesSortedByValues(map);
            System.out.println(s);
        } else {
            Map<Integer, String> map = new HashMap<>();
            fillIntegerStringMap(l, map);
            SortedSet<Map.Entry<Integer, String>> s = entriesSortedByValues(map);
            System.out.println(s);
        }

    }

    private static List<String> readMapPairs(Scanner scanner) {
        String line = scanner.nextLine();
        String[] entries = line.split("\\s+");
        return Arrays.asList(entries);
    }

    static void fillStringIntegerMap(List<String> l, Map<String, Integer> map) {
        l.stream()
                .forEach(s -> map.put(s.substring(0, s.indexOf(':')), Integer.parseInt(s.substring(s.indexOf(':') + 1))));
    }

    static void fillIntegerStringMap(List<String> l, Map<Integer, String> map) {
        l.stream()
                .forEach(s -> map.put(Integer.parseInt(s.substring(0, s.indexOf(':'))), s.substring(s.indexOf(':') + 1)));
    }

    //вашиот код овде
}

//test case

//Input:
//1
//eden:1 dva:2 tri:3 cetiri:4 pet:5 sest:2

//Output
//{sest=2, eden=1, tri=3, cetiri=4, dva=2, pet=5}
//[pet=5, cetiri=4, tri=3, sest=2, dva=2, eden=1]


//Input:
//2
//        1:eden 2:dva 3:tri 4:cetiri

//Output
//{1=eden, 2=dva, 3=tri, 4=cetiri}
//[3=tri, 1=eden, 2=dva, 4=cetiri]