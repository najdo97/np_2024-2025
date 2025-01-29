import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

interface iEmployee {
    double calcualatePay();

    double calcualateOvertimePay();
};

enum BonusType {
    FLAT,
    PERCENT
}

class BonusNotAllowedException extends Exception {
    public BonusNotAllowedException() {
        super("Bonus not allowed");
    }
}

abstract class Employee implements Comparable<Employee>, iEmployee {

    private String id;
    private String level;
    private double rate;
    private BonusType bonusType;
    private double bonus;

    public Employee(String id, String level, double rate) {
        this.id = id;
        this.level = level;
        this.rate = rate;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public abstract double calcualatePay();

    @Override
    public double calcualateOvertimePay() {
        return 0;
    }

    @Override
    public int compareTo(Employee o) {
        return Double.compare(o.calcualatePay(), calcualatePay());
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f", id, level, calcualatePay());
    }
}

class HourlyEmployee extends Employee {
    private double hoursWorked;

    public HourlyEmployee(String id, String level, double rate, double hoursWorked) {
        super(id, level, rate);
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calcualatePay() {
        double totalPay = 0;
        if (hoursWorked > 40) {
            totalPay = 40 * this.getRate();
            totalPay += (hoursWorked - 40) * 1.5 * this.getRate();
        } else {
            totalPay = hoursWorked * this.getRate();
        }
        return totalPay;
    }

    @Override
    public double calcualateOvertimePay() {
        double overtimePay = 0;

        if (hoursWorked < 40) {
            return overtimePay;
        } else {
            overtimePay += (hoursWorked - 40) * 1.5 * this.getRate();
        }

        return overtimePay;
    }

    public double calculateBonus() {
        if (getBonusType() == BonusType.FLAT) {
            return this.getBonus();
        } else {
            return (calcualatePay() / 100) * this.getBonus();
        }
    }


    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return String.format("%s Regular hours: work on this / Overtime hours: work on this",
                super.toString()); //", df.format(getRegularHours()), df.format(getOvertimeHours())"
    }


}

class FreelanceEmployee extends Employee {

    List<Integer> tickets;

    public FreelanceEmployee(String id, String level, double rate, List<Integer> tickets) {
        super(id, level, rate);
        this.tickets = tickets;
    }

    //Платата на FreelanceEmployee се пресметува така што сумата на поените на тикетите коишто програмерот ги решил се множат со плата по тикет (ticket rate) за нивото.
    @Override
    public double calcualatePay() {
        return (tickets.stream().mapToInt(x -> x).sum()) * this.getRate();
    }

    public int sumOfTicketPoints() {
        return (tickets.stream().mapToInt(x -> x).sum());
    }

    public double calculateBonus() {
        if (getBonusType() == BonusType.FLAT) {
            return this.getBonus();
        } else {
            return (calcualatePay() / 100) * this.getBonus();
        }
    }

    @Override
    public String toString() {
        return String.format("%s Tickets count: work on this  Tickets points: work on this",
                super.toString());//, points.size(), points.stream().mapToInt(x -> x).sum());
    }


}

class PayrollSystem {

    List<Employee> employees;

    private Map<String, Double> hourlyRateByLevel;
    private Map<String, Double> ticketRateByLevel;

    PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        this.employees = new ArrayList<>();
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;

    }

    Employee createEmployee(String line) throws BonusNotAllowedException {

        String[] input = line.split(" ");
        String[] employeeData = input[0].split(";");

        if (input.length == 2) {
            if (input[1].endsWith("%")) {
                if (Double.parseDouble(input[1].substring(0, input[1].length() - 1)) > 20) {
                    throw new BonusNotAllowedException();
                }
            } else {
                if (Double.parseDouble(input[1]) > 1000) {
                    throw new BonusNotAllowedException();
                }
            }
        }
        Employee employee;
        String id = employeeData[1];
        String level = employeeData[2];

        if (employeeData[0].startsWith("H")) {
            String hoursWorked = employeeData[3];

            employee = new HourlyEmployee(id, level, this.hourlyRateByLevel.get(level), Double.parseDouble(hoursWorked));

        } else {
            List<Integer> tickets = new ArrayList<>();

            for (int i = 3; i < employeeData.length - 1; i++) {
                tickets.add(Integer.valueOf(employeeData[i]));
            }
            employee = new FreelanceEmployee(id, level, this.ticketRateByLevel.get(level), tickets);
        }

        if (input.length == 2) {
            if (input[1].endsWith("%")) {
                employee.setBonusType(BonusType.PERCENT);
                employee.setBonus(Double.parseDouble(input[1].substring(0, input[1].length() - 1)));
            } else {
                employee.setBonusType(BonusType.FLAT);
                employee.setBonus(Double.parseDouble(input[1]));
            }
        }


        return employee;
    }


    //метод којшто ќе врати мапа каде што клучот е нивото на вработениот,
    // а вредноста е вкупниот износ која што компанијата го исплатила за прекувремена работа за вработените од тоа ниво.
    Map<String, Double> getOvertimeSalaryForLevels() {
        return this.employees.stream()
                .filter(employee -> employee instanceof HourlyEmployee)
                .map(employee -> (HourlyEmployee) employee)
                .collect(Collectors.groupingBy(
                                HourlyEmployee::getLevel,
                                TreeMap::new,
                                Collectors.summingDouble(HourlyEmployee::calcualateOvertimePay)
                        )
                );
    }

    void printStatisticsForOvertimeSalary() {
        DoubleSummaryStatistics stats = this.employees.stream()
                .filter(employee -> employee instanceof HourlyEmployee)
                .map(employee -> (HourlyEmployee) employee)
                .collect(Collectors.summarizingDouble(HourlyEmployee::calcualateOvertimePay));

        System.out.print(stats.getMin());
        System.out.print(stats.getMax());
        System.out.print(stats.getSum());
        System.out.print(stats.getAverage());

    }

    //  метод којшто ќе врати мапа каде што клучот е нивото на вработените, а вредноста е бројот на поени за тикети што се сработени од вработените од соодветното ниво.
    Map<String, Integer> ticketsDoneByLevel() {
        return this.employees.stream()
                .filter(e -> e instanceof FreelanceEmployee)
                .map(e -> (FreelanceEmployee) e)
                .collect(Collectors.groupingBy(
                                FreelanceEmployee::getLevel,
                                TreeMap::new,
                                Collectors.summingInt(FreelanceEmployee::sumOfTicketPoints)
                        )
                );
    }

    //метод којшто ќе врати сортирана колекција од првите n вработени сортирани во опаѓачки редослед според бонусот којшто го добиле на платата.
    Collection<Employee> getFirstNEmployeesByBonus(int n) {
        return this.employees.stream()
                .sorted()
                .collect(Collectors.toList());
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


//class BonusNotAllowedException extends Exception {
//    public BonusNotAllowedException() {
//        super("Bonus not allowed");
//    }
//}
//
//
//abstract class Employee implements Comparable<Employee> {
//    private String id;
//    private String level;
//    private double pay;
//
//    abstract void calculatePay(Map<String, Double> rate);
//
//
//    //    The method should return:
//    //    A negative integer if this is less than o.
//    //    Zero if this is equal to o.
//    //    A positive integer if this is greater than o.
//
//    @Override
//    public int compareTo(Employee o) {
//        if (this.getPay() < o.getPay()) {
//            return -1;
//        } else if (this.getPay() == o.getPay()) {
//            return 0;
//        } else return 1;
//    }
//
//    public Employee() {
//    }
//
//    public Employee(String id, String level) {
//        this.id = id;
//        this.level = level;
//        this.pay = 0;
//    }
//
//    public Employee(String id, String level, double pay) {
//        this.id = id;
//        this.level = level;
//        this.pay = pay;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    public double getPay() {
//        return pay;
//    }
//
//    public void setPay(double pay) {
//        this.pay = pay;
//    }
//}
//
//
//class HourlyEmployee extends Employee {
//    private double hoursWorked;
//    private double overtimeHours;
//    private double bonus;
//
//
//    public HourlyEmployee(double hoursWorked) {
//        this.hoursWorked = hoursWorked;
//    }
//
//    public HourlyEmployee(String id, String level, double hoursWorked, double bonus) {
//        super(id, level);
//        this.hoursWorked = hoursWorked;
//        this.bonus = bonus;
//        overtimeHours = 0;
//    }
//
//    public HourlyEmployee(String id, String level, double hoursWorked) {
//        super(id, level);
//        this.hoursWorked = hoursWorked;
//    }
//
//    public double getHoursWorked() {
//        return hoursWorked;
//    }
//
//    public void setHoursWorked(double hoursWorked) {
//        this.hoursWorked = hoursWorked;
//    }
//
//    public double getOvertimeHours() {
//        return overtimeHours;
//    }
//
//    public void setOvertimeHours(double overtimeHours) {
//        this.overtimeHours = overtimeHours;
//    }
//
//    public double getBonus() {
//        return bonus;
//    }
//
//    public void setBonus(double bonus) {
//        this.bonus = bonus;
//    }
//
//
//    double calculateOvertimePay(Map<String, Double> hourlyRateByLevel) {
//
//        return overtimeHours * 1.5 * hourlyRateByLevel.get(this.getLevel());
////        this.setPay((hoursWorked - 40) * hourlyRateByLevel.get(this.getLevel()) * 1.5 + 40 * hourlyRateByLevel.get(this.getLevel()));
//    }
//
//
//    @Override
//    void calculatePay(Map<String, Double> hourlyRateByLevel) {
//
//        if (this.hoursWorked <= 40) {
//            this.setPay(hoursWorked * hourlyRateByLevel.get(this.getLevel()));
//        } else {
//            this.overtimeHours = hoursWorked - 40;
//            this.setPay((hoursWorked - 40) * hourlyRateByLevel.get(this.getLevel()) * 1.5 + 40 * hourlyRateByLevel.get(this.getLevel()));
//        }
//    }
//
//    @Override
//    public String toString() {
//
//        double regularHours = hoursWorked;
//        double overtime = 0;
//        if (this.hoursWorked > 40) {
//            regularHours = 40;
//            overtime = this.hoursWorked - 40;
//        }
//        return "Employee ID: " + this.getId() + " Level: " + this.getLevel() + " Salary: " + String.format("%.2f", this.getPay()) +
//                " Regular hours: " + String.format("%.2f", regularHours) + " Overtime hours: " + String.format("%.2f", overtime) + " " + String.format("%.2f", bonus);
//    }
//}
//
//class FreelanceEmployee extends Employee {
//    private List<Integer> tickets;
//    private double bonusPercentage;
//
//    public FreelanceEmployee(List<Integer> tickets) {
//        this.tickets = tickets;
//    }
//
//    public FreelanceEmployee(String id, String level, List<Integer> tickets) {
//        super(id, level);
//        this.tickets = tickets;
//    }
//
//    public FreelanceEmployee(String id, String level, List<Integer> tickets, double bonusPercentage) {
//        super(id, level);
//        this.tickets = tickets;
//        this.bonusPercentage = bonusPercentage;
//    }
//
//
//    public List<Integer> getTickets() {
//        return tickets;
//    }
//
//    public void setTickets(List<Integer> tickets) {
//        this.tickets = tickets;
//    }
//
//    int getPoints() {
//        int sum = 0;
//        for (Integer pom : this.tickets) {
//            sum += pom;
//        }
//        return sum;
//    }
//
//    @Override
//    void calculatePay(Map<String, Double> ticketRateByLevel) {
//        int pointsSum = 0;
//        for (Integer task : this.tickets) {
//            pointsSum += task;
//        }
//        this.setPay(ticketRateByLevel.get(this.getLevel()) * pointsSum);
//    }
//
//
//    @Override
//    public String toString() {
//        int count = this.tickets.size();
//        int points = 0;
//        for (Integer task : this.tickets) {
//            points += task;
//        }
//
//        double bonus = this.getPay() * bonusPercentage / 100;
//
//
//        return "Employee ID: " + this.getId() + " Level: " + this.getLevel() + " Salary: " + String.format("%.2f", this.getPay()) +
//                " Tickets count: " + count + " Tickets points: " + points + " Bonus: " + String.format("%.2f", bonus);
//    }
//}
//
//
//class PayrollSystem {
//
//    //  Klasicen primer za polimorfizam, imame lista na Employee objekti,
//    //  ama sekoj employee objekt mora da e kreiran so nekoja od pod-klasite shto ja implmentiraat abstraktnata klasa Employee
//
//
//    private List<Employee> employees;
//
//    private Map<String, Double> hourlyRateByLevel;
//    private Map<String, Double> ticketRateByLevel;
//
//    PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
//        this.employees = new ArrayList<>();
//
//        this.hourlyRateByLevel = hourlyRateByLevel;
//        this.ticketRateByLevel = ticketRateByLevel;
//    }
//
//    Employee createEmployee(String line) throws BonusNotAllowedException {
//        String[] employeeData = line.split(";");
//        String[] pomosna = employeeData[employeeData.length - 1].split(" ");
//        employeeData[employeeData.length - 1] = pomosna[0];
//        String bonus;
//        if (pomosna.length == 2) {
//            bonus = pomosna[1];
//        } else {
//            bonus = "0";
//        }
//        Employee createdEmployee = null;
//
//        if (line.startsWith("H")) {
//            if (bonus.endsWith("%")) {
//
//                double bonusPercentage = Double.parseDouble(bonus.replaceAll("%", "").trim());
//
//                if (bonusPercentage > 20) {
//                    throw new BonusNotAllowedException();
//                }
//
//                createdEmployee = new HourlyEmployee(employeeData[1], employeeData[2], Double.parseDouble(employeeData[3]), bonusPercentage);
//
//
//            } else {
//
//                int flatBonus = Integer.parseInt(bonus);
//                if (flatBonus > 2000) {
//                    throw new BonusNotAllowedException();
//                }
//                createdEmployee = new HourlyEmployee(employeeData[1], employeeData[2], Double.parseDouble(employeeData[3]), flatBonus);
//
//            }
//
//
//        } else if (line.startsWith("F")) {
//
//            if (bonus.endsWith("%")) {
//                double bonusPercentage = Double.parseDouble(bonus.replaceAll("%", "").trim());
//                if (bonusPercentage > 20) {
//                    throw new BonusNotAllowedException();
//                }
//                List<Integer> tickets = new ArrayList<>();
//                for (int i = 3; i < employeeData.length - 1; i++) {
//                    tickets.add(Integer.parseInt(employeeData[i]));
//                }
//                createdEmployee = new FreelanceEmployee(employeeData[1], employeeData[2], tickets, bonusPercentage);
//            } else {
//
//                int flatBonus = Integer.parseInt(bonus);
//                if (flatBonus > 2000) {
//                    throw new BonusNotAllowedException();
//                }
//                List<Integer> tickets = new ArrayList<>();
//                for (int i = 3; i < employeeData.length - 1; i++) {
//                    tickets.add(Integer.parseInt(employeeData[i]));
//                }
//
//                createdEmployee = new FreelanceEmployee(employeeData[1], employeeData[2], tickets, flatBonus);
//
//
//            }
//
//
//        }
//        return createdEmployee;
//    }
//
//
//    Map<String, Collection<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {
//        return this.employees.stream()
//                .filter(r -> levels.contains(r.getLevel()))
//                .collect(Collectors.groupingBy(
//                        Employee::getLevel,
//                        TreeMap::new,
//                        Collectors.toCollection(() -> new TreeSet<Employee>(Comparator.reverseOrder()))
//                ));
//    }
//
//    Map<String, Double> getOvertimeSalaryForLevels() {
//
//        List<HourlyEmployee> overtimeWorkers = this.employees
//                .stream()
//                .filter(employee -> employee instanceof HourlyEmployee)
//                .map(employee -> (HourlyEmployee) employee)
//                .filter(employee -> employee.getHoursWorked() > 40)
//                .collect(Collectors.toList());
//
//        return overtimeWorkers
//                .stream()
//                .collect(Collectors.groupingBy(
//                                Employee::getLevel,
//                                TreeMap::new,
//                                Collectors.summingDouble(e -> e.calculateOvertimePay(this.hourlyRateByLevel))
//                        )
//                );
//    }
//
//    void printStatisticsForOvertimeSalary() {
//
//        DoubleSummaryStatistics overtimeStatistics = this.employees
//                .stream()
//                .filter(employee -> employee instanceof HourlyEmployee)
//                .map(employee -> (HourlyEmployee) employee)
//                .filter(employee -> employee.getOvertimeHours() > 40)
//                .mapToDouble(employee -> employee.calculateOvertimePay(this.hourlyRateByLevel))
//                .summaryStatistics();
//
//        System.out.println("Minimum: " + overtimeStatistics.getMin());
//        System.out.println("Maximum: " + overtimeStatistics.getMax());
//        System.out.println("Sum: " + overtimeStatistics.getSum());
//        System.out.println("Average: " + overtimeStatistics.getAverage());
//    }
//
//    Map<String, Integer> ticketsDoneByLevel() {
//        return this.employees.stream()
//                .filter(employee -> employee instanceof FreelanceEmployee)
//                .map(employee -> (FreelanceEmployee) employee)
//                .collect(Collectors.groupingBy(
//                        FreelanceEmployee::getLevel,
//                        TreeMap::new,
//                        Collectors.summingInt(FreelanceEmployee::getPoints)
//                ));
//
//    }
//
//    Collection<Employee> getFirstNEmployeesByBonus(int n) {
//        return this.employees
//                .stream()
//                .sorted()
//                .limit(n)
//                .collect(Collectors.toCollection(() -> new TreeSet<Employee>(Comparator.reverseOrder())));
//    }
//
//
//}
//
//public class PayrollSystemTest2 {
//
//    public static void main(String[] args) {
//
//        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
//        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
//        for (int i = 1; i <= 10; i++) {
//            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
//            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
//        }
//
//        Scanner sc = new Scanner(System.in);
//
//        int employeesCount = Integer.parseInt(sc.nextLine());
//
//        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
//        Employee emp = null;
//        for (int i = 0; i < employeesCount; i++) {
//            try {
//                emp = ps.createEmployee(sc.nextLine());
//            } catch (BonusNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        int testCase = Integer.parseInt(sc.nextLine());
//
//        switch (testCase) {
//            case 1: //Testing createEmployee
//                if (emp != null)
//                    System.out.println(emp);
//                break;
//            case 2: //Testing getOvertimeSalaryForLevels()
//                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
//                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
//                });
//                break;
//            case 3: //Testing printStatisticsForOvertimeSalary()
//                ps.printStatisticsForOvertimeSalary();
//                break;
//            case 4: //Testing ticketsDoneByLevel
//                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
//                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
//                });
//                break;
//            case 5: //Testing getFirstNEmployeesByBonus (int n)
//                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
//                break;
//        }
//
//    }
//}