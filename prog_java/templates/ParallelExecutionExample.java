import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyRunnable implements Runnable {
    @Override
    public void run() {
        // Your code to be executed in parallel
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}

public class ParallelExecutionExample {
    public static void main(String[] args) {
        int numberOfThreads = 5; // Adjust this according to your requirements
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.execute(new MyRunnable());
        }

        executor.shutdown();
    }
}