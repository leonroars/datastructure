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

    /**
     * <b>Initialization of two pointers</b> - <i>front & rear</i>
     * <p>
     * <p> I found that initializing both {@code front} & {@code rear} to be 0 cause semantic error under current design of push and pop operations.
     * <p> Let's just try to imagine ourselves checking {@code rear} right after firstly-processed pushFront() operation.
     * <p> You would definitely find that <b>an item exists in {@code rear} location now.</b> We can easily be sure of that pushRear() won't work appropriately under this status.
     * <p> This issue occurs for vice-versa.
     * <p> This semantic error occurs from that not like stack and queue - that both pointers of them move clockwise on push() and counter-clockwise on pop() -, two pointers of Deque have their own directions.
     *<pre>
     *     - front : Moves counter-clockwise direction on {@code pushFront()} and clockwise direction on {@code popFront()}.
     *     - rear : Moves clockwise direction on {@code pushRear()} and counter-clockwise direction on {@code popRear()}.
     *</pre>
     * For resolving this issue, I chose to <b>initialize front & rear pointer differently</b> as this way considered to be a 'cheaper' solution than adding case-handling using another condition inside push()&pop() operations.
     */
    @SuppressWarnings("unchecked")
    public FixedArrayDeque(int initCapacity){
        this.cap = initCapacity;
        this.fad = (Item[]) new Object[this.cap];
        this.front = 0;
        this.rear = 1;
    }

    // 230717: isEmpty(), isFull(), size() => NEEDS TO BE REPAIRED!! _ related to front/rear init change.
    public boolean isEmpty(){return this.front == this.rear;}
    public boolean isFull(){return this.size() == this.cap - 1;}

    public int size(){return ((this.rear - this.front) + this.cap) % this.cap;}

    /**
     * <b>pushFront(Item i)</b>
     * <p> {@code pushFront()} adds item into current {@code front} pointer position of underlying array. Then move {@code front} pointer one unit left - counter clockwise(CCW).
     * @param item
     * @return none
     */
    public void pushFront(Item item){
        if(!this.isFull()){
            this.fad[this.front] = item;
            System.out.printf("pushFront() has been successfully processed!: %s\n", this.fad[this.front]);
            this.front = ((this.front - 1) + this.cap) % this.cap;
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
            this.fad[this.rear] = item;
            System.out.printf("pushRear() has been successfully processed!: %s\n", this.fad[this.rear]);
            this.rear= ((this.rear + 1) + this.cap) % this.cap;
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
            this.front = ((this.front + 1) + this.cap) % this.cap;
            Item frontPopped = this.fad[this.front];
            this.fad[this.front] = null; // To avoid loitering.

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

            System.out.printf("popRear() has been successfully processed!: %s\n", rearPopped);
            return rearPopped;
        } else {
            throw new NoSuchElementException("A Deque is currently empty.");
        }
    }

    public Item peekFront(){
        if(!this.isEmpty()){
            return this.fad[((this.front + 1) + this.cap) % this.cap];
        } else {
            throw new NoSuchElementException("EMPTY! peekFront() cannot be processed!");
        }
    }

    public Item peekRear(){
        if(!this.isEmpty()){
            return this.fad[((this.rear - 1) + this.cap) % this.cap];
        }
    }


}
