public class SurroundedRegions {

    /**
     * Solves LeetCode 130 - Surrounded Regions.
     *
     * Problem Statement:
     * Given an m x n matrix board containing 'X' and 'O', capture all regions that
     * are 4-directionally surrounded by 'X'. A region is captured by flipping all 'O's
     * into 'X's in that surrounded region.
     *
     * Approach (Boundary Traversal via DFS):
     * 1. Any 'O' on the border of the board can never be surrounded by 'X'.
     * 2. Any 'O' connected (directly or transitively) to a border 'O' cannot be surrounded either.
     * 3. Therefore, traverse the four boundaries of the grid (first/last rows and first/last columns).
     * 4. When an 'O' is encountered on a boundary, execute a DFS (or BFS) to find and mark all
     *    connected 'O' cells with a temporary marker (e.g., 'T' for temporary/safe).
     * 5. After marking all boundary-connected 'O' cells:
     *    - Iterate over the entire board.
     *    - Any remaining 'O' cell was not connected to any boundary, meaning it is surrounded.
     *      Flip it to 'X'.
     *    - Any cell marked 'T' was safe and connected to a boundary. Restore it back to 'O'.
     *    - Cells containing 'X' remain unchanged.
     *
     * Complexity Analysis:
     * - Time Complexity: O(M * N), where M is the number of rows and N is the number of columns.
     *   Each cell is visited at most a constant number of times (boundary DFS and final scan).
     * - Space Complexity: O(M * N) in the worst case for the recursion call stack if the board is filled with 'O'.
     *
     * @param board The 2D character grid containing 'X' and 'O'.
     */
    public void solve(char[][] board) {
        // Base edge case: return immediately if the board is null or empty
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }

        int m = board.length;
        int n = board[0].length;

        // If dimensions are less than 3x3, no cell can be surrounded as all cells are on boundaries
        if (m < 3 || n < 3) {
            return;
        }

        // Step 1: Traverse the left and right boundary columns
        for (int i = 0; i < m; i++) {
            // Leftmost column
            if (board[i][0] == 'O') {
                dfs(board, i, 0, m, n);
            }
            // Rightmost column
            if (board[i][n - 1] == 'O') {
                dfs(board, i, n - 1, m, n);
            }
        }

        // Step 2: Traverse the top and bottom boundary rows
        for (int j = 0; j < n; j++) {
            // Topmost row
            if (board[0][j] == 'O') {
                dfs(board, 0, j, m, n);
            }
            // Bottommost row
            if (board[m - 1][j] == 'O') {
                dfs(board, m - 1, j, m, n);
            }
        }

        // Step 3: Flip captured cells to 'X' and restore safe cells back to 'O'
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    // Cell was never reached from the boundary, so it is surrounded
                    board[i][j] = 'X';
                } else if (board[i][j] == 'T') {
                    // Cell was safe and reachable from the boundary, restore to 'O'
                    board[i][j] = 'O';
                }
            }
        }
    }

    /**
     * Depth-First Search helper to mark all connected 'O' cells as safe ('T').
     *
     * @param board The 2D grid
     * @param r     Current row index
     * @param c     Current column index
     * @param m     Total number of rows
     * @param n     Total number of columns
     */
    private void dfs(char[][] board, int r, int c, int m, int n) {
        // Stop recursion if out of bounds or if cell is not 'O'
        if (r < 0 || r >= m || c < 0 || c >= n || board[r][c] != 'O') {
            return;
        }

        // Mark cell as safe using temporary marker 'T'
        board[r][c] = 'T';

        // Recursively visit all four adjacent orthogonal neighbors
        dfs(board, r - 1, c, m, n); // Up
        dfs(board, r + 1, c, m, n); // Down
        dfs(board, r, c - 1, m, n); // Left
        dfs(board, r, c + 1, m, n); // Right
    }

    /**
     * Helper method to print the board matrix.
     *
     * @param board The 2D character matrix
     */
    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j] + (j == row.length - 1 ? "" : " "));
            }
            System.out.println();
        }
    }

    /**
     * Main method demonstrating execution with sample inputs.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SurroundedRegions solution = new SurroundedRegions();

        // Example 1
        char[][] board1 = {
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'X', 'O', 'X'},
            {'X', 'O', 'X', 'X'}
        };

        System.out.println("Input Board 1:");
        printBoard(board1);

        solution.solve(board1);

        System.out.println("\nOutput Board 1:");
        printBoard(board1);

        // Example 2
        char[][] board2 = {
            {'X'}
        };

        System.out.println("\nInput Board 2:");
        printBoard(board2);

        solution.solve(board2);

        System.out.println("\nOutput Board 2:");
        printBoard(board2);
    }
}