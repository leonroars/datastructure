package deque;

import java.util.*;

public class SpliteratorExample {
        public static void main(String[] args) {
            // Create a list
            List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

            // Obtain a Spliterator
            Spliterator<Integer> sp1 = list.spliterator();

            // Use trySplit() to split the original into two spliterators
            Spliterator<Integer> sp2 = sp1.trySplit();

            //Check each spliterator's estimateSize()
            System.out.printf("sp1_estimateSize() : %d\n", sp1.estimateSize());
            System.out.printf("sp2_estimateSize() : %d\n", sp2.estimateSize());

            System.out.println("Output from sp1(Original Spliterator)");
            sp1.forEachRemaining(System.out :: println);

            // Any remaining elements after the split
            System.out.println("\nOutput from spliterator2(Newly created Spliterator):");
            sp2.forEachRemaining(System.out::println);

            // Checking remaining size. As spliterator 'consumes' the elements, They should return `0`
            System.out.printf("sp1_estimateSize() : %d\n", sp1.estimateSize()); // Returns 0
            System.out.printf("sp2_estimateSize() : %d\n", sp2.estimateSize()); // Returns 0

            /*
            "characteristics() of Spliterator"
            characteristics()
                - Methods that holding pre-defined `characteristics` of spliterator & its source collection as `integer value`.
                - And it returns result of bit-wise OR operation - which will include all characteristics this spliterator/source has - as `integer`.
                    - This is quite understandable if you know that bit-wise OR is actually an `union` operation in set theory.
                    - As a result, returned integer would be able to hold information about `what kind of characteristics does this spliterator & source has?'.

                Thus, characteristics() can be understood as methods that behave as `flag verifier`.
                Why flag? Because the way the work(actual data and operation using them) is based on concept of `flag`.

                - In Spliterator interface, there are 8-Keywords that each named corresponding characteristics it is standing for and  holding integer value correspondingly.
                  And meaning of having each of them as characteristic is as following:
                    1) IMMUTABLE : The source collection of this spliterator cannot be structurally modified(Immutable). _ Value: 100 0000 0000
                    2) NONNULL : The source of collection guarantees non-null element for all of its elements.           _ Value:   1 0000 0000
                    3) DISTINCT : For all the elements of source collection, !x.equals(y) for all pair of elements x, y. _ Value:             1
                    4) SORTED : All elements are ordered in defined sorting order.                                       _ Value:           100
                    5) ORDERED : Encountered order of elements is defined.                                               _ Value:        1 0000
                    6) CONCURRENT : Elements can be safely concurrently-modified(insert, delete etc.) by multiple thread without a help of external synchronization. _ Value: 1 0000 0000 0000
                    7) SIZED : Returned value by estimateSize() before splitting & traversal represents exact number of elements that would be encountered during traversal. Only if there were no modification in source. _ Value: 100 0000
                    8) SUBSIZED : All spliterators that resulted from trySplit() are SIZED. _ Value: 100 0000 0000 0000



             */

            System.out.printf("IMMUTABLE _ Binary : %s\n", Integer.toBinaryString(sp1.IMMUTABLE));
            System.out.printf("NONNULL _ Binary : %s\n", Integer.toBinaryString(sp1.NONNULL));
            System.out.printf("CONCURRENT _ Binary : %s\n", Integer.toBinaryString(sp1.CONCURRENT));
            System.out.printf("ORDERED _ Binary : %s\n", Integer.toBinaryString(sp1.ORDERED));
            System.out.printf("SORTED _ Binary : %s\n", Integer.toBinaryString(sp1.SORTED));
            System.out.printf("DISTINCT _ Binary : %s\n", Integer.toBinaryString(sp1.DISTINCT));
            System.out.printf("SIZED _ Binary : %s\n", Integer.toBinaryString(sp1.SIZED));
            System.out.printf("SUBSIZED _ Binary : %s\n", Integer.toBinaryString(sp1.SUBSIZED));

            System.out.println("\n");

            int charac1 = sp1.characteristics();
            String charac1Binary = Integer.toBinaryString(charac1);
            System.out.printf("charac1 : %d\n", charac1);
            System.out.printf("charac1_Binary : %s\n", charac1Binary);

            int charac2 = sp2.characteristics();
            String charac2Binary = Integer.toBinaryString(charac2);
            System.out.printf("charac2 : %d\n", charac2);
            System.out.printf("charac2_Binary : %s\n", charac2Binary);

            /* "Verifying if current spliterator has certain `characteristic`"
                Source: List
                Characteristics of it: SIZED, ORDERED

                Thus, & operation with only 'ORDERED, SIZED' should return value that is not 0.
             */
            System.out.printf("spliterator1's characteristic is IMMUTABLE? : %d\n", charac1 & sp1.IMMUTABLE); // Returns 0: This spliterator and its source do not have 'IMMUTABLE'
            System.out.printf("spliterator's characteristic is NONNULL? : %d\n", charac2 & sp2.NONNULL); // Returns 0: This spliterator and its source do not have 'NONNULL'
            System.out.printf("spliterator's characteristic has ORDEREd? : %d\n", charac1 & sp1.ORDERED); // Returns result != 0. This means it has ORDERED.
        }
    }