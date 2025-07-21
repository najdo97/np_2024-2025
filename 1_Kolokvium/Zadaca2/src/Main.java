/*
* Да се дефинира класа ShapesApplication чување на податоци за повеќе прозорци на кои и се сцртуваат геометриски слики во различна форма (квадрати и кругови)..

За класата да се дефинира:

ShapesApplication(double maxArea) - конструктор, каде maxArea е најголемата дозволена плоштина на секоја форма поединечно, која може да биде исцртана на прозорците.
void readCanvases (InputStream inputStream) - метод којшто од влезен поток на податоци ќе прочита информации за повеќе прозорци на кои се исцртуваат различните геометриски слики. Во секој ред се наоѓа информација за еден прозорец во формат: canvas_id type_1 size_1 type_2 size_2 type_3 size_3 …. type_n size_n каде што canvas_id е ИД-то на прозорецот, a после него следуваат информации за секоја форма во прозорецот. Секоја форма е означена со карактер што го означува типот на геометриската слика (S = square, C = circle) и со големината на страната на квадратот, односно радиусот на кругот.
При додавањето на геометриските слики на прозорецот треба да се спречи креирање и додавање на прозорец во кој има форма што има плоштина поголема од максимално дозволената. Како механизам за спречување треба да се користи исклучок од тип IrregularCanvasException (фрлањето на исклучокот не треба да го попречи вчитувањето на останатите прозорци и геометриски слики. Да се испечати порака Canvas [canvas_id] has a shape with area larger than [max_area].
void printCanvases (OutputStream os) - метод којшто на излезен поток ќе ги испечати информациите за сите прозорци во апликацијата. Прозорците да се сортирани во опаѓачки редослед според сумата на плоштините на геометриските слики во нив. Секој прозорец да е испечатен во следниот формат: ID total_shapes total_circles total_squares min_area max_area average_area.
За вредноста на PI користете ja константата Math.PI. За постигнување на точност со тест примерите користете double за сите децимални променливи.
* */
public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}

//0cc31e47 C 27 C 13 C 29 C 15 C 22
//5960017f C 30 S 15 S 588 C 25 C 14 S 14 S 17 C 19
//8ed50a65 C 29 S 12 C 13 S 30 C 25 S 11
//201c295e C 27 C 13 C 14 C 11 S 18 C 12
//184ef1d4 S 28 S 26 S 2001 S 28 C 30 C 16 S 18
//c4b48d9f S 26 C 18 C 18 S 16 S 12 C 29 S 19
//5e28f402 C 24 C 28 C 14 C 25 S 11 S 22 S 10 S 19 S 20 S 11 C 29
//91a5b09b C 30 S 10 S 28 S 10 S 18 C 28 S 14 S 10 S 30 C 21 C 24
//36e77dad C 29 S 11 S 25 S 30 C 21 C 17 S 400 S 30 S 23
//13343cb0 S 21 C 29 C 14 C 30 C 12


//===READING CANVASES AND SHAPES FROM INPUT STREAM===
//Canvas 5960017f has a shape with area larger than 10000.00
//Canvas 184ef1d4 has a shape with area larger than 10000.00
//Canvas 36e77dad has a shape with area larger than 10000.00
//===PRINTING SORTED CANVASES TO OUTPUT STREAM===
//5e28f402 11 5 6 100.00 2642.08 1007.35
//91a5b09b 11 4 7 100.00 2827.43 999.04
//0cc31e47 5 5 0 530.93 2642.08 1538.12
//13343cb0 5 4 1 441.00 2827.43 1395.73
//8ed50a65 6 3 3 121.00 2642.08 1050.25
//c4b48d9f 7 3 4 144.00 2642.08 873.55
//201c295e 6 5 1 324.00 2290.22 765.57
