import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        AccountService service = new AccountService();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Bank Management Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.next();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter ID: ");
                        String id = sc.next();
                        System.out.print("Enter Owner Name: ");
                        String name = sc.next();
                        System.out.print("Enter Initial Deposit: ");
                        double initialDeposit = sc.nextDouble();
                        service.addAccount(new Account(id, name, initialDeposit));
                        System.out.println("Account created successfully. Total accounts: " + service.getAccountCount());
                        break;

                    case "2":
                        System.out.println("\n--- Registered Accounts ---");
                        if (service.getAccountCount() == 0) {
                            System.out.println("No accounts found.");
                        } else {
                            for (Account a : service.getAllAccounts()) {
                                System.out.println(a);
                            }
                        }
                        break;

                    case "3":
                        System.out.print("Enter Account ID: ");
                        String depId = sc.next();
                        System.out.print("Enter Deposit Amount: ");
                        double depAmount = sc.nextDouble();
                        service.findById(depId).deposit(depAmount);
                        System.out.println("Deposit successful.");
                        break;

                    case "4":
                        System.out.print("From Account ID: ");
                        String fromId = sc.next();
                        System.out.print("To Account ID: ");
                        String toId = sc.next();
                        System.out.print("Transfer Amount: ");
                        double amount = sc.nextDouble();
                        service.transfer(fromId, toId, amount);
                        System.out.println("Transfer completed successfully.");
                        break;

                    case "5":
                        running = false;
                        System.out.println("Exiting application.");
                        break;

                    default:
                        System.out.println("Invalid selection. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}