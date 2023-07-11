package queue;
import java.util.Iterator;

// <Item>
// :A placeholder for keyword that specifies the type which this data structure can hold.
// : In here, <Item>, is called generic type parameter.
public class ResizableArrayQueue<Item> implements Iterable<Item> {

    //Initializing instance variables.
    private final int frontPointer = 0;
    private int rearPointer = 0;
    private int currentCap;
    private Item[] rq;

    //Q. When do we have to use 'this' or when should we not use it?
    // this keyword is used in following situation.
    // 1) Accessing instance variable.
    // 2) Method chaining
    // 3) Invoking constructor.: this()
    @SuppressWarnings("unchecked")
    public ResizableArrayQueue(int initCap){
        this.currentCap = initCap;
        rq = (Item[]) new Object[this.currentCap]; // 'Object' : Superclass of all the classes in Java including user-defined class. Thus, any type of object can be assigned to 'Object' type variable.
    }

    public boolean isEmpty(){return this.rearPointer == 0;}
    public int size(){return this.rearPointer;}

    public boolean isUnderHalf(){return this.size() < this.currentCap / 2;}

    @SuppressWarnings("unchecked")
    public void resize(float ratio){

        int newCap = (int)Math.ceil(this.currentCap * ratio);
        Item[] newArr = (Item[])new Object[newCap];
        //Array Copy
        for(int i=0; i<this.rearPointer; i++){
            newArr[i] = this.rq[i];
        }

        //Changing reference of instance variables & Updating current capacity.
        this.rq = newArr;
        this.currentCap = newCap;
    }

    public void enqueue(Item item){

        // I designed
        if(this.rearPointer == this.currentCap){
            this.resize(1.5f);
        }
        this.rq[this.rearPointer] = item;
        System.out.printf("enqueue() has been successfully processed!: %s\n", this.rq[this.rearPointer]);
        this.rearPointer++;
    }

    public Item dequeue(){
        Item dqItem = this.rq[this.frontPointer];
        //dequeue() requires re-allocation of items. : O(n)
        for(int i=0; i<this.rearPointer-1; i++){
            this.rq[i] = this.rq[i+1];
        }
        // As a result of reallocation, end-most index has to refer null.
        this.rq[this.rearPointer-1] = null;

        if(this.isUnderHalf()){
            this.resize(0.5f);
        }
        this.rearPointer--;
        System.out.printf("dequeue() has been successfully processed!: %s\n", dqItem);
        return dqItem;
    }

    public Item peek(){
        return this.rq[this.frontPointer];
    }

    public Iterator<Item> iterator() {return new ForwardQueueIterator(this.rearPointer, this.rq);}

    // This part needs to be fixed.
    public class ForwardQueueIterator implements Iterator<Item>{
        private int iterLocator;
        private int rearLocator;
        private Item[] iterCollection;

        public ForwardQueueIterator(int rearPointer, Item[] q){
            iterLocator = 0;
            rearLocator = rearPointer;
            this.iterCollection = q;
        }

        public boolean hasNext(){return this.iterLocator < this.rearLocator;} // By setting so, it doesn't print items which are currently null in queue.
        public Item next(){return this.iterCollection[iterLocator++];}
        public void remove(){}
    }

    public static void main(String[] args){

        // 여기 왜 이렇게 안 외워지지
        ResizableArrayQueue<Integer> rq1 = new ResizableArrayQueue<>(5);

        System.out.printf("Current Capacity: %d\n", rq1.currentCap);
        rq1.enqueue(1);
        rq1.enqueue(2);
        rq1.enqueue(3);
        rq1.enqueue(4);
        rq1.enqueue(5);
        rq1.enqueue(6); // Revoke resize().

        for(Integer i : rq1) {
            System.out.printf("%d\n", i);
        }
        System.out.printf("Current Capacity: %d\n", rq1.currentCap);
        rq1.dequeue();
        rq1.dequeue();
        rq1.dequeue();
        rq1.dequeue(); // Revoke resize().
        System.out.printf("Current Capacity: %d\n", rq1.currentCap);
        System.out.printf("peek: %d\n", rq1.peek());

        Iterator<Integer> rq1Iter = rq1.iterator();

        while(rq1Iter.hasNext()){
            System.out.printf("%d\n", rq1Iter.next());
        }
    }
}
