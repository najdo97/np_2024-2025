/*
Да се имплементира класа Subtitles која ќе чита од влезен тек (стандарден влез, датотека, ...) превод во стандарден srt формат. Секој еден елемент од преводот се состои од реден број, време на почеток на прикажување, време на крај на прикажување и текст и е во следниот формат (пример):

2
00:00:48,321 --> 00:00:50,837
Let's see a real bet.
Делот со текстот може да има повеќе редови. Сите елементи се разделени со еден нов ред.

Ваша задача е да ги имплементирате методите:

Subtitles() - default конструктор
int loadSubtitles(InputStream inputStream) - метод за читање на преводот (враќа резултат колку елементи се прочитани)
void print() - го печати вчитаниот превод во истиот формат како и при читањето.
void shift(int ms) - ги поместува времињата на сите елементи од преводот за бројот на милисекунди кои се проследува како аргумент (може да биде негативен, со што се поместуваат времињата наназад).
* */

import java.util.Scanner;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде


//17
//00:01:53,468 --> 00:01:54,745
//All right.
//
//18
//00:01:55,540 --> 00:01:57,305
//Johnny's got the head.
//
//19
//00:01:57,685 --> 00:01:59,382
//I've got the belly.
//
//20
//00:01:59,570 --> 00:02:00,856
//Which leaves you
//
//21
//00:02:00,907 --> 00:02:02,041
//the legs
//
//22
//00:02:02,150 --> 00:02:03,616
//the gut
//
//23
//00:02:03,807 --> 00:02:05,508
//or the fucking tale.
//
//24
//00:02:08,135 --> 00:02:10,449
//Where's the other fellow?
//
//25
//00:02:13,370 --> 00:02:14,950
//You're late.





//+++++ ORIGINIAL SUBTITLES +++++
//17
//00:01:53,468 --> 00:01:54,745
//All right.
//
//18
//00:01:55,540 --> 00:01:57,305
//Johnny's got the head.
//
//19
//00:01:57,685 --> 00:01:59,382
//I've got the belly.
//
//20
//00:01:59,570 --> 00:02:00,856
//Which leaves you
//
//21
//00:02:00,907 --> 00:02:02,041
//the legs
//
//22
//00:02:02,150 --> 00:02:03,616
//the gut
//
//23
//00:02:03,807 --> 00:02:05,508
//or the fucking tale.
//
//24
//00:02:08,135 --> 00:02:10,449
//Where's the other fellow?
//
//25
//00:02:13,370 --> 00:02:14,950
//You're late.
//
//SHIFT FOR -333 ms
//+++++ SHIFTED SUBTITLES +++++
//17
//00:01:53,135 --> 00:01:54,412
//All right.
//
//18
//00:01:55,207 --> 00:01:56,972
//Johnny's got the head.
//
//19
//00:01:57,352 --> 00:01:59,049
//I've got the belly.
//
//20
//00:01:59,237 --> 00:02:00,523
//Which leaves you
//
//21
//00:02:00,574 --> 00:02:01,708
//the legs
//
//22
//00:02:01,817 --> 00:02:03,283
//the gut
//
//23
//00:02:03,474 --> 00:02:05,175
//or the fucking tale.
//
//24
//00:02:07,802 --> 00:02:10,116
//Where's the other fellow?
//
//25
//00:02:13,037 --> 00:02:14,617
//You're late.
