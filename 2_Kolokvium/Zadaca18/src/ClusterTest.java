import java.util.*;
import java.util.stream.Collectors;

interface clusterElement<T> {
    long getId();

    double distanceTo(T other);
}

class Point2D implements clusterElement<Point2D> {

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

    @Override
    public double distanceTo(Point2D other) {
        return Math.sqrt(
                (Math.pow(this.x - other.x, 2))
                        + Math.pow(this.y - other.y, 2));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

class Cluster<T extends clusterElement<T>> {

    List<T> elements;

    public Cluster() {
        this.elements = new ArrayList<>();
    }

    void addItem(T elemenet) {
        this.elements.add(elemenet);
    }

    void near(long id, int top) {
        HashMap<Long, Double> points_distances = new HashMap<>();
        List<Map.Entry<Long, Double>> distances;
        for (T target : elements) {
            if (target.getId() == id) {
                for (T potentialTarget : this.elements) {
                    points_distances.put(
                            potentialTarget.getId(),
                            target.distanceTo(potentialTarget)
                    );

                }
                break;
            }

        }

        distances = points_distances.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        for (int i = 1; i <= top; i++) {
            System.out.printf("%d. %d -> %.3f \n", i, distances.get(i).getKey(), distances.get(i).getValue());
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
