import com.sun.source.tree.Tree;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class Faculty {
    private List<Record> records;

    public Faculty() {
        this.records = new ArrayList<>();
    }

    public Faculty(List<Record> records) {
        this.records = records;
    }

    void addRecord(String studentId, String courseName, int grade, LocalDate timestamp) {
        this.records.add(new Record(studentId, courseName, grade, timestamp));
    }

    //: Враќа мапа која го содржи просечниот успех на секој студент, земајќи ги предвид само оценките поголеми од 5.
    Map<String, Double> studentsAverageGrade() {

        records.stream().collect(Collectors.groupingBy())
        return this.records.stream()
                .filter(r -> r.getGrade() > 5)
                .collect(Collectors.groupingBy(
                                Record::getStudentId,
                                TreeMap::new,
                                Collectors.averagingInt(Record::getGrade)
                        )
                );
    }

    //: Враќа мапа која го содржи просечниот успех за секој предмет, земајќи ги предвид само оценките поголеми од 5.
    Map<String, Double> coursesAverageGrade() {
        return this.records.stream()
                .filter(r -> r.getGrade() > 5)
                .collect(Collectors.groupingBy(
                                Record::getCourseName,
                                TreeMap::new,
                                Collectors.averagingInt(Record::getGrade)
                        )
                );
    }

    // Враќа мапа со бројот на положени предмети за секој студент (оценки поголеми од 5).
    Map<String, Long> studentsPassedCoursesCount() {
        return this.records.stream()
                .filter(r -> r.getGrade() > 5)
                .collect(Collectors.groupingBy(
                                Record::getStudentId,
                                TreeMap::new,
                                Collectors.counting()
                        )
                );
    }

    // Враќа мапа со бројот на студенти кои го положиле секој предмет (оценки поголеми од 5).
    Map<String, Long> coursesPassedStudentsCount() {

        return this.records.stream()
                .filter(r -> r.getGrade() > 5)
                .collect(Collectors.groupingBy(
                                Record::getCourseName,
                                TreeMap::new,
                                Collectors.counting()
                        )
                );
    }

    // Враќа мапа каде што секој студент е поврзан со листа на предмети кои ги положил (со оценки поголеми од 5).
    Map<String, List<String>> studentsPassedCourses() {
        return this.records.stream()
                .filter(r -> r.getGrade() > 5)
                .collect(Collectors.groupingBy(
                                Record::getStudentId,
                                TreeMap::new,
                                Collectors.mapping(Record::getCourseName, Collectors.toList())
                                //ja pretvara lsitata od Records vo lista od Strings kade shto
                                // mapiranjeto se vrshi taka shto zima funkcija kako argument Record::getCourseName,
                                // i posle toa kazuvassh vo sho sakash da se pretvori strimot
                        )
                );
    }

    //: Враќа мапа во која што клучот е година-месец (пр. 2024-06) како месец за испитна сесија, а вредност е друга мапа
    // во која клуч е предметот, а вредност е просечната оценка по тој предмет во соодветната испитна сесија.
    // Во предвид се земаат само оценките поголеми од 5.
    Map<String, Map<String, Double>> averageGradePerExamSession() {

        return this.records.stream()
                .filter(r -> r.getGrade() > 5)
                .collect(Collectors.groupingBy(
                                Record::yearAndMonth,
                                TreeMap::new,
                                Collectors.groupingBy(
                                        Record::getCourseName,
                                        TreeMap::new,
                                        Collectors.averagingInt(Record::getGrade)
                                )
                        )
                );
    }

}

class Record {
    String studentId;

    String courseName;

    int grade;

    LocalDate timestamp;

    public Record(String studentId, String courseName, int grade, LocalDate timestamp) {
        this.studentId = studentId;
        this.courseName = courseName;
        this.grade = grade;
        this.timestamp = timestamp;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getGrade() {
        return grade;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String yearAndMonth() {
        return String.format("%04d-%02d", timestamp.getYear(), timestamp.getMonth().getValue());
    }
}

public class GroupByTest {
    public static void main(String[] args) {
        Faculty faculty = new Faculty();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("END")) {
                break;
            }
            String[] parts = input.split("\\s+");
            if (parts.length == 5 && parts[0].equalsIgnoreCase("addRecord")) {
                String studentId = parts[1];
                String courseName = parts[2];
                int grade = Integer.parseInt(parts[3]);
                LocalDate timestamp = LocalDate.parse(parts[4]);

                faculty.addRecord(studentId, courseName, grade, timestamp);
            }
        }

        while (true) {
            String method = scanner.nextLine().trim();
            switch (method) {
                case "studentsAverageGrade":
                    faculty.studentsAverageGrade().forEach((student, avgGrade) ->
                            System.out.printf("Student %s: %.2f%n", student, avgGrade));
                    break;

                case "coursesAverageGrade":
                    faculty.coursesAverageGrade().forEach((course, avgGrade) ->
                            System.out.printf("Course %s: %.2f%n", course, avgGrade));
                    break;

                case "studentsPassedCoursesCount":
                    faculty.studentsPassedCoursesCount().forEach((student, count) ->
                            System.out.printf("Student %s: %d courses%n", student, count));
                    break;

                case "coursesPassedStudentsCount":
                    faculty.coursesPassedStudentsCount().forEach((course, count) ->
                            System.out.printf("Course %s: %d students%n", course, count));
                    break;

                case "studentsPassedCourses":
                    faculty.studentsPassedCourses().forEach((student, courses) ->
                            System.out.printf("Student %s: %s%n", student, String.join(", ", courses)));
                    break;

                case "averageGradePerExamSession":
                    faculty.averageGradePerExamSession().forEach((session, courseGrades) -> {
                        System.out.printf("Session %s:%n", session);
                        courseGrades.forEach((course, avgGrade) ->
                                System.out.printf("  Course %s: %.2f%n", course, avgGrade));
                    });
                    break;

                case "END":
                    return;

                default:
                    System.out.println("Invalid method name. Please try again.");
            }
        }
    }
}