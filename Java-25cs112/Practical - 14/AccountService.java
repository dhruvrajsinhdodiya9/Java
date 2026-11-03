import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountService {
    private final List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        for (Account a : accounts) {
            if (a.getId().equalsIgnoreCase(account.getId())) {
                throw new IllegalArgumentException("Account with ID " + account.getId() + " already exists.");
            }
        }
        accounts.add(account);
    }

    public Account findById(String id) {
        for (Account a : accounts) {
            if (a.getId().equalsIgnoreCase(id)) {
                return a;
            }
        }
        throw new IllegalArgumentException("No account found with ID: " + id);
    }

    public void transfer(String fromId, String toId, double amount) {
        if (fromId.equalsIgnoreCase(toId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        Account sender = findById(fromId);
        Account receiver = findById(toId);

        sender.withdraw(amount);
        receiver.deposit(amount);
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public List<Account> getAllAccounts() {
        return Collections.unmodifiableList(accounts);
    }
}