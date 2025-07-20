/*Да се имплементира апликација за евидентирање на оценките на студентите на еден факултет. Студентите на факултетот може да бидат запишани на тригодишни или четиригодишни студии. Во текот на студиите, студентите имаат два семестри во секоја година и во секој од семестрите имаат по најмногу 3 предмети. За таа цел, дефинирајте класа Faculty во која што ќе чувате информации за студентите и нивните оценки во сите семестри. За класата да се имплементираат:

Default конструктор Faculty()
метод void addStudent(String id, int yearsOfStudies)- за додавање на студент на факултетот со индекс ID и години на студии yearsOfStudies.
метод void addGradeToStudent(String studentId, int term, String courseName, int grade) - за додавање на оценка grade по предметот courseName на студентот со индекс studentId во семестар term.
Со помош на исклучок од тип OperationNotAllowedException да се спречи додавање на повеќе од 3 оценки по семестар. Во таков случај да се испечати порака од формат Student [studentID] already has 3 grades in term [term]. Со истиот тип на исклучок да се спречи додавање на оценка во семестар поголем од 6 за тригодишни студии односно во семестар поголем од 8 за четиригодишни студии. Во овој случај да се испечати порака Term [term] is not possible for student with ID [studentId].
Да се детектира дипломирање на студентот. Студентот дипломира тогаш кога ќе положи 18 или 24 предмети во зависност од тоа колку години студира. Во моментот на дипломирање на студентот истиот треба да се избрише од евиденцијата и да се зачува лог за него во формат Student with ID [studentID] graduated with average grade [averageGrade] in [yearsOfStudies] years.
Метод String getFacultyLogs () - што ќе ги врати логовите за дипломираните студенти.
Метод String getDetailedReportForStudent (String id) - метод што ќе врати детален извештај студентот со индекс id. Пристапот до студентот со индекс ИД да има комплексност О(1)! Деталниот извештај е во формат:
Student: [id]
Term 1:
Courses for term: [count]
Average grade for term: [average]
…
…..
Term n:
Courses: [count]
Average grade for term: [average]
Average grade: [average grade for student]
Courses attended: [all_attended_courses, comma-separated, сортирани лексикографски]
Метод void printFirstNStudents (int n) - метод којшто ќе ги испечати краток извештај за најдобрите n студенти (според бројот на положени предмети, а доколку е ист бројот на положени предмети според просечната оценка ), сортирани во опаѓачки редослед. Краткиот извештај е во формат Student: [id] Courses passed: [coursesPassed] Average grade: [averageGrade].
Метод void printCourses () - метод којшто ќе ги испечати сите предмети во формат [course_name] [count_of_students] [average_grade] на факултетот сортирани според бројот на слушатели на предметот, а доколку е ист според просечната оценка.
Забрането е сортирање со функции, односно сортирањето да биде имплементирано со соодветни колекции!
*/


//package mk.ukim.finki.vtor_kolokvium;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class Faculty {


    public Faculty() {

    }

    void addStudent(String id, int yearsOfStudies) {
    }

    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {

    }

    String getFacultyLogs() {
        return "";
    }

    String getDetailedReportForStudent(String id) {
        return "";
    }

    void printFirstNStudents(int n) {

    }

    void printCourses() {

    }
}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase==10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase==11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i=11;i<15;i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}


//input:
//5

//Output:
//Testing addGrade and printFirstNStudents (not graduated student)
//Student: student4 Courses passed: 18 Average grade: 10.00
//Student: student8 Courses passed: 18 Average grade: 9.00
//Student: student2 Courses passed: 18 Average grade: 8.00
//Student: student6 Courses passed: 18 Average grade: 7.00
//Student: student10 Courses passed: 18 Average grade: 6.00
//Student: student9 Courses passed: 13 Average grade: 10.00
//Student: student3 Courses passed: 13 Average grade: 9.00
//Student: student7 Courses passed: 13 Average grade: 8.00
//Student: student1 Courses passed: 13 Average grade: 7.00
//Student: student5 Courses passed: 13 Average grade: 6.00