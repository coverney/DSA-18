import java.util.HashSet;
import java.util.List;

public class MCCR {
    private static int minKey(int[] key, Boolean[] reference, int V) {
        //returns the index of the vertex with smallest key and a false value in reference
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < V; v++) {
            if (!reference[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }

        return min_index;
    }

    private static int[][] restructure(EdgeWeightedGraph G) {
        // turns the EdgeWeightedGraph into a 2d array which makes finding the adjacent vertices easier to visualize
        int[][] graph = new int[G.numberOfV()][G.numberOfV()];
        for (int i = 0; i < G.numberOfV(); i++) {
            HashSet<Edge> edges = (HashSet<Edge>) G.edges(i);
            for (Edge edge : edges) {
                graph[i][edge.other(i)] = edge.weight();
            }
        }
        return graph;
    }

    private static int printMST(int parent[], int n, int graph[][]) {
        // prints out the minimum spanning tree and calculates the total_weight
        int total_weight = 0;
        System.out.println("Edge   Weight");
        for (int i = 1; i < n; i++) {
            System.out.println(parent[i] + " - " + i + "    " +
                    graph[parent[i]][i]);
            total_weight += graph[parent[i]][i];
        }
        return total_weight;
    }

    public static int MCCR(EdgeWeightedGraph G) {
        int num_vertices = G.numberOfV();
        int[][] graph = MCCR.restructure(G);

        // parent array keeps track of previous vertex for a given index/vertex
        int[] parent = new int[num_vertices];

        // key array keeps track of the weights for a given index/vertex, used heavily to find minimum paths
        int[] key = new int[num_vertices];

        // reference array used to keep track of whether a vertex has been added to the output
        Boolean[] reference = new Boolean[num_vertices];

        // start by setting all the values of key to be "infinite" and all the values of reference to be false
        for (int i = 0; i < num_vertices; i++) {
            key[i] = Integer.MAX_VALUE;
            reference[i] = false;
        }

        //the first key should be the first vertex (0), the first vertex has no parent
        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < num_vertices - 1; count++) {
            // pick minimum key vertex from set of vertices and add it final list
            int u = MCCR.minKey(key, reference, num_vertices);
            reference[u] = true;

            // update key and parent index of adjacent vertices (those that have not been picked yet)
            // of the picked vertex
            for (int v = 0; v < num_vertices; v++) {
                if (graph[u][v] != 0 && !reference[v] && graph[u][v] < key[v]) {
//                  // set parent of adjacent vertex to be most recently picked vertex (u)
                    parent[v] = u;
                    // update the key to be the weight between vertices u and v
                    key[v] = graph[u][v];
                }
            }

        }

        return MCCR.printMST(parent, num_vertices, graph);
    }

}

