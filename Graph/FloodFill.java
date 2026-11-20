/**
 * LeetCode 733: Flood Fill
 * 
 * Problem Description:
 * An image is represented by an m x n integer grid 'image' where image[i][j] represents 
 * the pixel value of the image. You are also given three integers sr, sc, and color. 
 * You should perform a flood fill on the image starting from the pixel image[sr][sc].
 * 
 * To perform a flood fill:
 * 1. Consider the starting pixel image[sr][sc].
 * 2. Find all pixels connected 4-directionally (up, down, left, right) to the starting pixel
 *    that have the same initial color as image[sr][sc].
 * 3. Replace the color of all of the aforementioned pixels with the new 'color'.
 * 4. Return the modified image.
 * 
 * Approach:
 * Depth-First Search (DFS):
 * - If the starting pixel already has the target color, no modifications are needed; return image immediately
 *   to avoid infinite recursion/cycles.
 * - Store the original color of the starting pixel.
 * - Recursively visit adjacent 4-directional neighbors (up, down, left, right) that match the original color,
 *   updating their color to the target color.
 * 
 * Complexity Analysis:
 * - Time Complexity: O(m * n), where m is the number of rows and n is the number of columns.
 *   In the worst case, every pixel might have the original color and need to be visited once.
 * - Space Complexity: O(m * n), due to the recursion call stack in the worst case (e.g., a grid of all identical colors).
 */
public class FloodFill {

    // Direction vectors representing 4-directional movements: up, down, left, right.
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /**
     * Performs a flood fill on the given image starting at (sr, sc).
     *
     * @param image 2D grid of pixels
     * @param sr    Starting row index
     * @param sc    Starting column index
     * @param color New color to apply
     * @return The modified 2D grid after flood fill
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        // Base validation: check for valid image dimensions
        if (image == null || image.length == 0 || image[0].length == 0) {
            return image;
        }

        int originalColor = image[sr][sc];

        // If the starting pixel is already the target color, no work is needed.
        // This check also prevents infinite recursion loops.
        if (originalColor != color) {
            dfs(image, sr, sc, originalColor, color);
        }

        return image;
    }

    /**
     * Helper method to perform Depth-First Search recursively.
     *
     * @param image         The grid being modified
     * @param r             Current row
     * @param c             Current column
     * @param originalColor The color of the pixel component being replaced
     * @param newColor      The new color to assign to matching pixels
     */
    private void dfs(int[][] image, int r, int c, int originalColor, int newColor) {
        // Boundary checks and color match check
        if (r < 0 || r >= image.length || c < 0 || c >= image[0].length || image[r][c] != originalColor) {
            return;
        }

        // Fill current pixel with the new color
        image[r][c] = newColor;

        // Traverse all 4 cardinal directions (up, down, left, right)
        for (int[] dir : DIRECTIONS) {
            dfs(image, r + dir[0], c + dir[1], originalColor, newColor);
        }
    }
}
