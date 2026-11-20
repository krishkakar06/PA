/**
 * LeetCode 200 - Number of Islands
 *
 * Problem Statement:
 * Given an m x n 2D binary grid 'grid' which represents a map of '1's (land)
 * and '0's (water), return the number of islands.
 *
 * An island is surrounded by water and is formed by connecting adjacent lands
 * horizontally or vertically. You may assume all four edges of the grid are all
 * surrounded by water.
 *
 * Approach: Depth-First Search (DFS)
 * -----------------------------------
 * 1. Scan the 2D grid cell by cell.
 * 2. Whenever an unvisited land cell ('1') is encountered, it represents the discovery
 *    of a new island. We increment our island counter.
 * 3. We then initiate a Depth-First Search (DFS) traversal starting from that cell
 *    to visit and mark all connected land cells (horizontally and vertically).
 * 4. In the DFS function, we "sink" the visited land cell by changing its value from '1'
 *    to '0' (or any marker), preventing it from being counted or visited again.
 * 5. Recursively traverse all four orthogonal neighbors (up, down, left, right).
 * 6. Continue until all cells in the grid have been processed.
 *
 * Complexity Analysis:
 * - Time Complexity: O(M * N), where M is the number of rows and N is the number of columns.
 *   Each cell is visited at most a constant number of times (once in the outer loops, and
 *   up to four times from its neighbors during DFS).
 * - Space Complexity: O(M * N) in the worst case where the entire grid is land. The recursion
 *   call stack can grow up to M * N frames.
 */
public class NumberOfIslands {

    /**
     * Counts the number of islands in the given 2D binary grid.
     *
     * @param grid 2D array of chars containing '1' (land) and '0' (water)
     * @return the total number of connected islands
     */
    public int numIslands(char[][] grid) {
        // Base edge case: return 0 if the grid is null or empty
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islandCount = 0;

        // Iterate through every cell in the grid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // When an unvisited land cell is encountered
                if (grid[r][c] == '1') {
                    // Increment the count for this new island
                    islandCount++;
                    // Trigger DFS to traverse and sink all connected land cells
                    dfs(grid, r, c);
                }
            }
        }

        return islandCount;
    }

    /**
     * Helper method to perform Depth-First Search (DFS) traversal.
     * Sinks the current land cell and recursively visits its 4-directionally
     * connected neighbors (up, down, left, right).
     *
     * @param grid the 2D grid of land and water
     * @param r    the current row index
     * @param c    the current column index
     */
    private void dfs(char[][] grid, int r, int c) {
        // Boundary checks and water/visited check:
        // If the current indices are out of grid bounds, or if the cell
        // is not land ('1'), terminate the recursion.
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != '1') {
            return;
        }

        // Mark current cell as visited by sinking it to '0'
        grid[r][c] = '0';

        // Recursively visit all 4 orthogonal directions
        dfs(grid, r - 1, c); // Up
        dfs(grid, r + 1, c); // Down
        dfs(grid, r, c - 1); // Left
        dfs(grid, r, c + 1); // Right
    }

    /**
     * Main method demonstrating sample test cases from LeetCode 200.
     */
    public static void main(String[] args) {
        NumberOfIslands solver = new NumberOfIslands();

        // Test Case 1: Expected output = 1
        char[][] grid1 = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        System.out.println("Test Case 1 Number of Islands: " + solver.numIslands(grid1));

        // Test Case 2: Expected output = 3
        char[][] grid2 = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        System.out.println("Test Case 2 Number of Islands: " + solver.numIslands(grid2));
    }
}