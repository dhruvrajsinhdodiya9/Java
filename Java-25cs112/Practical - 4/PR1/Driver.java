import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter how many passwords to test: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter password: ");
            String pw = sc.nextLine();

            System.out.println("Rules Passed:");
            System.out.println(" - Length >= 8: " + PasswordChecker.hasLength(pw));
            System.out.println(" - Uppercase letter: " + PasswordChecker.hasUpper(pw));
            System.out.println(" - Digit: " + PasswordChecker.hasDigit(pw));
            System.out.println(" - Special character: " + PasswordChecker.hasSpecial(pw));

            System.out.println("Result: \"" + pw + "\" -> " + PasswordChecker.strength(pw));
        }

        sc.close();
    }
}