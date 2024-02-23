package priorityqueue;

/**
 * Indexed Minimum Priority Queue.
 * <p>
 * It's minimum-based variant of indexed priority queue.
 * <p>
 * What makes this different from classic priority queue is that this allows changing certain element's key(priority) by specifying the key of it.
 * <p>
 * Especially useful for finding <i>Minimum Spanning Tree(MST)</i> in undirected/edge-weighted graph using <i>eager version of <b>Prim's Algorithm</b></i>.
 * <p>
 * In such usage, index stands for vertex number & key stands for weight of edge that connected to this vertex.
 * <p> ex. If keys[0] = 0.38, It means that certain edge that its one endpoint is vertex '0', has weight 0.38.
 * @param <K> Key or Priority of each element.
 */
public class IndexedMinPQ <K extends Comparable<K>>{

    /**
     * <b>Three Underlying Array</b>
     * <p>
     * 1) <b>K[] keys</b>
     * <p> - Array of indexed keys. The mapping between indices & keys are immutable once after it's instantiated.</p>
     * <br></br>
     * 2) <b>int[] pq</b>
     * <p> - Actual storage that behaves in priority queue manner.
     * <p> - Stores element from index 1 to N, thus requires N + 1 size.
     * <p> - If pq[1] = 5, it means that keys[5] has the top-most heap order. </p>
     * <br>
     * 3) <b>int[] qp</b>
     * <p> - Current heap order of key that has corresponding index.
     * <p> - If qp[0] = 1, It means that (key whose index is 0) = (keys[0]) has heap order '1'. </p>
     */
    private K[] keys;
    private int[] pq;
    private int[] qp;

    private int N; // End - of - List Locator;

    public IndexedMinPQ(int size){
        N = 0;
        keys = (K[])new Comparable[size];
        pq = new int[size  + 1];
        qp = new int[size];
    }

    public void insert(int idx, K key){
        if(idx > keys.length - 1 || idx < 0){throw new IndexOutOfBoundsException("Index out of bound.");}
        keys[idx] = key;
        pq[++N] = idx;
        promote(N);
    }

    public int deleteMin(){
        int min = pq[1];
        pq[1] = pq[N];
        pq[N] = min;
        N--;
        pq[N + 1] = -1;
        demote(1);
        return min;
    }

    public void updateKey(int idx, K newKey){}

    private void promote(int thisHeapOrder){
        while(thisHeapOrder > 1 && !greater(thisHeapOrder, thisHeapOrder / 2)){
            exch(thisHeapOrder, thisHeapOrder / 2);
            thisHeapOrder = thisHeapOrder / 2;
        }
    }
    private void demote(int thisHeapOrder){
        while(thisHeapOrder * 2 <= N){
            int childIdx = thisHeapOrder * 2;
            if(!greater(childIdx, childIdx + 1)){childIdx++;}
            if(!greater(thisHeapOrder, childIdx)){break;}
            exch(thisHeapOrder, childIdx);
            thisHeapOrder = childIdx;
        }
    }

    private boolean greater(int thisHeapOrder, int thatHeapOrder){
        return keys[pq[thisHeapOrder]].compareTo(keys[pq[thatHeapOrder]]) > 0;
    }

    private void exch(int thisHeapOrder, int thatHeapOrder){
        int tempKey = pq[thisHeapOrder];
        qp[pq[thisHeapOrder]] = thatHeapOrder;
        qp[pq[thatHeapOrder]] = thisHeapOrder;
        pq[thisHeapOrder] = pq[thatHeapOrder];
        pq[thatHeapOrder] = tempKey;


    }

    public boolean isEmpty(){return N == 0;}

    public boolean contains(int i){
        return qp[i] != -1;
    }

    public int size(){
        return N;
    }

    public static void main(String[] args){
        IndexedMinPQ<String> pq = new IndexedMinPQ<>(8);
        pq.insert(0, "A");
        pq.insert(1, "M");
        pq.insert(2, "I");
        pq.insert(3, "N");
        pq.insert(4, "H");
        pq.insert(5, "E");
        pq.insert(6, "Z");
        pq.insert(7, "P");

        while(!pq.isEmpty()){
            System.out.println(pq.deleteMin());
        }
        // Expected : A - E - H - I - M - N - P - Z
        // Result : Same.
    }
}
