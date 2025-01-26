import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

abstract class Employee {
    private String employeeId;
    private String level;

    public Employee() {
    }

    public Employee(String employeeId, String level) {
        this.employeeId = employeeId;
        this.level = level;
    }

}

class HourlyEmployee extends Employee {
    // HourlyEmployee добиваат плата базирана на вкупниот број на изработени часови

    //Платата на HourlyEmployee се пресметува така што сите часови работа до 40 часа се множат со саатницата определена за нивото,
    // а сите часови работа над 40 часа, се множат со саатницата на нивото зголемена за коефициент 1.5.

    private int hoursWorked;

    public HourlyEmployee(String employeeId, String level, int hoursWorked) {
        super(employeeId, level);
        this.hoursWorked = hoursWorked;
    }


}

class FreelanceEmployee extends Employee {
    // FreelanceEmployee добиваат плата базирана на поените на тикетите што ги решиле

    // Платата на FreelanceEmployee се пресметува така што сумата на поените на тикетите коишто програмерот ги
    // решил се множат со плата по тикет (ticket rate) за нивото.


}

class PayrollSystem {

    private List<HourlyEmployee> hourlyEmployees;
    private List<FreelanceEmployee> freelanceEmployees;

    private Map<String, Double> hourlyRateByLevel;
    private Map<String, Double> ticketRateByLevel;


    public PayrollSystem() {
        this.hourlyEmployees = new ArrayList<>();
        this.freelanceEmployees = new ArrayList<>();
        this.hourlyRateByLevel = new HashMap<>();
        this.ticketRateByLevel = new HashMap<>();
    }

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        //  конструктор со два аргументи - мапи.
        //  Првата мапа означува колку е саатницата за соодветно ниво за вработените што земаат плата по час работа,
        //  а втората мапа означува колку е платата по поен од тикет за соодветното ниво за фриленсерите.
        this.hourlyEmployees = new ArrayList<>();
        this.freelanceEmployees = new ArrayList<>();

        

    }

    void readEmployeesData(InputStream is) {
        //метод за вчитување на податоците за вработените во компанијата,
        // при што за секој вработен податоците се дадени во нов ред.
        // Податоците за вработените се во следниот формат:

        // Доколку вработениот е HourlyEmployee: H;ID;level;hours;
        // Доколку вработениот е FreelanceEmployee: F;ID;level;ticketPoints1;ticketPoints2;...;ticketPointsN;

    }

    Map<String, Collection<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {
        //- метод којшто нa излезен поток ќе врати мапа од вработeните во нивоата levels групирани по нивоа.
        // Вработените да бидат сортирани според плата во опаѓачки редослед во рамките на нивото.
        // Доколку платата е иста, да се споредуваат според нивото.
    }


}

public class PayrollSystemTest {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
        }

        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);

        System.out.println("READING OF THE EMPLOYEES DATA");
        payrollSystem.readEmployees(System.in);

        System.out.println("PRINTING EMPLOYEES BY LEVEL");
        Set<String> levels = new LinkedHashSet<>();
        for (int i = 5; i <= 10; i++) {
            levels.add("level" + i);
        }
        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
        result.forEach((level, employees) -> {
            System.out.println("LEVEL: " + level);
            System.out.println("Employees: ");
            employees.forEach(System.out::println);
        });


    }
}