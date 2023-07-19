package circularqueue;
import java.util.Iterator;
import java.util.NoSuchElementException;


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

    public int size(){return (this.rearP - this.frontP + this.currentCap) % this.currentCap;}
    public boolean isUnderHalf(){return this.size() < (this.currentCap / 2);}

    @SuppressWarnings("unchecked")
    public void resize(float ratio){
        int newCap = (int)Math.ceil(this.currentCap * ratio);
        Item[] newArr = (Item[]) new Object[newCap];
        int newRear = 0;

        // Shallow-copying underlying array
        for(int i = this.frontP; i <= (this.frontP + this.size()); i++){
            if(i < this.currentCap){
                newArr[i] = this.raq[i];
                newRear = i;
            } else {
                newArr[i] = this.raq[i % this.currentCap];
                newRear = i;
            }
        }
        this.raq = newArr;
        this.currentCap = newCap;
        this.rearP = newRear;
    }
    public void enqueue(Item item){
        if(this.size() == this.currentCap - 1){
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

    public Iterator<Item> iterator() {return new CircularQIterator(this.raq, this.frontP, this.rearP, this.currentCap, this.size());}


    public class CircularQIterator implements Iterator<Item>{
        Item[] cqReciever;
        int cqfrontP;
        int cqrearP;
        int elemCounter;
        int size;
        int capacity;

        public CircularQIterator(Item[] CQ, int frontP, int rearP, int currentCapacity, int size){
            this.cqReciever = CQ;
            this.cqfrontP = frontP;
            this.cqrearP = rearP;
            this.elemCounter = 0;
            this.capacity = currentCapacity;
            this.size = size;
        }

        //Q. This part caused warning when it was without public access identifier. Why?
        //A. The error you've encountered is what generally referred to as 'weaker-access-privileges' error.
        //   This error belongs to 'visibility conflict'.
        //   If you implement certain interface and want to make overriden method from it,
        //   then, that overriden method must have same or wider access privileges that the method it overrides in the interface.
        public boolean hasNext(){
            return elemCounter < this.size;
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
        System.out.printf("The number of elements at this moment: %d\n", raq1.size());

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
        System.out.printf("Capacity: %d\n", raq1.currentCap);
        System.out.printf("Current raq1 frontP: %d\n", raq1.frontP);
        System.out.printf("Current raq1 rearP: %d\n", raq1.rearP);

        // Invoke full() status & resize
        raq1.enqueue(5);
        System.out.printf("The number of elements at this moment: %d\n", raq1.size());
        System.out.printf("Current Capacity: %d\n", raq1.currentCap);
        System.out.printf("Current frontP: %d\n", raq1.frontP);
        System.out.printf("Current rearP: %d\n", raq1.rearP);

        for(int i : raq1){
            System.out.printf("%d\n", i);
        }

    }

}
