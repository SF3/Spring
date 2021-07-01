package demos.reactor.threading.shared;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Utils {
    public static String threadId() {
        return String.valueOf(Thread.currentThread().getId());
    }

    public static void printTabbed(String str) {
        System.out.println("\t" + str);
    }

    public static String randomValue() {
        DecimalFormat df = new DecimalFormat("00.00");
        return df.format(Math.random() * 100);
    }

    public static void tidyUp(ExecutorService... pools) {
        System.out.println("Shutting down thread pools");
        Stream.of(pools).forEach(pool -> {
            pool.shutdown();
            try {
                if (pool.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.out.println("Thread pool closed");
                }
            } catch (InterruptedException e) {
                System.err.println("Termination interrupted");
            }
        });
        System.out.println("Bye...");
    }
}
