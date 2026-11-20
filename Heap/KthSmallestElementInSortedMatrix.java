import java.util.PriorityQueue;

/**
 * LeetCode 378: Kth Smallest Element in a Sorted Matrix
 * 
 * Given an n x n matrix where each of the rows and columns is sorted in
 * ascending order, return the kth smallest element in the matrix.
 * 
 * Note that it is the kth smallest element in the sorted order, not the
 * kth distinct element.
 * 
 * Approach: Min-Heap (PriorityQueue) / K-way Merge
 * ------------------------------------------------
 * Since every row in the matrix is sorted in ascending order, we can view
 * the problem as finding the kth smallest element across n sorted lists.
 * 
 * Data Structure:
 * - A Min-Heap (PriorityQueue) storing 3-element integer arrays: [value, row, col].
 * - Ordered by value in ascending order (using Integer.compare(a[0], b[0]) to prevent overflow).
 * 
 * Algorithm:
 * 1. Initialize the min-heap with the first element of each row: [matrix[r][0], r, 0].
 *    We only need to push at most min(n, k) rows, since the kth smallest element
 *    cannot be beyond row index min(n, k) - 1.
 * 2. Pop the minimum element from the heap k - 1 times.
 *    For each popped element [value, row, col], if col + 1 < n, push the next
 *    element from that same row: [matrix[row][col + 1], row, col + 1].
 * 3. The kth polled element's value is our answer.
 * 
 * Complexity:
 * - Time Complexity: O(min(n, k) + k * log(min(n, k)))
 *   - Heap initialization takes O(min(n, k)).
 *   - Each of the k extraction/insertion operations takes O(log(min(n, k))).
 *   - Total time is O(k * log(min(n, k))), which is much better than O(n^2 log(n^2)).
 * - Space Complexity: O(min(n, k))
 *   - The min-heap contains at most min(n, k) elements at any time.
 */
public class KthSmallestElementInSortedMatrix {

    /**
     * Finds the kth smallest element in an n x n sorted matrix using a Min-Heap.
     * 
     * @param matrix an n x n matrix where each row and column is sorted in ascending order
     * @param k      the order statistic to find (1-indexed, 1 <= k <= n^2)
     * @return the kth smallest element in the matrix
     */
    public int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix must not be null or empty.");
        }

        int n = matrix.length;

        // Min-heap storing integer arrays of length 3: [value, row, col]
        // We use Integer.compare to avoid potential integer overflow with subtraction
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        // Step 1: Seed the min-heap with the first element of each of the first min(n, k) rows.
        // We cap the rows at min(n, k) because the kth smallest element cannot appear in
        // any row index >= k (since each preceding row has at least one smaller or equal element).
        int initialRows = Math.min(n, k);
        for (int row = 0; row < initialRows; row++) {
            minHeap.offer(new int[] { matrix[row][0], row, 0 });
        }

        // Step 2: Pop from the min-heap k - 1 times.
        // Each time we pop an element [val, row, col], we add the next element from the same
        // row ([matrix[row][col + 1], row, col + 1]) into the heap, if it exists.
        for (int i = 0; i < k - 1; i++) {
            int[] current = minHeap.poll();
            int row = current[1];
            int col = current[2];

            // If there is another element in the same row, offer it to the min-heap
            if (col + 1 < n) {
                minHeap.offer(new int[] { matrix[row][col + 1], row, col + 1 });
            }
        }

        // Step 3: The root of the heap is now the kth smallest element.
        return minHeap.poll()[0];
    }

    /**
     * Main method demonstrating usage and verifying correctness with example test cases.
     */
    public static void main(String[] args) {
        KthSmallestElementInSortedMatrix solver = new KthSmallestElementInSortedMatrix();

        // Test Case 1: Standard 3x3 matrix from LeetCode Example 1
        int[][] matrix1 = {
            { 1,  5,  9 },
            { 10, 11, 13 },
            { 12, 13, 15 }
        };
        int k1 = 8;
        int result1 = solver.kthSmallest(matrix1, k1);
        System.out.println("Test Case 1: Result = " + result1 + " | Expected = 13");

        // Test Case 2: 1x1 matrix from LeetCode Example 2
        int[][] matrix2 = {
            { -5 }
        };
        int k2 = 1;
        int result2 = solver.kthSmallest(matrix2, k2);
        System.out.println("Test Case 2: Result = " + result2 + " | Expected = -5");

        // Test Case 3: Matrix with duplicate values
        int[][] matrix3 = {
            { 1, 2 },
            { 1, 3 }
        };
        int k3 = 2;
        int result3 = solver.kthSmallest(matrix3, k3);
        System.out.println("Test Case 3: Result = " + result3 + " | Expected = 1");
    }
}
