import java.util.InputMismatchException;
import java.util.Scanner;

class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean success = false;
        int attempts = 0;

        while (!success) {
            attempts++;
            try {
                System.out.print("Enter first number: ");
                double num1 = sc.nextDouble();

                System.out.print("Enter operator (+, -, *, /): ");
                char op = sc.next().charAt(0);

                System.out.print("Enter second number: ");
                double num2 = sc.nextDouble();

                double result = 0;

                if (op == '+') {
                    result = num1 + num2;
                } else if (op == '-') {
                    result = num1 - num2;
                } else if (op == '*') {
                    result = num1 * num2;
                } else if (op == '/') {
                    if (num2 == 0) {
                        throw new DivideByZeroException("Error: Cannot divide by zero.");
                    }
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Invalid operator.");
                    continue;
                }

                System.out.println("Result: " + result);
                success = true;

            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid number format entered.");
                sc.nextLine();
            } catch (DivideByZeroException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Log: Finished attempt #" + attempts + "\n");
            }
        }

        sc.close();
    }
}