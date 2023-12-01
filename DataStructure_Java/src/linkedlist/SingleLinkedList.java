package linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;



public class SingleLinkedList<Item> implements Iterable<Item> {

    // Node definition.
    private class Node<Item>{
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
        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = head;

        head = newNode;
        // Edge-case handling
        if(size == 0){tail = newNode;}
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
        size++;
    }


    // removeFirst() : O(1). Returns the node head is pointing, and decrease size by 1. If size == 0, return null.
    public Node<Item> removeFirst(){
        // Edge-case handling I
        if(size == 0){return null;}

        Node<Item> target = head;
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
        if(size == 0){return null;}

        Node<Item> iter = head;

        while(iter.next != tail){
            iter = iter.next;
        }

        iter.next = null;
        tail = iter;

        size--;

        // Edge-case handling II
        if(size == 0){head = null; tail = null;}

        return iter;
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

        Node<Item> priorIdx = get(index - 1);
        Node<Item> newNode = new Node<>(item);
        newNode.next = priorIdx.next;
        priorIdx.next = newNode;

        size ++;

        return true;
    }

    // insert(int index, Node<Item> node) : O(n). Insert pre-created Node object into given index location.
    public boolean insert(int index, Node<Item> node){

        if(index == 0){addFirst(node);}

        Node<Item> priorIdx = get(index - 1);

        node.next = priorIdx.next;
        priorIdx.next = node;

        size ++;

        return true;
    }


    // remove(int index) : O(n). Remove node at given index location. Internally calls get().
    public Node<Item> remove(int index){
        // Edge case handling.
        if(index == 0){removeFirst();}
        if(index == size - 1){removeLast();}

        Node<Item> priorIdx = get(index - 1);
        Node<Item> removed = priorIdx.next;

        priorIdx.next = removed.next;
        removed.next = null;

        return removed;
    }


    // reverse() : O(n). Reverse current linked-list.
    public void reverse(){
        // 1. Swap head & tail.
        Node<Item> temp = head;
        head = tail;
        tail = head;

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
        StringBuilder sb = new StringBuilder();
        sb.append("Current Singly Linked_List Status : \n");
        Node<Item> loc = head;

        while(loc != null){
            Item locItem = loc.item;
            String currentItem = locItem.toString();
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

        Node<Item> iterLoc;
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
            iterLoc = head;
            returned = iterLoc.item;
            iterLoc = iterLoc.next;
            remaining--;

            return returned;
        }

        public void remove(){}

    }


    // TestApplication.
    public static void main(String[] args){
        SingleLinkedList<Integer> sl = new SingleLinkedList<>();
        sl.addFirst(0);
        sl.addLast(1);
        sl.addLast(2);
        sl.addLast(3);
        sl.addFirst(-1);

        System.out.println(sl);
        System.out.println(sl.get(3)); // Should return 2

        sl.reverse();
        System.out.println(sl);

    }
}
