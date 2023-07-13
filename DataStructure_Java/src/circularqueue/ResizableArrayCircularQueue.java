package circularqueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import queue.ResizableArrayQueue;

public class ResizableArrayCircularQueue<Item> implements Iterable<Item> {
    private Item[] raq;
    private int currentCap;
    private int frontP;
    private int rearP;

    @SuppressWarnings("unchecked")
    public ResizableArrayCircularQueue(int capacity){
        this.currentCap = capacity;
        this.raq = (Item[]) new Object[currentCap];
        this.frontP = 0;
        this.rearP = 0;
    }

    public boolean isEmpty(){return this.size() == 0;}
    public boolean isFull(){return this.size() == this.currentCap;}

    public int size(){return (this.rearP - this.frontP + this.currentCap) % this.currentCap;}
    public boolean isUnderHalf(){return this.size() < (this.currentCap / 2);}

    @SuppressWarnings("unchecked")
    public void resize(float ratio){
        int newCap = (int)Math.ceil(this.currentCap * ratio);
        Item[] newArr = (Item[]) new Object[newCap];

        // Shallow-copying underlying array
        for(int i = this.frontP; i <= (this.size() + (this.frontP-1)); i++){
            if(i < this.currentCap){
                newArr[i] = this.raq[i];
            } else {
                newArr[i % this.currentCap] = this.raq[i % this.currentCap];
            }
        }
        this.raq = newArr;
        this.currentCap = newCap;
    }
    public void enqueue(Item item){
        if(this.size() == this.currentCap){
            this.resize(1.5f);
        }
        int enqPos = (this.rearP + this.currentCap) % this.currentCap;
        this.raq[enqPos] = item;
        System.out.printf("enqueue() has been successfully processed!: %s\n", this.raq[enqPos]);
        this.rearP = ((this.rearP + 1) + this.currentCap) % this.currentCap;
    }

    public Item dequeue(){
        if(!this.isEmpty()){
            if(isUnderHalf()){
                this.resize(0.5f);
            }
            Item dqedItem = this.raq[this.frontP];
            this.raq[this.frontP] = null; // Avoid loitering.
            this.frontP = ((this.frontP + 1) + this.currentCap) % this.currentCap; // Moving front pointer forward.
            System.out.printf("dequeue() has been successfully processed!: %s\n", dqedItem);
            return dqedItem;
        }else{
            //Exception handling
            throw new NoSuchElementException("The circular queue is empty at the moment.\n");
        }
    }

    public Item peek(){
        return this.raq[this.frontP];
    }

    public Iterator<Item> iterator() {return new CircularQIterator(this.raq, this.frontP, this.rearP, this.currentCap);}

    // Q. What is the difference between using public class and inner class for iterator??
    public class CircularQIterator implements Iterator<Item>{
        Item[] cqReciever;
        int cqfrontP;
        int cqrearP;
        int elemCounter;
        int capacity;

        public CircularQIterator(Item[] CQ, int frontP, int rearP, int currentCapacity){
            this.cqReciever = CQ;
            this.cqfrontP = frontP;
            this.cqrearP = rearP;
            this.elemCounter = 0;
            this.capacity = currentCapacity;
        }

        // This part caused warning when it was without public access identifier. Why? => Briefly on ChatGPT.
        public boolean hasNext(){
            return elemCounter < this.cqReciever.length;
        }

        public Item next(){
            Item nextItem = this.cqReciever[cqfrontP];
            this.cqfrontP = ((this.cqfrontP + 1) + this.capacity) % this.capacity;
            this.elemCounter++;
            return nextItem;
        }
        public void remove(){}
    }
    // Test code
    public static void main(String[] args){
        // Instantiation.
        ResizableArrayCircularQueue<Integer> raq1 = new ResizableArrayCircularQueue<>(4);
        ResizableArrayCircularQueue<String> raq2 = new ResizableArrayCircularQueue<>(4);

        raq1.enqueue(0);
        raq1.enqueue(1);
        raq1.enqueue(2);
        raq1.dequeue();
        // Pointers movement check after dequeue():1
        System.out.printf("peek(): %d\n", raq1.peek());
        System.out.printf("Current raq1 frontP: %d\n", raq1.frontP);
        System.out.printf("Current raq1 rearP: %d\n", raq1.rearP);
        System.out.printf("Capacity : %d\n", raq1.currentCap);

        raq1.dequeue();
        // Pointers movement check after dequeue():2
        System.out.printf("peek(): %d\n", raq1.peek());
        System.out.printf("Current raq1 frontP: %d\n", raq1.frontP);
        System.out.printf("Current raq1 rearP: %d\n", raq1.rearP);

        raq1.enqueue(3); // rearPointer turn-over occurs at this line.
        System.out.printf("Current raq1 rearP has to be 0. : %d\n", raq1.rearP);
        raq1.enqueue(400); // Refers to 4 at 0 index.


        System.out.printf("peek(): %d\n", raq1.peek());
        System.out.printf("Current raq1 frontP: %d\n", raq1.frontP);
        System.out.printf("Current raq1 rearP: %d\n", raq1.rearP);

        System.out.printf("Capacity: %d\n", raq1.currentCap);
        System.out.printf("Current raq1 frontP: %d\n", raq1.frontP);
        System.out.printf("Current raq1 rearP: %d\n", raq1.rearP);

    }

}
