import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class File {
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
    public String toString() {

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
                .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folder_names) {
        return this.folders.keySet()
                .stream()
                .forEach(f->this.folders.get(folder_names))
                .;
    }

    public Map<Integer, Set<File>> byYear() {

    }

    public Map<String, Long> sizeByMonthAndDay() {

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

