import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {
    String message() default "must be > 0";
}

class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends BankException {
    private final long shortfall;

    public InsufficientFundsException(long shortfall) {
        super("Insufficient funds. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    public long getShortfall() {
        return shortfall;
    }
}

class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) {
        super(message);
    }
}

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    LIST_ACCOUNTS,
    EXIT
}

interface Transactable {
    void deposit(long amount) throws InvalidAmountException;
    boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException;
}

interface InterestBearing {
    double interestRate();
    default double yearlyInterest(long balance) {
        return (balance * interestRate()) / 100.0;
    }
}

abstract class Account implements Transactable, InterestBearing, Serializable, Comparable<Account> {
    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;
    private String ownerName;

    @Positive
    private long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public synchronized long getBalance() {
        return balance;
    }

    public abstract boolean canWithdraw(long amount);

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be strictly positive");
        }
        this.balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be strictly positive");
        }
        if (!canWithdraw(amount)) {
            long shortfall = amount - this.balance;
            throw new InsufficientFundsException(shortfall > 0 ? shortfall : 0);
        }
        this.balance -= amount;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Balance: " + balance + " | Rate: " + interestRate() + "%";
    }
}

class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long minBalance;

    public SavingsAccount(String accountNumber, String ownerName, long balance, long minBalance) {
        super(accountNumber, ownerName, balance);
        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= minBalance;
    }
}

class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long overdraftLimit;

    public CurrentAccount(String accountNumber, String ownerName, long balance, long overdraftLimit) {
        super(accountNumber, ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= -overdraftLimit;
    }
}

class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;

    public FixedDepositAccount(String accountNumber, String ownerName, long balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false;
    }
}

class Validator {
    public static boolean isValidMobile(String mobile) {
        return mobile != null && mobile.matches("^[6-9][0-9]{9}$");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}

class AnnotationValidator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Positive.class)) {
                field.setAccessible(true);
                try {
                    Object val = field.get(obj);
                    if (val instanceof Long && ((Long) val) <= 0) {
                        Positive p = field.getAnnotation(Positive.class);
                        errors.add(field.getName() + " " + p.message());
                    }
                } catch (IllegalAccessException ignored) {}
            }
        }
        return errors;
    }
}

class Repository<T> {
    private final Map<String, T> storage = new HashMap<>();

    public T save(T entity) {
        try {
            Class<?> clazz = entity.getClass();
            Field idField = null;
            while (clazz != null && idField == null) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Id.class)) {
                        idField = field;
                        break;
                    }
                }
                clazz = clazz.getSuperclass();
            }

            if (idField != null) {
                idField.setAccessible(true);
                String id = (String) idField.get(entity);
                storage.put(id, entity);
            }
        } catch (IllegalAccessException ignored) {}
        return entity;
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }
}

class BankService {
    private final Repository<Account> repository;
    private int accountCounter = 1000;

    public BankService(Repository<Account> repository) {
        this.repository = repository;
    }

    public Account openAccount(String type, String ownerName, long initialBalance) throws BankException {
        if (initialBalance <= 0) {
            throw new InvalidAmountException("Initial balance must be positive");
        }
        accountCounter++;
        String accNum = "AC" + accountCounter;
        Account account;

        switch (type.toUpperCase()) {
            case "SAVINGS" -> account = new SavingsAccount(accNum, ownerName, initialBalance, 500);
            case "CURRENT" -> account = new CurrentAccount(accNum, ownerName, initialBalance, 2000);
            case "FD" -> account = new FixedDepositAccount(accNum, ownerName, initialBalance);
            default -> throw new BankException("Unknown account type");
        }

        repository.save(account);
        return account;
    }

    public void deposit(String accNum, long amount) throws BankException {
        Account acc = repository.findById(accNum);
        if (acc == null) {
            throw new BankException("Account not found: " + accNum);
        }
        acc.deposit(amount);
    }

    public void withdraw(String accNum, long amount) throws BankException {
        Account acc = repository.findById(accNum);
        if (acc == null) {
            throw new BankException("Account not found: " + accNum);
        }
        acc.withdraw(amount);
    }

    public void transfer(String fromAccNum, String toAccNum, long amount) throws BankException {
        Account from = repository.findById(fromAccNum);
        Account to = repository.findById(toAccNum);

        if (from == null || to == null) {
            throw new BankException("One or both accounts were not found");
        }

        Account firstLock = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account secondLock = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    public List<Account> listAccounts() {
        List<Account> list = repository.findAll();
        Collections.sort(list);
        return list;
    }
}

public class MiniBankApp {
    public static void main(String[] args) {
        BankInfo info = new BankInfo("MiniBank National", "Main Campus Branch");
        System.out.println("=========================================");
        System.out.println("   Welcome to " + info.name());
        System.out.println("   Branch: " + info.branch());
        System.out.println("=========================================");

        Repository<Account> repo = new Repository<>();
        BankService service = new BankService(repo);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. List Accounts");
            System.out.println("6. Exit");
            System.out.print("Select an option (1-6): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.LIST_ACCOUNTS;
                case 6 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid selection. Try again.");
                continue;
            }

            try {
                switch (option) {
                    case OPEN_ACCOUNT -> {
                        System.out.print("Enter owner name: ");
                        String name = scanner.nextLine();
                        System.out.print("Account type (SAVINGS / CURRENT / FD): ");
                        String type = scanner.nextLine();
                        System.out.print("Initial balance: ");
                        long bal = scanner.nextLong();
                        scanner.nextLine();
                        Account acc = service.openAccount(type, name, bal);
                        System.out.println("Created successfully: " + acc);
                    }
                    case DEPOSIT -> {
                        System.out.print("Enter account number: ");
                        String accNum = scanner.nextLine();
                        System.out.print("Enter amount: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();
                        service.deposit(accNum, amount);
                        System.out.println("Deposit completed.");
                    }
                    case WITHDRAW -> {
                        System.out.print("Enter account number: ");
                        String accNum = scanner.nextLine();
                        System.out.print("Enter amount: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();
                        service.withdraw(accNum, amount);
                        System.out.println("Withdrawal completed.");
                    }
                    case TRANSFER -> {
                        System.out.print("From account: ");
                        String from = scanner.nextLine();
                        System.out.print("To account: ");
                        String to = scanner.nextLine();
                        System.out.print("Amount: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();
                        service.transfer(from, to, amount);
                        System.out.println("Transfer completed.");
                    }
                    case LIST_ACCOUNTS -> {
                        System.out.println("\nAll Accounts:");
                        for (Account a : service.listAccounts()) {
                            System.out.println(a);
                        }
                    }
                    case EXIT -> {
                        System.out.println("Thank you for using MiniBank. Goodbye!");
                        running = false;
                    }
                }
            } catch (BankException e) {
                System.out.println("Operation Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}