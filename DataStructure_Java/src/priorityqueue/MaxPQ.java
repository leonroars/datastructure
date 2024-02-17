package priorityqueue;

import java.util.NoSuchElementException;

/**
 * <b><i>Binary-heap</i> structured Max Priority Queue</b>.
 * <p>
 * This implementation is strictly based on <i>Algorithms by Robert Sedgewick and Kevin Wayne</i>.
 * <p>
 * But I've changed some methods and variables names to make it more readable without a book-specific context.
 * @param <K> Generic Type parameter for key. Prioritiezed by this value.
 */
public class MaxPQ<K extends Comparable<K>>{
    private K[] pq; // Underlying Array.
    private int EoL; // End-of-List. Locating at the index of the last item.

    public MaxPQ(int size){
        // Binary-heap structured MaxPQ uses array indices for parent-child relationship representation.
        // Thus, actual index of the very first element is set to be 1.
        pq = (K[])new Comparable[size + 1];
        EoL = 0;
    }

    public boolean isEmpty(){return EoL == 0;}

    public void insert(K key){
        pq[++EoL] = key;
        promotion(EoL);
    }
    public K deleteMax(){
        if(isEmpty()){throw new NoSuchElementException("Currently Empty!");}
        K max = pq[1];
        exch(1, EoL);
        EoL--;
        pq[EoL + 1] = null; // Avoid loitering.
        demotion(1);

        return max;
    }


    // ***************** Re-heapifying Methods ******************* //

    /**
     * Compare its key with that of its parent.
     * <p>
     * If the key of current is greater than that of its parent, change their location.
     * <p>
     * Repeat those two steps till the heap-order is restored.
     * @param current Index of an element(key) that is going to be promoted if it has to be.
     */
    private void promotion(int current){
        while(current > 1 && less(current / 2, current)){
            exch(current / 2, current);
            current = current / 2;
        }
    }

    /**
     * Compare the key of current to that of its children - which are located at 2  * current, 2 * current + 1, correspondingly - .
     * <p>
     * If the key of current is larger thant that of one of its children, change their location.
     * <p>
     * Repeat until the heap-order is restored.
     * @param current Index of an element that is going to be demoted(go down) if it has to be.
     */
    private void demotion(int current){
        while(2*current <= EoL){
            int child = current * 2;
            if(child < EoL && less(child, child+1)){child++;} // Choosing bigger child.
            if(!less(current, child)){break;}
            exch(current, child);
            current =  child;
        }
    }

    // ***************** Inner Utility Methods ******************* //

    /**
     * Compare the elements each at thisIdx and thatIdx location.
     * <p>
     *
     * @param thisIdx Index of about-to-be-demoted or about-to-be-promoted element.
     * @param thatIdx
     * @return true if the element(key) at thisIdx is smaller than the other.
     */
    private boolean less(int thisIdx, int thatIdx){
        return pq[thisIdx].compareTo(pq[thatIdx]) < 0;
    }

    /**
     * Exchange elements(keys) at thisIdx with the one at thatIdx.
     * @param thisIdx
     * @param thatIdx
     */
    private void exch(int thisIdx, int thatIdx){
        K temp = pq[thisIdx];
        pq[thisIdx] = pq[thatIdx];
        pq[thatIdx] = temp;
    }

    // Test Program
    public static void main(String[] args){

        // Testing String keys.
        MaxPQ<String> strpq = new MaxPQ<>(8);
        strpq.insert("A");
        strpq.insert("Z");
        strpq.insert("C");
        strpq.insert("S");
        strpq.insert("K");
        strpq.insert("L");
        strpq.insert("D");
        strpq.insert("E");

        while(!strpq.isEmpty()){
            System.out.println(strpq.deleteMax());
        }
        // Expected : Z - S - L - K - E - D - C - A
        // Result : Same.

    }
}
