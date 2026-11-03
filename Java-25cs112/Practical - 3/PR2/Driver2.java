import java.util.Scanner;

public class Driver2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter numerator and denominator of first fraction: ");
        int num1 = sc.nextInt();
        int den1 = sc.nextInt();

        System.out.print("Enter numerator and denominator of second fraction: ");
        int num2 = sc.nextInt();
        int den2 = sc.nextInt();

        System.out.print("Enter numerator and denominator of third fraction: ");
        int num3 = sc.nextInt();
        int den3 = sc.nextInt();

        Fraction f1 = new Fraction(num1, den1);
        Fraction f2 = new Fraction(num2, den2);
        Fraction f3 = new Fraction(num3, den3);

        System.out.println("\nReduced Fractions:");
        System.out.println("Fraction 1: " + f1);
        System.out.println("Fraction 2: " + f2);
        System.out.println("Fraction 3: " + f3);

        System.out.println("\nComparisons:");
        System.out.println("f1 equals f2: " + f1.equals(f2));
        System.out.println("f2 equals f3: " + f2.equals(f3));
        System.out.println("f1 equals f3: " + f1.equals(f3));

        sc.close();
    }
}