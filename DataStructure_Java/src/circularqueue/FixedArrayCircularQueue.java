package circularqueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedArrayCircularQueue<Item> implements Iterable<Item> {
    Item[] faq;
    private int frontP;
    private int rearP;
    private int cap;

    // Setting constructor.
    @SuppressWarnings("unchecked")
    public FixedArrayCircularQueue(int initCapacity){
        this.faq = (Item[])new Object[initCapacity];
        this.cap = initCapacity;
        this.frontP = 0;
        this.rearP = 0;
    }

    /** <b>Implementation Convention for isFull() & isEmpty()</b>
     *
     *<p>      A full-status is set to be 1-less than full capacity in fixed-capacity circular queue.
     *<p>      This is on purpose for 2-reasons.
     *<p>          - For discrimination between full-status and empty-status.
     *<p>          - For avoiding overflow.
     */
    public boolean isEmpty(){return this.frontP == this.rearP;}
    public boolean isFull(){return this.size() == this.cap - 1;}


    public int size(){return ((this.rearP - this.frontP) + this.cap) % this.cap;}

    public void enqueue(Item item){
        if(!this.isFull()){
            this.faq[this.rearP] = item;
            System.out.printf("enqueue() has been successfully processed!: %s\n", this.faq[this.rearP]);
            this.rearP = (this.rearP + 1) % this.cap;
        }else{
            System.out.println("Circular Queue is now FULL!\n");
        }
    }

    public Item dequeue(){
        if(!this.isEmpty()){
            Item dqedItem = this.faq[this.frontP];
            System.out.printf("dequeue() has been successfully processed!: %s\n", dqedItem);
            this.frontP++;
            return dqedItem;
        }else{
            throw new NoSuchElementException("Circular Queue is now empty!");
        }
    }

    public Item peek(){
        if(!this.isEmpty()){
            return this.faq[this.frontP];
        }else{
            throw new NoSuchElementException("Circular Queue is now empty!");
        }
    }

    public Iterator<Item> iterator() {return new CircularQIterator(this.frontP, this.rearP, this.faq, this.size(), this.cap);}

    public class CircularQIterator implements Iterator<Item>{
        private int frontCounter;
        private int rearCounter;
        private Item[] cqReciever;
        private int elemCounter;
        private int qsize;
        private int qcap;

        public CircularQIterator(int frontPointer, int rearPointer, Item[] cq, int qsize, int qcap){
            this.frontCounter = frontPointer;
            this.rearCounter = rearPointer;
            this.cqReciever = cq;
            this.elemCounter = 0;
            this.qsize = qsize;
            this.qcap = qcap;
        }

        public boolean hasNext(){return this.elemCounter <= this.qsize;}

        public Item next(){
            Item nextItem = this.cqReciever[this.frontCounter];
            this.frontCounter = (this.frontCounter + 1) % this.qcap;
            this.elemCounter++;
            return nextItem;
        }
        public void remove(){}
    }

    // Test code
    public static void main(String[] args){
        FixedArrayCircularQueue<String> faq1 = new FixedArrayCircularQueue<>(5);
        //enqueue(), isEmpty(), isFull(), size(), peek(), pointers movement test.
        faq1.enqueue("0");
        faq1.enqueue("1");
        faq1.enqueue("2");
        faq1.enqueue("3");
        System.out.printf("Current frontP: %d\n", faq1.frontP);
        System.out.printf("Current rearP: %d\n", faq1.rearP);
        System.out.printf("Current size(): %d\n", faq1.size());
        System.out.printf("Currently Full?: %b\n", faq1.isFull());
        System.out.printf("Currently Empty?: %b\n", faq1.isEmpty());
        System.out.printf("Scheduled to be dequeue() next time: %s\n", faq1.peek());
        System.out.printf("\n");

        //dequeue(), pointers' circular movement test
        faq1.dequeue();
        faq1.dequeue();
        faq1.enqueue("4");
        faq1.enqueue("5");
        System.out.printf("Current frontP: %d\n", faq1.frontP);
        System.out.printf("Current rearP: %d\n", faq1.rearP);
        System.out.printf("Current size(): %d\n", faq1.size());
        System.out.printf("Currently Full?: %b\n", faq1.isFull());
        System.out.printf("Currently Empty?: %b\n", faq1.isEmpty());
        System.out.printf("Scheduled to be dequeue() next time: %s\n", faq1.peek());

    }

}
