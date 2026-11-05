public class DeadlockFixed {
    static final Object A = new Object();
    static final Object B = new Object();

    static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (A) {
                System.out.println("Thread 1 locked A");

                synchronized (B) {
                    System.out.println("Thread 1 locked B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (A) {
                System.out.println("Thread 2 locked A");

                synchronized (B) {
                    System.out.println("Thread 2 locked B");
                }
            }
        });

        t1.start();
        t2.start();
    }
}