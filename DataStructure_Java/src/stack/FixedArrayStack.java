package stack;
import java.util.Iterator;

// Lesson Learnt: You need to implement methods inside the abstract class or interface you're about to implement. This sounds quite obvious after I understood it.
public class FixedArrayStack<Item> implements Iterable<Item> {
    private Item[] a;
    private int topPointer;
    private int capacity;

    @SuppressWarnings("unchecked") // Optional: Suppressing unchecked casting warning(Object => Item)
    public FixedArrayStack(int cap) {
        this.capacity = cap;
        this.a = (Item[]) new Object[this.capacity];
        this.topPointer = 0;
    }
    public void push(Item item){
        if (this.size() < this.capacity){
            this.a[topPointer] = item;
            this.topPointer++;
        }
        else{
            System.out.println("Stack is Full. Push cannot be processed."); // I chose to print error message over throwing exception.
        }

    }

    public Item pop(){
        if(this.topPointer != 0){
            Item item = this.a[topPointer];
            this.a[topPointer] = null;
            this.topPointer--;
            return item;
        }
        else{
            System.out.println("Stack is empty. Pop cannot be processed.");
            return null;
        }
    }

    public Item peek() {
        System.out.printf("peek(): %s\n", this.a[topPointer-1]); // Use $s format specifier for generic type as String object type is most generally supported by other reference types by their instance method toString().
        return this.a[topPointer-1];
    }
    public int size() {
        return this.topPointer;
    }

    public boolean isEmpty(){
        return this.topPointer == 0;
    }


    public Iterator<Item> iterator() {
        return new ReverseArrayIterator(this.topPointer, this.a);
    }

    public class ReverseArrayIterator implements Iterator<Item>{
        int i;
        Item[] a;
        public ReverseArrayIterator(int topPointer, Item[] a){
            this.i = topPointer-1;
            this.a = a;
        }
        public boolean hasNext(){
            return i >= 0;
        }
        public Item next(){
            return this.a[i--];
        }
        public void remove(){
        }
    }



    public static void main(String[] args){
        FixedArrayStack<Integer> s1 = new FixedArrayStack<>(5);
        FixedArrayStack<String> s2 = new FixedArrayStack<>(7);

        s1.push(1);
        s1.push(3);
        s1.peek();
        boolean isEmpty = s1.isEmpty();
        System.out.printf("s1 isEmpty: %b\n", isEmpty);
        int sz = s1.size();
        System.out.printf("s1 size: %d\n", sz);
        System.out.printf("s1 top pointer: %d\n", s1.topPointer);

        // Using forEach iteration
        for(Integer i : s1){
            System.out.printf("%d\n", i);
        }

        // Using while iteration.
        Iterator<Integer> s1iter = s1.iterator();
        while(s1iter.hasNext()){
            Integer i = s1iter.next();
            System.out.printf("%d\n", i);
        }

        s2.push("word");
        s2.push("a");
        s2.peek();
        boolean s2empty = s2.isEmpty();
        System.out.printf("s2 isEmpty: %b\n", s2empty);
        int s2z = s2.size();
        System.out.printf("s2 size: %d\n", s2z);
        System.out.printf("s2 top pointer: %d\n", s2.topPointer);
        s1.iterator();

        Iterator<String> s2iter = s2.iterator();
        while(s2iter.hasNext()){
            String item = s2iter.next();
            System.out.println(item);
        }

    }
}