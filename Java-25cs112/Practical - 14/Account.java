public class Account {
    private final String id;
    private final String owner;
    private double balance;

    public Account(String id, String owner, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.id = id;
        this.owner = owner;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be strictly positive.");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be strictly positive.");
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient balance.");
        }
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Balance: $%.2f", id, owner, balance);
    }
}