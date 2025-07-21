/*
* Да се дефинира класа ShapesApplication во која се чуваат податоци за повеќе прозорци на кои се исцртуваат геометриски слики во форма на квадрат.

За класата да се дефинира:

ShapesApplication() - конструктор
int readCanvases (InputStream inputStream) - метод којшто од влезен поток на податоци ќе прочита информации за повеќе прозорци на кои се исцртуваат квадрати. Во секој ред од потокот е дадена информација за еден прозорец во формат: canvas_id size_1 size_2 size_3 …. size_n, каде што canvas_id е ИД-то на прозорецот, а после него следуваат големините на страните на квадратите што се исцртуваат во прозорецот. Методот треба да врати цел број што означува колку квадрати за сите прозорци се успешно прочитани.
void printLargestCanvasTo (OutputStream outputStream) - метод којшто на излезен поток ќе го испечати прозорецот чии квадрати имаат најголем периметар. Печатењето да се изврши во форматот canvas_id squares_count total_squares_perimeter.
* */

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}

//input
//364fbe94 24 30 22 33 32 30 37 18 29 27 33 21 27 26
//0469e20f 26 14 14 28 37 14 36 30
//33f2c7c0 18 12 14 38 28 26 17 22 33 36 28 33 36 38

//output
//===READING SQUARES FROM INPUT STREAM===
//        36
//        ===PRINTING LARGEST CANVAS TO OUTPUT STREAM===
//        364fbe94 14 1556