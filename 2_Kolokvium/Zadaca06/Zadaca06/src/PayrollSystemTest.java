import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

abstract class Employee {
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    private String employeeId;
    private String level;

    public Employee() {
    }

    public Employee(String employeeId, String level) {
        this.employeeId = employeeId;
        this.level = level;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

class HourlyEmployee extends Employee {

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "hoursWorked=" + hoursWorked +
                '}';
    }

    private double hoursWorked;

    public HourlyEmployee(String employeeId, String level, double hoursWorked) {
        super(employeeId, level);
        this.hoursWorked = hoursWorked;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}

class FreelanceEmployee extends Employee {
    @Override
    public String toString() {
        return "FreelanceEmployee{" +
                "ticketsSolved=" + ticketsSolved +
                '}';
    }

    private List<Integer> ticketsSolved;


    public FreelanceEmployee(List<Integer> ticketsSolved) {
        this.ticketsSolved = ticketsSolved;
    }

    public FreelanceEmployee(String employeeId, String level, List<Integer> ticketsSolved) {
        super(employeeId, level);
        this.ticketsSolved = ticketsSolved;
    }

    public List<Integer> getTicketsSolved() {
        return ticketsSolved;
    }

    public void setTicketsSolved(List<Integer> ticketsSolved) {
        this.ticketsSolved = ticketsSolved;
    }
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
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
    }

    void readEmployeesData(InputStream is) {
        Scanner input = new Scanner(is);

        while (input.hasNext()) {
            String employee = input.next();
            // Доколку вработениот е HourlyEmployee: H;ID;level;hours;
            if (employee.startsWith("H")) {
                String[] data = employee.split(";");
                this.hourlyEmployees.add(new HourlyEmployee(data[1], data[2], Double.parseDouble(data[3])));
            }
            // Доколку вработениот е FreelanceEmployee: F;ID;level;ticketPoints1;ticketPoints2;...;ticketPointsN;
            if (employee.startsWith("F")) {
                String[] data = employee.split(";");
                ArrayList<Integer> poeni = new ArrayList<>();
                for (int i = 3; i < data.length; i++) {
                    poeni.add(Integer.parseInt(data[i]));
                }
                this.freelanceEmployees.add(new FreelanceEmployee(data[1], data[2], poeni));
            }
        }
    }

    public Double calculatePay(Employee e) {
        double totalPay = 0.0;

        // HourlyEmployee добиваат плата базирана на вкупниот број на изработени часови
        // Платата на HourlyEmployee се пресметува така што сите часови работа до 40 часа се множат со саатницата определена за нивото,
        // а сите часови работа над 40 часа, се множат со саатницата на нивото зголемена за коефициент 1.5.


        if (e instanceof HourlyEmployee) {

            HourlyEmployee he = (HourlyEmployee) e;
            Double hoursWorked = he.getHoursWorked();
            Double rate = this.hourlyRateByLevel.get(he.getLevel());

            if (hoursWorked <= 40) {
                totalPay = rate * hoursWorked;
            } else {
                totalPay = rate * 40;
                totalPay += (hoursWorked - 40) * rate * 1.5;
            }

        } else if (e instanceof FreelanceEmployee) {
            // FreelanceEmployee добиваат плата базирана на поените на тикетите што ги решиле
            // Платата на FreelanceEmployee се пресметува така што сумата на поените на тикетите коишто програмерот ги
            // решил се множат со плата по тикет (ticket rate) за нивото.

            FreelanceEmployee fe = (FreelanceEmployee) e;
            Integer ticket_points = 0;
            for (Integer ticket : fe.getTicketsSolved()) {
                ticket_points += ticket;
            }
            totalPay = ticket_points * this.ticketRateByLevel.get(fe.getLevel());
        }

        return totalPay;
    }

    Map<String, Set<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {
        List<Employee> allEmployees = new ArrayList<>();

        allEmployees.addAll(this.freelanceEmployees);
        allEmployees.addAll(this.hourlyEmployees);

        Comparator<Employee> employeeComparator = (e1, e2) -> {
            int payComparison = calculatePay(e2).compareTo(calculatePay(e1)); // Descending pay
            if (payComparison != 0) return payComparison;

            return e1.getLevel().compareTo(e2.getLevel()); // Ascending level if pay is equal
        };

        return allEmployees.stream()
                .filter(e -> {
                    String level = e.getLevel();
                    return levels.contains(level);
                })
                .collect(Collectors.groupingBy(
                        Employee::getLevel,
                        TreeMap::new,
                        Collectors.toCollection(() -> new TreeSet<Employee>(employeeComparator)))
                );
    }

    //- метод којшто нa излезен поток ќе врати мапа од вработeните во нивоата levels групирани по нивоа.
    // Вработените да бидат сортирани според плата во опаѓачки редослед во рамките на нивото.
    // Доколку платата е иста, да се споредуваат според нивото.
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
        payrollSystem.readEmployeesData(System.in);

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