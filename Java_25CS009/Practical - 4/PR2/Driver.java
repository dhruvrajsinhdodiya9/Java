import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] logs = {
            "10:05 alice Hello there",
            "10:07 bob Good morning",
            "malformed_log_line_here",
            "10:12 charlie say hello to everyone"
        };

        System.out.print("Enter keyword to search: ");
        String keyword = sc.nextLine();

        int matches = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {
            if (ChatFilter.filterLine(line, keyword, report)) {
                matches++;
            }
        }

        System.out.println("Matches: " + matches);
        if (matches > 0) {
            System.out.print(report.toString());
        }

        sc.close();
    }
}