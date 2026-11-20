/**
 * LeetCode 695: Max Area of Island
 *
 * Problem Description:
 * You are given an m x n binary matrix 'grid'. An island is a group of 1's
 * (representing land) connected 4-directionally (horizontal or vertical).
 * You may assume all four edges of the grid are surrounded by water (0's).
 *
 * The area of an island is the number of cells with a value 1 in the island.
 * Return the maximum area of an island in 'grid'. If there is no island, return 0.
 *
 * Approach - Depth-First Search (DFS):
 * -------------------------------------
 * 1. Iterate through each cell (r, c) in the m x n grid.
 * 2. Whenever an unvisited land cell (grid[r][c] == 1) is encountered:
 *    - Trigger a DFS traversal starting from (r, c).
 *    - The DFS explores all 4-directionally connected land cells (up, down, left, right).
 *    - For each unvisited 1, the DFS recursively computes and returns the total size of the island.
 * 3. Update the global maximum area:
 *    maxArea = Math.max(maxArea, currentIslandArea).
 * 4. To avoid revisiting cells and infinite recursion:
 *    - Sink each visited land cell by modifying grid[r][c] = 0 in-place.
 *    - In-place modification avoids the need for an auxiliary boolean visited[][] matrix,
 *      optimizing space usage.
 * 5. Return the maximum area found after checking all cells.
 *
 * Complexity Analysis:
 * --------------------
 * - Time Complexity: O(m * n), where m is the number of rows and n is the number of columns.
 *   Every cell is visited at most once during the grid traversal and checked at most 4 times
 *   during adjacent neighbor expansions. Sinking the cell to 0 ensures it is not processed again.
 * - Space Complexity: O(m * n) in the worst case for the recursion call stack
 *   (e.g., when the entire grid is land and DFS recurses through all m * n cells).
 */
public class MaxAreaOfIsland {

    /**
     * Computes the maximum area of an island in the given binary grid.
     *
     * @param grid an m x n 2D array where 1 represents land and 0 represents water
     * @return the maximum area of an island, or 0 if no island exists
     */
    public int maxAreaOfIsland(int[][] grid) {
        // Base case: check for null or empty grid
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int maxArea = 0;

        // Traverse every cell in the matrix
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                // When an unvisited land cell is found, perform DFS to calculate island size
                if (grid[r][c] == 1) {
                    int currentArea = dfs(grid, r, c);
                    maxArea = Math.max(maxArea, currentArea);
                }
            }
        }

        return maxArea;
    }

    /**
     * Helper method to perform Depth-First Search (DFS) on the grid.
     * Explores all 4-directionally adjacent land cells, marks them visited (by sinking them to 0),
     * and returns the total size (area) of the island.
     *
     * @param grid the binary grid
     * @param r    current row index
     * @param c    current column index
     * @return the size of the island connected to (r, c)
     */
    private int dfs(int[][] grid, int r, int c) {
        // Base case: check boundary limits and verify if the cell is land (1)
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != 1) {
            return 0;
        }

        // Mark the current cell as visited by sinking it to 0
        // This ensures the cell will not be counted again or cause infinite loops
        grid[r][c] = 0;

        // Count current cell (area 1) plus the areas of all 4 connected neighbors
        int area = 1;

        // Recursively visit all 4 directions (Down, Up, Right, Left)
        area += dfs(grid, r + 1, c); // Down
        area += dfs(grid, r - 1, c); // Up
        area += dfs(grid, r, c + 1); // Right
        area += dfs(grid, r, c - 1); // Left

        return area;
    }

    /**
     * Main method demonstrating the solution with various test cases.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        MaxAreaOfIsland solver = new MaxAreaOfIsland();

        // Test Case 1: Standard grid with multiple islands (LeetCode Example 1)
        int[][] grid1 = {
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };
        int result1 = solver.maxAreaOfIsland(grid1);
        System.out.println("Test Case 1 - Expected: 6, Got: " + result1);

        // Test Case 2: Grid containing only water (LeetCode Example 2)
        int[][] grid2 = {
            {0, 0, 0, 0, 0, 0, 0, 0}
        };
        int result2 = solver.maxAreaOfIsland(grid2);
        System.out.println("Test Case 2 - Expected: 0, Got: " + result2);

        // Test Case 3: Single cell containing land
        int[][] grid3 = {
            {1}
        };
        int result3 = solver.maxAreaOfIsland(grid3);
        System.out.println("Test Case 3 - Expected: 1, Got: " + result3);

        // Test Case 4: Completely connected grid of land
        int[][] grid4 = {
            {1, 1, 1},
            {1, 1, 1}
        };
        int result4 = solver.maxAreaOfIsland(grid4);
        System.out.println("Test Case 4 - Expected: 6, Got: " + result4);
    }
}