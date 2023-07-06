package queue;
import java.util.*;
import java.util.Queue;

class BFS {
    public static void main(String[] args) {
        // Create a sample graph using adjacency matrix.
        int[][] graph = {
                {0, 1, 1, 0, 0, 0},
                {1, 0, 0, 1, 1, 0},
                {1, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1},
                {0, 1, 1, 0, 0, 1},
                {0, 0, 0, 1, 1, 0}
        };

        int startVertex = 0; // Starting vertex for BFS

        // Perform BFS
        bfs(graph, startVertex);
    }

    public static void bfs(int[][] graph, int startVertex) {
        int numVertices = graph.length;
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();

        // Mark the start vertex as visited and enqueue it
        visited[startVertex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            System.out.print(currentVertex + " ");

            // Traverse all adjacent vertices of the current vertex
            for (int neighbor = 0; neighbor < numVertices; neighbor++) {
                if (graph[currentVertex][neighbor] == 1 && !visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }
}