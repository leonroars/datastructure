package linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;



public class SingleLinkedList<Item> implements Iterable<Item> {

    // Node definition.
    private class Node<Item>{
        Item item;
        Node<Item> next;

        // Node Constructor I : With value as a parameter.
        public Node(Item item){
            this.item = item;
        }

        // Node Constructor II : No parameter.
        public Node(){
            this.item = null;
            this.next = null;
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

    // insert(int index, Item item)

    // insert(int index, Node<Item> node)


    // remove(int index)

    // reverse()

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

    }
}
