import java.util.Scanner;

public class Driver1 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Point[] p = new Point[5];

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter x and y: ");
            int x = sc.nextInt();
            int y = sc.nextInt();

            p[i] = new Point(x, y);
        }

        System.out.println("\nPoints:");
        for (Point point : p) {
            System.out.println(point);
        }

        int distinct = 0;

        for (int i = 0; i < p.length; i++) {
            boolean found = false;

            for (int j = 0; j < i; j++) {
                if (p[i].equals(p[j])) {
                    found = true;
                    break;
                }
            }

            if (!found)
                distinct++;
        }

        System.out.println("Distinct: " + distinct);

        sc.close();
    }
}