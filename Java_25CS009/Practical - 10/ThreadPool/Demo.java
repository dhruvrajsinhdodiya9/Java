import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            ThreadPoolRunner obj = new ThreadPoolRunner(i);
            executor.submit(obj);
        }

        executor.shutdown();

        System.out.println("All tasks completed.");
    }
}