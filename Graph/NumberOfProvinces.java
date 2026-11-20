/**
 * File: NumberOfProvinces.java
 *
 * LeetCode 547 - Number of Provinces
 * URL: https://leetcode.com/problems/number-of-provinces/
 *
 * Problem Description:
 * There are n cities. Some of them are connected, while some are not. If city a is connected
 * directly with city b, and city b is connected directly with city c, then city a is connected
 * indirectly with city c.
 *
 * A province is a group of directly or indirectly connected cities and no other cities outside
 * of the group.
 *
 * You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and
 * the jth city are directly connected, and isConnected[i][j] = 0 otherwise.
 *
 * Return the total number of provinces.
 *
 * Constraints:
 * - 1 <= n <= 200
 * - n == isConnected.length
 * - n == isConnected[i].length
 * - isConnected[i][j] is 1 or 0
 * - isConnected[i][i] == 1
 * - isConnected[i][j] == isConnected[j][i]
 */
public class NumberOfProvinces {

    /**
     * Primary method required by LeetCode.
     * Finds the total number of provinces (connected components) in the graph.
     *
     * By default, this delegates to the DFS approach.
     * You can swap the call to findCircleNumUnionFind or findCircleNumBFS as needed.
     *
     * @param isConnected n x n adjacency matrix where isConnected[i][j] == 1 denotes a connection
     * @return the total number of provinces
     */
    public int findCircleNum(int[][] isConnected) {
        // Delegates to DFS approach (can also use findCircleNumUnionFind or findCircleNumBFS)
        return findCircleNumDFS(isConnected);
    }

    // =========================================================================
    // APPROACH 1: Depth-First Search (DFS)
    // =========================================================================

    /**
     * Approach 1: Depth-First Search (DFS)
     *
     * Concept:
     * - The n x n matrix represents the adjacency matrix of an undirected graph with n vertices.
     * - A province is equivalent to an undirected connected component.
     * - We iterate through all vertices from 0 to n - 1. If vertex i has not been visited,
     *   it must belong to a new province.
     * - We increment our province count and invoke DFS from vertex i to recursively visit all
     *   connected neighbors, marking them as visited so they are not counted again.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n^2), where n is the number of cities. We traverse the entire
     *   n x n matrix because for each city, we inspect all other n cities to check connectivity.
     * - Space Complexity: O(n), for the boolean visited array and the recursion call stack
     *   (in the worst case of a line graph, recursion depth can reach n).
     *
     * @param isConnected n x n adjacency matrix
     * @return total number of provinces
     */
    public int findCircleNumDFS(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }

        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for (int i = 0; i < n; i++) {
            // If the city has not been visited yet, it starts a new province
            if (!visited[i]) {
                provinces++;
                dfs(i, isConnected, visited);
            }
        }

        return provinces;
    }

    /**
     * Helper method to perform DFS traversal from a given city.
     *
     * @param city        current city being explored
     * @param isConnected adjacency matrix
     * @param visited     boolean array tracking visited cities
     */
    private void dfs(int city, int[][] isConnected, boolean[] visited) {
        visited[city] = true;

        for (int neighbor = 0; neighbor < isConnected.length; neighbor++) {
            // Traverse all directly connected, unvisited neighbor cities
            if (isConnected[city][neighbor] == 1 && !visited[neighbor]) {
                dfs(neighbor, isConnected, visited);
            }
        }
    }

    // =========================================================================
    // APPROACH 2: Disjoint Set Union (Union-Find)
    // =========================================================================

    /**
     * Approach 2: Disjoint Set Union (Union-Find / DSU)
     *
     * Concept:
     * - Union-Find is the standard data structure for tracking connected components dynamically.
     * - Initially, every city is in its own disjoint set, so there are n components.
     * - We iterate over all unique pairs of cities (i, j) where i < j (since the graph is undirected).
     * - If isConnected[i][j] == 1, we perform a union operation on city i and city j.
     * - If i and j belonged to different sets, merging them decrements the component count by 1.
     * - After evaluating all edges, the remaining component count is the number of provinces.
     *
     * Optimizations Used:
     * 1. Path Compression (in find): Flattens the tree structure whenever find is called,
     *    pointing nodes directly to their set root.
     * 2. Union by Rank (in union): Attaches the shallower tree to the root of the deeper tree,
     *    preventing trees from becoming unbalanced.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n^2 * alpha(n)), where n is the number of cities and alpha is the
     *   Inverse Ackermann function. Since alpha(n) < 5 for all practical inputs, this is
     *   effectively O(n^2) time to iterate over the matrix.
     * - Space Complexity: O(n), for the parent and rank arrays inside the UnionFind class.
     *
     * @param isConnected n x n adjacency matrix
     * @return total number of provinces
     */
    public int findCircleNumUnionFind(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }

        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);

        // Iterate through the upper triangular portion of the adjacency matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }

        return uf.getCount();
    }

    /**
     * Disjoint Set Union (DSU) / Union-Find data structure
     * implemented with Path Compression and Union by Rank.
     */
    public static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        private int count; // Number of disjoint components

        /**
         * Initializes n elements, each as an independent set.
         *
         * @param n total number of elements (cities)
         */
        public UnionFind(int n) {
            this.parent = new int[n];
            this.rank = new int[n];
            this.count = n;

            for (int i = 0; i < n; i++) {
                parent[i] = i; // Each node is its own representative initially
                rank[i] = 0;   // Initial tree depth/rank is 0
            }
        }

        /**
         * Finds the representative root of the set containing element x.
         * Employs path compression to make all traversed nodes point directly to the root.
         *
         * @param x the element to find
         * @return the root representative of element x
         */
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        /**
         * Merges the sets containing elements x and y using Union by Rank.
         *
         * @param x first element
         * @param y second element
         * @return true if two distinct sets were successfully merged, false if already in the same set
         */
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            // Already in the same connected component
            if (rootX == rootY) {
                return false;
            }

            // Union by Rank: attach smaller tree under larger tree
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }

            count--; // Merging two components reduces the total province count
            return true;
        }

        /**
         * Returns the current number of disjoint sets (provinces).
         *
         * @return the number of connected components
         */
        public int getCount() {
            return count;
        }
    }

    // =========================================================================
    // APPROACH 3: Breadth-First Search (BFS) [Alternative]
    // =========================================================================

    /**
     * Approach 3: Breadth-First Search (BFS)
     *
     * Concept:
     * - Iteratively traverses each connected component level-by-level using a Queue.
     * - When an unvisited city is discovered, increment the province count, mark it visited,
     *   and enqueue it.
     * - Dequeue cities one by one and enqueue all their unvisited connected neighbors.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n^2), where n is the number of cities.
     * - Space Complexity: O(n), for the visited array and the BFS queue.
     *
     * @param isConnected n x n adjacency matrix
     * @return total number of provinces
     */
    public int findCircleNumBFS(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }

        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                provinces++;
                visited[i] = true;
                queue.offer(i);

                while (!queue.isEmpty()) {
                    int curr = queue.poll();
                    for (int neighbor = 0; neighbor < n; neighbor++) {
                        if (isConnected[curr][neighbor] == 1 && !visited[neighbor]) {
                            visited[neighbor] = true;
                            queue.offer(neighbor);
                        }
                    }
                }
            }
        }

        return provinces;
    }

    // =========================================================================
    // MAIN METHOD: For Local Testing and Verification
    // =========================================================================

    public static void main(String[] args) {
        NumberOfProvinces solver = new NumberOfProvinces();

        // Test Case 1:
        // City 0 and City 1 are connected, City 2 is isolated.
        // Expected Output: 2 provinces
        int[][] test1 = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 1}
        };

        // Test Case 2:
        // No cities are connected with each other.
        // Expected Output: 3 provinces
        int[][] test2 = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };

        // Test Case 3:
        // All cities are connected in a chain: 0 - 1 - 2
        // Expected Output: 1 province
        int[][] test3 = {
            {1, 1, 0},
            {1, 1, 1},
            {0, 1, 1}
        };

        // Test Case 4: Single city
        // Expected Output: 1 province
        int[][] test4 = {
            {1}
        };

        System.out.println("--- Test Case 1 ---");
        System.out.println("findCircleNum (Default/DFS) : " + solver.findCircleNum(test1) + " (Expected: 2)");
        System.out.println("findCircleNumDFS            : " + solver.findCircleNumDFS(test1) + " (Expected: 2)");
        System.out.println("findCircleNumUnionFind      : " + solver.findCircleNumUnionFind(test1) + " (Expected: 2)");
        System.out.println("findCircleNumBFS            : " + solver.findCircleNumBFS(test1) + " (Expected: 2)");

        System.out.println("\n--- Test Case 2 ---");
        System.out.println("findCircleNum (Default/DFS) : " + solver.findCircleNum(test2) + " (Expected: 3)");
        System.out.println("findCircleNumDFS            : " + solver.findCircleNumDFS(test2) + " (Expected: 3)");
        System.out.println("findCircleNumUnionFind      : " + solver.findCircleNumUnionFind(test2) + " (Expected: 3)");
        System.out.println("findCircleNumBFS            : " + solver.findCircleNumBFS(test2) + " (Expected: 3)");

        System.out.println("\n--- Test Case 3 ---");
        System.out.println("findCircleNum (Default/DFS) : " + solver.findCircleNum(test3) + " (Expected: 1)");
        System.out.println("findCircleNumDFS            : " + solver.findCircleNumDFS(test3) + " (Expected: 1)");
        System.out.println("findCircleNumUnionFind      : " + solver.findCircleNumUnionFind(test3) + " (Expected: 1)");
        System.out.println("findCircleNumBFS            : " + solver.findCircleNumBFS(test3) + " (Expected: 1)");

        System.out.println("\n--- Test Case 4 ---");
        System.out.println("findCircleNum (Default/DFS) : " + solver.findCircleNum(test4) + " (Expected: 1)");
        System.out.println("findCircleNumDFS            : " + solver.findCircleNumDFS(test4) + " (Expected: 1)");
        System.out.println("findCircleNumUnionFind      : " + solver.findCircleNumUnionFind(test4) + " (Expected: 1)");
        System.out.println("findCircleNumBFS            : " + solver.findCircleNumBFS(test4) + " (Expected: 1)");
    }
}