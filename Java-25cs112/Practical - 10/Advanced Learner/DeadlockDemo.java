public class DeadlockDemo {
    static final Object A = new Object();
    static final Object B = new Object();

    static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (A) {
                System.out.println("Thread 1 locked A");

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Thread 1 waiting for B");

                synchronized (B) {
                    System.out.println("Thread 1 locked B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                System.out.println("Thread 2 locked B");

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Thread 2 waiting for A");

                synchronized (A) {
                    System.out.println("Thread 2 locked A");
                }
            }
        });

        t1.start();
        t2.start();
    }
}