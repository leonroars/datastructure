package deque;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class ResizableArrayDeque<Item> implements Iterable<Item> {
    private Item[] rad;
    private int front;
    private int rear;
    private int size;
    private int currentCap;

    @SuppressWarnings("unchecked")
    public ResizableArrayDeque(int initCapacity){
        this.currentCap = initCapacity;
        this.rad = (Item[]) new Object[this.currentCap];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public int size() {return this.size;}

    public boolean isEmpty() {return this.size == 0;}

    // Q. Would it be free from error if I set full-status to be literally full?
    // A. Only If I design resize() and make it to be triggered properly. If I don't, it will over-write the items that has been written by the other pointer.
    public boolean isFull() {return this.size() == this.currentCap;}

    // isUnderHalf(): Return true if the number of elements falls under its current capacity
    public boolean isUnderHalf() {return this.size() < this.currentCap / 2;}

    @SuppressWarnings("unchecked")
    public void resize(float ratio){
        //Initialize
        int newCap = (int) Math.ceil(this.currentCap * ratio);
        Item[] newArr = (Item[]) new Object[newCap];
        int newFront = newCap - (this.currentCap - this.front);

        //Shallow-Copying
        for(int i = this.front; i < (this.front + this.size()); i++){
            if(i < this.currentCap){
                // I designed this array-copying logic in a way that resizing actually looks like inserting increased space to be inserted between front-rear pointers.
                newArr[newCap - (this.currentCap - i)] = this.rad[i % this.currentCap];
            } else {
                newArr[i % this.currentCap] = this.rad[i % this.currentCap];
            }
        }
        // Updating instance variables.
        this.front = newFront;
        this.rad = newArr;
        this.currentCap = newCap;

    }
    /**
     * <b>pushFront(Item e)</b>
     * <p> pushFront(e) moves front counter counter-clockwise first, then add item into updated front pointer location.
     * @param item
     */
    public void pushFront(Item item){
        // Setting condition for resize() invocation.
        if(this.isFull()){
            this.resize(1.5f);
        }
        // 1) Move front pointer counter-clockwise.
        this.front = ((this.front - 1) + this.currentCap) % this.currentCap;
        // 2) Add item into updated front pointer location.
        this.rad[this.front] = item;
        // 3) Update size variable
        this.size++;
        System.out.printf("pushFront() has been successfully processed!: %s\n", this.rad[this.front]);
    }

    public void pushRear(Item item){
        // Setting condition for resize() invocation.
        if(this.isFull()){
            this.resize(1.5f);
        }
        // 1) Add item into current rear position.
        this.rad[this.rear] = item;
        System.out.printf("pushRear() has been successfully processed!: %s\n", this.rad[this.rear]);
        // 2) Move rear pointer clockwise.
        this.rear = ((this.rear + 1) + this.currentCap) % this.currentCap;
        // 3) Update size variable.
        this.size++;
    }

    public Item popFront(){
        if(!this.isEmpty()){
            if(this.isUnderHalf()){
                this.resize(0.5f);
            }
            Item pfItem = this.rad[this.front];
            this.rad[this.front] = null; // To avoid loitering
            this.front = ((this.front + 1) + this.currentCap) % this.currentCap;
            System.out.printf("popFront() has been successfully processed!: %s\n", pfItem);
            this.size--;
            return pfItem;
        } else {
            throw new NoSuchElementException("Deque is EMPTY. popFront() cannot be processed!\n");
        }
    }

    public Item popRear(){
        if(!this.isEmpty()){
            if(this.isUnderHalf()){
                this.resize(0.5f);
            }
            this.rear = ((this.rear - 1) + this.currentCap) % this.currentCap;
            Item prItem = this.rad[this.rear];
            this.rad[this.rear] = null; // To avoid loitering
            System.out.printf("popRear() has been successfully processed!: %s\n", prItem);
            this.size--;
            return prItem;
        } else {
            throw new NoSuchElementException("Deque is EMPTY. popRear() cannot be processed!\n");
        }
    }

    public Item peekFront() {
        if(!this.isEmpty()){
            return this.rad[this.front];
        } else {
            System.out.println("Dequeue is Empty. peekFront() cannot be processed!");
            return null;
        }
    }

    public Item peekRear() {
        if(!this.isEmpty()){
            return this.rad[((this.rear - 1) + this.currentCap) & this.currentCap];
        } else {
            System.out.println("Dequeue is Empty. peekRear() cannot be processed!");
            return null;
        }
    }

    public Iterator<Item> iterator() {return new RADIterator(this.front, this.rear, this.currentCap, this.size(), this.rad);}

    public class RADIterator implements Iterator<Item> {
        public int frontP;
        public int rearP;
        public int cap;
        public int size;
        public int locator;
        public int iterCounter;
        public Item[] radReceiver;

        public RADIterator(int front, int rear, int cap, int size, Item[] deque) {
            this.frontP = front;
            this.rearP = rear;
            this.cap = cap;
            this.size = size;
            this.radReceiver = deque;
            this.iterCounter = 0;
            this.locator = frontP;
        }

        public boolean hasNext() {
            return this.iterCounter < this.size;
        }

        public Item next() {
            Item nextItem = this.radReceiver[this.locator];
            this.locator = ((this.locator + 1) + this.cap) % this.cap;
            this.iterCounter++;
            return nextItem;
        }

        public void remove() {
        }

    }


        // Test code
        public static void main(String[] args) {
            // Instantiate
            ResizableArrayDeque<Integer> rad1 = new ResizableArrayDeque<>(6);

            // push*() test => Passed!
            rad1.pushFront(-1);
            rad1.pushRear(0);
            rad1.pushFront(-2);
            rad1.pushRear(1);
            rad1.pushFront(-3); // front : at index_3(item contains) / rear : at index_2(not containing item) / The number of item : 5 / Not Full.

            // status-examination & pointers movement test => Passed!
            System.out.printf("Current front pointer: %d\n", rad1.front);
            System.out.printf("Current rear pointer: %d\n", rad1.rear);
            System.out.printf("The number of items: %d\n", rad1.size());
            System.out.printf("isEmpty()? : %b\n", rad1.isEmpty());
            System.out.printf("isFull()? : %b\n", rad1.isFull());

            // full-status test => Passed!
            rad1.pushRear(2);
            System.out.printf("Current front pointer: %d\n", rad1.front);
            System.out.printf("Current rear pointer: %d\n", rad1.rear);
            System.out.printf("The number of items: %d\n", rad1.size());
            System.out.printf("Current Capacity: %d\n", rad1.currentCap);
            System.out.printf("isEmpty()? : %b\n", rad1.isEmpty());
            System.out.printf("isFull()? : %b\n", rad1.isFull());

            // resize()_enlarging test: KEEP YOUR EYES ON array-copying result.
            rad1.pushFront(5); // front pointer should be locating '5(containing item)', rear should be locating '3(not containing item)' => Passed!
            System.out.printf("Current front pointer: %d\n", rad1.front);
            System.out.printf("Current rear pointer: %d\n", rad1.rear);
            System.out.printf("The number of items: %d\n", rad1.size());
            System.out.printf("Current Capacity: %d\n", rad1.currentCap);
            System.out.printf("isEmpty()? : %b\n", rad1.isEmpty());
            System.out.printf("isFull()? : %b\n", rad1.isFull());

            // Iteration Test => Passed!!
            for(int i : rad1){
                System.out.printf("%d\n", i);
            }

            // pop*() operations & size() % empty-status examination test => Passed!
            rad1.popFront(); // front: 6(item-containing) _ rear: 3(item-not-containing) _ 6
            rad1.popRear(); // front: 6(item-containing) _ rear: 2(item-not-containing) _ 5
            rad1.popFront(); // front: 7(item-containing) _ rear: 2(item-not-containing) _ 4
            rad1.popRear(); // front: 7(item-containing) _ rear: 1(item-not-containing) _ 3
            rad1.popFront(); // front: 8(item-containing) _ rear: 1(item-not-containing) _ 2
            rad1.popRear(); // front: 8(item-containing) _ rear: 0(item-not-containing) _ 1
            rad1.popFront(); // front: 0(empty) _ rear: 0(empty) _ 0
            System.out.printf("Current front pointer: %d\n", rad1.front);
            System.out.printf("Current rear pointer: %d\n", rad1.rear);
            System.out.printf("The number of items: %d\n", rad1.size());
            System.out.printf("Current Capacity: %d\n", rad1.currentCap);
            System.out.printf("isEmpty()? : %b\n", rad1.isEmpty());
            System.out.printf("isFull()? : %b\n", rad1.isFull());


        }
    }

