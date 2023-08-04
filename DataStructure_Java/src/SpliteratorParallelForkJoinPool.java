import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class SpliteratorParallelForkJoinPool {
    public static void main(String[] args) {
        // Create a list with 100 integers.
        List<Integer> list = new ArrayList<>();
        for (int i = 2; i <= 100; i++) {
            list.add(i);
        }

        // Create a Spliterator.
        Spliterator<Integer> spliterator = list.spliterator();

        // Split the original spliterator. Now, spliterator1 covers the first half
        // of the list, and spliterator covers the second half.
        Spliterator<Integer> spliterator1 = spliterator.trySplit();

        // Create two tasks for counting primes in the two halves.
        CountPrimesTask task1 = new CountPrimesTask(spliterator1);
        CountPrimesTask task2 = new CountPrimesTask(spliterator);

        // Use a ForkJoinPool to execute the tasks in parallel.
        ForkJoinPool pool = new ForkJoinPool();
        long count1 = pool.invoke(task1);
        long count2 = pool.invoke(task2);

        // Output the results.
        System.out.println("Primes found in first half: " + count1);
        System.out.println("Primes found in second half: " + count2);
        System.out.println("Total primes found: " + (count1 + count2));
    }

    public static boolean isPrime(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    static class CountPrimesTask extends RecursiveTask<Long> {
        private final Spliterator<Integer> spliterator;

        CountPrimesTask(Spliterator<Integer> spliterator) {
            this.spliterator = spliterator;
        }

        @Override
        protected Long compute() {
            AtomicLong count = new AtomicLong();
            while (spliterator.tryAdvance((Integer number) -> {
                if (isPrime(number)) {
                    count.getAndIncrement();
                }
            })) {}
            return count.get();
        }
    }
}
