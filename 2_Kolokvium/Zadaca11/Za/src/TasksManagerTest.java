import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;

class DeadlineNotValidException extends Exception {
    public DeadlineNotValidException(String message) {
        super(String.format("Dealine is not valid."));
    }
}

class Task {
    private String category;
    private String taskName;
    private String description;
    private LocalDateTime deadline;
    private int priority;

    public Task(String category, String taskName, String description) {
        this.category = category;
        this.taskName = taskName;
        this.description = description;
        this.deadline = null;
        this.priority = 0;
    }

    public Task(String category, String taskName, String description, LocalDateTime deadline) {
        this.category = category;
        this.taskName = taskName;
        this.description = description;
        this.deadline = deadline;
        this.priority = 0;
    }

    public Task(String category, String taskName, String description, int priority) {
        this.category = category;
        this.taskName = taskName;
        this.description = description;
        this.deadline = null;
        this.priority = priority;
    }

    public Task(String category, String taskName, String description, LocalDateTime deadline, int priority) {
        this.category = category;
        this.taskName = taskName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }


    public String getCategory() {
        return category;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }
}

class TaskManager {

    private List<Task> tasks;

    void readTasks(InputStream inputStream) {

        Scanner sc = new Scanner(inputStream);

        while (sc.hasNext()) {
            String[] input = sc.next().split(",");
            Task task;
            if (input.length == 3) {
                task = new Task(input[0], input[1], input[2]);
            } else if (input.length == 4) {
                if (input[3].length() == 1) {
                    task = new Task(input[0], input[1], input[2], Integer.parseInt(input[3]));           //cetvrtiot parametar e prioritetot
                } else {
                    LocalDateTime date = LocalDateTime.parse(input[3]);
                    task = new Task(input[0], input[1], input[2], date);           //cetvrtiot parametar e local date time
                }
            } else {
                LocalDateTime date = LocalDateTime.parse(input[3]);
                task = new Task(input[0], input[1], input[2], date, Integer.parseInt(input[4]));           //cetvrtiot parametar e prioritetot
            }

            try {
                LocalDateTime cutOffDate = LocalDateTime.parse("2020-06-02T00:00:00.000");

                if (!task.getDeadline().isBefore(cutOffDate)) {
                    throw new DeadlineNotValidException("Kasnish mali");
                } else {
                    this.tasks.add(task);
                }
            } catch (DeadlineNotValidException d) {
                System.out.println(d.getMessage());
            }
        }
    }

    void printTasks(OutputStream os, boolean includePriority, boolean includeCategory) {
//        Доколку includeCategory e true да се испечатат задачите групирани според категории, во спротивно се печатат сите внесени задачи
//
//        Доколку includePriority e true да се испечатат задачите сортирани според приоритетот (при што 1 е највисок приоритет),
//        a немаат приоритет или имаат ист приоритет се сортираат растечки според временското растојание помеѓу рокот и моменталниот датум,
//        односно задачите со рок најблизок до денешниот датум се печатат први.
//
//        Доколку includePriority e false се печатат во растечки редослед според временското растојание помеѓу рокот и моменталниот датум.
//        При печатењето на задачите се користи default опцијата за toString (доколку работите вo IntelliJ),
//        со тоа што треба да внимавате на името на променливите.


        //System.out.println("By categories with priority");
        if (includePriority == true && includeCategory == true) {

        }

        //System.out.println("By categories WITHOUT priority");
        if (includePriority == false && includeCategory == true) {

        }

        // System.out.println("All tasks without priority");
        if (includePriority == false && includeCategory == false) {

        }

        //System.out.println("All tasks with priority");
        if (includePriority == true && includeCategory == false) {

        }


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
