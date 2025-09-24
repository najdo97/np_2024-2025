import java.util.*;
import java.util.stream.Collectors;

class Point2D {

    private long id;
    private float x;
    private float y;


    public Point2D(long id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

class Cluster<T> {

    List<T> elements;

    public Cluster() {
        this.elements = new ArrayList<>();
    }

    public Cluster(List<T> elements) {
        this.elements = elements;
    }

    void addItem(T elemenet) {
        this.elements.add(elemenet);
    }

    double calculate_distance(Point2D target, Point2D potentialTarget) {
        return Math.sqrt((Math.pow(target.getX() - potentialTarget.getX(), 2)) + Math.pow(target.getY() - potentialTarget.getY(), 2));
    }

    void near(long id, int top) {
        List<Double> distances = new ArrayList<>();
        for (T element : elements) {
            if (element instanceof Point2D) {
                Point2D target = (Point2D) element;
                if (target.getId() == id) {
                    for (int j = 0; j < this.elements.size(); j++) {
                        if (elements.get(j) instanceof Point2D) {
                            Point2D potentialTarget = (Point2D) elements.get(j);
                            distances.add(calculate_distance(target, potentialTarget));
                        }
                        break;
                    }
                }
            }
            Collections.sort(distances);
            for (int i = 0; i < top; i++) {
                System.out.println(distances.get(i));
            }
        }

    }
}

public class ClusterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cluster<Point2D> cluster = new Cluster<>();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            long id = Long.parseLong(parts[0]);
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);
            cluster.addItem(new Point2D(id, x, y));
        }
        int id = scanner.nextInt();
        int top = scanner.nextInt();
        cluster.near(id, top);
        scanner.close();
    }
}
