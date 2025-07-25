/*
Потребно е да се дефинираат два распоредувачи на задачи кои го имплеметираат интерфејсот TaskScheduler. TaskScheduler е генерички интерфејс за распоредување на задачи (Task) со еден метод schedule кој прима низа од задачи а како резултат се очекува да врати листа од истите тие задачи распоредени согласно критериумите за распоредување.

Задача (Task) е интерфејс кој имплемeнтира метод кој го дава редниот број на задачата за извршување. Постојат да вида на задачи (TimedTask и PriorityTask). Редниот број на извршување на задачата кај TimedTask е дефиниран преку времето на извршување (time), додека кај PriorityTask тој е дефиниран преку приоритетот на извршување (priority).

Првиот распоредувач ги распоредува задачите на тој начин што истите ги сортира според нивниот реден број. Неговата имплементација потребно е да биде дадена со анонимна класа (во рамки на методот getOrdered од класата Schedulers).

Вториот распоредувач го задржува редоследот на добиените задачи, но, ги филтрира (отфрла) сите задачи со поголем реден број од даден праг (order). Неговата имплементација потребно е да биде дадена со ламбда израз (во рамки на методот getFiltered од класата Schedulers).

Распоредените задачи се стартуваат со помош на генеричката класа TaskRunner. За оваа класа потребно е да го дефинирате само параметарскиот тип.
* */

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * I Partial exam 2016
 */
public class TaskSchedulerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Task[] timeTasks = new Task[n];
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            timeTasks[i] = new TimedTask(x);
        }
        n = scanner.nextInt();
        Task[] priorityTasks = new Task[n];
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            priorityTasks[i] = new PriorityTask(x);
        }
        Arrays.stream(priorityTasks).forEach(System.out::println);
        TaskRunner<Task> runner = new TaskRunner<>();
        System.out.println("=== Ordered tasks ===");
        System.out.println("Timed tasks");
        runner.run(Schedulers.getOrdered(), timeTasks);
        System.out.println("Priority tasks");
        runner.run(Schedulers.getOrdered(), priorityTasks);
        int filter = scanner.nextInt();
        System.out.printf("=== Filtered time tasks with order less then %d ===\n", filter);
        runner.run(Schedulers.getFiltered(filter), timeTasks);
        System.out.printf("=== Filtered priority tasks with order less then %d ===\n", filter);
        runner.run(Schedulers.getFiltered(filter), priorityTasks);
        scanner.close();
    }
}

class TaskRunner ??? {
public void run(TaskScheduler???? scheduler, T[] tasks) {
    List<T> order = scheduler.schedule(tasks);
    order.forEach(System.out::println);
}
}

interface TaskScheduler ???

interface Task {
    //dopolnete ovde
}

class PriorityTask implements Task {
    private final int priority;

    public PriorityTask(int priority) {
        this.priority = priority;
    }


    @Override
    public String toString() {
        return String.format("PT -> %d", getOrder());
    }
}

class TimedTask implements Task {
    private final int time;

    public TimedTask(int time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return String.format("TT -> %d", getOrder());
    }
}

class Schedulers {
    public static ??? TaskScheduler<T> getOrdered() {

        // vashiot kod ovde (annonimous class)

    }

    public static ??? TaskScheduler<T> getFiltered(int order) {

        // vashiot kod ovde (lambda expression)

    }
}

//98
//661 700 593 609 130 418 620 597 390 371 726 464 938 511 659 490 486 150 406 292 645 110 864 217 464 911 583 756 314 196 482 881 899 79 827 719 671 643 797 235 144 866 778 470 787 271 742 597 454 9 95 885 559 959 741 999 353 419 808 459 451 406 397 387 18 318 562 538 43 447 498 552 273 193 733 956 965 933 44 208 901 593 265 19 32 404 759 837 948 153 445 965 688 465 495 49 760 8
//35
//235 529 188 818 329 120 928 333 209 199 513 659 482 173 7 470 693 454 339 224 860 17 544 109 785 985 519 699 176 573 714 967 735 470 497
//750







//PT -> 235
//PT -> 529
//PT -> 188
//PT -> 818
//PT -> 329
//PT -> 120
//PT -> 928
//PT -> 333
//PT -> 209
//PT -> 199
//PT -> 513
//PT -> 659
//PT -> 482
//PT -> 173
//PT -> 7
//PT -> 470
//PT -> 693
//PT -> 454
//PT -> 339
//PT -> 224
//PT -> 860
//PT -> 17
//PT -> 544
//PT -> 109
//PT -> 785
//PT -> 985
//PT -> 519
//PT -> 699
//PT -> 176
//PT -> 573
//PT -> 714
//PT -> 967
//PT -> 735
//PT -> 470
//PT -> 497
//=== Ordered tasks ===
//Timed tasks
//TT -> 8
//TT -> 9
//TT -> 18
//TT -> 19
//TT -> 32
//TT -> 43
//TT -> 44
//TT -> 49
//TT -> 79
//TT -> 95
//TT -> 110
//TT -> 130
//TT -> 144
//TT -> 150
//TT -> 153
//TT -> 193
//TT -> 196
//TT -> 208
//TT -> 217
//TT -> 235
//TT -> 265
//TT -> 271
//TT -> 273
//TT -> 292
//TT -> 314
//TT -> 318
//TT -> 353
//TT -> 371
//TT -> 387
//TT -> 390
//TT -> 397
//TT -> 404
//TT -> 406
//TT -> 406
//TT -> 418
//TT -> 419
//TT -> 445
//TT -> 447
//TT -> 451
//TT -> 454
//TT -> 459
//TT -> 464
//TT -> 464
//TT -> 465
//TT -> 470
//TT -> 482
//TT -> 486
//TT -> 490
//TT -> 495
//TT -> 498
//TT -> 511
//TT -> 538
//TT -> 552
//TT -> 559
//TT -> 562
//TT -> 583
//TT -> 593
//TT -> 593
//TT -> 597
//TT -> 597
//TT -> 609
//TT -> 620
//TT -> 643
//TT -> 645
//TT -> 659
//TT -> 661
//TT -> 671
//TT -> 688
//TT -> 700
//TT -> 719
//TT -> 726
//TT -> 733
//TT -> 741
//TT -> 742
//TT -> 756
//TT -> 759
//TT -> 760
//TT -> 778
//TT -> 787
//TT -> 797
//TT -> 808
//TT -> 827
//TT -> 837
//TT -> 864
//TT -> 866
//TT -> 881
//TT -> 885
//TT -> 899
//TT -> 901
//TT -> 911
//TT -> 933
//TT -> 938
//TT -> 948
//TT -> 956
//TT -> 959
//TT -> 965
//TT -> 965
//TT -> 999
//Priority tasks
//PT -> 7
//PT -> 17
//PT -> 109
//PT -> 120
//PT -> 173
//PT -> 176
//PT -> 188
//PT -> 199
//PT -> 209
//PT -> 224
//PT -> 235
//PT -> 329
//PT -> 333
//PT -> 339
//PT -> 454
//PT -> 470
//PT -> 470
//PT -> 482
//PT -> 497
//PT -> 513
//PT -> 519
//PT -> 529
//PT -> 544
//PT -> 573
//PT -> 659
//PT -> 693
//PT -> 699
//PT -> 714
//PT -> 735
//PT -> 785
//PT -> 818
//PT -> 860
//PT -> 928
//PT -> 967
//PT -> 985
//=== Filtered time tasks with order less then 750 ===
//TT -> 661
//TT -> 700
//TT -> 593
//TT -> 609
//TT -> 130
//TT -> 418
//TT -> 620
//TT -> 597
//TT -> 390
//TT -> 371
//TT -> 726
//TT -> 464
//TT -> 511
//TT -> 659
//TT -> 490
//TT -> 486
//TT -> 150
//TT -> 406
//TT -> 292
//TT -> 645
//TT -> 110
//TT -> 217
//TT -> 464
//TT -> 583
//TT -> 314
//TT -> 196
//TT -> 482
//TT -> 79
//TT -> 719
//TT -> 671
//TT -> 643
//TT -> 235
//TT -> 144
//TT -> 470
//TT -> 271
//TT -> 742
//TT -> 597
//TT -> 454
//TT -> 9
//TT -> 95
//TT -> 559
//TT -> 741
//TT -> 353
//TT -> 419
//TT -> 459
//TT -> 451
//TT -> 406
//TT -> 397
//TT -> 387
//TT -> 18
//TT -> 318
//TT -> 562
//TT -> 538
//TT -> 43
//TT -> 447
//TT -> 498
//TT -> 552
//TT -> 273
//TT -> 193
//TT -> 733
//TT -> 44
//TT -> 208
//TT -> 593
//TT -> 265
//TT -> 19
//TT -> 32
//TT -> 404
//TT -> 153
//TT -> 445
//TT -> 688
//TT -> 465
//TT -> 495
//TT -> 49
//TT -> 8
//=== Filtered priority tasks with order less then 750 ===
//PT -> 235
//PT -> 529
//PT -> 188
//PT -> 329
//PT -> 120
//PT -> 333
//PT -> 209
//PT -> 199
//PT -> 513
//PT -> 659
//PT -> 482
//PT -> 173
//PT -> 7
//PT -> 470
//PT -> 693
//PT -> 454
//PT -> 339
//PT -> 224
//PT -> 17
//PT -> 544
//PT -> 109
//PT -> 519
//PT -> 699
//PT -> 176
//PT -> 573
//PT -> 714
//PT -> 735
//PT -> 470
//PT -> 497