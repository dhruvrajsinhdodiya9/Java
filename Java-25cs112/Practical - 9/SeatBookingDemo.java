import java.util.concurrent.atomic.AtomicInteger;

public class SeatBookingDemo {

    // Unsynchronized booking system
    static class UnsyncBookingSystem {
        private int seatsLeft = 5;
        private final AtomicInteger successfulBookings = new AtomicInteger(0);

        public void book(String customerName) {
            // Check-then-act flaw: multiple threads read seatsLeft > 0 concurrently
            if (seatsLeft > 0) {
                try {
                    // Small sleep widens the race window to guarantee overlap
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}

                seatsLeft--;
                successfulBookings.incrementAndGet();
                System.out.println("[Unsync] " + customerName + " booked a seat. Remaining: " + seatsLeft);
            } else {
                System.out.println("[Unsync] " + customerName + " failed: Sold out.");
            }
        }

        public int getSuccessfulBookings() {
            return successfulBookings.get();
        }

        public int getSeatsLeft() {
            return seatsLeft;
        }
    }

    // Synchronized booking system
    static class SyncBookingSystem {
        private int seatsLeft = 5;
        private int successfulBookings = 0;

        // Atomically checks and decrements
        public synchronized void book(String customerName) {
            if (seatsLeft > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}

                seatsLeft--;
                successfulBookings++;
                System.out.println("[Sync]   " + customerName + " booked a seat. Remaining: " + seatsLeft);
            } else {
                System.out.println("[Sync]   " + customerName + " failed: Sold out.");
            }
        }

        public int getSuccessfulBookings() {
            return successfulBookings;
        }

        public int getSeatsLeft() {
            return seatsLeft;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int customerCount = 10;

        // --- TEST 1: Unsynchronized Booking ---
        System.out.println("=== 2. Unsynchronized Booking (Oversell) ===");
        UnsyncBookingSystem unsyncSystem = new UnsyncBookingSystem();
        Thread[] unsyncCustomers = new Thread[customerCount];

        for (int i = 0; i < customerCount; i++) {
            final String name = "User-" + (i + 1);
            unsyncCustomers[i] = new Thread(() -> unsyncSystem.book(name));
            unsyncCustomers[i].start();
        }

        for (Thread t : unsyncCustomers) {
            t.join();
        }

        System.out.println("Total Successful: " + unsyncSystem.getSuccessfulBookings() 
                + "/5 (Seats left in state: " + unsyncSystem.getSeatsLeft() + ")\n");

        // --- TEST 2: Synchronized Booking ---
        System.out.println("=== 3. Synchronized Booking (Exact Limit) ===");
        SyncBookingSystem syncSystem = new SyncBookingSystem();
        Thread[] syncCustomers = new Thread[customerCount];

        for (int i = 0; i < customerCount; i++) {
            final String name = "User-" + (i + 1);
            syncCustomers[i] = new Thread(() -> syncSystem.book(name));
            syncCustomers[i].start();
        }

        for (Thread t : syncCustomers) {
            t.join();
        }

        System.out.println("Total Successful: " + syncSystem.getSuccessfulBookings() 
                + "/5 (Seats left in state: " + syncSystem.getSeatsLeft() + ")");
    }
}