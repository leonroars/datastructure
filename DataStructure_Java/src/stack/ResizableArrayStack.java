package stack;
import java.util.Iterator;
import java.util.EmptyStackException;

public class ResizableArrayStack<Item> implements Iterable<Item> {
    private Item[] rs;
    private int currentCap;
    private int topPointer;

    @SuppressWarnings("unchecked")
    public ResizableArrayStack(int initialCapacity) {
        this.currentCap = initialCapacity;
        this.topPointer = 0;
        this.rs = (Item[]) new Object[this.currentCap];
    }

    /**
     * Return true if the stack is full. A full status is when topPointer is equal to currentCap.
     * @return true if topPointer is equal to currentCap.
     */
    public boolean isFull() {return this.topPointer == this.currentCap;}

    /**
     * Return true if the stack is empty. An empty status is when topPointer is equal to 0.
     * @return true if the topPointer is equal to 0.
     */
    public boolean isEmpty(){
        return this.topPointer == 0;
    }


    /** {@code isUnderHalf()}
     *
     * <p> - Return {@code true} if the number of elements at the moment is less than the half of its current capacity(currentCap).
     * <p> - I designed its logic to be triggered conservatively than that in case of enlarging by setting bifurcation point to be the case that the number of elements is less than the half of its currentCap.
     * <p> The reason why I did that: I assumed that actively triggered shrinking might harm elements integrity.
     *
     *
     */
    public boolean isUnderHalf() {return this.topPointer - 1 < (int)Math.floor(this.currentCap * 0.5f);}


    /** A resizing has to be implemented bidirectionally. - shrinking, enlarging.
     * <p>The point is, where would it be better to be placed that specifying its determination of whether it shrinks or enlarges the array and of how much will it do so.
     *<p>
     * <p>A: resize() method decide the ratio and whether to shrink or enlarge by getting parameterized current capacity and the number of elements.
     * <p>B: resize() method gets resizing ratio(float type data) as its parameter. It only does its job - resizing underlying array.
     *  And whether resize() method enlarges or shrinks the array sorely depends on the parameter-ed ratio which is given inside push() and pop() method blocks.
     *<p>
     *<p>
     * I chose B as I thought B can provide better code clarity, reusability over A and seems more precisely corresponds to the logic.
     * <p> And by doing so, anyone who want to reuse this code with more easily adjustable parameterized 'ratio'.
     **/
    @SuppressWarnings("unchecked")
    public void resize(float ratio) {
        int newCap = (int)Math.ceil(this.currentCap * ratio); // Setting new capacity. newCap = roundUp_and_int(currentCap(int) * ratio(float))
        Item[] newArr = (Item[]) new Object[newCap];

        //Shallow Copy: As it copies references thar corresponds to each index of rs[] to newArr[].
        for(int i=0;i<topPointer;i++){
            newArr[i] = this.rs[i];
        }
        this.rs = newArr; // Replacing reference of object 'rs' to newly created 'newArr'. newArr would be GC-ed as it refers none.
        this.currentCap = newCap; //Updating capacity.

    }

    /**
     * Add item given as argument into stack and place it on topPointer location. Then, increase topPointer.
     * <p> If stack becomes full after push(), resize(1.5f) is called for enlarging the stack. A ratio can be modified under user's decision.
     * @param item
     * @return void(For quick check I added print statement that prints whether push() has been successfully done or not.)
     */
    public void push(Item item) {
        if (this.isFull()) {
            this.resize(1.5f);
            }
        this.rs[this.topPointer] = item;
        System.out.printf("push() has been successfully processed!: %s\n", this.rs[this.topPointer]);
        this.topPointer++;
    }

    /**
     * Retract item which is located on end-most place of stack. Then, return it.
     * <p> Retraction is done by changing reference of index that just has been popped to {@code null} for avoid loitering.
     * <p>If the number of element after pop() reaches below the half of its current capacity, resize(0.5f) is called.
     * <p>A ratio can be modified under user's decision.
     * @return Item poppedItem
     */
    public Item pop() {
        if(!this.isEmpty()){
            Item poppedItem = this.rs[this.topPointer-1];
            this.rs[this.topPointer-1] = null; // Loitering Prevention.
            this.topPointer--;

            //Resize underlying array if the number of items after pop() process goes below a half of its current capacity. Else this block is ignored.
            if(this.isUnderHalf()){
                this.resize(0.5f);
            }
            //Result return block.
            System.out.printf("pop() has been successfully processed!: %s\n", poppedItem);
            return poppedItem;

        } else {
            throw new EmptyStackException();
        }
    }

    /**
     * Returns integer type value that represents the number of elements in stack at the moment.
     * @return {@code int} the number of elements
     */
    public int size() {
        System.out.printf("The number of items at the moment: %d\n", this.topPointer-1);
        return this.topPointer-1;
    }

    /**
     * Returns item which is at the end-most place but does not retract it. The item returned can be understood as "scheduled-to-be-popped"
     * @return Item e which is at the end-most place
     */
    public Item peek() {
        System.out.printf("Scheduled to be popped next: %s\n", this.rs[this.topPointer-1]);
        return this.rs[this.topPointer-1];
    }

    /**
     * {@code public Iterator<Item> iterator()} : method
     *<pre>
     *- {@code interface Iterable<T>} has abstract method {@code iterator()} that returns {@code interface Iterator<T>} object.
     *- As {@code ResizeableArrayStack} implements {@code interface Iterable<T>}, {@code ResizeableArrayStack} class must include actual description of implemented interface's member methods.
     *</pre>
     *{@code interface Iterator<T>} has following abstract methods: {@code hasNext(), next(), remove()}
     */
    public Iterator<Item> iterator() {return new ReverseArrayIterator(this.topPointer, this.rs);}

    // Interface requires implementations for its inner abstract methods or subclasses. Understandable in common sense.
    // Improvement: Can I separate ReverseArrayIterator class for satisfying single-responsibility principle?
    public class ReverseArrayIterator implements Iterator<Item>{
        int iterPointer;
        Item[] innerArray;

        public ReverseArrayIterator(int topPointer, Item[] innerArray) {
            this.iterPointer = topPointer;
            this.innerArray = innerArray;
        }

        public boolean hasNext() {return this.iterPointer > 0;}
        public Item next(){return this.innerArray[--iterPointer];}
        public void remove(){}

    }

    public static void main(String[] args){
        // Test Program
        ResizableArrayStack<Integer> rs1 = new ResizableArrayStack<>(5);
        ResizableArrayStack<String> rs2 = new ResizableArrayStack<>(5);

        // Integer Stack Test
        System.out.printf("Current Capacity: %d\n", rs1.currentCap);
        rs1.push(1);
        rs1.push(2);
        rs1.push(3);
        rs1.push(4);
        rs1.push(5);
        rs1.push(6); // Triggering resize()
        System.out.printf("Current Capacity: %d\n", rs1.currentCap);
        System.out.printf("Is stack full?: %b\n", rs1.isFull());
        System.out.printf("Is stack empty?: %b\n", rs1.isEmpty());
        rs1.peek();
        rs1.pop();
        rs1.pop();
        rs1.pop();
        System.out.printf("Is the number of elements in stack under half of its original capacity?: %b\n", rs1.isUnderHalf());
        rs1.pop();
        rs1.peek();
        System.out.printf("Current Capacity: %d\n", rs1.currentCap);

        /*
          for(ObjectType e : collection){//action}
            - Enhanced for loop. Also referred to as "for-each statement"
            - This is considered to be a better option over while loop that process same logic.
                - 1) More close to idiomatic
                - 2) Thus, better readability
                - 3) Less pollute-prone on method scope.
                    - A while loop requires outside-of-block-scope variable for iterator or index locator on its action.
                    - That means, this method scope variable remains and takes space even after loop process.
         */
        for (Integer integer : rs1) {
            System.out.printf("%d\n", integer);
        }
    }
}
