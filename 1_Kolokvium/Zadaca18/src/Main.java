/*
Дадена е класа Coin која се користи за симулирање на вртење монета. Методот flip во оваа класа со помош на класата Random (која користи рамномерна распределба) враќа HEAD (глава) или TAIL (петка) со еднаква веројатност од 0.5 (50%).

Да се напише класа LoadedCoin која наследува од класата Coin и го препокрива методот flip така што ќе враќа HEAD со некоја веројатност P (0 - 100%). Веројатноста P е класна променлива и се иницијализира преку конструкторот.
* */

import java.util.Random;
import java.util.Scanner;


public class LoadedCoinTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int probability = scanner.nextInt();
        Coin c = new Coin();
        int heads = 0;
        int n = 1000;
        for(int i = 0; i < n; i++) {
            SIDE side = c.flip();
            if(side == SIDE.HEAD) {
                heads++;
            }
        }
        if(heads > 450 && heads < 550) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
        c = new LoadedCoin(probability);
        heads = 0;
        for(int i = 0; i < n; i++) {
            SIDE side = c.flip();
            if(side == SIDE.HEAD) {
                heads++;
            }
        }
        if(heads > probability * 10 - 50 && heads < probability * 10 + 50) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
enum SIDE {
    HEAD, TAIL
}
class Coin {

    SIDE side;

    public SIDE flip() {
        Random random = new Random();
        boolean isHead = random.nextBoolean();
        if (isHead) {
            return SIDE.HEAD;
        } else {
            return SIDE.TAIL;
        }
    }
}

class LoadedCoin extends Coin {
    // vasiot kod ovde
}


//76


//YES
//YES