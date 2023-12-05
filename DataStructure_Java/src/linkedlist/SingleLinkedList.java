package linkedlist;

import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.NoSuchElementException;



public class SingleLinkedList<Item> implements Iterable<Item> {

    // Node definition.
    private static class Node<Item>{
        Item item;
        Node<Item> next;

        // Node Constructor I : With value as a parameter.
        public Node(Item item){this.item = item;}

        // Node Constructor II : No parameter.
        public Node(){
            this.item = null;
            this.next = null;
        }

        public String toString(){
            return item.toString();
        }
    }

    // Class Member Declaration.
    private int size;
    private Node<Item> head;
    private Node<Item> tail;

    // Constructor I : Empty Singly Linked-List Initialize.
    public SingleLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    // Constructor II : Initialize Singly Linked-List by creating initNode(first node) with given argument as its item.
    public SingleLinkedList(Item item){
        Node<Item> initNode = new Node<>();
        initNode.item = item;
        initNode.next = null;

        head = initNode;
        tail = initNode;

        // Didn't write as 'size++', as I thought assigning '1' to size variable represent 'initialization' purpose more precisely.
        size = 1;
    }

    // Constructor III
    public SingleLinkedList(Node<Item> node){
        node.next = null;

        head = node;
        tail = node;

        size = 1;
    }

    /** I/O Operations.
     * - Each has two versions. - One for value as a parameter, the other for already-instantiated Node instance as a paarameter.
     * <p>
     * <br>
     * - Operations <p>
     *  1) addFirst(), addLast() : Adds given node or a newly created node having given value to corresponding position.<p>
     *  2) removeFirst(), removeLast() : Removes node at corresponding position.
     * </br>
     */

    // addFirst(Item item) : O(1)
    public void addFirst(Item item){
        Node<Item> newNode = new Node<>(item);
        newNode.next = head;

        // Edge-case handling
        if(size == 0){tail = newNode;}
        head = newNode;

        size++;
    }

    // addFirst(Node node) : O(1)
    public void addFirst(Node<Item> node){
        node.next = head;
        head = node;
        // Edge-case handling
        if(size == 0){tail = node;}

        size++;
    }

    // addLast(Item item) : O(1)
    public void addLast(Item item){
        Node<Item> newNode = new Node<>(item);
        newNode.next = null;

        // Edge-case handling.
        if(size == 0){head = newNode; tail = newNode;}

        tail.next = newNode;
        tail = newNode;
        size++;
    }

    // addLast(Node node) : O(1)
    public void addLast(Node<Item> node){
        node.next = null;

        // Edge-case handling.
        if(size == 0){head = node; tail = node;}

        tail.next = node;
        tail = node;
        size++;
    }


    // removeFirst() : O(1). Returns the node head is pointing, and decrease size by 1. If size == 0, return null.
    public Node<Item> removeFirst(){
        // Edge-case handling I
        if(size == 0){return head;} // It will return null anyway. Without using any extra memory space for `target` node.

        Node<Item> target = head;
        head = target.next;
        target.next = null;

        size--;

        // Edge-case handling II
        if(size == 0){
            head = null;
            tail = null;
        }

        return target;
    }

    // removeLast() : O(n), as it requires iteration over consisting nodes till it reaches (n-1)th node to update its next pointer.
    public Node<Item> removeLast(){
        // Edge-case handling I
        if(size == 0){
            System.out.println("Currently Empty");
            return tail;
        }

        Node<Item> iter = head;
        Node<Item> result = tail;

        while(iter.next != tail){
            iter = iter.next;
        }
        iter.next = tail.next;
        tail = iter;

        size--;

        // Edge-case handling II
        if(size == 0){head = null; tail = null;}

        return result;
    }

    // get(int index)_Search By Index : O(n)
    public Node<Item> get(int index){

        // Edge-case Handling
        if(index == 0){return head;}
        if(index == size - 1){return tail;}
        if(index < 0 || index > size - 1){throw new NoSuchElementException("Index Out Of Range");}

        Node<Item> result = head;
        for(int i = 0; i < index; i++){
            result = result.next;
        }

        return result;
    }

    // set(int index, Item item)_Set the value of the node located at given index. : O(n). Internally calls get(int index).
    public boolean set(int index, Item item){
        // Edge-case is handled by get().
        Node<Item> target = get(index);

        // I could call addFirst(item) but I thought doing so might blur the boundary between two methods.
        if(target == null){System.out.println("Currently Empty. Please try adding new element first."); return false;}

        target.item = item;

        return true;
    }


    // insert(int index, Item item) : O(n). Internally calls get(index - 1) to update its pointer to point newly inserted node.
    public boolean insert(int index, Item item){

        // Edge case handling : If (index == 0). This has to be handled before calling get() or it can cause exception directly.
        if(index == 0){addFirst(item); return true;}
        if(index == size){addLast(item); return true;}

        Node<Item> newNode = new Node<>(item);
        Node<Item> priorIdx = get(index - 1);
        newNode.next = priorIdx.next;
        priorIdx.next = newNode;

        size ++;

        return true;
    }

    // insert(int index, Node<Item> node) : O(n). Insert pre-created Node object into given index location.
    public boolean insert(int index, Node<Item> node){

        if(index == 0){addFirst(node);}
        if(index == size){addLast(node);}

        Node<Item> priorIdx = get(index - 1);

        node.next = priorIdx.next;
        priorIdx.next = node;

        size ++;

        return true;
    }


    // remove(int index) : O(n). Remove node at given index location. Internally calls get().
    public Node<Item> remove(int index){
        // Edge case handling.
        if(index == 0){return removeFirst();}
        if(index == size - 1){return removeLast();}

        Node<Item> priorIdx = get(index - 1);
        Node<Item> removed = priorIdx.next;

        priorIdx.next = removed.next;
        removed.next = null;

        size--;

        if(size == 0){head = null; tail = null;}

        return removed;
    }

    // removeAll() : O(n). Delete all elements.
    public void removeAll(){
        // This makes all the nodes unreachable, thus their memory space will be reclaimed by GC.
        // But the SingleLinkedList instance will be alive.
        head = null;
        tail = null;
        size = 0;
        // Logic below is my first version of implementation, But it has possibility for causing memory leak.
//        Node<Item> eraserFront = head;
//        Node<Item> eraserRear = eraserFront;
//
//        for(int i = 0; i < size; i++){
//            eraserFront = eraserRear;
//            eraserRear = eraserRear.next;
//            eraserFront.next = null;
//        }
    }


    // reverse() : O(n). Reverse current linked-list.
    public void reverse(){
        // 1. Swap head & tail.
        Node<Item> temp = head;
        head = tail;
        tail = temp;

        // 2. Three-finger Initialize.
        Node<Item> before = null;
        Node<Item> after = temp;

        // 3. Iterate & reverse every node's next pointer.
        for(int i = 0; i < size; i++){
            after = temp.next;
            temp.next = before;
            before = temp;
            temp = after;
        }
    }

    // size() : O(1)
    public int size(){
        return size;
    }

    // toString() : Using BufferedWriter
    public String toString(){
        // Edge Case handling : Empty
        if(size == 0){System.out.println("Currently Empty"); return null;}

        StringBuilder sb = new StringBuilder();
        sb.append("Current Singly Linked_List Status : \n");
        Node<Item> loc = head;

        while(loc != null){

            String currentItem = loc.toString();
            sb.append(currentItem);
            if(loc == head){
                sb.append("(head) -> ");
            }
            else if(loc.next == null){
                sb.append("(tail)_END");
            } else{
                sb.append(" -> ");
            }

            loc = loc.next;
        }
        return sb.toString();
    }

    // iterator
    public Iterator<Item> iterator(){return new LLIterator();}

    private class LLIterator implements Iterator<Item>{

        Node<Item> iterLoc = head;
        int remaining;

        public LLIterator(){
            this.remaining = size;
        }
        public boolean hasNext(){return remaining > 0;}

        public Item next(){
            if(!hasNext()){throw new NoSuchElementException("There's no remained node.");}

            // SingleLinkedList<Item> sl = SingleLinkedList.this;
            // - Opted out for consideration of its inefficiency, but the way it illustrates
            //    'how to refer OuterClass instance inside InnerClass' seemed to be worth commented.

            Item returned;
            returned = iterLoc.item;
            iterLoc = iterLoc.next;
            remaining--;

            return returned;
        }

        public void remove(){}

    }


    // TestApplication.
    public static void main(String[] args){

        // Initialization Test
        SingleLinkedList<Integer> sl = new SingleLinkedList<>();

        // Basic Adding Operation Test. ==> Passed!
        sl.addFirst(0);
        sl.addLast(1);
        sl.addLast(2);
        sl.addLast(3);
        sl.addFirst(-1);

        System.out.printf("Current head : %s\n", sl.head);
        System.out.printf("Current tail : %s\n", sl.tail);
        System.out.println(sl); // -1(head) -> 0 -> 1 -> 2 -> 3(tail)_END

        // removeAll() Test. ==> Passed!
        sl.removeAll();
        System.out.println(sl);
        System.out.printf("Current head : %s\n", sl.head);
        System.out.printf("Current tail : %s\n", sl.tail);

        // insert(int index, Item item) & insert(int index, Node<Item> node) Test ==> Passed!
        sl.insert(0, 0);
        sl.insert(1, 1);
        sl.insert(2, 2);
        sl.insert(2, 3);
        sl.insert(3, 4);
        Node<Integer> newNode = new Node<>(5);
        sl.insert(4, newNode);

        System.out.println(sl);

        // get() & set() Test ==> Passed!
        for(int i = 0; i < sl.size(); i++){
            if(Integer.parseInt(sl.get(i).toString()) != i){
                sl.set(i, i);
            }
            System.out.println(sl.get(i));
        }
        System.out.println(sl);

        // Status Method Test : size(), this.head, this.tail ==> Passed
        System.out.printf("Current head : %s\n", sl.head); // Expected = 0/ Result = 0
        System.out.printf("Current tail : %s\n", sl.tail); // Expected = 5 / Result = 5
        System.out.printf("Current size : %d\n", sl.size()); // Expected = 6 / Result = 6

        // remove(), removeLast(), removeFirst() Test
        sl.remove(4); // Removed - index : 4 / item : 4
        sl.remove(4); // Removed - index : 4(removeLast()) / item 5;
        sl.removeFirst();

        System.out.println(sl); // Expected : 1(head) -> 2 -> 3(tail)
        System.out.printf("Current head : %s\n", sl.head); // Expected = 1/ Result = 1
        System.out.printf("Current tail : %s\n", sl.tail); // Expected = 3 / Result = 3
        System.out.printf("Current size : %d\n", sl.size()); // Expected = 3 / Result = 3

        // reverse() Test
        sl.reverse();
        System.out.println(sl); // Expected : 3(head) -> 2 -> 1(tail)
        System.out.printf("Current head : %s\n", sl.head); // Expected = 3/ Result = 3
        System.out.printf("Current tail : %s\n", sl.tail); // Expected = 1 / Result = 1
        System.out.printf("Current size : %d\n", sl.size()); // Expected = 3 / Result = 3


        /*
        TEST DONE!
         */








    }
}
