package queue;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

//Test code for built-in Queue interface demonstration.
public class QueueBuiltIn {
    public static void main(String[] args){

        /** As Queue is interface, it has to be implemented through what referred to as implementing class.
         *  * ArrayDeque, LinkedList(class) -- implements --> Deque(interface) --- extends(Inheritance) --> Queue(interface)
         *  Thus, instantiation of built-in Queue interface requires either ArrayDeque or LinkedList object.
         */

        // Instantiate
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<String> q2 = new LinkedList<>();

        // q1<Integer> Test code
        q1.offer(3);
        q1.offer(2);
        System.out.printf("q1 size: %d\n", q1.size());

        for(int i : q1){
            System.out.printf("q1 item: %d\n", i);
        }

        // q2<String> Test code
        q2.offer("This\n");
        q2.offer("is\n");
        q2.offer("Linked-List version\n");
        q2.offer("Q!\n");

        for(String s : q2){
            System.out.printf("q2 item: %s\n", s);
        }

        System.out.printf("q2 size: %d\n", q2.size());
    }
}
