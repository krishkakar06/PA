/**
 * LeetCode 1971: Find if Path Exists in Graph
 *
 * Problem Description:
 * There is a bi-directional graph with 'n' vertices, where each vertex is labeled
 * from 0 to n - 1 (inclusive). The edges in the graph are represented as a 2D integer
 * array 'edges', where each edges[i] = [u_i, v_i] denotes a bi-directional edge between
 * vertex u_i and vertex v_i. Every vertex pair is connected by at most one edge, and
 * no vertex has an edge to itself.
 *
 * Given 'edges' and the integers 'n', 'source', and 'destination', return true if there
 * is a valid path from 'source' to 'destination', or false otherwise.
 *
 * Approaches Included in this Class:
 * 1. Breadth-First Search (BFS) using an Adjacency List (Primary 'validPath' method).
 *    - Time Complexity: O(V + E) where V = n and E = edges.length.
 *    - Space Complexity: O(V + E) for the adjacency list, queue, and visited array.
 * 2. Depth-First Search (DFS) - Iterative using an explicit Stack.
 *    - Time Complexity: O(V + E).
 *    - Space Complexity: O(V + E).
 * 3. Disjoint Set Union (Union-Find / DSU) with Path Compression and Union by Rank.
 *    - Time Complexity: O(V + E * alpha(V)), where alpha is the inverse Ackermann function (nearly O(1)).
 *    - Space Complexity: O(V) for the parent and rank arrays.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FindIfPathExistsInGraph {

    // =========================================================================
    // Approach 1: Breadth-First Search (BFS) with Adjacency List
    // =========================================================================

    /**
     * Determines if a valid path exists between source and destination using BFS.
     *
     * @param n           Number of vertices labeled from 0 to n - 1.
     * @param edges       2D array representing bi-directional edges between vertices.
     * @param source      Starting vertex.
     * @param destination Target vertex.
     * @return true if a valid path exists from source to destination, false otherwise.
     */
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        // Base case: If source and destination are the same vertex, a trivial path exists.
        if (source == destination) {
            return true;
        }

        // Step 1: Build the adjacency list for the bi-directional graph.
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u); // Graph is bi-directional
        }

        // Step 2: Initialize BFS data structures.
        // Visited array prevents processing nodes multiple times and avoids cycles.
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();

        // Start BFS traversal from the source vertex.
        queue.offer(source);
        visited[source] = true;

        // Step 3: Traverse the graph level by level.
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            // Check if we have reached the destination vertex.
            if (curr == destination) {
                return true;
            }

            // Explore all unvisited neighbors of the current vertex.
            for (int neighbor : adj.get(curr)) {
                if (!visited[neighbor]) {
                    // Early exit optimization: check neighbor before adding to queue.
                    if (neighbor == destination) {
                        return true;
                    }
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        // If the queue is exhausted and destination was not reached, no path exists.
        return false;
    }

    // =========================================================================
    // Approach 2: Depth-First Search (DFS) - Iterative with Stack
    // =========================================================================

    /**
     * Determines if a valid path exists between source and destination using iterative DFS.
     * Iterative DFS is preferred over recursion here to prevent java.lang.StackOverflowError
     * when n is as large as 200,000.
     *
     * @param n           Number of vertices.
     * @param edges       2D array representing bi-directional edges.
     * @param source      Starting vertex.
     * @param destination Target vertex.
     * @return true if a valid path exists, false otherwise.
     */
    public boolean validPathDFS(int n, int[][] edges, int source, int destination) {
        if (source == destination) {
            return true;
        }

        // Build adjacency list
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // Stack for iterative DFS traversal
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[n];

        stack.push(source);
        visited[source] = true;

        while (!stack.isEmpty()) {
            int curr = stack.pop();

            if (curr == destination) {
                return true;
            }

            for (int neighbor : adj.get(curr)) {
                if (!visited[neighbor]) {
                    if (neighbor == destination) {
                        return true;
                    }
                    visited[neighbor] = true;
                    stack.push(neighbor);
                }
            }
        }

        return false;
    }

    // =========================================================================
    // Approach 3: Disjoint Set Union (Union-Find)
    // =========================================================================

    /**
     * Disjoint Set Union (DSU) / Union-Find data structure with:
     * 1. Path Compression: Flattens the tree during find operations.
     * 2. Union by Rank: Attaches smaller tree under the root of the larger tree.
     */
    public static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        /**
         * Initializes Union-Find data structure with 'size' isolated components.
         *
         * @param size Number of elements (0 to size - 1).
         */
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i; // Initially, each element is its own parent (representative)
                rank[i] = 0;   // Initial tree depth/rank is 0
            }
        }

        /**
         * Finds the representative root of the set containing element 'x'.
         * Applies path compression so future lookups take nearly O(1) time.
         *
         * @param x Element to find.
         * @return Representative root of the component containing 'x'.
         */
        public int find(int x) {
            if (parent[x] != x) {
                // Path compression: point node directly to the root
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        /**
         * Merges the components containing elements 'x' and 'y'.
         * Uses union by rank to keep the resulting tree as flat as possible.
         *
         * @param x First element.
         * @param y Second element.
         * @return true if elements were merged, false if already in the same component.
         */
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            // Already connected in the same component
            if (rootX == rootY) {
                return false;
            }

            // Union by rank: attach smaller depth tree under root of larger depth tree
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }

            return true;
        }

        /**
         * Checks if elements 'x' and 'y' belong to the same connected component.
         *
         * @param x First element.
         * @param y Second element.
         * @return true if 'x' and 'y' share the same root representative.
         */
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }

    /**
     * Determines if a valid path exists between source and destination using Union-Find.
     * Connects all vertices with an edge, then checks if source and destination are connected.
     *
     * @param n           Number of vertices.
     * @param edges       2D array representing bi-directional edges.
     * @param source      Starting vertex.
     * @param destination Target vertex.
     * @return true if source and destination belong to the same component, false otherwise.
     */
    public boolean validPathUnionFind(int n, int[][] edges, int source, int destination) {
        if (source == destination) {
            return true;
        }

        UnionFind uf = new UnionFind(n);

        // Union vertices connected by each edge
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);

            // Optimization: Early exit if source and destination become connected
            if (uf.connected(source, destination)) {
                return true;
            }
        }

        return uf.connected(source, destination);
    }

    // =========================================================================
    // Demonstration & Unit Testing
    // =========================================================================

    public static void main(String[] args) {
        FindIfPathExistsInGraph solver = new FindIfPathExistsInGraph();

        // Test Case 1: Connected graph with a cycle
        // Vertices: 3, Edges: [[0,1],[1,2],[2,0]], Source: 0, Destination: 2
        // Expected: true
        int n1 = 3;
        int[][] edges1 = {{0, 1}, {1, 2}, {2, 0}};
        int source1 = 0;
        int destination1 = 2;

        System.out.println("Test Case 1 (Expected: true):");
        System.out.println("  BFS:        " + solver.validPath(n1, edges1, source1, destination1));
        System.out.println("  DFS:        " + solver.validPathDFS(n1, edges1, source1, destination1));
        System.out.println("  Union-Find: " + solver.validPathUnionFind(n1, edges1, source1, destination1));

        // Test Case 2: Disconnected graph
        // Vertices: 6, Edges: [[0,1],[0,2],[3,5],[5,4],[4,3]], Source: 0, Destination: 5
        // Expected: false
        int n2 = 6;
        int[][] edges2 = {{0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3}};
        int source2 = 0;
        int destination2 = 5;

        System.out.println("\nTest Case 2 (Expected: false):");
        System.out.println("  BFS:        " + solver.validPath(n2, edges2, source2, destination2));
        System.out.println("  DFS:        " + solver.validPathDFS(n2, edges2, source2, destination2));
        System.out.println("  Union-Find: " + solver.validPathUnionFind(n2, edges2, source2, destination2));

        // Test Case 3: Single node graph (source == destination)
        // Vertices: 1, Edges: [], Source: 0, Destination: 0
        // Expected: true
        int n3 = 1;
        int[][] edges3 = {};
        int source3 = 0;
        int destination3 = 0;

        System.out.println("\nTest Case 3 (Expected: true):");
        System.out.println("  BFS:        " + solver.validPath(n3, edges3, source3, destination3));
        System.out.println("  DFS:        " + solver.validPathDFS(n3, edges3, source3, destination3));
        System.out.println("  Union-Find: " + solver.validPathUnionFind(n3, edges3, source3, destination3));
    }
}
