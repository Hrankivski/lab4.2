import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ConcurrentHashMap<String, String> logs = new ConcurrentHashMap<>();
        long startTime = System.nanoTime();

        // Asynchronously generate the array
        CompletableFuture<Double[]> futureArray = CompletableFuture.supplyAsync(() -> {
            Double[] numbers = new Double[20];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = ThreadLocalRandom.current().nextDouble(1.0, 100.0);
            }
            String formattedArray = Arrays.stream(numbers)
                    .map(n -> String.format("%.2f", n))
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("");
            logs.put("Generated Array", "Generated array: " + formattedArray);
            return numbers;
        }, executor);

        // Calculate min and max asynchronously
        CompletableFuture<Double> minFuture = futureArray.thenApplyAsync(array -> {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < array.length; i += 2) {
                if (array[i] < min) {
                    min = array[i];
                }
            }
            logs.put("Min", "Minimum on odd positions: " + String.format("%.2f", min));
            return min;
        }, executor);

        CompletableFuture<Double> maxFuture = futureArray.thenApplyAsync(array -> {
            double max = Double.MIN_VALUE;
            for (int i = 1; i < array.length; i += 2) {
                if (array[i] > max) {
                    max = array[i];
                }
            }            logs.put("Max", "Maximum on even positions: " + String.format("%.2f", max));
            return max;
        }, executor);

        // Output results and measure time
        CompletableFuture<Void> resultFuture = minFuture.thenCombineAsync(maxFuture, (min, max) -> {
            double result = min + max;
            double duration = (System.nanoTime() - startTime) / 1000_000.0;
            logs.put("Result", "Result (Min+Max): " + String.format("%.2f", result));
            logs.put("Time", "Time taken: " + String.format("%.4f", duration) + " ms");
            return null;
        }, executor);

        // Ensure all futures are completed
        resultFuture.join();
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Output all logs in a controlled manner
        logs.forEach((key, value) -> System.out.println(value));
    }
}
