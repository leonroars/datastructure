package deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedArrayDeque<Item> implements Iterable<Item>{

    /**
     * <b>NOTE: I/O operations of Deque</b>
     * <p> As Deque is double-ended queue, it requires in-and-out for both side - front and rear.
     * <p> So originally-named operations in Queue - enqueue() and dequeue() - are seperated into following operations.
     * <br>
     * <p> <b>pushFront(), pushRear(), popFront(), popRear()</b>
     */
    private Item[] fad;
    private int front;
    private int rear;
    private int cap;
    private int size;

    /**
     * <b>I/O & Status-examination Logics for Deque: <i>isFull(), isEmpty(), push*(), pop*()</i></b>
     * <p>
     * <p> To make this data structure work properly with both pointers initialized 0, I/O and status-examination logics have to be built different with the way they have been.
     * <p> I/O and status-examination logics were designed to be behave under following rules.
     * <pre>
     * - push(item)_Stack / enqueue(item)_Queue & CQ : First add item into rear pointer location of underlying array, then increase rear pointer
     * - pop()_Stack : Decrease rear pointer, then extract item from decreased rear pointer location of underlying array.
     * - dequeue()_Q & CQ : First extract item from front pointer location of underlying array, then increase front pointer.
     * </pre>
     * <p> Let's just try to imagine ourselves checking {@code rear} right after firstly-processed pushFront() operation.
     * <p> You would definitely find that <b>an item exists in {@code rear} location now.</b> We can easily be sure of that pushRear() won't work appropriately under this status.
     * <p> This issue occurs for vice-versa.
     * <p> To resolve this issue, I designed I/O and status-examination logic as following:
     *<pre>
     * - pushFront(item) : Move pointer count-clockwise, then add item into rear pointer location of array.
     * - pushRear(item) :  Add item into front pointer location of array, then move pointer clockwise.
     * - popFront() : Extract item from rear pointer location of array, then move pointer counter-clockwise.
     * - popRear() : Move front pointer clockwise, then extract item from decreased front pointer location of array.
     *</pre>
     */

    @SuppressWarnings("unchecked")
    public FixedArrayDeque(int initCapacity){
        this.cap = initCapacity;
        this.fad = (Item[]) new Object[this.cap];
        this.front = 0;
        this.rear = 0;
        this.size = 0;

    }

    public boolean isEmpty(){return this.size == 0;}

    // isFull() is important operation that preventing over-writing threat.
    public boolean isFull(){return this.size() == this.fad.length - 1;}


    // How can I return the number of items only using two pointers?
    public int size(){return this.size;}

    /**
     * <b>pushFront(Item i)</b>
     * <p> {@code pushFront()} adds item into current {@code front} pointer position of underlying array. Then move {@code front} pointer one unit left - counter clockwise(CCW).
     * @param item
     * @return none
     */
    public void pushFront(Item item){
        if(!this.isFull()){
            this.front = ((this.front - 1) + this.cap) % this.cap; // 1) Move front pointer first.
            this.fad[this.front] = item; // 2) Add item into updated front pointer location
            System.out.printf("pushFront() has been successfully processed!: %s\n", this.fad[this.front]);
            this.size++;
        } else {
            System.out.println("pushFront() cannot be processed. Deque is now FULL!");
        }
    }

    /**
     * <b>pushRear(Item i)</b>
     * <p> {@code pushRear()} adds item into current {@code rear} pointer position of underlying array. Then move {@code rear} pointer one unit right - clockwise(CW).
     * @param item
     * @return none
     */
    public void pushRear(Item item){
        if(!this.isFull()){
            this.fad[this.rear] = item; // 1) Add item into current rear pointer location.
            System.out.printf("pushRear() has been successfully processed!: %s\n", this.fad[this.rear]);
            this.rear= ((this.rear + 1) + this.cap) % this.cap; // 2) Update rear pointer.
            this.size++;
        } else {
            System.out.println("pushRear() cannot be processed. Deque is now FULL");
        }
    }

    /**
     * <b>popFront()</b>
     * <p> {@code pushRear()} retrieve item from 'lastly added' {@code front} pointer position of underlying array.
     * @param none
     * @return Item frontPopped
     */
    public Item popFront(){
        if(!this.isEmpty()){
            Item frontPopped = this.fad[this.front];
            this.fad[this.front] = null; // To avoid loitering.
            this.front = ((this.front + 1) + this.cap) % this.cap;
            this.size--;
            System.out.printf("popFront() has been successfully processed!: %s\n", frontPopped);
            return frontPopped;
        } else {
            throw new NoSuchElementException("A Deque is currently empty.");
        }
    }

    /**
     * <b>popRear()</b>
     * <p> {@code popRear()} retrieve item from 'lastly added' {@code rear} pointer position of underlying array.
     * @param none
     * @return Item rearPopped
     */
    public Item popRear(){
        if(!this.isEmpty()){
            this.rear = ((this.rear - 1) + this.cap) % this.cap;
            Item rearPopped = this.fad[this.rear];
            this.fad[this.rear] = null; // To avoid loitering.
            this.size--;
            System.out.printf("popRear() has been successfully processed!: %s\n", rearPopped);
            return rearPopped;
        } else {
            throw new NoSuchElementException("A Deque is currently empty.");
        }
    }

    public Item peekFront(){
        if(!this.isEmpty()){
            return this.fad[this.front];
        } else {
            throw new NoSuchElementException("EMPTY! peekFront() cannot be processed!");
        }
    }

    public Item peekRear(){
        if(!this.isEmpty()){
            return this.fad[((this.rear - 1) + this.cap) % this.cap];
        } else {
            throw new NoSuchElementException("EMPTY! peekRear() cannot be processed!");
        }
    }

    // Iter from front pointer to rear pointer - 1 in clockwise order.;
    public Iterator<Item> iterator() {return new FADIterator(this.front, this.rear, this.size, this.fad);}

    public class FADIterator<Item> implements Iterator<Item>{
        private int frontP;
        private int rearP;
        private int locator;
        private int counter;
        private int dqsize;
        private Item[] dReceiver;

        public FADIterator(int front, int rear, int size, Item[] dq) {
            this.frontP = front;
            this.rearP = rear;
            this.dqsize = size;
            this.dReceiver = dq;
            this.locator = this.frontP;
            this.counter = 0;
        }

        public boolean hasNext(){return this.counter < this.dqsize;}

        public Item next(){
            Item nextItem = this.dReceiver[this.locator];
            this.locator = ((this.locator + 1) + this.dReceiver.length) % this.dReceiver.length;
            return nextItem;
        }

        public void remove() {}
    }

}
