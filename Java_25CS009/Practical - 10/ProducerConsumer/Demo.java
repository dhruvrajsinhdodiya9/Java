import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args)
            throws InterruptedException {

        Buffer b = new Buffer();

        ExecutorService executor =
                Executors.newFixedThreadPool(2);

        executor.submit(new Producer(b));
        executor.submit(new Consumer(b));

        executor.shutdown();

        System.out.println("All items processed.");
    }
}