/*
* Да се имплементира класа Canvas на која ќе чуваат различни форми. За секоја форма се чува:

id:String
color:Color (enum дадена)
Притоа сите форми треба да имплментираат два интерфејси:

Scalable - дефиниран со еден метод void scale(float scaleFactor) за соодветно зголемување/намалување на формата за дадениот фактор
Stackable - дефиниран со еден метод float weight() кој враќа тежината на формата (се пресметува како плоштина на соодветната форма)
Во класата Canvas да се имплементираат следните методи:

void add(String id, Color color, float radius) за додавање круг
void add(String id, Color color, float width, float height) за додавање правоаголник
При додавањето на нова форма, во листата со форми таа треба да се смести на соодветното место според нејзината тежина. Елементите постојано се подредени според тежината во опаѓачки редослед.

void scale(String id, float scaleFactor) - метод кој ја скалира формата со даденото id за соодветниот scaleFactor. Притоа ако има потреба, треба да се изврши преместување на соодветните форми, за да се задржи подреденоста на елементите.
Не смее да се користи сортирање на листата.

toString() - враќа стринг составен од сите фигури во нов ред. За секоја фигура се додава:

C: [id:5 места од лево] [color:10 места од десно] [weight:10.2 места од десно] ако е круг

R: [id:5 места од лево] [color:10 места од десно] [weight:10.2 места од десно] ако е правоаголник
Користење на instanceof ќе се смета за неточно решение
* */

import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}
public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

enum Color {
    RED, GREEN, BLUE
}

class Canvas {

}



//1 c1 RED 15
//1 c2 GREEN 7
//1 c3 RED 9
//2 r1 BLUE 4 7
//2 r2 GREEN 10 8
//3 c2 2.4
//3 r1 5.1



//ORIGNAL:
//C: c1   RED           706.86
//C: c3   RED           254.47
//C: c2   GREEN         153.94
//R: r2   GREEN          80.00
//R: r1   BLUE           28.00
//AFTER SCALING: c2 2.40
//C: c2   GREEN         886.68
//C: c1   RED           706.86
//C: c3   RED           254.47
//R: r2   GREEN          80.00
//R: r1   BLUE           28.00
//ORIGNAL:
//C: c2   GREEN         886.68
//C: c1   RED           706.86
//C: c3   RED           254.47
//R: r2   GREEN          80.00
//R: r1   BLUE           28.00
//AFTER SCALING: r1 5.10
//C: c2   GREEN         886.68
//R: r1   BLUE          728.28
//C: c1   RED           706.86
//C: c3   RED           254.47
//R: r2   GREEN          80.00