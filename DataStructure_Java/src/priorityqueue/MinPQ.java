package priorityqueue;

public class MinPQ<K extends Comparable<K>> {
    private K[] pq;
    private int EoL; // End of List.

    public MinPQ(int size) {
        pq = (K[]) new Comparable[size + 1];
        EoL = 0;
    }

    public void insert(K key) {
        pq[++EoL] = key;
        promote(EoL);
    }

    public K deleteMin() {
        K min = pq[1];
        exch(1, EoL);
        EoL--;
        pq[EoL + 1] = null;
        demote(1);

        return min;
    }

    public boolean isEmpty() {
        return EoL == 0;
    }

    private boolean greater(int thisIdx, int thatIdx) {
        return pq[thisIdx].compareTo(pq[thatIdx]) > 0;
    }

    private void exch(int thisIdx, int thatIdx) {
        K temp = pq[thisIdx];
        pq[thisIdx] = pq[thatIdx];
        pq[thatIdx] = temp;
    }

    private void promote(int current) {
        while (current > 1 && greater(current / 2, current)) {
            exch(current / 2, current);
            current = current / 2;
        }
    }

    private void demote(int current) {
        while (current * 2 <= EoL) {
            int child = current * 2;
            if (child < EoL && !(greater(child + 1, child))){child++;} // Picking smaller child.
            if (!greater(current, child)){break;}
            exch(current, child);
            current = child;
        }
    }



    // Test Client
    public static void main(String[] args){

        MinPQ<String> pq = new MinPQ<>(8);

        // Testing String keys.
        pq.insert("A");
        pq.insert("Z");
        pq.insert("C");
        pq.insert("S");
        pq.insert("K");
        pq.insert("L");
        pq.insert("D");
        pq.insert("E");

        while(!pq.isEmpty()){
            System.out.println(pq.deleteMin());
        }
        // Expected : A - C - D - E - K - L - S - Z
        // Result : Same.

    }
}
