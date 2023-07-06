package queue;
import java.sql.SQLOutput;
import java.util.Iterator;
import stack.FixedArrayStack.ReverseArrayIterator;

public class FixedArrayQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int capacity;
    private int rearPointer;

    @SuppressWarnings("unchecked")
    public FixedArrayQueue(int cap){
        this.capacity = cap;
        this.q = (Item[]) new Object[this.capacity];
        this.rearPointer = 0;
    }

    public void enqueue(Item item){
        if(this.size() < this.capacity){
            this.q[this.rearPointer] = item;
            System.out.printf("Enqueue has been successfully processed!: %s\n", this.q[this.rearPointer]); // For well corresponded check for result of enqueue(), I chose to print actual item in the position where enqueued item has to be placed.
            this.rearPointer++;
        } else { System.out.println("Enqueue cannot be processed as q is already full\n");}
    }

    public Item dequeue() {
        if(this.size() > 0){
            Item dqed = this.q[0];
            System.out.printf("Dequeue has been successfully processed!: %s\n", dqed);

            // Re-allocating rest of items. At this point, dequeue() has approximately O(n) time complexity.
            // To make it faster, I could think of using two-pointe each pointing front and rear. But that would lead to waste of given capacity as it has to replace dequeued item to null.
            // In this perspective, Circular Queue can be a solution.
            for(int i=0;i<this.rearPointer;i++){
                this.q[i] = this.q[i+1];
            }
            this.rearPointer--;
            return dqed;
        } else {
            System.out.println("Dequeue cannot be processed. Q is already empty!\n");
            return null;
        }
    }

    public int size(){
        return this.rearPointer;
    }

    public boolean isEmpty(){
        System.out.printf("Is Q empty?: %b\n", (this.rearPointer == 0));
        return this.rearPointer == 0;
    }

    public Item peek() {
        System.out.printf("Scheduled to be next dequeued item: %s\n", this.q[0]);
        return this.q[0];
    }

    public Iterator<Item> iterator() {
        return new ForwardArrayIterator(this.rearPointer, this.q);
    }
    public class ForwardArrayIterator implements Iterator<Item>{
        int frontP;
        int rearP;
        Item[] qReceiver;

        public ForwardArrayIterator(int rearPointer, Item[] q){
            this.frontP = 0;
            this.rearP = rearPointer;
            this.qReceiver = q;
        }

        public boolean hasNext(){
            return this.frontP < this.rearP;
        }
        public Item next(){
            return this.qReceiver[this.frontP++];
        }
        public void remove(){}

    }

    //Testing through main method.
    public static void main(String[] args){
        FixedArrayQueue<Integer> q1 = new FixedArrayQueue<>(6);
        FixedArrayQueue<String> q2 = new FixedArrayQueue<>(8);

        q1.enqueue(1);
        q1.enqueue(2);
        q1.enqueue(3);
        q1.peek();
        q1.size();
        q1.isEmpty();
        q1.dequeue();
        q1.peek();
        Iterator<Integer> q1Iter = q1.iterator();

        while(q1Iter.hasNext()){
            Integer q1Item = q1Iter.next();
            System.out.print(q1Item + "\n");
        }




        q2.enqueue("this");
        q2.enqueue("is");
        q2.enqueue("String");
        q2.enqueue("Queue!");
        q2.size();
        q2.dequeue();
        q2.isEmpty();
        q2.peek();
        Iterator<String> q2Iter = q2.iterator();

        while(q2Iter.hasNext()){
            String q2Item = q2Iter.next();
            System.out.print(q2Item + "\n");
        }





    }
}
