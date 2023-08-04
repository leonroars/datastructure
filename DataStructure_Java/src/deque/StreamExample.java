package deque;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> squaresOfEvens = nums.stream() // create a Stream<Integer>
                .filter(n -> n % 2 == 0)  // Intermediate operation: filter out even numbers
                .map(n -> n * n) // Intermediate operation: calculate square of each number
                .collect(Collectors.toList()); // Terminal operation: convert result back to list

        System.out.println(squaresOfEvens); // print the result
    }
}