import jdk.jfr.Category;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;





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



















































//import jdk.jfr.Category;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//class DeadlineNotValidException extends Exception {
//    public DeadlineNotValidException(String message) {
//        super(String.format("The deadline " + message + " has already passed"));
//    }
//}
//
//class Task {
//    private String category;
//    private String taskName;
//    private String description;
//    private LocalDateTime deadline;
//    private Optional<Integer> priority;
//
//    public Task(String category, String taskName, String description) {
//        this.category = category;
//        this.taskName = taskName;
//        this.description = description;
//        this.deadline = null;
//        this.priority = Optional.empty();
//
//    }
//
//    public Task(String category, String taskName, String description, LocalDateTime deadline) {
//        this.category = category;
//        this.taskName = taskName;
//        this.description = description;
//        this.deadline = deadline;
//        this.priority = Optional.empty();
//    }
//
//    public Task(String category, String taskName, String description, int priority) {
//        this.category = category;
//        this.taskName = taskName;
//        this.description = description;
//        this.deadline = null;
//        this.priority = Optional.of(priority);
//    }
//
//    public Task(String category, String taskName, String description, LocalDateTime deadline, int priority) {
//        this.category = category;
//        this.taskName = taskName;
//        this.description = description;
//        this.deadline = deadline;
//        this.priority = Optional.of(priority);
//    }
//
//
//    public String getCategory() {
//        return category;
//    }
//
//    public String getTaskName() {
//        return taskName;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public LocalDateTime getDeadline() {
//        return deadline;
//    }
//
//    public Optional<Integer> getPriority() {
//        return priority;
//    }
//
//    @Override
//    public String toString() {
//
//        if (deadline == null && priority.isEmpty()) {
//            return "Task{" + "name='" + taskName + '\'' + ", description='" + description + '\'' + '}';
//        } else if (deadline != null && priority.isEmpty()) {
//            return "Task{" + "name='" + taskName + '\'' + ", description='" + description + '\'' + ", deadline=" + deadline + '}';
//        } else if (deadline == null && !priority.isEmpty()) {
//            return "Task{" + "name='" + taskName + '\'' + ", description='" + description + '\'' + ", priority=" + priority.get() + '}';
//        } else {
//            return "Task{" + "name='" + taskName + '\'' + ", description='" + description + '\'' + ", deadline=" + deadline + ", priority=" + priority.get() + '}';
//        }
//
//    }
//}
//
//class TaskManager {
//
//    private List<Task> tasks;
//
//    public TaskManager() {
//        this.tasks = new ArrayList<>();
//    }
//
//    public TaskManager(List<Task> tasks) {
//        this.tasks = tasks;
//    }
//
//    void readTasks(InputStream inputStream) {
//
//        Scanner sc = new Scanner(inputStream);
//
//        while (sc.hasNext()) {
//            String line = sc.nextLine().trim();
//            if (line.isBlank()) {
//                continue;
//            }
//            String[] input = line.split(",");
//            try {
//                Task task;
//                if (input.length == 3) {
//                    task = new Task(input[0], input[1], input[2]);
//                } else if (input.length == 4) {
//                    if (input[3].length() == 1) {
//                        task = new Task(input[0], input[1], input[2], Integer.parseInt(input[3]));           //cetvrtiot parametar e prioritetot
//                    } else {
//                        LocalDateTime date = LocalDateTime.parse(input[3]);
//                        task = new Task(input[0], input[1], input[2], date);           //cetvrtiot parametar e local date time
//                    }
//                } else {
//                    LocalDateTime date = LocalDateTime.parse(input[3]);
//                    task = new Task(input[0], input[1], input[2], date, Integer.parseInt(input[4]));           //cetvrtiot parametar e prioritetot
//                }
//
//                LocalDateTime cutOffDate = LocalDateTime.parse("2020-06-02T00:00:00.000");
//
//                if (task.getDeadline() == null) {
//                    this.tasks.add(task);
//                } else if (!task.getDeadline().isAfter(cutOffDate)) {
//                    throw new DeadlineNotValidException(task.getDeadline().toString());
//                } else {
//                    this.tasks.add(task);
//
//                }
//            } catch (DeadlineNotValidException d) {
//                System.out.println(d.getMessage());
//            }
//        }
//    }
//
//    void printTasks(OutputStream os, boolean includePriority, boolean includeCategory) {
//
//        PrintWriter pw = new PrintWriter(os);
//
//        Comparator<Task> comparePriority = (o1, o2) -> {
//            if (!o1.getPriority().isPresent() && !o2.getPriority().isPresent()) {
//                return 0;
//            } else if (!o1.getPriority().isPresent()) {
//                return 1;
//            } else if (!o2.getPriority().isPresent()) {
//                return -1;
//            }
//
//            return Integer.compare(o1.getPriority().orElse(Integer.MAX_VALUE), o2.getPriority().orElse(Integer.MAX_VALUE));
//        };
//
//        //System.out.println("By categories with priority");
//        if (includePriority && includeCategory) {
//            HashMap<String, List<Task>> separatedTasks =
//                    this.tasks
//                            .stream()
//                            .collect(Collectors.groupingBy(
//                                            u -> u.getCategory(),
//                                            HashMap::new,
//                                            Collectors.toList()
//                                    )
//                            );
//            separatedTasks.forEach((key, value) -> {
//                value.sort(comparePriority);
//            });
//
//
//            separatedTasks.keySet()
//                    .stream()
//                    .forEach(category -> {
//                        List<Task> tasks = separatedTasks.get(category);
//                        pw.println(category.toUpperCase());
//                        tasks.forEach(t -> {
//                            pw.println(t.toString());
//                        });
//                    });
//
//
//        }
//
//        //System.out.println("By categories WITHOUT priority");
//        if (includePriority == false && includeCategory == true) {
//
//            HashMap<String, List<Task>> separatedTasks =
//                    this.tasks
//                            .stream()
//                            .collect(
//                                    Collectors.groupingBy(
//                                            u -> u.getCategory(),
//                                            HashMap::new,
//                                            Collectors.toList()
//                                    )
//                            );
//            separatedTasks.keySet().forEach(e -> {
//                List<Task> lista = separatedTasks.get(e);
//                pw.println(e.toUpperCase());
//                lista.forEach(v -> {
//                    pw.println(v.toString());
//                });
//            });
//        }
//
//        // System.out.println("All tasks without priority");
//        if (includePriority == false && includeCategory == false) {
//            this.tasks.stream()
//
//                    .forEach(t -> {
//                        pw.println(t.toString());
//                    });
//
//        }
//
//        //System.out.println("All tasks with priority");
//        if (includePriority == true && includeCategory == false) {
//            this.tasks.stream()
//                    .sorted(comparePriority)
//                    .forEach(t ->
//                    {
//                        pw.println(t.toString());
//                    });
//        }
//
//
//        pw.flush();
//    }
//
//
//}
//
//
//public class TasksManagerTest {
//
//    public static void main(String[] args) {
//
//        TaskManager manager = new TaskManager();
//
//        System.out.println("Tasks reading");
//        manager.readTasks(System.in);
//        System.out.println("By categories with priority");
//        manager.printTasks(System.out, true, true);
//        System.out.println("-------------------------");
//        System.out.println("By categories without priority");
//        manager.printTasks(System.out, false, true);
//        System.out.println("-------------------------");
//        System.out.println("All tasks without priority");
//        manager.printTasks(System.out, false, false);
//        System.out.println("-------------------------");
//        System.out.println("All tasks with priority");
//        manager.printTasks(System.out, true, false);
//        System.out.println("-------------------------");
//
//    }
//}
