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
    public InvalidIDException() {
        super(String.format("Invalid ID."));
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

    void readShapes(InputStream is) throws InvalidDimensionException, InvalidIDException {

        Scanner input = new Scanner(is);
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] data = line.split(" ");

            if (Double.parseDouble(data[2]) == 0) {
                throw new InvalidDimensionException();
            }

            if (Integer.parseInt(data[0]) == 1) {
                Circle circle = new Circle("Circle", Double.parseDouble(data[2]));
                this.users.computeIfAbsent(data[1], e -> new ArrayList<>()).add(circle);

                if (!validUserId(data[1])) {
                    throw new InvalidIDException();
                }

            } else if (Integer.parseInt(data[0]) == 2) {
                Square square = new Square("Square", Double.parseDouble(data[2]));
                this.users.computeIfAbsent(data[1], e -> new ArrayList<>()).add(square);

                if (!validUserId(data[1])) {
                    throw new InvalidIDException();
                }

            } else if (Integer.parseInt(data[0]) == 3) {

                if (Double.parseDouble(data[3]) == 0) {
                    throw new InvalidDimensionException();
                }
                Rectangle rectangle = new Rectangle("Rectangle", Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                this.users.computeIfAbsent(data[1], e -> new ArrayList<>()).add(rectangle);

                if (!validUserId(data[1])) {
                    throw new InvalidIDException();
                }
            }
        }
    }

    void scaleShapes(String userID, double coef) {

        if (!this.users.containsKey(userID)) {

            return;
        }

        this.users.put(userID,
                this.users.get(userID)
                        .stream()
                        .peek(e -> e.scaleShape(coef))
                        .collect(Collectors.toList())
        );
    }

    void printAllShapes(OutputStream os) {
        Set<Shape> shapes = this.users.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(TreeSet::new));

        PrintWriter writer = new PrintWriter(os);
        shapes.forEach(shape -> writer.println(shape.toString())); // Print each shape
        writer.flush(); // Ensure the output is written
    }

    void printByUserId(OutputStream os) {
        PrintWriter writer = new PrintWriter(os);

        this.users.forEach((userId, shapes) -> {
                    writer.println(String.format("Shapes of user: %s ", userId));
                    shapes.forEach(shape -> writer.println(shape.toString()));
                }
        );
        writer.flush(); // Ensure the output is written


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
        } catch (InvalidDimensionException | InvalidIDException e) {
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