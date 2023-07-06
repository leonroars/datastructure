package circularqueue;

public class FixedArrayCircularQueue {
    int front;
    int rear;
    int[] cq;
    int currentSize;
    int maxSize;

    // - Constructure()
    public FixedArrayCircularQueue(int maxsize) {
        this.maxSize = maxsize;
        this.cq = new int[maxSize];
        this.front = 0;
        this.rear = 0;
        this.currentSize = 0;
    }


    public void enqueue(int item) {
        if (!this.isFull()) {
            this.rear = (this.rear + 1) % this.maxSize;
            this.cq[this.rear] = item;
            System.out.printf("%d has been successfully enqueue-ed!\n", this.cq[this.rear]);
            this.currentSize ++;
        }
        else {
            System.out.println("Circular Queue is Full : enqueue() cannot be processed.\n");
        }
    }
    public void dequeue() {
        if (!this.isEmpty()) {
            this.front = (this.front + 1) % this.maxSize;
            System.out.printf("%d has been successfully dequeue-ed!\n", this.cq[this.front]);
            this.cq[this.front] = 0;
            this.currentSize--;
        }

    }

    public int size() {
        return this.currentSize;
    }

    public boolean isFull() {
        if (this.front == (this.rear + 1) % this.maxSize){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isEmpty() {
        if (this.front == this.rear) {
            return true;
        }
        else {
            return false;
        }
    }

    public StringBuilder display() {
        StringBuilder items = new StringBuilder();
        int indicator = this.front + 1;
        for (int i = 0; i < this.size(); i++){
            items.append(Integer.toString(this.cq[indicator % this.maxSize]));
            items.append("\n");
            indicator++;

        }
        return items;
    }

    public void peek() {
        if (!this.isEmpty()){
            int tempPeek = (this.front + 1) % this.maxSize;
            System.out.printf("%d will be dequeued next.\n", this.cq[tempPeek]);
        }
    }
    // Test in Main()
    public static void main(String[] args){
        FixedArrayCircularQueue testcq = new FixedArrayCircularQueue(8);
        testcq.enqueue(0);
        testcq.enqueue(1);
        testcq.enqueue(2);
        testcq.enqueue(3);
        testcq.enqueue(4);
        testcq.dequeue();
        testcq.dequeue();
        testcq.dequeue();
        testcq.enqueue(5);
        testcq.enqueue(6);
        testcq.enqueue(7);
        testcq.enqueue(8);
        boolean f1 = testcq.isFull();
        System.out.printf("The Circular Queue isFull() result: %s\n", f1 ? "True":"False");
        boolean e1 = testcq.isEmpty();
        System.out.printf("The Circular Queue iEmpty() result: %s\n", e1 ? "True":"False");
        testcq.dequeue();
        testcq.dequeue();
        testcq.dequeue();
        testcq.enqueue(9);
        testcq.enqueue(10);
        int a = testcq.size();
        System.out.printf("Current CQ Size: %d\n", a);

        StringBuilder whole = testcq.display();

        System.out.printf("%s", whole);
        testcq.peek();
    }
}
