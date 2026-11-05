public class CounterRaceDemo {

    // Shared counter class
    static class Counter {
        private int count = 0;

        // Unsynchronized: prone to lost updates
        public void incrementUnsync() {
            count++;
        }

        // Synchronized: only one thread executes this critical section at a time
        public synchronized void incrementSync() {
            count++;
        }

        public int getCount() {
            return count;
        }

        public void reset() {
            count = 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int numThreads = 10;
        final int incrementsPerThread = 10_000;
        final int expectedTotal = numThreads * incrementsPerThread; // 100,000

        Counter counter = new Counter();

        // --- TEST 1: Unsynchronized ---
        Thread[] unsyncThreads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            unsyncThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.incrementUnsync();
                }
            });
            unsyncThreads[i].start();
        }

        for (Thread t : unsyncThreads) {
            t.join();
        }

        System.out.println("=== 1. Counter Race Demo ===");
        System.out.println("Expected count: " + expectedTotal);
        System.out.println("Unsynchronized result: " + counter.getCount() 
                + " (Lost updates: " + (expectedTotal - counter.getCount()) + ")");

        // --- TEST 2: Synchronized ---
        counter.reset();
        Thread[] syncThreads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            syncThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.incrementSync();
                }
            });
            syncThreads[i].start();
        }

        for (Thread t : syncThreads) {
            t.join();
        }

        System.out.println("Synchronized result  : " + counter.getCount() + " (Exact match)");
        System.out.println();
    }
}