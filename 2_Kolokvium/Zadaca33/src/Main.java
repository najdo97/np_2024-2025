/*
* Да се имплементира класа LogCollector за чување и анализирање на логовите на сервисите (и на микросервисите кои се дел од истите) на една компанија. За класата да се обезбедат следните методи:

Потребни конструктори
void addLog (String log) - метод за додавање на лог во колекторот на логови. Логот е потребно да се парсира и истиот секогаш е во следниот формат: service_name microservice_name type message timestamp, каде што type може да биде INFO, WARN и ERROR.
void printServicesBySeverity() - метод кој ќе ги испечати сите сервиси за кои колекторот има собрано логови сортирани според просечната сериозност (анг. severity) на сите логовите произведени од тој сервис во опаѓачки редослед.
Map<Integer, Integer> getSeverityDistribuition (String service, String microservice) - метод кој враќа мапа од нивоата на сериозност детектирани во логовите на микросервисот microservice кој се наоѓа во сервисот service со бројот на логови кои ја имаат соодветната сериозност. Доколку microservice e null, потребно е резултатот да се однесува на сите логови од конкретниот сервис (без разлика на микросервисот).
void displayLogs(String service, String microservice, String order)- метод кој ги печати логовите на микросервисот microservice кој се наоѓа во сервисот service сортирани според правилото дадено во order. Правилото може да ги има следните 4 вредности:
NEWEST_FIRST (се печатат најновите логови прво)
OLDEST_FIRST (се печатат најстарите логови прво)
MOST_SEVERE_FIRST (се печатат логови со најголема сериозност прво)
LEAST_SEVERE_FIRST (се печатат логови со најмала сериозност прво)
Сериозноста на логовите е цел број кој зависи од типот на логот и содржината на пораката и таа се пресметува на следниот начин:

логовите од тип INFO секогаш имаат сериозност 0
логовите од тип WARN секогаш имаат сериозност 1, а доколку во пораката се содржи и текстот might cause error, сериозноста се зголемува за 1
логовите од тип ERROR секогаш имаат сериозност 3, а доколку во пораката се содржи и текстот:
fatal - сериозноста се зголемува за 2
exception - сериозноста се зголемува за 3
Решенија кои не користат правилни приципи од ООП за пресметка на сериозноста на различните типови на логови ќе бидат оценети со најмногу 70% од предвидените поени за задачата!*/

public class LogsTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogCollector collector = new LogCollector();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("addLog")) {
                collector.addLog(line.replace("addLog ", ""));
            } else if (line.startsWith("printServicesBySeverity")) {
                collector.printServicesBySeverity();
            } else if (line.startsWith("getSeverityDistribution")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                if (parts.length == 3) {
                    microservice = parts[2];
                }
                collector.getSeverityDistribution(service, microservice).forEach((k,v)-> System.out.printf("%d -> %d%n", k,v));
            } else if (line.startsWith("displayLogs")){
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                String order = null;
                if (parts.length == 4) {
                    microservice = parts[2];
                    order = parts[3];
                } else {
                    order = parts[2];
                }
                System.out.println(line);

                collector.displayLogs(service, microservice, order);
            }
        }
    }
}


//input:
//addLog service2 microservice3 ERROR Log message 1.This error is a fatal error!! It was caused by an exception. 101
//addLog service2 microservice3 ERROR Log message 2. 252
//addLog service2 microservice1 ERROR Log message 3. 355
//addLog service1 microservice3 WARN Log message 4. 437
//addLog service1 microservice1 INFO Log message 5. 532
//addLog service1 microservice1 INFO Log message 6. 659
//addLog service2 microservice2 ERROR Log message 7. 761
//addLog service1 microservice2 WARN Log message 8.This action might cause error. 861
//addLog service2 microservice2 INFO Log message 9. 953
//addLog service1 microservice3 WARN Log message 10.This action might cause error. 1056
//printServicesBySeverity


//output:
//Service name: service2 Count of microservices: 3 Total logs in service: 5 Average severity for all logs: 3.40 Average number of logs per microservice: 1.67
//Service name: service1 Count of microservices: 3 Total logs in service: 5 Average severity for all logs: 1.00 Average number of logs per microservice: 1.67