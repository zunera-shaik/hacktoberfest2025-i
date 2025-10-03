import java.util.*;

class MinCostMaxFlow {

    // Represents an edge in the graph
    static class Edge {
        int to;
        int capacity;
        int cost;
        int flow;
        int reverseEdgeIndex;

        public Edge(int to, int capacity, int cost, int reverseEdgeIndex) {
            this.to = to;
            this.capacity = capacity;
            this.cost = cost;
            this.flow = 0;
            this.reverseEdgeIndex = reverseEdgeIndex;
        }
    }

    private int numNodes;
    private List<List<Edge>> graph;

    /**
     * Constructor for the MinCostMaxFlow class.
     * @param n The number of nodes in the graph (0 to n-1).
     */
    public MinCostMaxFlow(int n) {
        this.numNodes = n;
        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
    }

    /**
     * Adds a directed edge to the flow network.
     * @param from The starting node.
     * @param to The ending node.
     * @param capacity The maximum flow through the edge.
     * @param cost The cost per unit of flow through the edge.
     */
    public void addEdge(int from, int to, int capacity, int cost) {
        // Forward edge
        Edge forward = new Edge(to, capacity, cost, graph.get(to).size());
        graph.get(from).add(forward);

        // Reverse (residual) edge: capacity 0, cost -cost
        Edge backward = new Edge(from, 0, -cost, graph.get(from).size() - 1);
        graph.get(to).add(backward);
    }

    /**
     * Solves the Min-Cost Max-Flow problem using the Successive Shortest Path (SSP)
     * algorithm with Bellman-Ford for finding the shortest augmenting path.
     * @param source The source node.
     * @param sink The sink node.
     * @param maxFlowLimit The maximum flow desired (use a large number for Max Flow).
     * @return A long array: [0] -> Total Min Cost, [1] -> Total Max Flow.
     */
    public long[] minCostMaxFlow(int source, int sink, int maxFlowLimit) {
        long totalFlow = 0;
        long minCost = 0;
        
        while (totalFlow < maxFlowLimit) {
            // dist: array to store shortest path cost from source to all other nodes
            long[] dist = new long[numNodes];
            Arrays.fill(dist, Long.MAX_VALUE);
            dist[source] = 0;

            // parentEdge: stores the index of the edge used to reach this node
            int[] parentEdge = new int[numNodes];
            // parentNode: stores the previous node in the shortest path
            int[] parentNode = new int[numNodes];
            
            // Flag to detect if a path was augmented in this iteration (Bellman-Ford)
            boolean pathFound = false;
            
            // Bellman-Ford algorithm to find the shortest path from source to sink in the residual graph
            // Iterates N-1 times to ensure all paths up to length N-1 are considered
            for (int i = 0; i < numNodes; i++) {
                pathFound = false;
                for (int u = 0; u < numNodes; u++) {
                    if (dist[u] == Long.MAX_VALUE) continue;
                    
                    for (int vIndex = 0; vIndex < graph.get(u).size(); vIndex++) {
                        Edge edge = graph.get(u).get(vIndex);
                        int v = edge.to;

                        // Check for residual capacity and an improving path
                        if (edge.capacity - edge.flow > 0 && dist[u] + edge.cost < dist[v]) {
                            dist[v] = dist[u] + edge.cost;
                            parentNode[v] = u;
                            parentEdge[v] = vIndex;
                            pathFound = true;
                        }
                    }
                }
                // Optimization: if no relaxation occurred in a full pass, we can stop early.
                if (!pathFound) break;
            }

            // If the sink is unreachable, no more augmenting paths exist
            if (dist[sink] == Long.MAX_VALUE) {
                break;
            }
            
            // --- Augment the flow along the shortest path ---
            
            // Find the maximum flow (bottleneck capacity) along the path
            int pathFlow = maxFlowLimit - (int)totalFlow;
            int curr = sink;
            while (curr != source) {
                int prev = parentNode[curr];
                int edgeIndex = parentEdge[curr];
                pathFlow = Math.min(pathFlow, graph.get(prev).get(edgeIndex).capacity - graph.get(prev).get(edgeIndex).flow);
                curr = prev;
            }

            // Update the flow and cost
            totalFlow += pathFlow;
            minCost += (long)pathFlow * dist[sink];
            
            // Update flow in both forward and reverse residual edges
            curr = sink;
            while (curr != source) {
                int prev = parentNode[curr];
                int edgeIndex = parentEdge[curr];
                Edge forwardEdge = graph.get(prev).get(edgeIndex);
                
                // Update forward edge flow
                forwardEdge.flow += pathFlow;
                
                // Update reverse edge capacity/flow
                Edge reverseEdge = graph.get(forwardEdge.to).get(forwardEdge.reverseEdgeIndex);
                reverseEdge.capacity += pathFlow; // The reverse edge's flow is implicitly tracked by its capacity
                // For a true residual graph implementation, it's (reverseEdge.flow -= pathFlow) 
                // but setting capacity handles it cleanly here.
                
                curr = prev;
            }
        }

        return new long[]{minCost, totalFlow};
    }

    // --- Example Usage ---
    public static void main(String[] args) {
        // Problem: A simple network with 4 nodes (0, 1, 2, 3)
        // Source = 0, Sink = 3
        MinCostMaxFlow mcmf = new MinCostMaxFlow(4);

        // Edge (from, to, capacity, cost)
        mcmf.addEdge(0, 1, 10, 2);
        mcmf.addEdge(0, 2, 8, 4);
        mcmf.addEdge(1, 2, 5, 2);
        mcmf.addEdge(1, 3, 5, 6);
        mcmf.addEdge(2, 3, 10, 3);
        
        int source = 0;
        int sink = 3;
        // The total possible flow is 18 (10 from 0->1, 8 from 0->2)
        int maxFlowLimit = 100; // Find the absolute max flow with min cost

        long[] result = mcmf.minCostMaxFlow(source, sink, maxFlowLimit);

        System.out.println("--- Min-Cost Max-Flow Result ---");
        System.out.println("Max Flow: " + result[1]);
        System.out.println("Min Cost: " + result[0]);
        
       
    }
}
