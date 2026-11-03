import java.util.Scanner;

public class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    public CinemaShow(String title) {
        this(title, 100);
    }

    public boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    public void cancel(int n) {
        seatsAvailable += n;
        if (seatsAvailable > capacity) {
            seatsAvailable = capacity;
        }
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie title: ");
        String title = sc.nextLine();

        System.out.print("Enter capacity: ");
        int cap = sc.nextInt();

        CinemaShow show = new CinemaShow(title, cap);

        System.out.println("Initial seats available: " + show.getSeatsAvailable());

        System.out.print("Enter seats to book (first booking): ");
        int b1 = sc.nextInt();
        boolean res1 = show.book(b1);
        System.out.println("Booking " + b1 + " seats: " + res1 + " | Seats left: " + show.getSeatsAvailable());

        System.out.print("Enter seats to book (second booking): ");
        int b2 = sc.nextInt();
        boolean res2 = show.book(b2);
        System.out.println("Booking " + b2 + " seats: " + res2 + " | Seats left: " + show.getSeatsAvailable());

        System.out.println("Total booked: " + CinemaShow.getTotalBooked());

        sc.close();
    }
}