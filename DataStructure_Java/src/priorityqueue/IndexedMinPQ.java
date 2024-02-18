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
     * <p> - Current heap order per index.
     * <p> - If qp[0] = 1, It means that (key whose index is 0) = (pq[0]) has heap order '1'. </p>
     */
    private K[] keys;
    private int[] pq;
    private int[] qp;

    private int N; // End - of - List Locator;

    public IndexedMinPQ(int size){
        N = size;
        keys = (K[])new Comparable[N];
        pq = new int[N  + 1];
        qp = new int[N];
    }

    public void insert(int idx, K key){}

    public int deleteMin(){}

    public void updateKey(int idx, K newKey){}

    private void promote(){}
    private void demote(){}

    private boolean greater(){}

    private void exch(){}

    public boolean isEmpty(){return N == 0;}

    public boolean contains(int i){}

    public int size(){}
}
