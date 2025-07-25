/*Да се имплементира класа DailyTemperatures во која се вчитуваат температури на воздухот (цели броеви) за различни денови од годината (број од 1 до 366). Температурите за еден ден се во еден ред во следниот формат (пример): 137 23C 15C 28C. Првиот број претставува денот во годината, а потоа следуваат непознат број на мерења на температури за тој ден во скала во Целзиусови степени (C) или Фаренхајтови степени (F).

Во оваа класа да се имплементираат методите:

DailyTemperatures() - default конструктор
void readTemperatures(InputStream inputStream) - метод за вчитување на податоците од влезен тек
void writeDailyStats(OutputStream outputStream, char scale) - метод за печатање на дневна статистика (вкупно мерења, минимална температура, максимална температура, просечна температура) за секој ден, подредени во растечки редослед според денот. Вториот аргумент scale одредува во која скала се печатат температурите C - Целзиусова, F - Фаренхајтова. Форматот за печатање на статистиката за одреден ден е следниот:
[ден]: Count: [вк. мерења - 3 места] Min: [мин. температура] Max: [макс. температура] Avg: [просек ]

Минималната, максималната и просечната температура се печатат со 6 места, од кои 2 децимални, а по бројот се запишува во која скала е температурата (C/F).

Формула за конверзија од Целзиусуви во Фаренхајтови: $\frac{T * 9}{5} + 32$

Формула за конверзија од Фаренхајтови во Целзиусуви: $\frac{(T - 32) * 5}{9}$

Забелешка: да се постигне иста точност како во резултатите од решението, за пресметување на просекот и конверзијата во различна скала температурите се чуваат со тип Double.
*/

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

//Input

//317 24C 29C 28C 29C
//140 47F 49F 46F 46F 47F 49F 48F 50F 45F 47F 46F 49F 50F 47F 50F 49F 49F 47F 45F
//299 18C 17C 17C 18C 16C 19C 16C 16C 17C 18C 21C 21C
//257 30C
//231 13F 17F 16F 14F 13F 15F 18F 16F 15F 13F 16F 13F 13F 16F 18F
//113 21F
//133 69F 68F
//65 89F 92F 90F 91F 88F 92F 88F 89F 93F 92F 92F 89F 92F 90F 91F
//235 41F 41F 40F 36F 38F 41F 37F 40F 37F 37F 41F 36F 40F 36F 36F 36F 41F 37F
//131 2C 3C 1C 0C 2C 4C 3C -1C -1C 2C 2C 3C 0C -1C 0C
//335 70F 71F 66F 70F 70F 67F 67F 70F 68F 66F 70F
//64 35C 36C 32C 34C 33C 31C 33C 35C 35C 31C 35C 32C 36C 32C 35C 34C 36C
//302 92F 93F 90F 95F 93F 91F 93F 90F 92F 92F 95F 91F 95F 92F 92F 91F 94F 90F 95F
//306 34C 34C 32C 33C 37C 37C 35C 37C
//363 42F 40F 43F 42F 38F 40F 38F 40F 40F 38F
//157 74F 71F 75F 72F 71F 71F 73F 76F 72F 73F 75F 75F 71F 71F 76F
//91 98F 100F 102F 102F 99F 101F 102F
//311 24C 22C 21C 23C 22C 23C 23C 23C 24C
//340 44F 43F 41F 40F 43F 43F 43F 43F 43F 44F 44F 44F 45F 42F
//11 101F 103F 102F 102F 104F 104F 105F
//315 74F 71F 73F 74F
//203 86F 86F 84F 83F 83F 85F
//33 -10C -6C -9C -10C -6C
//224 0C -1C 0C 3C -2C -1C 1C -1C 0C
//88 34C 30C 30C 32C 32C 33C 34C 33C 29C 34C 32C 32C
//55 13C 10C 9C 14C 14C 10C 13C 12C 9C 13C 9C 10C 9C 11C 12C
//34 4C 1C 6C 3C 1C 3C 5C
//103 25C 26C 30C 27C 30C 28C 29C 29C 29C 28C 30C 25C
//24 -1C 2C
//230 51F 51F 55F 54F 55F 55F 52F 54F 52F 55F 56F
//98 14C
//160 94F 92F 92F 95F 92F 96F 96F 93F 92F 93F 97F 92F 96F 95F 92F 94F
//206 49F 53F 52F 52F 53F 52F 48F 48F 48F 49F 53F 49F 50F 52F 50F 53F 50F 49F 52F 51F

//Result
//=== Daily temperatures in Celsius (C) ===
// 11: Count:   7 Min:  38.33C Max:  40.56C Avg:  39.44C
// 24: Count:   2 Min:  -1.00C Max:   2.00C Avg:   0.50C
// 33: Count:   5 Min: -10.00C Max:  -6.00C Avg:  -8.20C
// 34: Count:   7 Min:   1.00C Max:   6.00C Avg:   3.29C
// 55: Count:  15 Min:   9.00C Max:  14.00C Avg:  11.20C
// 64: Count:  17 Min:  31.00C Max:  36.00C Avg:  33.82C
// 65: Count:  15 Min:  31.11C Max:  33.89C Avg:  32.52C
// 88: Count:  12 Min:  29.00C Max:  34.00C Avg:  32.08C
// 91: Count:   7 Min:  36.67C Max:  38.89C Avg:  38.10C
// 98: Count:   1 Min:  14.00C Max:  14.00C Avg:  14.00C
//103: Count:  12 Min:  25.00C Max:  30.00C Avg:  28.00C
//113: Count:   1 Min:  -6.11C Max:  -6.11C Avg:  -6.11C
//131: Count:  15 Min:  -1.00C Max:   4.00C Avg:   1.27C
//133: Count:   2 Min:  20.00C Max:  20.56C Avg:  20.28C
//140: Count:  19 Min:   7.22C Max:  10.00C Avg:   8.71C
//157: Count:  15 Min:  21.67C Max:  24.44C Avg:  22.81C
//160: Count:  16 Min:  33.33C Max:  36.11C Avg:  34.34C
//203: Count:   6 Min:  28.33C Max:  30.00C Avg:  29.17C
//206: Count:  20 Min:   8.89C Max:  11.67C Avg:  10.36C
//224: Count:   9 Min:  -2.00C Max:   3.00C Avg:  -0.11C
//230: Count:  11 Min:  10.56C Max:  13.33C Avg:  12.02C
//231: Count:  15 Min: -10.56C Max:  -7.78C Avg:  -9.41C
//235: Count:  18 Min:   2.22C Max:   5.00C Avg:   3.55C
//257: Count:   1 Min:  30.00C Max:  30.00C Avg:  30.00C
//299: Count:  12 Min:  16.00C Max:  21.00C Avg:  17.83C
//302: Count:  19 Min:  32.22C Max:  35.00C Avg:  33.57C
//306: Count:   8 Min:  32.00C Max:  37.00C Avg:  34.88C
//311: Count:   9 Min:  21.00C Max:  24.00C Avg:  22.78C
//315: Count:   4 Min:  21.67C Max:  23.33C Avg:  22.78C
//317: Count:   4 Min:  24.00C Max:  29.00C Avg:  27.50C
//335: Count:  11 Min:  18.89C Max:  21.67C Avg:  20.35C
//340: Count:  14 Min:   4.44C Max:   7.22C Avg:   6.11C
//363: Count:  10 Min:   3.33C Max:   6.11C Avg:   4.50C
//=== Daily temperatures in Fahrenheit (F) ===
// 11: Count:   7 Min: 101.00F Max: 105.00F Avg: 103.00F
// 24: Count:   2 Min:  30.20F Max:  35.60F Avg:  32.90F
// 33: Count:   5 Min:  14.00F Max:  21.20F Avg:  17.24F
// 34: Count:   7 Min:  33.80F Max:  42.80F Avg:  37.91F
// 55: Count:  15 Min:  48.20F Max:  57.20F Avg:  52.16F
// 64: Count:  17 Min:  87.80F Max:  96.80F Avg:  92.88F
// 65: Count:  15 Min:  88.00F Max:  93.00F Avg:  90.53F
// 88: Count:  12 Min:  84.20F Max:  93.20F Avg:  89.75F
// 91: Count:   7 Min:  98.00F Max: 102.00F Avg: 100.57F
// 98: Count:   1 Min:  57.20F Max:  57.20F Avg:  57.20F
//103: Count:  12 Min:  77.00F Max:  86.00F Avg:  82.40F
//113: Count:   1 Min:  21.00F Max:  21.00F Avg:  21.00F
//131: Count:  15 Min:  30.20F Max:  39.20F Avg:  34.28F
//133: Count:   2 Min:  68.00F Max:  69.00F Avg:  68.50F
//140: Count:  19 Min:  45.00F Max:  50.00F Avg:  47.68F
//157: Count:  15 Min:  71.00F Max:  76.00F Avg:  73.07F
//160: Count:  16 Min:  92.00F Max:  97.00F Avg:  93.81F
//203: Count:   6 Min:  83.00F Max:  86.00F Avg:  84.50F
//206: Count:  20 Min:  48.00F Max:  53.00F Avg:  50.65F
//224: Count:   9 Min:  28.40F Max:  37.40F Avg:  31.80F
//230: Count:  11 Min:  51.00F Max:  56.00F Avg:  53.64F
//231: Count:  15 Min:  13.00F Max:  18.00F Avg:  15.07F
//235: Count:  18 Min:  36.00F Max:  41.00F Avg:  38.39F
//257: Count:   1 Min:  86.00F Max:  86.00F Avg:  86.00F
//299: Count:  12 Min:  60.80F Max:  69.80F Avg:  64.10F
//302: Count:  19 Min:  90.00F Max:  95.00F Avg:  92.42F
//306: Count:   8 Min:  89.60F Max:  98.60F Avg:  94.78F
//311: Count:   9 Min:  69.80F Max:  75.20F Avg:  73.00F
//315: Count:   4 Min:  71.00F Max:  74.00F Avg:  73.00F
//317: Count:   4 Min:  75.20F Max:  84.20F Avg:  81.50F
//335: Count:  11 Min:  66.00F Max:  71.00F Avg:  68.64F
//340: Count:  14 Min:  40.00F Max:  45.00F Avg:  43.00F
//363: Count:  10 Min:  38.00F Max:  43.00F Avg:  40.10F
