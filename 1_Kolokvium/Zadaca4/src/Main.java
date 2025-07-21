//Потребно е да се развие систем за процесирање на логови. За секој лог треба да се чува пораката од логот (String), типот на логот (String) и временски печат (long). За таа цел комплетирајте го интерфејсот ILog.
//
//За да се процесираат логовите ќе се користи генеричкиот интерфејс LogProcessor. Овој интерфејс има само еден метод со потпис: ArrayList processLogs (ArrayList logs). Методот добива влезен аргумент логови коишто треба да ги процесира, а враќа резултат процесирани логови. Интерфејсот ви е даден и за истиот треба да ги пополните само генеричките параметри.
//
//Дадена ви е класата LogSystem во којашто се чува листа на логови. За класата да се дефинираат соодветните генерички параметри, да се имплементира конструктор LogSystem(ArrayList elements), како и да се комплетира методот printResults().
//
//Во овој метод потребно е да креирате три конкретни процесори на логови (со помош на ламбда изрази):
//
//Процесор којшто ќе ги врати само логовите коишто се од тип INFO
//Процесор којшто ќе ги врати само логовите чиишто пораки се пократки од 100 карактери
//Процесор којшто ќе ги врати логовите сортирани според временскиот печат во растечки редослед (од најстар кон најнов лог).
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

interface ILog {
    //TODO write methods definitions here;
}


interface LogProcessor??? {
ArrayList??? processLogs(ArrayList??? logs);
}

class LogSystem??? {
//TODO add instance variable(s)

//TODO constructor

void printResults() {

    //TODO define concrete log processors with lambda expressions
    LogProcessor??? firstLogProcessor = ???

    LogProcessor??? secondLogProcessor = ???

    LogProcessor??? thirdLogProcessor = ???

    System.out.println("RESULTS FROM THE FIRST LOG PROCESSOR");
    firstLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));

    System.out.println("RESULTS FROM THE SECOND LOG PROCESSOR");
    secondLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));

    System.out.println("RESULTS FROM THE THIRD LOG PROCESSOR");
    thirdLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));
}
}

class RealLog implements ILog, Comparable<RealLog> {
    String type;
    String message;
    long timestamp;
    static Random rdm = new Random();
    static int index = 100;

    public RealLog(String type, String message, long timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(RealLog o) {
        return Long.compare(this.timestamp, o.timestamp);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String newMessage) {
        this.message = newMessage;
    }


    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public static RealLog createLog(String line) {
        String[] parts = line.split("\\s+");
        String date = parts[0] + "T" + parts[1];
        LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ZoneId zoneId = ZoneId.systemDefault();
        long timestamp = ldt.atZone(zoneId).toEpochSecond() * 1000 + index;
        ++index;
        String type = parts[3];
        String message = Arrays.stream(parts).skip(4).collect(Collectors.joining(" "));
        return new RealLog(type, message, timestamp);
    }

    @Override
    public String toString() {
        return String.format("%d [%s] %s", timestamp, type, message);
    }
}

class DummyLog implements ILog, Comparable<DummyLog> {
    String type;
    String message;
    long timestamp;

    public DummyLog(String type, String message, long timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(DummyLog o) {
        return Long.compare(this.getTimestamp(), o.getTimestamp());
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String newMessage) {
        this.message = message;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public static DummyLog createLog(String line) {
        String[] parts = line.split("\\s+");
        return new DummyLog(parts[0], parts[1], Long.parseLong(parts[2]));
    }

    @Override
    public String toString() {
        return "DummyLog{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

public class LoggerTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int firstCount = Integer.parseInt(sc.nextLine());

        ArrayList<RealLog> realLogs = IntStream.range(0, firstCount)
                .mapToObj(i -> RealLog.createLog(sc.nextLine()))
                .collect(Collectors.toCollection(ArrayList::new));

        int secondCount = Integer.parseInt(sc.nextLine());

        ArrayList<DummyLog> dummyLogs = IntStream.range(0, secondCount)
                .mapToObj(i -> DummyLog.createLog(sc.nextLine()))
                .collect(Collectors.toCollection(ArrayList::new));

        LogSystem<RealLog> realLogSystem = new LogSystem<>(realLogs);
        LogSystem<DummyLog> dummyLogSystem = new LogSystem<>(dummyLogs);

        System.out.println("===REAL LOGS SYSTEM RESULTS===");
        realLogSystem.printResults();
        System.out.println("===DUMMY LOGS SYSTEM RESULTS===");
        dummyLogSystem.printResults();

    }
}


//3
//2020-11-18 02:20:20 [dag-scheduler-event-loop] INFO FairSchedulableBuilder: Added task set TaskSet_550.0 tasks to pool 8528611190673175966
//2020-11-18 02:20:20 [dispatcher-event-loop-3] INFO TaskSetManager: Starting task 0.0 in stage 550.0 (TID 4051, 10.162.242.30, executor 1, partition 0, PROCESS_LOCAL)
//2020-11-18 02:20:20 [dispatcher-event-loop-3] INFO TaskSetManager: Starting task 1.0 in stage 550.0 (TID 4052, 10.162.235.85, executor 0, partition 1, PROCESS_LOCAL)
//3
//ERROR Some_short_message-8 8878539449911250943
//ERROR Some_loooooooooooooooooooooooooong_dummy_message_that_should_be_more_than_100_charsszzzzzzzzzzzzzzzz-64 7026192678309265809
//INFO Some_short_message-13 2051624829434088144



//===REAL LOGS SYSTEM RESULTS===
//RESULTS FROM THE FIRST LOG PROCESSOR
//1605666020100 [INFO] FairSchedulableBuilder: Added task set TaskSet_550.0 tasks to pool 8528611190673175966
//1605666020101 [INFO] TaskSetManager: Starting task 0.0 in stage 550.0 (TID 4051, 10.162.242.30, executor 1, partition 0, PROCESS_LOCAL)
//1605666020102 [INFO] TaskSetManager: Starting task 1.0 in stage 550.0 (TID 4052, 10.162.235.85, executor 0, partition 1, PROCESS_LOCAL)
//RESULTS FROM THE SECOND LOG PROCESSOR
//1605666020100 [INFO] FairSchedulableBuilder: Added task set TaskSet_550.0 tasks to pool 8528611190673175966
//RESULTS FROM THE THIRD LOG PROCESSOR
//1605666020100 [INFO] FairSchedulableBuilder: Added task set TaskSet_550.0 tasks to pool 8528611190673175966
//1605666020101 [INFO] TaskSetManager: Starting task 0.0 in stage 550.0 (TID 4051, 10.162.242.30, executor 1, partition 0, PROCESS_LOCAL)
//1605666020102 [INFO] TaskSetManager: Starting task 1.0 in stage 550.0 (TID 4052, 10.162.235.85, executor 0, partition 1, PROCESS_LOCAL)
//===DUMMY LOGS SYSTEM RESULTS===
//RESULTS FROM THE FIRST LOG PROCESSOR
//DummyLog{type='INFO', message='Some_short_message-13', timestamp=2051624829434088144}
//RESULTS FROM THE SECOND LOG PROCESSOR
//DummyLog{type='ERROR', message='Some_short_message-8', timestamp=8878539449911250943}
//DummyLog{type='INFO', message='Some_short_message-13', timestamp=2051624829434088144}
//RESULTS FROM THE THIRD LOG PROCESSOR
//DummyLog{type='INFO', message='Some_short_message-13', timestamp=2051624829434088144}
//DummyLog{type='ERROR', message='Some_loooooooooooooooooooooooooong_dummy_message_that_should_be_more_than_100_charsszzzzzzzzzzzzzzzz-64', timestamp=7026192678309265809}
//DummyLog{type='ERROR', message='Some_short_message-8', timestamp=8878539449911250943}