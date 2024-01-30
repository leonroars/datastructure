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
    /*------------------------------ Deletion Methods ------------------------------*/
    public void deleteMin(){
        root = deleteMin(root);
    }

    /**
     * Delete the node which having the smallest key in given tree or subtree.<br>
     * <p>Code from <i>Algorithms</i> by Robert Sedgewick & Kevin Wayne.
     * @param x - A root of whole tree or subtree.
     * @return A root of tree that minimum node has been deleted.
     */
    private Node<K, V> deleteMin(Node<K, V> x){
        if(x.left == null){return x.right;} // Base Case
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(K key){
        root = delete(root, key);
    }

    /**
     * This deletion is implementation of deletion algorithm proposed by Thomas Hibbard in 1962.<br>
     * <p>Code from <i>Algorithms</i> by Robert Sedgewick & Kevin Wayne.
     * @param x - Starting point. A root of whole tree or subtree.
     * @param key - Target key value.
     * @return A root of tree or subtree that the target node is eliminated if it has existed.
     */
    private Node<K, V> delete(Node<K, V> x, K key){
        if(x == null){return null;} // Base Case I
        int cmp = key.compareTo(x.key);
        if(cmp == 0){ // Base Case II
            // Handling these two cases separately can improve efficiency as it eliminates unnecessary procedures.
            if(x.left == null){return x.right;}
            if(x.right == null){return x.left;}

            Node<K, V> successor = min(x.right); // Find & Assign the smallest node in x's right subtree. This will replace x.
            successor.right = deleteMin(x.right);
            successor.left = x.left;
            x = successor; // Replace x with its successor.
        }
        if(cmp < 0){x.left = delete(x.left, key);}
        if(cmp > 0){x.right = delete(x.right, key);}

        // Since updating 'node.N' is not only for the node that has been replaced but for all its upper level nodes,
        // updating node size should be done at the outer area of case-handiling(if) blocks.
        // So that every time we come back to upper-level stack frame from end-most stack frame,
        // Each node.N can be safely updated properly.
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    /*------------Order-base Operations Implementation---------*/

    /**
     * User-interface method. It internally invokes class-private min(Node x) method.
     * @return The smallest key.
     */
    public K min(){
        return min(root).key;
    }

    /**
     * Recursively explores & return the node which is located at the deepest depth in the left-most subtree side.
     * @param x - Start point node.
     * @return The node which has the smallest key in tree.
     */
    private Node<K, V> min(Node<K, V> x){
        if(x == null){throw new IllegalArgumentException("Cannot take null input as argument.");}
        if(x.left == null){return x;}
        return min(x.left);
    }

    /**
     * User-interface method. It internally invokes class-private max(Node x) method.
     * @return The largest key.
     */
    public K max(){
        return max(root).key;
    }

    /**
     * Recursively explores & return the node which is located at the deepest depth in the right-most subtree side.
     * @param x - Start point node.
     * @return The node which has the largest key in tree.
     */
    private Node<K, V> max(Node<K, V> x){
        if(x == null){throw new IllegalArgumentException("Cannot take null input as argument.");}
        if(x.left == null){return x;}
        return max(x.right);
    }

    /**
     * User-interface method. Returns the largest key in the keys that is smaller than given key.
     * @param key - target key
     * @return The largest key value among the ones which is smaller than given key.
     */
    public K floor(K key){
        return floor(root, key).key;
    }

    /**
     * Recursively explores tree start from given node and returns the largest key among the smaller keys.<br>
     * (Largest key <= given key)
     * <p>
     * The way it works in recursive manner is concise, yet quite tricky to understand. WATCH CAREFULLY.
     * @param x - Start point node.
     * @param key - target key.
     * @return The largest key value in the keys that is smaller than given key.
     */
    private Node<K, V> floor(Node<K, V> x, K key){
        if(x == null){return null;}

        int cmp = key.compareTo(x.key);
        if(cmp < 0){return floor(x.left, key);}
        if(cmp == 0){return x;}

        Node<K, V> resultFromRSub = floor(x.right, key);
        if(resultFromRSub != null){return resultFromRSub;}
        else return x; // // This means, that there's no smaller node in right subtree of current node. Thus, current node is the floor.
    }

    public K ceiling(K key){
        return ceiling(root, key).key;
    }

    private Node<K, V> ceiling(Node<K, V> x, K key){
        if(x == null){return null;}

        int cmp = key.compareTo(x.key);
        if(cmp == 0){return x;}
        if(cmp > 0){return ceiling(x.right, key);}
        Node<K, V> resultFromLSub = floor(x.left, key);
        if(resultFromLSub != null){return resultFromLSub;}
        else return x;
    }



    /*---------------Utility Method Implementation------------*/

    /**
     * Traverse given tree in BFS manner, and append every node in StringBuilder.
     * <br>
     * Single line in StringBuilder will hold the nodes in same level and delimited by space.
     */
    public void printTree(){
        
    }
    public static void main(String[] args){
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.putNode(5, "root A_Added First");
        bst.putNode(2, "B_second");
//        bst.put(8, )

    }
}
