import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        // Raw stream of user logins containing multiple repeats
        List<String> logins = Arrays.asList(
            "alice_dev", "bob_99", "charlie_k", "alice_dev",
            "diana_m", "bob_99", "evan_t", "alice_dev", "fiona_s"
        );

        System.out.println("Original Logins Stream (" + logins.size() + " total entries):");
        System.out.println(logins);
        System.out.println();

        // 1. HashSet: tracks uniqueness with O(1) lookups (no order guarantee)
        Set<String> uniqueSet = new HashSet<>(logins);

        // 2. LinkedHashSet: preserves exact insertion / first-seen order
        Set<String> firstSeenOrderSet = new LinkedHashSet<>(logins);

        // Print unique count
        System.out.println("Unique Visitors Count: " + uniqueSet.size());

        // Extract first three distinct visitors according to first-seen order
        System.out.println("\nFirst 3 Distinct Visitors (by arrival time):");
        int count = 0;
        for (String visitor : firstSeenOrderSet) {
            System.out.println((count + 1) + ". " + visitor);
            count++;
            if (count == 3) {
                break;
            }
        }

        // Complete ordered view
        System.out.println("\nAll Distinct Visitors in First-Seen Order:");
        System.out.println(firstSeenOrderSet);
    }
}