package deque;

public class ResizableArrayDeque<Item> implements Iterable<Item>{
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

    // Would it be free from error if I set full-status to be literally full?
    public boolean isFull() {return this.size() == this.currentCap;}

    // isUnderHalf(): Return true if the number of elements falls under its current capacity
    public boolean isUnderHalf() {return this.size() < this.currentCap / 2;}

    @SuppressWarnings("unchecked")
    public void resize(float ratio){
        int newCap = (int) Math.ceil(this.currentCap * ratio);
        Item[] newArr = (Item[]) new Object[newCap];


    }
    /**
     * <b>pushFront(Item e)</b>
     * <p> pushFront(e) moves front counter counter-clockwise first, then add item into updated front pointer location.
     * @param item
     */
    public void pushFront(Item item){
        if(!this.isFull()){
            if()
        }
    }

}
