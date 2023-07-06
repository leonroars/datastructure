package queue;

import java.util.Arrays;

public class QueueSimple {
    final int front = 0;
    int rear;
    int[] queue;
    int size;

    public QueueSimple(int size) {
        this.size = size;
        this.rear = 0;
        this.queue = new int[size];
    }

    public void enqueue(int x) {
        this.queue[this.rear] = x;
        this.rear ++;
    }
    public boolean isEmpty() {
        return this.front == this.rear;
    }
    public int size() {
        if(!this.isEmpty()){
            return this.rear - this.front + 1;
        }else {
            return 0;
        }
    }
    public void dequeue() {
        if(!this.isEmpty()){
            System.out.printf("%d has popped!: \n", this.queue[this.rear]);
            this.queue[this.rear] = 0;
            this.rear --;
        }else{
            System.out.println("Queue is empty");
        }
    }
    public int peek() {
        return this.queue[this.rear];
    }
    public static void main(String[] args) {
        QueueSimple q = new QueueSimple(6);
        q.enqueue(1);
        q.enqueue(12);
        q.enqueue(3);
        q.enqueue(8);
        q.enqueue(9);

        System.out.println("Present Q Status: " + Arrays.toString(q.queue));

        q.dequeue();
        q.dequeue();
        int pk = q.peek();
        System.out.printf("%d = peek\n", pk);
        int sz = q.size();
        System.out.printf("%d = size\n", sz);
        boolean empt = q.isEmpty();
        System.out.printf("%b = isempty\n", empt);

    }
}

