package queue;

import java.util.ArrayDeque;
import java.util.Queue;

public class QueueBuiltIn {
    public static void main(String[] args){
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(3);
        q.offer(2);
        System.out.printf("%d\n", q.size());
    }
}
