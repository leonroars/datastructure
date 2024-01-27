package tree;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class BinarySearchTree<K extends Comparable<K>, V> {

    /*----------------------------------------Class Field----------------------------------- */
    private Node<K, V> root;


    /*--------------------  Node class Declaration------------------- */
    // I declared it as static class, since I'm going to write test program inside this class file.
    // In such setting, generic type parameter has to be declared in class definition part.
    // If it's not static, then it doesn't have to be.
    static class Node<K, V>{
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;
        private int N; // The number of nodes in subtree which is rooted from this node instance.

        Node(K key, V value, int N) {
            this.key = key;
            this.value = value;
            this.N = 1;
        }
    }

    /*-------------------------------------Class Methods--------------------------------------*/

    /*-----------------------Status-Checking Methods---------------------------*/

    /**
     *
     * This is user-interface method of getting current tree size.
     * <br>
     * It internally invokes class-private method which takes reference to a root node as its argument.
     * <p></p>
     * @return The number of nodes in tree currently.
     */
    public int size(){
        return size(root);
    }

    /**
     * A class-private method. Not meant to be invoked by user directly.
     * <br>
     * It returns the number of nodes in subtree that is rooted from given node.
     *
     * @return The number of nods in subtree which is rooted from given node x.
     */
    @SuppressWarnings("unchecked")
    private int size(Node<K, V> x){
        if(x == null){return 0;}
        else{return x.N;}
    }

    /*--------------------------Node Instantiation Methods------------------------------*/
    /**
     * User-interface method for creating new node instance.
     * <p>
     * By defining user-interface method and make it invoke package-private Node() constructor,
     * <br>It guarantees a good amount of encapsulation.
     * @param key
     * @param value
     * @return
     */
    public Node<K, V> newNode(K key, V value, int N){return new Node<>(key, value, N);}


    /*--------------------------Retrieval Methods------------------------------*/

    /*------------Recursive Implementation------------*/
    public V getVal(K key){
        return getVal(root, key);
    }

    /**
     * @param x is root of the target subtree.
     * @param key is the target key.
     * @return A value of the node which has given key. If there's no such node, return null.
     */
    private V getVal(Node<K, V> x, K key){
        if(x == null){return null;}
        int cmp = key.compareTo(x.key);

        if(cmp > 0){return getVal(x.right, key);}
        else if(cmp < 0){return getVal(x.left, key);}
        else{return x.value;}
    }

    /*---------------Iterative Implementation------------*/

    /**
     * Return a node if there's any which has given key as its key.
     * <br>
     * If a tree is empty, throws <i>EmptyStackException()</i>;
     * <br>
     * If there's no such node, throws <i>NoSuchElementException()</i>;
     * @param key - target key.
     * @return A node which has given key.
     */
    public Node<K, V> get(K key){
        Node<K, V> traveler = root;
        if(traveler == null){throw new EmptyStackException();}

        while(traveler != null){
            int cmp = key.compareTo(traveler.key);

            if(cmp > 0){traveler = traveler.right;}
            if(cmp < 0){traveler = traveler.left;}
            if(cmp == 0){return traveler;}
        }
        throw new NoSuchElementException("There's no such node which has given key in tree at the moment.");
    }


    /*---------------------------- Insertion Methods ------------------------------*/

    /*------------Recursive Implementation------------*/

    /**
     * User-interface method.
     * <p>
     * It updates the reference of 'root' node from original to the result that came from private method putNode(Node x, K key, V value).
     * <p>
     * This mechanism well demonstrating the fact that 'A BST is a reference to a root node in implementation'.
     * @param key - Input key
     * @param value - Input value
     */
    public void putNode(K key, V value){
        root = putNode(root, key, value);
    }

    /**
     * It recursively explores & specifies the location where new node with given key, value has to be put in.
     * <p>
     * And if there's already a node having given key, update its value to given value.
     * @param x - A root node of target subtree.
     * @param key - Input key.
     * @param value - Input value.
     * @return A reference to a root node of whole updated tree.
     */
    private Node<K, V> putNode(Node<K, V> x, K key, V value){
        if(x == null){return newNode(key, value, 1);}

        int cmp = key.compareTo(x.key);

        if(cmp < 0){x.left = putNode(x.left, key, value);}
        if(cmp > 0){x.right = putNode(x.right, key, value);}
        if(cmp == 0){x.value = value;}

        // Updating the number of nodes in its subtree.
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    /*---------------Iterative Implementation------------*/
    private void put(K key, V value){
        if(root == null){root = newNode(key, value, 1);}

        Node<K, V> traveler = root;
        Node<K, V> prev = null;

        while(traveler != null){

            int cmp = key.compareTo(traveler.key);
            if(cmp < 0){prev = traveler; traveler = traveler.left;}
            if(cmp > 0){prev = traveler; traveler = traveler.right;}
            if(cmp == 0){traveler.value = value; return;}
        }
        traveler = newNode(key, value, 1);

        int cmp = key.compareTo(prev.key);
        if(cmp < 0){prev.left = traveler;}
        if (cmp > 0){prev.right = traveler;}
    }

    /*---------------Utility Method Implementation------------*/
    // BFS
    public void printTree(){

    }
    public static void main(String[] args){
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.putNode(5, "root A_Added First");
        bst.putNode(2, "B_second");
//        bst.put(8, )

    }
}