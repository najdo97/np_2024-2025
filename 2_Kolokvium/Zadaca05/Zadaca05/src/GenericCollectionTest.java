/*
 * Да се дефинира класата GenericCollection во која што ќе се чуваат елементи коишто треба да се споредливи и елементи коишто имаат timestamp на креирање. Класата да ги овозможи следните методи:
 * void addGenericItem (String category, T element)- метод за додавање на нов елемент во дадена категорија
 * Collection<T> findAllBetween (LocalDateTime from, LocalDateTime to) - метод којшто ќе врати колекција од сите елементи што се наоѓаат во интервалот на датуми даден како аргументи на функцијата.
 * Collection<T> itemsFromCategories (List<String> categories) - метод што ќе ги врати елементите што се наоѓаат во категориите дадени како аргумент на функцијата.
 * public Map<String, Set<Т>> byMonthAndDay()- враќа мапа во која што елементите се групирани според нивниот timestamp (поточно месецот и денот конкатенирани со - измеѓу нив пр. 12-30, без разлика на годината). Месецот се добива со повик на методот getMonth(), а денот getDayOfMonth().
 * public Map<Integer, Long> countByYear()- враќа мапа во која што клучеви се сите години кога има креирани некој елелемт, а соодветната вредност е бројот на елементи креирани во таа година.
 * Секаде каде што има колекција од елементи, истите треба да бидат сортирани во опаѓачки редослед!
 * */

import com.sun.source.tree.Tree;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


class GenericCollection<T extends Comparable<T> & IHasTimestamp> {

    Map<String, List<T>> elements;

    public GenericCollection() {
        this.elements = new HashMap<>();
    }

    public GenericCollection(Map<String, List<T>> elements) {
        this.elements = elements;
    }

    void addGenericItem(String category, T element) {
        elements.computeIfAbsent(category, e -> new ArrayList<>())
                .add(element);

        // computeIfAbsent - prviot argument e klucot koj shto se prebaruva vo mapata, ako go najde ke go vrati parot za toj kluc, vo ovoj slucaj ke ja vrati promenlivata od tip ArrayList
        // vo slucaj da ne postoi vrednost vo mapta za toj kluc, vtoriot argument od funkcijata e instrukcii kako da postapi programata vo pozadina

        // e - > new ArrayList<>()

        // "e" preststavuva argument vo lambda funkcija , taka shto vo taa promenliva se cuva klucot vrz koj se vrshat operacii
        // Ovaa linija kod ustvari vika, za kategorijata  koja e zacuvana vo "e" , kreiraj nov key - value par vo mapata taka shto , kako vrednost za ovoj kluc ke treba da bide nova prazna ArrayList

        //Na krajot, ovaa funkcija vrakja prazna Array list, koja e shtotuku kreirana ili pak vekje postoecka ArrayList koja sodrzi elementi
        // kako i da e , na kraj se dodava nashiot element shto ni e praten kako argument vo funkcijata
    }

    //метод којшто ќе врати колекција од сите елементи што се наоѓаат во интервалот на датуми даден како аргументи на функцијата.
    Collection<T> findAllBetween(LocalDateTime from, LocalDateTime to) {
        return this.elements.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(e -> e.getTimestamp().isAfter(from) && e.getTimestamp().isBefore(to))
                .collect(Collectors.toCollection(() -> new TreeSet<T>(Comparator.reverseOrder())));
    }


    //метод што ќе ги врати елементите што се наоѓаат во категориите дадени како аргумент на функцијата.
    Collection<T> itemsFromCategories(List<String> categories) {

        return this.elements.keySet()
                .stream()
                .filter(key -> categories.contains(key))
                .flatMap(key -> this.elements.get(key).stream())
                .collect(Collectors.toCollection(() -> new TreeSet<T>(Comparator.reverseOrder())));
    }


    //враќа мапа во која што елементите се групирани според нивниот timestamp
    // (поточно месецот и денот конкатенирани со - измеѓу нив пр. 12-30,без разлика на годината).
    // Месецот се добива со повик на методот getMonth(), а денот getDayOfMonth().

    public Map<String, Set<T>> byMonthAndDay() {

        return this.elements.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        e -> {
                            // Ovdeka se kreira klucot, ovaa funkcija za sekoj element vo strimot go izvleguva klucot, i potoa go dodava vo HashMapata
                            int month = e.getTimestamp().getMonthValue();
                            int day = e.getTimestamp().getDayOfMonth();
                            return String.format("%02d-%02d", month, day);
                        },
                        TreeMap::new,   //2 argument e kakov tip na Map sakame da koristime
                        Collectors.toCollection(() -> new TreeSet<T>(Comparator.reverseOrder())))   // Kolektorot ustvari gi pribira site vrednosti i gi smestuva kako values za dadeniot kluc
                );

    }

    //враќа мапа во која што клучеви се сите години кога има креирани некој елелемт,
    // а соодветната вредност е бројот на елементи креирани во таа година.
    public Map<Integer, Long> countByYear() {

        return this.elements.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        e -> e.getTimestamp().getYear(),
                        TreeMap::new,
                        Collectors.counting()
                ));
    }

}


interface IHasTimestamp {
    LocalDateTime getTimestamp();
}

class IntegerElement implements Comparable<IntegerElement>, IHasTimestamp {

    int value;
    LocalDateTime timestamp;


    public IntegerElement(int value, LocalDateTime timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(IntegerElement o) {
        return Integer.compare(this.value, o.value);
    }

    @Override
    public String toString() {
        return "IntegerElement{" +
                "value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}

class StringElement implements Comparable<StringElement>, IHasTimestamp {

    String value;
    LocalDateTime timestamp;


    public StringElement(String value, LocalDateTime timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(StringElement o) {
        return this.value.compareTo(o.value);
    }

    @Override
    public String toString() {
        return "StringElement{" +
                "value='" + value + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

class TwoIntegersElement implements Comparable<TwoIntegersElement>, IHasTimestamp {

    int value1;
    int value2;
    LocalDateTime timestamp;

    public TwoIntegersElement(int value1, int value2, LocalDateTime timestamp) {
        this.value1 = value1;
        this.value2 = value2;
        this.timestamp = timestamp;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(TwoIntegersElement o) {
        int cmp = Integer.compare(this.value1, o.value1);
        if (cmp != 0)
            return cmp;
        else
            return Integer.compare(this.value2, o.value2);
    }

    @Override
    public String toString() {
        return "TwoIntegersElement{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                ", timestamp=" + timestamp +
                '}';
    }
}

public class GenericCollectionTest {

    public static void main(String[] args) {

        int type1, type2;
        GenericCollection<IntegerElement> integerCollection = new GenericCollection<IntegerElement>();
        GenericCollection<StringElement> stringCollection = new GenericCollection<StringElement>();
        GenericCollection<TwoIntegersElement> twoIntegersCollection = new GenericCollection<TwoIntegersElement>();
        Scanner sc = new Scanner(System.in);

        type1 = sc.nextInt();

        int count = sc.nextInt();

        for (int i = 0; i < count; i++) {
            if (type1 == 1) { //integer element
                int value = sc.nextInt();
                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
                String category = sc.next();
                integerCollection.addGenericItem(category, new IntegerElement(value, timestamp));
            } else if (type1 == 2) { //string element
                String value = sc.next();
                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
                String category = sc.next();
                stringCollection.addGenericItem(category, new StringElement(value, timestamp));
            } else { //two integer element
                int value1 = sc.nextInt();
                int value2 = sc.nextInt();
                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
                String category = sc.next();
                twoIntegersCollection.addGenericItem(category, new TwoIntegersElement(value1, value2, timestamp));
            }
        }


        type2 = sc.nextInt();

        if (type2 == 1) { //findAllBetween
            LocalDateTime start = LocalDateTime.of(2008, 1, 1, 0, 0);
            LocalDateTime end = LocalDateTime.of(2020, 1, 30, 23, 59);
            if (type1 == 1)
                printResultsFromFindAllBetween(integerCollection, start, end);
            else if (type1 == 2)
                printResultsFromFindAllBetween(stringCollection, start, end);
            else
                printResultsFromFindAllBetween(twoIntegersCollection, start, end);
        } else if (type2 == 2) { //itemsFromCategories
            List<String> categories = new ArrayList<>();
            int n = sc.nextInt();
            while (n != 0) {
                categories.add(sc.next());
                n--;
            }
            if (type1 == 1)
                printResultsFromItemsFromCategories(integerCollection, categories);
            else if (type1 == 2)
                printResultsFromItemsFromCategories(stringCollection, categories);
            else
                printResultsFromItemsFromCategories(twoIntegersCollection, categories);
        } else if (type2 == 3) { //byMonthAndDay
            if (type1 == 1)
                printResultsFromByMonthAndDay(integerCollection);
            else if (type1 == 2)
                printResultsFromByMonthAndDay(stringCollection);
            else
                printResultsFromByMonthAndDay(twoIntegersCollection);
        } else { //countByYear
            if (type1 == 1)
                printResultsFromCountByYear(integerCollection);
            else if (type1 == 2)
                printResultsFromCountByYear(stringCollection);
            else
                printResultsFromCountByYear(twoIntegersCollection);
        }


    }

    private static void printResultsFromItemsFromCategories(
            GenericCollection<?> collection, List<String> categories) {
        collection.itemsFromCategories(categories).forEach(element -> System.out.println(element.toString()));
    }

    private static void printResultsFromFindAllBetween(
            GenericCollection<?> collection, LocalDateTime start, LocalDateTime end) {
        collection.findAllBetween(start, end).forEach(element -> System.out.println(element.toString()));
    }

    private static void printSetOfElements(Set<?> set) {
        System.out.print("[");
        System.out.print(set.stream().map(Object::toString).collect(Collectors.joining(", ")));
        System.out.println("]");
    }

    private static void printResultsFromByMonthAndDay(GenericCollection<?> collection) {
        collection.byMonthAndDay().forEach((key, value) -> {
            System.out.print(key + " -> ");
            printSetOfElements(value);
        });
    }

    private static void printResultsFromCountByYear(GenericCollection<?> collection) {
        collection.countByYear().forEach((key, value) -> {
            System.out.println(key + " -> " + value);
        });
    }
}


//
//class GenericCollection<T extends Comparable<T> & IHasTimestamp> {
//
//    private Map<String, List<T>> elements;
//
//    public GenericCollection() {
//        this.elements = new HashMap<>();
//    }
//
//    public GenericCollection(Map<String, List<T>> elements) {
//        this.elements = elements;
//    }
//
//    void addGenericItem(String category, T element) {
//        if (this.elements.get(category) == null) {
//            this.elements.put(category, new ArrayList<>());
//        }
//        this.elements.get(category).add(element);
//    }
//
//    Collection<T> findAllBetween(LocalDateTime from, LocalDateTime to) {
//        return this.elements.values()
//                .stream()
//                .flatMap(List::stream)
//                .filter(item -> {
//                            LocalDateTime timestamp = item.getTimestamp();
//                            return timestamp.isAfter(from) && timestamp.isBefore(to);
//                        }
//                )
//                .collect(Collectors.toCollection(() -> new TreeSet<T>(Comparator.reverseOrder())));
//
//    }
//
//    Collection<T> itemsFromCategories(List<String> categories) {
//
//        return elements.keySet()
//                .stream()
//                .filter(categories::contains)
//                .flatMap(element -> elements.get(element).stream())
//                .collect(Collectors.toCollection(() -> new TreeSet<T>(Comparator.reverseOrder())));
//
//
////        return categories.stream()
////                .map(category -> elements.get(category))
////                .flatMap(Collection::stream)
////                .collect(Collectors.groupingBy(
////                                element -> element.get(),
////                                TreeMap::new,
////                                Collectors.toList()
////                        )
////                );
//
//        //kreirame stream of Stringovi, pa potoa .map() funkcijata samo vrshi operacija za sekoj clen od taa lista
//        //u pricip znae deka category e String (taka ni e pratena kako argument) i operacijata elements.get(category) ja vrshi za sekoja promenliva vo pratenata niza
//        //operacijata e : naogja kategorija vo nashata hash mapa
//
//        //Bidejki imame poishe kategorii i poise listi, istite so flatMap() se spojuvaat u edna
//
//    }
//
//    public Map<String, Set<T>> byMonthAndDay() {
//
//        return this.elements.values()
//                .stream()
//                .flatMap(Collection::stream)
//                .collect(Collectors.groupingBy(
//                        element -> {
//                            int month = element.getTimestamp().getMonthValue();
//                            int day = element.getTimestamp().getDayOfMonth();
//                            return String.format("%02d-%02d", month, day);
//                        },
//                        TreeMap::new,
//                        Collectors.toCollection(() -> new TreeSet<T>(Comparator.reverseOrder()))
//                ));
//    }
//
//    public Map<Integer, Long> countByYear() {
//        return this.elements.values()
//                .stream()
//                .flatMap(Collection::stream)
//                .collect(Collectors.groupingBy(
//                        element -> element.getTimestamp().getYear(),
//                        TreeMap::new,
//                        Collectors.counting()
//                ));
//    }
//
//}
//
//interface IHasTimestamp {
//    LocalDateTime getTimestamp();
//}
//
//class IntegerElement implements Comparable<IntegerElement>, IHasTimestamp {
//
//    int value;
//    LocalDateTime timestamp;
//
//
//    public IntegerElement(int value, LocalDateTime timestamp) {
//        this.value = value;
//        this.timestamp = timestamp;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    @Override
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    @Override
//    public int compareTo(IntegerElement o) {
//        return Integer.compare(this.value, o.value);
//    }
//
//    @Override
//    public String toString() {
//        return "IntegerElement{" +
//                "value=" + value +
//                ", timestamp=" + timestamp +
//                '}';
//    }
//}
//
//class StringElement implements Comparable<StringElement>, IHasTimestamp {
//
//    String value;
//    LocalDateTime timestamp;
//
//
//    public StringElement(String value, LocalDateTime timestamp) {
//        this.value = value;
//        this.timestamp = timestamp;
//    }
//
//    @Override
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    @Override
//    public int compareTo(StringElement o) {
//        return this.value.compareTo(o.value);
//    }
//
//    @Override
//    public String toString() {
//        return "StringElement{" +
//                "value='" + value + '\'' +
//                ", timestamp=" + timestamp +
//                '}';
//    }
//}
//
//class TwoIntegersElement implements Comparable<TwoIntegersElement>, IHasTimestamp {
//
//    int value1;
//    int value2;
//    LocalDateTime timestamp;
//
//    public TwoIntegersElement(int value1, int value2, LocalDateTime timestamp) {
//        this.value1 = value1;
//        this.value2 = value2;
//        this.timestamp = timestamp;
//    }
//
//    @Override
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    @Override
//    public int compareTo(TwoIntegersElement o) {
//        int cmp = Integer.compare(this.value1, o.value1);
//        if (cmp != 0)
//            return cmp;
//        else
//            return Integer.compare(this.value2, o.value2);
//    }
//
//    @Override
//    public String toString() {
//        return "TwoIntegersElement{" +
//                "value1=" + value1 +
//                ", value2=" + value2 +
//                ", timestamp=" + timestamp +
//                '}';
//    }
//}
//
//public class GenericCollectionTest {
//
//    public static void main(String[] args) {
//
//        int type1, type2;
//        GenericCollection<IntegerElement> integerCollection = new GenericCollection<IntegerElement>();
//        GenericCollection<StringElement> stringCollection = new GenericCollection<StringElement>();
//        GenericCollection<TwoIntegersElement> twoIntegersCollection = new GenericCollection<TwoIntegersElement>();
//        Scanner sc = new Scanner(System.in);
//
//        type1 = sc.nextInt();
//
//        int count = sc.nextInt();
//
//        for (int i = 0; i < count; i++) {
//            if (type1 == 1) { //integer element
//                int value = sc.nextInt();
//                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
//                String category = sc.next();
//                integerCollection.addGenericItem(category, new IntegerElement(value, timestamp));
//            } else if (type1 == 2) { //string element
//                String value = sc.next();
//                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
//                String category = sc.next();
//                stringCollection.addGenericItem(category, new StringElement(value, timestamp));
//            } else { //two integer element
//                int value1 = sc.nextInt();
//                int value2 = sc.nextInt();
//                LocalDateTime timestamp = LocalDateTime.parse(sc.next());
//                String category = sc.next();
//                twoIntegersCollection.addGenericItem(category, new TwoIntegersElement(value1, value2, timestamp));
//            }
//        }
//
//
//        type2 = sc.nextInt();
//
//        if (type2 == 1) { //findAllBetween
//            LocalDateTime start = LocalDateTime.of(2008, 1, 1, 0, 0);
//            LocalDateTime end = LocalDateTime.of(2020, 1, 30, 23, 59);
//            if (type1 == 1)
//                printResultsFromFindAllBetween(integerCollection, start, end);
//            else if (type1 == 2)
//                printResultsFromFindAllBetween(stringCollection, start, end);
//            else
//                printResultsFromFindAllBetween(twoIntegersCollection, start, end);
//        } else if (type2 == 2) { //itemsFromCategories
//            List<String> categories = new ArrayList<>();
//            int n = sc.nextInt();
//            while (n != 0) {
//                categories.add(sc.next());
//                n--;
//            }
//            if (type1 == 1)
//                printResultsFromItemsFromCategories(integerCollection, categories);
//            else if (type1 == 2)
//                printResultsFromItemsFromCategories(stringCollection, categories);
//            else
//                printResultsFromItemsFromCategories(twoIntegersCollection, categories);
//        } else if (type2 == 3) { //byMonthAndDay
//            if (type1 == 1)
//                printResultsFromByMonthAndDay(integerCollection);
//            else if (type1 == 2)
//                printResultsFromByMonthAndDay(stringCollection);
//            else
//                printResultsFromByMonthAndDay(twoIntegersCollection);
//        } else { //countByYear
//            if (type1 == 1)
//                printResultsFromCountByYear(integerCollection);
//            else if (type1 == 2)
//                printResultsFromCountByYear(stringCollection);
//            else
//                printResultsFromCountByYear(twoIntegersCollection);
//        }
//
//
//    }
//
//    private static void printResultsFromItemsFromCategories(
//            GenericCollection<?> collection, List<String> categories) {
//        collection.itemsFromCategories(categories).forEach(element -> System.out.println(element.toString()));
//    }
//
//    private static void printResultsFromFindAllBetween(
//            GenericCollection<?> collection, LocalDateTime start, LocalDateTime end) {
//        collection.findAllBetween(start, end).forEach(element -> System.out.println(element.toString()));
//    }
//
//    private static void printSetOfElements(Set<?> set) {
//        System.out.print("[");
//        System.out.print(set.stream().map(Object::toString).collect(Collectors.joining(", ")));
//        System.out.println("]");
//    }
//
//    private static void printResultsFromByMonthAndDay(GenericCollection<?> collection) {
//        collection.byMonthAndDay().forEach((key, value) -> {
//            System.out.print(key + " -> ");
//            printSetOfElements(value);
//        });
//    }
//
//    private static void printResultsFromCountByYear(GenericCollection<?> collection) {
//        collection.countByYear().forEach((key, value) -> {
//            System.out.println(key + " -> " + value);
//        });
//    }
//}