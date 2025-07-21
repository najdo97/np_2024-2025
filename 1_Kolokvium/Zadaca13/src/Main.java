/*
Да се дефинира класа Component во која се чуваат:

бојата
тежината
колекција од внатрешни компоненти (референци од класата Component).
Во оваа класа да се дефинираат методите:

Component(String color, int weight) - конструктор со аргументи боја и тежина
void addComponent(Component component) - за додавање нова компонента во внатрешната колекција (во оваа колекција компонентите секогаш се подредени според тежината во растечки редослед, ако имаат иста тежина подредени се алфабетски според бојата).
Да се дефинира класа Window во која се чуваат:

име
компоненти.
Во оваа класа да се дефинираат следните методи:

Window(String) - конструктор
void addComponent(int position, Component component) - додава нова компонента на дадена позиција (цел број). На секоја позиција може да има само една компонента, ако се обидеме да додадеме компонента на зафатена позиција треба да се фрли исклучок од класата InvalidPositionException со порака Invalid position [pos], alredy taken!. Компонентите се подредени во растечки редослед според позицијата.
String toString() - враќа стринг репрезентација на објектот (дадена во пример излезот)
void changeColor(int weight, String color) - ја менува бојата на сите компоненти со тежина помала од проследената
void swichComponents(int pos1, int pos2) - ги заменува компонените од проследените позиции.
* */

import java.util.Scanner;
public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде
//FIREFOX
//1
//RED
//30
//3
//MAGENTA
//90
//0
//1
//2
//GREEN
//40
//3
//RED
//50
//2
//BLUE
//50
//2
//CYAN
//60
//1
//YELLOW
//80
//3
//WHITE
//35
//0
//2
//4
//60
//BLACK
//1 2





//=== ORIGINAL WINDOW ===
//WINDOW FIREFOX
//1:30:RED
//---40:GREEN
//------50:BLUE
//---------60:CYAN
//------50:RED
//---90:MAGENTA
//2:80:YELLOW
//---35:WHITE
//
//=== CHANGED COLOR (60, BLACK) ===
//WINDOW FIREFOX
//1:30:BLACK
//---40:BLACK
//------50:BLACK
//---------60:CYAN
//------50:BLACK
//---90:MAGENTA
//2:80:YELLOW
//---35:BLACK
//
//=== SWITCHED COMPONENTS 1 <-> 2 ===
//WINDOW FIREFOX
//1:80:YELLOW
//---35:BLACK
//2:30:BLACK
//---40:BLACK
//------50:BLACK
//---------60:CYAN
//------50:BLACK
//---90:MAGENTA