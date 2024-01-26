import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

class Counter {
    private int val;
    private final Object lock = new Object();

    public Counter(int start) {
        this.val = start;
    }

    public int getAndIncrement() {
        synchronized (lock) {
            int currentVal = val;
            val++;
            return currentVal;
        }
    }
}

class PrimeWorker implements Runnable {
    private final Counter counter;
    private List<Integer> primes;
    private int end;

    public PrimeWorker(Counter counter, List<Integer> primes, int end) {
        this.counter = counter;
        this.primes = primes;
        this.end = end;
    }

    @Override
    public void run() {
        while (true) {
            int currentVal;
            currentVal = counter.getAndIncrement();

            if (currentVal > end) {
                break;
            }

            if (isPrime(currentVal)) {
                synchronized (primes) {
                    primes.add(currentVal);
                }
            }
        }

    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}

public class ParallelPrimeFinder {
    public static void main(String[] args) {
        int start = 1;
        int end = (int) Math.pow(10, 8);
        int numThreads = 8;
        Counter counter = new Counter(start);
        List<Integer> primes = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        // start execution time
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            PrimeWorker worker = new PrimeWorker(counter, primes, end);
            Thread thread = new Thread(worker);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // all threads complete, end execution time
        long endTime = System.currentTimeMillis();

        String fileName = "primes.txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            long sumPrimes = primes.stream().mapToLong(Long::valueOf).sum();
            Collections.sort(primes);
            List<Integer> list = primes.subList(primes.size() - 10, primes.size());

            printWriter.printf(
                    "Execution Time in : %d s\nTotal Number Primes Found: %d\nSum of All Primes: %d\nTop Ten Maximum Primes From Lowest To Highest: %s ",
                    (endTime - startTime) / 1000,
                    primes.size(), sumPrimes, list.toString());
            printWriter.close();

        } catch (IOException e) {
        }

    }
}
