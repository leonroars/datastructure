package graph;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * Graph represented in <b>adjacency-list</b>.
 * <p>
 * Mostly from the one from the book Algorithms by Robert Sedgewick and Kevin Wayne,
 * <p>
 * But I've changed a bit for my own taste.
 */
public class Graph {
    private int V;
    private int E;
    private ArrayList<Integer>[] adj;
    private ArrayList<Integer> dfs = new ArrayList<>();
    private BitSet visited = new BitSet(this.V); // Creat V-bit size BitSet for storing visited vertices.

    /**
     * Empty Graph Constructor. Graph is initialized to have capacity as much as given V.
     * @param V - The number of vertices. (Graph Size)
     */
    public Graph(int V){
        adj = (ArrayList<Integer>[])new ArrayList[V]; // Basically, Java array doesn't support generic. For it to be so, it has to be "cast".
        this.V = V;
        this.E = 0;

        // Initializing vertex-indexed array to have empty ArrayList in each index.
        for(int v = 0; v < V; v++){
            adj[v] = new ArrayList<Integer>();
        }
    }
    /**
     * Return total number of vertices.
     * @return V - The number of vertices / Initialized size of current Graph instance
     */
    public int V(){return V;}

    /**
     * Return total number of edges.
     * @return E - The number of edges.
     */
    public int E(){return E;}

    public void addEdge(int v, int w){
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v]; // It returns ArrayList<Integer> located at index-v, which is iterable.
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < adj.length; i++){
            for(int adj : adj(i)){
                sb.append(i);
                sb.append(" - ");
                sb.append(adj);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * DFS Traversal Method. Returns visited vertices in DFS-traversal order starting from given vertex v.
     * @return ArrayList<Integer> dfs - Vertices visited in order of DFS.
     */
    public ArrayList<Integer> DFS(int v){
//        ArrayList<Integer> dfs = new ArrayList<>();
//        BitSet visited = new BitSet(this.V); // Creat V-bit size BitSet for storing visited vertices.
        visited.set(v);
        dfs.add(v);
        for(int u : adj[v]){
            if(!visited.get(u)){
                DFS(u);
            }
        }

        return dfs;
    }

//    public ArrayList<Integer> DFS(int v, int w){
//
//    }
}

