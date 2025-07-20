/*
* Да се напише класа TaskManager што ќе служи за менаџирање на задачи (tasks) на даден корисник. За класата да се имплементираат методите:

readTasks (InputStream inputStream) - метод за вчитување на задачите на корисникот при што секоја задача е во следниот формат: [категорија][име_на_задача],[oпис],[рок_за_задачата],[приоритет]. Рокот за задачата и приоритетот се опционални полиња.
Не смее да се дозволи дадена задача да има рок којшто e после 2.6.2020. Во ваков случај да се фрли исклучот од тип DeadlineNotValidException. Да се фати исклучокот на соодветно место, така што нема да се попречи вчитувањето на останатите задачи!!!
void printTasks(OutputStream os, boolean includePriority, boolean includeCategory) - метод за печатење на задачите.
Доколку includeCategory e true да се испечатат задачите групирани според категории, во спротивно се печатат сите внесени задачи
Доколку includePriority e true да се испечатат задачите сортирани според приоритетот (при што 1 е највисок приоритет), a немаат приоритет или имаат ист приоритет се сортираат растечки според временското растојание помеѓу рокот и моменталниот датум, односно задачите со рок најблизок до денешниот датум се печатат први.
Доколку includePriority e false се печатат во растечки редослед според временското растојание помеѓу рокот и моменталниот датум.
При печатењето на задачите се користи default опцијата за toString (доколку работите вo IntelliJ), со тоа што треба да внимавате на името на променливите.
Бонус: Користење на шаблони за дизајн на софтвер за репрезентација на задачите и за нивното креирање.
* */

import jdk.jfr.Category;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class DeadlineNotValidException extends Exception {
    public DeadlineNotValidException(LocalDateTime message) {
        super(String.format("The deadline " + message + " has already passed"));
    }
}

class Task {
    private String category;
    private String name;
    private String description;
    private Optional<LocalDateTime> deadline;
    private Optional<Integer> priority;


    public Task(String category, String name, String description,
                Optional<LocalDateTime> deadline,
                Optional<Integer> priority) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Optional<LocalDateTime> getDeadline() {
        return deadline;
    }

    public Optional<Integer> getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        if (deadline.isEmpty() && priority.isEmpty()) {
            return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' +
                    '}';
        } else if (deadline.isEmpty()) {
            return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' +
                    ", priority=" + priority.get() +
                    '}';
        } else if (priority.isEmpty()) {
            return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' +
                    ", deadline=" + deadline.get() +
                    '}';
        } else {
            return "Task{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", deadline=" + deadline.get() +
                    ", priority=" + priority.get() +
                    '}';

        }

    }
}

class TaskManager {

    List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

    void readTasks(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);

        Task task;
        LocalDateTime limit = LocalDateTime.parse("2020-06-02T00:00:00.000");

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split(",");
            try {
                if (input.length == 5) {
                    LocalDateTime deadline = LocalDateTime.parse(input[3]);
                    if (!deadline.isAfter(limit)) {
                        throw new DeadlineNotValidException(deadline);
                    }
                    Integer priority = Integer.parseInt(input[4]);

                    task = new Task(input[0], input[1], input[2],
                            Optional.of(deadline),
                            Optional.of(priority));
                } else if (input.length == 4) {
                    if (input[3].length() > 1) {

                        LocalDateTime deadline = LocalDateTime.parse(input[3]);
                        if (!deadline.isAfter(limit)) {
                            throw new DeadlineNotValidException(deadline);
                        }
                        task = new Task(input[0], input[1], input[2],
                                Optional.of(deadline),
                                Optional.empty());
                    } else {
                        Integer priority = Integer.parseInt(input[3]);

                        task = new Task(input[0], input[1], input[2],
                                Optional.empty(),
                                Optional.of(priority));
                    }
                } else {
                    task = new Task(input[0], input[1], input[2],
                            Optional.empty(),
                            Optional.empty());
                }

                this.tasks.add(task);

            } catch (DeadlineNotValidException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    void printTasks(OutputStream os, boolean includePriority, boolean includeCategory) {
        PrintWriter pw = new PrintWriter(os);


        //  System.out.println("By categories with priority");
        if (includePriority && includeCategory) {
            Map<String, List<Task>> groupedTasks = this.tasks
                    .stream()
                    .collect(Collectors.groupingBy(
                                    Task::getCategory,
                                    Collectors.collectingAndThen(
                                            Collectors.toList(),
                                            list -> {
                                                list.sort(Comparator.comparing(t -> t.getPriority().orElse(Integer.MAX_VALUE)));
                                                return list;
                                            }
                                    )
                            )
                    );

            groupedTasks.forEach((c, t) -> {
                pw.println(c.toUpperCase());
                t.forEach(pw::println);
            });
        }
        //    System.out.println("By categories without priority");
        if (includePriority == false && includeCategory == true) {
            Map<String, List<Task>> groupedTasks = this.tasks
                    .stream()
                    .collect(Collectors.groupingBy(
                                    Task::getCategory,
                                    Collectors.toList()
                            )
                    );

            groupedTasks.forEach((c, t) -> {
                pw.println(c.toUpperCase());
                t.forEach(pw::println);
            });

        }

        Comparator<Task> compareByDeadline = (o1, o2) -> {
            if (o1.getDeadline().isPresent() && o2.getDeadline().isEmpty()) {
                return -1;
            } else if (o1.getDeadline().isEmpty() && o2.getDeadline().isPresent()) {
                return 1;
            } else if (o1.getDeadline().isEmpty() && o2.getDeadline().isEmpty()) {
                return 0;
            } else {
                return o1.getDeadline().get().compareTo(o2.getDeadline().get());
            }
        };


        //      System.out.println("All tasks without priority");
        if (includePriority == false && includeCategory == false) {

            List<Task> sortedTasks = this.tasks.stream()
                    .sorted(compareByDeadline)
                    .collect(Collectors.toList());

            sortedTasks.forEach(pw::println);
        }

        //        System.out.println("All tasks with priority");
        if (includePriority == true && includeCategory == false) {
            List<Task> sortedTasks = this.tasks
                    .stream()
                    .sorted(Comparator.comparing((Task t) -> t.getPriority().orElse(Integer.MAX_VALUE)).thenComparing(compareByDeadline))
                    .collect(Collectors.toList());

            sortedTasks.forEach(pw::println);
        }


        pw.flush();
    }


}


public class TasksManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Tasks reading");
        manager.readTasks(System.in);
        System.out.println("By categories with priority");
        manager.printTasks(System.out, true, true);
        System.out.println("-------------------------");
        System.out.println("By categories without priority");
        manager.printTasks(System.out, false, true);
        System.out.println("-------------------------");
        System.out.println("All tasks without priority");
        manager.printTasks(System.out, false, false);
        System.out.println("-------------------------");
        System.out.println("All tasks with priority");
        manager.printTasks(System.out, true, false);
        System.out.println("-------------------------");

    }
}
