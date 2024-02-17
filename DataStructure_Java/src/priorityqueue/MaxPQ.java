package priorityqueue;

import java.util.NoSuchElementException;

public class MaxPQ<K extends Comparable<K>>{
    private K[] pq;
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
    private void promotion(int current){
        while(current > 1 && less(current / 2, current)){
            exch(current / 2, current);
            current = current / 2;
        }
    }

    private void demotion(int current){
        while(2*current <= EoL){
            int child = current * 2;
            if(child < EoL && less(child, child+1)){child++;} // Choosing bigger child.
            if(!less(current, child)){break;}
            exch(current, child);
            current =  child;
        }
    }

    private boolean less(int thisIdx, int thatIdx){
        return pq[thisIdx].compareTo(pq[thatIdx]) < 0;
    }

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
