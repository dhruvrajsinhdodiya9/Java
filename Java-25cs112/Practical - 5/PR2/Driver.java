import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter FullTime employee name: ");
        String fullTimeName = sc.nextLine();

        System.out.print("Enter FullTime employee ID: ");
        int fullTimeId = sc.nextInt();

        System.out.print("Enter fixed salary: ");
        double fixedSalary = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter PartTime employee name: ");
        String partTimeName = sc.nextLine();

        System.out.print("Enter PartTime employee ID: ");
        int partTimeId = sc.nextInt();

        System.out.print("Enter working hours: ");
        double hours = sc.nextDouble();

        System.out.print("Enter hourly rate: ");
        double rate = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Intern name: ");
        String internName = sc.nextLine();

        System.out.print("Enter Intern ID: ");
        int internId = sc.nextInt();

        System.out.print("Enter stipend: ");
        double stipend = sc.nextDouble();

        Employee[] employees = {
                new FullTime(fullTimeName, fullTimeId, fixedSalary),
                new PartTime(partTimeName, partTimeId, hours, rate),
                new Intern(internName, internId, stipend)
        };

        double total = 0;

        for (Employee employee : employees) {

            double salary = employee.monthlySalary();

            System.out.println(
                    employee.name + " (ID: " + employee.id + ") Salary: " + salary
            );

            if (employee instanceof Intern) {
                System.out.println("Note: This employee is an intern.");
            }

            total += salary;
        }

        System.out.println("Total Monthly Salary: " + total);

        sc.close();
    }
}