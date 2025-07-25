/*
 * Да се напише класа Canvas во која што ќе се чуваат форми од различен тип. За секоја форма треба да може да се добијат информации за колкава плоштина и периметар има, како и да се овозможи формата да биде скалирана за некој коефициент. Во класата Canvas да се имплементираат:
 * default конструктор
 * void readShapes (InputStream is) - метод за вчитување на информации за формите од влезен поток.
 * Информациите за секоја форма се дадени во секој ред. При вчитување на формите прво се вчитува број (1 = круг/2 = квадрат/3 = правоаголник), па потоа се чита ИД-то на корисникот што ја креирал формата, па потоа доколку станува збор за круг/квадрат се вчитува еден децимален број за радиусот/страната на кругот/квадартот, а доколку е правоаголник се вчитуваат два децимални броја за должина и висина на правоаголнкот.
 * ИД на корисникот мора да биде стринг со должина од 6 знаци, при што не се дозволени специјални знаци (само букви и бројки). Доколку некој ИД не е во ред да се фрли исклучок од тип InvalidIDException при креирањето на формата, а со истиот справете се во рамки на функцијата readShapes, односно неправилно ИД да не повлече прекинување на вчитувањето на формите.
 * Димензија на форма не смее да биде 0. Во таков случај да се фрли исклучок од тип InvalidDimensionException. Овој исклучок треба да го прекине понатамошното читање на останатите форми.
 * void scaleShapes (String userID, double coef) - метод што ќе ги скалира сите форми креирани од корисникот userID со коефициентот coef (ќе ги помножи сите димензии на формата со тој коефициент).
 * void printAllShapes (OutputStream os) - метод што ќе ги испечати формите на излезен поток сортирани според нивната плоштина во растечки редослед
 * void printByUserId (OutputStream os) - метод што ќе ги испечати формите групирани според корисникот којшто ги креирал, при што корисниците ќе се сортирани според бројот на форми што ги имаат креирано (доколку тој број е ист тогаш според сумата на плоштините на формите). Формите на даден корисник ќе се сортирани според периметарот во опаѓачки редослед.
 * void statistics (OutputStream os) - метод што ќе испечати статистики за плоштините на сите форми во колекцијата (min, max, average, sum, count).
 * Напомени:

 * За да се постигне посакуваната точност, користете постојано double за сите децимални броеви!
 * Забрането е користење на .sorted() за да се постигне сортирање на формите. Овој метод може да го искористите само за сортирање на групите на корисници според број на форми што ги задале (во методот printByUserId )
 * */
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


abstract class Shape implements Comparable<Shape> {
    private String name;

    public Shape(String name) {
        this.name = name;
    }

    abstract double calculateArea();

    abstract double calculatePerimeter();

    abstract void scaleShape(double coef); //increases all variables by a coef

    @Override
    abstract public String toString();


    //todo
    @Override
    public int compareTo(Shape o) {

        return Double.compare(this.calculateArea(), o.calculateArea());
    }


}

class InvalidIDException extends Exception {
    public InvalidIDException(String userId) {
        super(String.format("ID %s is not valid", userId));
    }
}

class InvalidDimensionException extends Exception {
    public InvalidDimensionException() {
        super(String.format("Dimension 0 is not allowed!"));
    }
}


class Circle extends Shape {
    private double radius;

    public Circle(String name, double radius) {
        super(name);
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        return radius * radius * Math.PI;
    }

    @Override
    double calculatePerimeter() {
        return radius * Math.PI * 2;
    }

    @Override
    void scaleShape(double coef) {
        this.radius = this.radius * coef;
    }

    @Override
    public String toString() {
        return String.format("Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f", this.radius, calculateArea(), calculatePerimeter());
    }
}

class Square extends Shape {
    private double sideLength;

    public Square(String name, double sideLength) {
        super(name);
        this.sideLength = sideLength;
    }

    @Override
    double calculateArea() {
        return sideLength * sideLength;
    }

    @Override
    double calculatePerimeter() {
        return sideLength * 4;
    }

    @Override
    void scaleShape(double coef) {
        this.sideLength = this.sideLength * coef;
    }

    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f", this.sideLength, calculateArea(), calculatePerimeter());
    }
}

class Rectangle extends Shape {
    private double sideA;
    private double sideB;

    public Rectangle(String name, double sideA, double sideB) {
        super(name);
        this.sideA = sideA;
        this.sideB = sideB;
    }

    @Override
    double calculateArea() {
        return sideA * sideB;
    }

    @Override
    double calculatePerimeter() {
        return 2 * sideA + 2 * sideB;
    }

    @Override
    void scaleShape(double coef) {
        this.sideA = this.sideA * coef;
        this.sideB = this.sideB * coef;

    }

    @Override
    public String toString() {
        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f", sideA, sideB, calculateArea(), calculatePerimeter());
    }
}


class Canvas {

    private Map<String, List<Shape>> users;


    public Canvas() {
        this.users = new HashMap<>();
    }

    boolean validUserId(String userId) {
        if (userId.length() != 6) {
            return false;
        }
        for (int i = 0; i < userId.length(); i++) {
            if (Character.isAlphabetic(userId.charAt(i)) || Character.isDigit(userId.charAt(i))) {

            } else return false;
        }
        return true;
    }

    void readShapes(InputStream is) throws InvalidDimensionException {

        Scanner input = new Scanner(is);
        while (input.hasNext()) {
            try {


                String line = input.nextLine();
                String[] data = line.split(" ");
                if (!validUserId(data[1])) {
                    throw new InvalidIDException(data[1]);
                }
                if (Double.parseDouble(data[2]) == 0) {
                    throw new InvalidDimensionException();
                }

                if (Integer.parseInt(data[0]) == 1) {
                    Circle circle = new Circle("Circle", Double.parseDouble(data[2]));
                    this.users.computeIfAbsent(data[1], e -> new ArrayList<>()).add(circle);

                } else if (Integer.parseInt(data[0]) == 2) {
                    Square square = new Square("Square", Double.parseDouble(data[2]));
                    this.users.computeIfAbsent(data[1], e -> new ArrayList<>()).add(square);

                } else if (Integer.parseInt(data[0]) == 3) {

                    if (Double.parseDouble(data[3]) == 0) {
                        throw new InvalidDimensionException();
                    }
                    Rectangle rectangle = new Rectangle("Rectangle", Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                    this.users.computeIfAbsent(data[1], e -> new ArrayList<>()).add(rectangle);
                }


            } catch (InvalidIDException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void scaleShapes(String userID, double coef) {

        if (!this.users.containsKey(userID)) {
            return;
        }
        this.users.get(userID)
                .forEach(e -> e.scaleShape(coef));
    }


    void printAllShapes(OutputStream os) {
        Set<Shape> shapes = this.users.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(() -> new TreeSet<Shape>(Shape::compareTo)));

        PrintWriter writer = new PrintWriter(os);
        shapes.forEach(shape -> writer.println(shape.toString())); // Print each shape
        writer.flush(); // Ensure the output is written
    }

    void printByUserId(OutputStream os) {

        PrintWriter writer = new PrintWriter(os);


        LinkedHashMap<String, List<Shape>> sortedUsers = users.entrySet()
                .stream()
                //.sorted(Comparator.comparing((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())).thenComparing())
                .sorted(Comparator.comparingInt((Map.Entry<String,List<Shape>> e)->e.getValue().size()).reversed()
                        .thenComparing(e->e.getValue()
                                .stream()
                                .mapToDouble(Shape::calculateArea).sum()))
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream()
                                        .sorted(Comparator.comparingDouble(Shape::calculatePerimeter))
                                        .collect(Collectors.toList()),
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        )
                );

        sortedUsers.forEach((userId, shapes) -> {
                    writer.println(String.format("Shapes of user: %s ", userId));
                    shapes.forEach(shape -> writer.println(shape.toString()));
                }
        );
        writer.flush();

    }

    void statistics(OutputStream os) {
        PrintWriter writer = new PrintWriter(os);

        DoubleSummaryStatistics stats = this.users.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.summarizingDouble(Shape::calculateArea));

        writer.println(String.format("count: %d\nsum: %.2f\nmin: %.2f\naverage: %.2f\nmax: %.2f",
                stats.getCount(), stats.getSum(), stats.getMin(), stats.getAverage(), stats.getMax()));

        writer.flush(); // Ensure the output is written

    }


}

public class CanvasTest {

    public static void main(String[] args) throws InvalidDimensionException, InvalidIDException {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");

        try {
            canvas.readShapes(System.in);
        } catch (InvalidDimensionException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}