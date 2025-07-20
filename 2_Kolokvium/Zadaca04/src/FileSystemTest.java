/*
 * Да се имплементира класа FileSystem за едноставен податочен систем. За вашиот податочен систем треба да имплементирате сопствена класа за датотека File со податоци за име (String), големина (Integer) и време на креирање (LocalDateTime) Класата треба да ги овозможува следните функционалности:
 *
 * public void addFile(char folder, String name, int size, LocalDateTime createdAt) - метод за додавање нова датотека File во фолдер со даденото име (името на фолдерот е еден знак, може да биде . или голема буква)
 * public List<File> findAllHiddenFilesWithSizeLessThen(int size) - враќа листа на сите скриени датотеки (тоа се датотеки чие што име започнува со знакот за точка .) со големина помала од size.
 * public int totalSizeOfFilesFromFolders(List<Character> folders) - враќа вкупна големина на сите датотеки кои се наоѓаат во фолдерите кои се зададени во листата folders
 * public Map<Integer, Set<File>> byYear() - враќа мапа Map во која за датотеките се групирани според годината на креирање.
 * public Map<String, Long> sizeByMonthAndDay() - враќа мапа Map во која за секој месец и ден (независно од годината) се пресметува вкупната големина на сите датотеки креирани во тој месец и тој ден. Месецот се добива со повик на методот getMonth(), а денот getDayOfMonth().
 * Датотеките во секој фолдер се подредени според датумот на креирање во растечки редослед, потоа според името лексикографски и на крај според големината. Да се имплементира ваков компаратор во самата класа File. Исто така да се имплементира и toString репрезентација во следниот формат:
 *
 * %-10[name] %5[size]B %[createdAt]
 * */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class File implements Comparable<File>{
    private String name;
    private Integer size;
    private LocalDateTime creationDate;

    public File() {
    }

    public File(String name, Integer size, LocalDateTime creationDate) {
        this.name = name;
        this.size = size;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }



    @Override
    public int compareTo(File other) {
        return Comparator.comparing(File::getCreationDate) // Compare by creation date
                .thenComparing(File::getName)             // Then by name lexicographically
                .thenComparing(File::getSize)             // Then by size
                .compare(this, other);
    }



    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return String.format("%-10s %5dB %s",name, size, creationDate.format(formatter));
    }

}

class FileSystem {

    private HashMap<Character, List<File>> folders;

    public FileSystem() {
        this.folders = new HashMap<>();
    }

    public FileSystem(HashMap<Character, List<File>> folders) {
        this.folders = folders;
    }

    public HashMap<Character, List<File>> getFolders() {
        return folders;
    }

    public void setFolders(HashMap<Character, List<File>> folders) {
        this.folders = folders;
    }


    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {

        if (folders.get(folder) != null) {
            List<File> listOfFiles = folders.get(folder);
            listOfFiles.add(new File(name, size, createdAt));

            //    folders.put(folder, listOfFiles); --> no need for this, the list is passed on by reference so in the line above it updates the lsit and therfore the hashmap

        } else {
            List<File> listOfFiles = new ArrayList<>();
            listOfFiles.add(new File(name, size, createdAt));
            folders.put(folder, listOfFiles);
        }
    }


    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
        return this.folders.values()
                .stream()
                .flatMap(List::stream)
                .filter(file -> file.getName().startsWith("."))
                .filter(file -> file.getSize() < size)
//                .sorted()                                               //ova sorted e problem, defaultniot sort ne e tocen,
//                                                                          no ne e specificirano kako treba da se overridne
                .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folder_names) {
        return this.folders.entrySet()
                .stream()
                .filter(entry -> folder_names.contains(entry.getKey()))
                .flatMap(entry -> entry.getValue().stream())
                .mapToInt(File::getSize)
                .sum();
    }

    public Map<Integer, Set<File>> byYear() {

        return this.folders.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                                file -> file.getCreationDate().getYear(),
                                Collectors.toSet()
                        )
                );

    }

    public Map<String, Long> sizeByMonthAndDay() {
        return this.folders.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                                file -> file.getCreationDate().getMonth() + "-" + file.getCreationDate().getDayOfMonth(),
                                Collectors.summingLong(File::getSize)
                        )
                );

    }

}


public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here

