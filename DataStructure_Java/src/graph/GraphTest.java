package graph;

import graph.Graph;
public class GraphTest {
    public static void main(String[] args){
        Graph G = new Graph(5);
        G.addEdge(0, 4);
        G.addEdge(0, 1);
        G.addEdge(1, 3);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);

        System.out.println(G);
        System.out.println(G.adj(0));
        System.out.println(G.E());
        System.out.println(G.DFS(0));

        // 정섭이한테 안전한 API 만들 때 고려해야할 사항 : 입력 안전장치 만들기 귀띔해주기
    }
}
