// Java program for Prim's MST for
// adjacency list representation of graph
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Assignment24 {
    class node1 {

        int dest;

        int weight;

        node1(int a, int b) {
            dest = a;
            weight = b;
        }
    }

    static class Graph {

        int V;

        LinkedList<node1>[] adj;

        Graph(int e) {
            V = e;
            adj = new LinkedList[V];
            for (int o = 0; o < V; o++)
                adj[o] = new LinkedList<>();
        }
    }

    class node {
        int vertex;
        int key;
    }

    class comparator implements Comparator<node> {

        @Override
        public int compare(node node0, node node1) {
            return node0.key - node1.key;
        }
    }

    void addEdge(Graph graph, int src, int dest, int weight) {

        node1 node0 = new node1(dest, weight);
        node1 node = new node1(src, weight);
        graph.adj[src].addLast(node0);
        graph.adj[dest].addLast(node);
    }

    void prims_mst(Graph graph) {

        Boolean[] mstset = new Boolean[graph.V];
        node[] e = new node[graph.V];

        int[] parent = new int[graph.V];

        for (int o = 0; o < graph.V; o++)
            e[o] = new node();

        for (int o = 0; o < graph.V; o++) {

            mstset[o] = false;

            e[o].key = Integer.MAX_VALUE;
            e[o].vertex = o;
            parent[o] = -1;
        }

        mstset[0] = true;

        e[0].key = 0;

        // Use TreeSet instead of PriorityQueue as the remove function of the PQ is O(n)
        // in java.
        PriorityQueue<node> queue = new PriorityQueue<node>(new comparator());

        for (int o = 0; o < graph.V; o++)
            queue.add(e[o]);

        while (!queue.isEmpty()) {

            node node0 = queue.poll();

            mstset[node0.vertex] = true;

            for (node1 iterator : graph.adj[node0.vertex]) {

                if (mstset[iterator.dest] == false) {
                    if (e[iterator.dest].key > iterator.weight) {
                        queue.remove(e[iterator.dest]);
                        e[iterator.dest].key = iterator.weight;
                        queue.add(e[iterator.dest]);
                        parent[iterator.dest] = node0.vertex;
                    }
                }
            }
        }

        for (int o = 1; o < graph.V; o++)
            System.out.println(parent[o] + " " + "-" + " " + o);
    }

    public static void main(String[] args) throws FileNotFoundException {
        int V = 50;

        try (Scanner scanner = new Scanner(new File("./weighted_graph.txt"))){
            Graph graph = new Graph(V);
            Assignment24 e = new Assignment24();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(" ");
                e.addEdge(graph, Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                e.addEdge(graph, Integer.parseInt(data[1]), Integer.parseInt(data[0]), Integer.parseInt(data[2]));
            }
            e.prims_mst(graph);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
