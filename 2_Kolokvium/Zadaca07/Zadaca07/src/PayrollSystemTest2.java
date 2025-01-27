import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;


class BonusNotAllowedException extends Exception {
    public String BonusNotAllowedException() {
        return String.format("Bonus not allowed");
    }
}

abstract class Employee implements Comparable<Employee> {
    private String id;
    private String level;
    private double pay;

    abstract void calculatePay(Map<String, Double> rate);


    //    The method should return:
    //    A negative integer if this is less than o.
    //    Zero if this is equal to o.
    //    A positive integer if this is greater than o.

    @Override
    public int compareTo(Employee o) {
        if (this.getPay() < o.getPay()) {
            return -1;
        } else if (this.getPay() == o.getPay()) {
            return 0;
        } else return 1;
    }

    public Employee() {
    }

    public Employee(String id, String level) {
        this.id = id;
        this.level = level;
        this.pay = 0;
    }

    public Employee(String id, String level, double pay) {
        this.id = id;
        this.level = level;
        this.pay = pay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }
}


class HourlyEmployee extends Employee {
    private double hoursWorked;
    private double overtimeHours;
    private double bonus;


    public HourlyEmployee(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public HourlyEmployee(String id, String level, double hoursWorked, double bonus) {
        super(id, level);
        this.hoursWorked = hoursWorked;
        this.bonus = bonus;
        overtimeHours = 0;
    }

    public HourlyEmployee(String id, String level, double hoursWorked) {
        super(id, level);
        this.hoursWorked = hoursWorked;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    void calculatePay(Map<String, Double> hourlyRateByLevel) {

        if (this.hoursWorked <= 40) {
            this.setPay(hoursWorked * hourlyRateByLevel.get(this.getLevel()));
        } else {
            this.overtimeHours = hoursWorked - 40;
            this.setPay((hoursWorked - 40) * hourlyRateByLevel.get(this.getLevel()) * 1.5 + 40 * hourlyRateByLevel.get(this.getLevel()));
        }
    }

    @Override
    public String toString() {

        double regularHours = hoursWorked;
        double overtime = 0;
        if (this.hoursWorked > 40) {
            regularHours = 40;
            overtime = this.hoursWorked - 40;
        }
        return "Employee ID: " + this.getId() + " Level: " + this.getLevel() + " Salary: " + String.format("%.2f", this.getPay()) +
                " Regular hours: " + String.format("%.2f", regularHours) + " Overtime hours: " + String.format("%.2f", overtime) + " " + String.format("%.2f", bonus);
    }
}

class FreelanceEmployee extends Employee {
    private List<Integer> tickets;
    private double bonusPercentage;

    public FreelanceEmployee(List<Integer> tickets) {
        this.tickets = tickets;
    }

    public FreelanceEmployee(String id, String level, List<Integer> tickets) {
        super(id, level);
        this.tickets = tickets;
    }

    public FreelanceEmployee(String id, String level, List<Integer> tickets, double bonusPercentage) {
        super(id, level);
        this.tickets = tickets;
        this.bonusPercentage = bonusPercentage;
    }


    public List<Integer> getTickets() {
        return tickets;
    }

    public void setTickets(List<Integer> tickets) {
        this.tickets = tickets;
    }

    @Override
    void calculatePay(Map<String, Double> ticketRateByLevel) {
        int pointsSum = 0;
        for (Integer task : this.tickets) {
            pointsSum += task;
        }
        this.setPay(ticketRateByLevel.get(this.getLevel()) * pointsSum);
    }


    @Override
    public String toString() {
        int count = this.tickets.size();
        int points = 0;
        for (Integer task : this.tickets) {
            points += task;
        }

        double bonus = this.getPay() * bonusPercentage / 100;


        return "Employee ID: " + this.getId() + " Level: " + this.getLevel() + " Salary: " + String.format("%.2f", this.getPay()) +
                " Tickets count: " + count + " Tickets points: " + points + " Bonus: " + String.format("%.2f", bonus);
    }
}


class PayrollSystem {

    //  Klasicen primer za polimorfizam, imame lista na Employee objekti,
    //  ama sekoj employee objekt mora da e kreiran so nekoja od pod-klasite shto ja implmentiraat abstraktnata klasa Employee


    private List<Employee> employees;

    private Map<String, Double> hourlyRateByLevel;
    private Map<String, Double> ticketRateByLevel;

    PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        this.employees = new ArrayList<>();

        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
    }

    Employee createEmployee(String line) throws BonusNotAllowedException {
        String[] employeeData = line.split(";");
        Employee createdEmployee = null;
        if (line.startsWith("H")) {

            int bonus = Integer.parseInt(employeeData[employeeData.length - 1].trim());
            if (bonus > 2000) {
                throw new BonusNotAllowedException();
            }

            createdEmployee = new HourlyEmployee(employeeData[1], employeeData[2], Double.parseDouble(employeeData[3]), bonus);


        } else if (line.startsWith("F")) {
            String pom = employeeData[employeeData.length - 1].trim();

            double bonusPercentage = Double.parseDouble(pom.replaceAll("%", ""));
            if (bonusPercentage > 20) {
                throw new BonusNotAllowedException();
            }

            List<Integer> tickets = new ArrayList<>();
            for (int i = 3; i < employeeData.length - 1; i++) {
                tickets.add(Integer.parseInt(employeeData[i]));
            }
            createdEmployee = new FreelanceEmployee(employeeData[1], employeeData[2], tickets, bonusPercentage);

        }
        return createdEmployee;
    }


    Map<String, Collection<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {
        return this.employees.stream()
                .filter(r -> levels.contains(r.getLevel()))
                .collect(Collectors.groupingBy(
                        Employee::getLevel,
                        TreeMap::new,
                        Collectors.toCollection(() -> new TreeSet<Employee>(Comparator.reverseOrder()))
                ));
    }

    Map<String, Double> getOvertimeSalaryForLevels() {

        this.employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getLevel,
                        TreeMap::new,
                        Collectors.summarizingDouble( ())
                )
        );
    }

    void printStatisticsForOvertimeSalary() {

    }

    Map<String, Integer> ticketsDoneByLevel() {

    }

    Collection<Employee> getFirstNEmployeesByBonus(int n) {

    }


}

public class PayrollSystemTest2 {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
        }

        Scanner sc = new Scanner(System.in);

        int employeesCount = Integer.parseInt(sc.nextLine());

        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
        Employee emp = null;
        for (int i = 0; i < employeesCount; i++) {
            try {
                emp = ps.createEmployee(sc.nextLine());
            } catch (BonusNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }

        int testCase = Integer.parseInt(sc.nextLine());

        switch (testCase) {
            case 1: //Testing createEmployee
                if (emp != null)
                    System.out.println(emp);
                break;
            case 2: //Testing getOvertimeSalaryForLevels()
                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
                });
                break;
            case 3: //Testing printStatisticsForOvertimeSalary()
                ps.printStatisticsForOvertimeSalary();
                break;
            case 4: //Testing ticketsDoneByLevel
                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
                });
                break;
            case 5: //Testing getFirstNEmployeesByBonus (int n)
                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
                break;
        }

    }
}


//  void readEmployeesData(InputStream is) {
//        Scanner sc = new Scanner(is);
//        while (sc.hasNext()) {
//            String employeeData = sc.next();
//            if (employeeData.startsWith("H")) {
//
//                String[] data = employeeData.split(";");
//                HourlyEmployee pom = new HourlyEmployee(data[1], data[2], Double.parseDouble(data[3]));
//                pom.calculatePay(hourlyRateByLevel);
//
//                //   Employee test = new Employee();    ----------------> NE FUNKCIONIRA !!! NEMOZE DA SE KREIRA OBJEKT OD ABSTRAKTNA KLASA !!!
//
//                //   Employee test = new HourlyEmployee(data...);  -----> Ova funkionira bidejki HourlyEmployee e obivna klasa koja shto ja implementira Abstraktnata klasa Employee
//
//
//                this.employees.add(pom);
//
//            } else if (employeeData.startsWith("F")) {
//                String[] data = employeeData.split(";");
//                ArrayList<Integer> tickets = new ArrayList<>();
//                for (int i = 3; i < data.length; i++) {
//                    tickets.add(Integer.parseInt(data[i]));
//                }
//                FreelanceEmployee pom = new FreelanceEmployee(data[1], data[2], tickets);
//                pom.calculatePay(this.ticketRateByLevel);
//                this.employees.add(pom);
//            }
//        }
//    }