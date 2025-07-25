/*
Да се имплементира класа MojDDV која што од влезен тек ќе чита информации за скенирани фискални сметки од страна на еден корисник на истоимената апликација. Податоците за фискалните сметки се во следниот формат:

ID item_price1 item_tax_type1 item_price2 item_tax_type2 … item_price-n item_tax_type-n

На пример: 12334 1789 А 1238 B 1222 V 111 V

Постојат три типа на данок на додадена вредност и тоа:

А (18% од вредноста)
B (5% од вредноста)
V (0% од вредноста)
Повратокот на ДДВ изнесува 15% од данокот на додадената вредност за артикалот.

Да се имплементираат методите:

void readRecords (InputStream inputStream)- метод којшто ги чита од влезен тек податоците за фискалните сметки. Доколку е скенирана фискална сметка со износ поголем од 30000 денари потребно е да се фрли исклучок од тип AmountNotAllowedException. Дефинирајте каде ќе се фрла исклучокот, и каде ќе биде фатен, на начин што оваа функција, ќе може да ги прочита сите фискални коишто се скенирани. Исклучокот треба да испечати порака “Receipt with amount [сума на сите артикли] is not allowed to be scanned”.
void printTaxReturns (OutputStream outputStream) - метод којшто на излезен тек ги печати сите скенирани фискални сметки во формат ID SUM_OF_AMOUNTS TAX_RETURN, каде што SUM_OF_AMOUNTS e збирот на сите артикли во фискалната сметка, а TAX_RETURN е пресметаниот повраток на ДДВ за таа фискална сметка.
дополнително:

void printStatistics (OutputStream outputStream) - метод којшто на излезен тек печати статистики за повратокот на ДДВ од сите скенирани фискални сметки во формат min: MIN max: MAX sum: SUM count: COUNT average: AVERAGE, при што секоја од статистиките е во нов ред, а пак вредноста на статистиката е оддалечена со таб од името на статистиката (погледнете тест пример). Децималните вредности се печатат со 5 места, од кои 3 се за цифрите после децималата. Целите вредности се пишуваат со 5 места (порамнети на лево).
печатењето на вредностите во методот printTaxReturns се врши на тој начин што:
сите информации се одвоени со таб
id-то i сумата на фискалната сметка се печатат со 10 места
повратокот на ДДВ со 10 места, од кои 5 се за цифрите после децималата.
* */

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}

//70876 1585 B 1585 V 247 V 1391 B 1391 V 1649 B 1649 V 548 B 548 V 640 B 640 V 1309 B 1309 V 1486 V 2093 V 106 V 2001 V 361 V
//198710 1306 A 1306 B 1306 V 432 V 1222 V 1851 V 390 V 111 A 111 B 111 V 991 V 1611 A 1611 B 1611 V
//140819 709 A 709 B 709 V 1628 A 1628 B 1628 V 680 V
//584886 411 A 411 B 411 V 699 B 699 V 1571 V 1307 B 1307 V
//588253 1528 B 1528 V 1744 B 1744 V 1033 V 412 B 412 V 1221 A 1221 B 1221 V 328 A 328 B 328 V 1301 A 1301 B 1301 V 1778 A 1778 B 1778 V 1651 A 1651 B 1651 V 1937 V 1521 V 2068 B 2068 V
//970452 1703 V 1796 B 1796 V 1221 V 885 A 885 B 885 V 183 V 788 B 788 V 1753 B 1753 V 456 V 926 V 1898 V 410 B 410 V 824 B 824 V
//51307 2002 B 2002 V 1776 V 2097 B 2097 V 1128 A 1128 B 1128 V 151 A 151 B 151 V
//570505 1090 A 1090 B 1090 V 1121 B 1121 V 971 B 971 V 1260 A 1260 B 1260 V 443 V
//987793 1436 V 1648 B 1648 V 1197 V 992 B 992 V 528 A 528 B 528 V 739 A 739 B 739 V 750 B 750 V
//752690 1626 B 1626 V 1785 A 1785 B 1785 V 1938 V 1733 V 1137 B 1137 V 1832 A 1832 B 1832 V



//===READING RECORDS FROM INPUT STREAM===
//Receipt with amount 34832 is not allowed to be scanned
//===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===
//     70876	     20538	  53.41500
//    198710	     13970	 104.46600
//    140819	      7691	  80.62650
//    584886	      6816	  29.22450
//    970452	     20184	  72.31500
//     51307	     13811	  74.86800
//    570505	     11677	  96.76500
//    987793	     13214	  69.13650
//    752690	     20048	 145.50900
//===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===
//min:	29.225
//max:	145.509
//sum:	726.325
//count:	9
//avg:	80.703