package linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<Item> implements Iterable<Item> {
    static class Node<Item>{
        private Item item;
        Node<Item> next;
        Node<Item> prev;

        // Node Constructor I : Empty Node Initializer
        public Node(){
            this.next = null;
            this.prev = null;
        }

        // Node Constructor II : Node /w item initializer
        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }

        public String toString(){
            String result;
            if(this.item == null){
                result = "null";
            } else {result = this.item.toString();}

            return result;
        }
    }

    // Class Member
    int size;
    Node<Item> head;
    Node<Item> tail;

    // DoublyLinkedList Constructor I : Empty Initializer
    public DoublyLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    // DoublyLinkedList Constructor II : Initializer /w given node.
    public DoublyLinkedList(Node<Item> initNode){
        // Nullifying initNode's prev & next is sorely depends on implementor's decision.
        initNode.prev = null;
        initNode.next = null;
        head = initNode;
        tail = initNode;
        size = 1;
    }

    public DoublyLinkedList(Item item){
        Node<Item> initNode = new Node<>(item);
        head = tail = initNode;
        size = 1;
    }

    // int size() : O(1). Returns the number of elements.
    public int size(){return size;}

    // boolean isEmpty() : O(1). Checks if doubly linked list is empty currently as a boolean result.
    public boolean isEmpty(){return size == 0;}

    /**
     * I/O Operations
     * <p>
     * 1. Adding Operations
     * <p>
     * - addFirst()
     * <p>
     * - addLast()
     * <p>
     * - insert()
     *
     * <p>
     * 2. Removing Operations
     * <p>
     * - removeFirst()
     * <p>
     * - removeLast()
     * <p>
     * - remove()
     * <p>
     * - removeAll()
     * <p>
     * 3. Access & Modification
     * <p>
     * - get() : Access
     * <p>
     * - set() : Modification
     */

    // void addFirst(Item item) : O(1). Internally initialize a new node with given item, then add it into current head place.
    public void addFirst(Item item){
        Node<Item> newNode = new Node<>(item);
        newNode.next = head;
        head.prev = newNode;

        newNode.prev = null;
        head = newNode;
        // Edge-case Handling
        if(size == 0){tail = newNode;}

        size++;
    }
    // void addFirst(Node<Item> node) : O(1). Add given node at current head place.
    public void addFirst(Node<Item> node){
        node.prev = null;
        node.next = head;
        head.prev = node;

        head = node;
        // Edge-case Handling
        if(size == 0){tail = node;}

        size++;
    }
    // void addLast(Item item) : O(1).
    public void addLast(Item item){
        Node<Item> newNode = new Node<>(item);

        if(size == 0){head = tail = newNode;}
        else{
            newNode.next = null;
            newNode.prev = tail;

            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    // void addLast(Node<Item> node) : O(1).
    public void addLast(Node<Item> node){
        node.prev = tail;
        node.next = null;
        tail.next = node;

        tail = node;
        //Edge-case Handling
        if(size == 0){head = node;}

        size++;
    }

    // Node<Item> removeFirst() : O(1).
    public Node<Item> removeFirst(){
        // Edge-case I Handling
        if(size == 0){throw new NoSuchElementException("Currently Empty. Cannot Processed.");}

        Node<Item> result;
        result = head;

        // Simultaneously process logic and edge-case II handling.
        if(size == 1){
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
            result.next = null;
        }
        size--;

        return result;
    }

    // Node<Item> removeLast() : O(1).
    public Node<Item> removeLast(){
        //Edge-case I handling.
        if(size == 0){throw new NoSuchElementException("Currently Empty. Cannot be processed");}
        Node<Item> result = tail;

        if(size == 1){head = tail = null;}
        else{
            tail = tail.prev;
            tail.next = null;
            result.prev = null;
        }
        size--;

        return result;
    }

    // Node<Item> get(int index) : O(n) - It's slightly efficient than that of SingleLinkedList, as it only iterates half of n times.
    public Node<Item> get(int index){
        // Edge-cases handling
        if(size == 0){return null;}
        if(index < 0 || index > size - 1){throw new IndexOutOfBoundsException("Given index is out of bound.");}

        Node<Item> iter;

        if(index < (size - 1) / 2){
            iter = head;
            for(int i = 0; i < index; i++){
                iter = iter.next;
            }
        } else {
            iter = tail;
            for(int i = size - 1; i > index; i--){
                iter = iter.prev;
            }
        }

        return iter;
    }

    // boolean set(int index, Item item) : O(n). Sets the item of node at given index to given item.
    public boolean set(int index, Item item){
        //Edge-case Handling
        if(size == 0){return false;}
        // Other edge-cases are handled by calling get(int index).
        Node<Item> target = get(index);
        target.item = item;

        return true;
    }

    // boolean insert(int index, Node<Item> node) : O(n).
    public boolean insert(int index, Node<Item> node){
        // Edge-case Handling
        if(index == 0 || size == 0){addFirst(node); return true;}
        if(index == size - 1){addLast(node); return true;}

        Node<Item> priorIdx = get(index - 1);
        Node<Item> afterIdx = priorIdx.next;

        node.next = afterIdx;
        node.prev = priorIdx;

        priorIdx.next = node;
        afterIdx.prev = node;

        size++;

        return true;
    }

    // boolean insert(int index, Item item) : O(n).
    public boolean insert(int index, Item item){
        if(index == 0 || size == 0){addFirst(item); return true;}
        if(index == size){addLast(item); return true;}

        Node<Item> newNode = new Node<>(item);
        Node<Item> priorIdx = get(index - 1);
        Node<Item> afterIdx = priorIdx.next;

        newNode.next = afterIdx;
        newNode.prev = priorIdx;

        priorIdx.next = newNode;
        afterIdx.prev = newNode;

        size++;

        return true;
    }

    // Node<Item> remove(int index) : O(n). Internally calls get(). Fetch and remove a node at given index, and return it.
    public Node<Item> remove(int index){
        Node<Item> target = get(index);
        if(target == head){return removeFirst();}
        if(target == tail){return removeLast();}

        Node<Item> priorIdx = target.prev;
        Node<Item> afterIdx = target.next;
        priorIdx.next = afterIdx;
        afterIdx.prev = priorIdx;

        size --;
        if(size == 0){head = tail = null;}

        return target;
    }

    public boolean removeAll(){head = tail = null; return true;}

    public boolean reverse(){
        if(size == 0){System.out.println("Nothing To Reverse."); return false;}

        // Swapping head & tail to each other.
        Node<Item> temp = head;
        head = tail;
        tail = temp;

        Node<Item> iter = head;
        // Swap prev & next of each node during iteration.
        for(int i = 0; i < size; i++){
            temp = iter.prev;
            iter.prev = iter.next;
            iter.next = temp;

            iter = iter.next;
        }
        return true;
    }
    // String toString() : To make whole Doubly Linked List instance be printable.
    public String toString(){

        if(isEmpty()){String result = "Currently Empty"; return result;}
        StringBuilder sb = new StringBuilder();
        Node<Item> iter = head;
        for(int i = 0; i < size; i++){
            sb.append(iter.toString());
            if(iter == head){sb.append("(head)");}
            if(iter == tail){sb.append("(tail)"); break;}
            sb.append(" -> ");
            iter = iter.next;
        }
        return sb.toString();
    }


    public Iterator<Item> iterator(){return new DLLIterator();}
    private class DLLIterator implements Iterator<Item>{

        int remaining;
        Node<Item> iterLoc = head;
        public DLLIterator(){remaining = size;}

        // Q: 'remove()' in 'linkedlist.DoublyLinkedList.DLLIterator' clashes with 'remove()' in 'java.util.Iterator'; attempting to assign weaker access privileges ('private'); was 'public'
        public boolean hasNext(){return remaining > 0;}

        // Q : next()' in 'linkedlist.DoublyLinkedList.DLLIterator' clashes with 'next()' in 'java.util.Iterator'; attempting to use incompatible return type
        public Item next(){
            Node<Item> temp = iterLoc;
            iterLoc = iterLoc.next;
            remaining--;

            return temp.item;
        }
        // Optional
        public void remove(){}
    }


    // Test Program
    public static void main(String[] args){

        // Empty DoublyLinkedList Constructor Test => Passed!
        DoublyLinkedList<Integer> ddl = new DoublyLinkedList<>();
        System.out.println(ddl.head);
        System.out.println(ddl.tail);

        // Item-given DoublyLinkedList Constructor Test => Passed!
        ddl = new DoublyLinkedList<>(3);
        System.out.println(ddl.head);
        System.out.println(ddl.tail);

        // remove() & DoublyLinkedList.toString() test => Passed!
        ddl.remove(0);
        System.out.println(ddl);

        // Add Operations Test ==> Passed!
        ddl.addLast(1);
        ddl.addFirst(0);
        ddl.addLast(2);
        ddl.addLast(4);
        ddl.insert(3, 3);
        System.out.println(ddl); // Expected & Result : 0(head) -> 1 -> 2 -> 3 -> 4(tail)

        // reverse() Test : Passed!
        ddl.reverse();
        System.out.println(ddl);

        // removeFirst(), removeLast(), size() Test : Passed!
        ddl.removeFirst();
        ddl.removeLast();
        System.out.println(ddl);
        System.out.println(ddl.size());

        // iterator() Test : Passed!
        for(Object o : ddl){
            System.out.println(o);
        }
    }


}
