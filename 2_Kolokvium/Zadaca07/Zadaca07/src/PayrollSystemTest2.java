/*
 * Да се (до)имплементира апликацијата за евиденција на работниот ангажман на вработени во една ИТ компанија. Препорака: За да немате проблем со исти имиња на класи користете посебни пакети за двете задачи, доколку сакате да ги решавате одделно.
 *
 * За таа цел да се имплементира класата PayrollSystem во која што ќе се чуваат информации за вработени во компанијата. Како и во претходната задача постојат два типа на вработени HourlyEmployee и FreelanceEmployee. Пресметката на плата за двата типа на вработени е иста како и во претходната задача. За класата PayrollSystem да се имплементираат:
 *
 * PayrollSystem(Map<String,Double> hourlyRateByLevel, Map<String,Double> ticketRateByLevel) - ист како и во претходната задача
 * Employee createEmployee (String line) - метод којшто врз основа на влезен стринг во којшто се запишани информациите за даден вработен, ќе креира и врати објект од класа Employee. Дополнително, методот ќе го смести вработениот во системот за плати. Информациите за вработениот се во истиот формат како и во првата задача, со тоа што во овој случај има можност за бонус на вработениот којшто е одделен со празно место од информациите за вработениот. Постојат два типа на бонуси којшто вработениот може да ги добие и тоа:
 * Фиксен паричен бонус (запишан како бројка). пр. H;ID;level;hours; 100 (во овој случај добива фиксен бонус на плата од 100\$)
 * Процентуален паричен бонус (запишан како број со знак процент). пр. F;ID;level;ticketPoints1;ticketPoints2;...;ticketPointsN; 10% (во овој случај добива процентуален бонус од 10% од неговата плата).
 * Во претходниот метод, со искчучок од тип BonusNotAllowedException да се спречи креирање на вработен на којшто му е доделен фиксен бонус поголем од 1000\$ или процентуален бонус поголем од 20%.
 * Map<String, Double> getOvertimeSalaryForLevels () - метод којшто ќе врати мапа каде што клучот е нивото на вработениот, а вредноста е вкупниот износ која што компанијата го исплатила за прекувремена работа за вработените од тоа ниво.
 * void printStatisticsForOvertimeSalary () - метод којшто ќе испечати статистки (минимум, максимум, сума, просек) на исплатените додатоци за прекувремена работа на сите вработени во компанијата.
 * Map<String, Integer> ticketsDoneByLevel() - метод којшто ќе врати мапа каде што клучот е нивото на вработените, а вредноста е бројот на поени за тикети што се сработени од вработените од соодветното ниво.
 * Collection<Employee> getFirstNEmployeesByBonus (int n) - метод којшто ќе врати сортирана колекција од првите n вработени сортирани во опаѓачки редослед според бонусот којшто го добиле на платата.*/

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

interface iEmployee {
    double calcualatePay();

    double calcualateBonusPay();
}

;

enum BonusType {
    FLAT,
    PERCENT
}

//todo - adjust this
class BonusNotAllowedException extends Exception {
    public BonusNotAllowedException(String n) {
        super(String.format("Bonus of %s is not allowed", n));
    }
}

abstract class Employee implements Comparable<Employee>, iEmployee {

    private String id;
    private String level;
    private double rate;
    private BonusType bonusType;
    private double bonus;


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


    public Employee(String id, String level, double rate) {
        this.id = id;
        this.level = level;
        this.rate = rate;
    }

    @Override
    public abstract double calcualatePay();

    @Override
    public double calcualateBonusPay() {
        if (getBonusType() == BonusType.FLAT) {
            return this.getBonus();
        } else {
            return (calcualatePay() / 100) * this.getBonus();
        }

    }

    @Override
    public int compareTo(Employee o) {
        return Double.compare(this.calcualatePay(), o.calcualatePay());
    }

    @Override
    public String toString() {

        return String.format("Employee ID: %s Level: %s Salary: %.2f", id, level, calcualatePay() + calcualateBonusPay());
    }
}

class HourlyEmployee extends Employee {
    private double hoursWorked;

    public HourlyEmployee(String id, String level, double rate, double hoursWorked) {
        super(id, level, rate);
        this.hoursWorked = hoursWorked;
    }

    double getRegularHours() {
        if (hoursWorked <= 40) {
            return hoursWorked;
        } else {
            return 40;
        }
    }

    double getOvertimeHours() {
        if (hoursWorked <= 40) {
            return 0;
        } else {
            return hoursWorked - 40;
        }
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

    public double calcualateOvertimePay() {
        double overtimePay = 0;

        if (hoursWorked <= 40) {
            return overtimePay;
        } else {
            overtimePay += (hoursWorked - 40.0) * 1.5 * this.getRate();
        }

        return overtimePay;
    }


    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return String.format("%s Regular hours: %s Overtime hours: %s Bonus: %.2f", super.toString(), df.format(getRegularHours()), df.format(getOvertimeHours()), this.calcualateBonusPay());
    }


}

class FreelanceEmployee extends Employee {

    List<Integer> tickets;

    public List<Integer> getTickets() {
        return tickets;
    }

    public void setTickets(List<Integer> tickets) {
        this.tickets = tickets;
    }

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


    @Override
    public String toString() {

        if (this.getBonus() != 0) {
            return String.format("%s Tickets count: %d Tickets points: %d Bonus: %.2f", super.toString(), tickets.size(), tickets.stream().mapToInt(x -> x).sum(), this.calcualateBonusPay());
        } else {
            return String.format("%s Tickets count: %d Tickets points: %d", super.toString(), tickets.size(), tickets.stream().mapToInt(x -> x).sum());
        }
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
                    throw new BonusNotAllowedException(input[1]);
                }
            } else {
                if (Double.parseDouble(input[1]) > 1000) {
                    throw new BonusNotAllowedException(input[1].concat("$"));
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

            for (int i = 3; i < employeeData.length; i++) {
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

        employees.add(employee);
        return employee;
    }


    //todo - medtodot raboti, sortiranjeto ne e vo red
    Map<String, Double> getOvertimeSalaryForLevels() {
        return this.employees.stream()
                .filter(e -> e instanceof HourlyEmployee)
                .map(e -> (HourlyEmployee) e)
                .collect(Collectors.groupingBy(
                                HourlyEmployee::getLevel,
                                //TreeMap::new,
                                Collectors.summingDouble(HourlyEmployee::calcualateOvertimePay)
                        )
                );
    }

    void printStatisticsForOvertimeSalary() {
        DoubleSummaryStatistics stats = this.employees.stream()
                .filter(e -> e instanceof HourlyEmployee)
                .map(e -> (HourlyEmployee) e)
                .collect(Collectors.summarizingDouble(HourlyEmployee::calcualateOvertimePay));

        System.out.printf("Statistics for overtime salary: Min: %.2f Average: %.2f Max: %.2f Sum: %.2f", stats.getMin(), stats.getAverage(), stats.getMax(), stats.getSum());
    }

    Map<String, Integer> ticketsDoneByLevel() {
        return this.employees.stream()
                .filter(e -> e instanceof FreelanceEmployee)
                .map(e -> (FreelanceEmployee) e)
                .collect(Collectors.groupingBy(
                                FreelanceEmployee::getLevel,
                                //TreeMap::new,
                                Collectors.summingInt(e -> e.tickets.size())
                        )
                );
    }

    Comparator<Employee> bonusComparator = new Comparator<Employee>() {
        @Override
        public int compare(Employee o1, Employee o2) {
            return Double.compare(o1.calcualateBonusPay(), o2.calcualateBonusPay());
        }
    };

    Collection<Employee> getFirstNEmployeesByBonus(int n) {
        return this.employees
                .stream()
                .sorted(bonusComparator.reversed())
                .limit(n)
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
