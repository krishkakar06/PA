import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * LeetCode 973 - K Closest Points to Origin
 *
 * Problem Description:
 * Given an array of points where points[i] = [xi, yi] represents a point on the
 * X-Y plane and an integer k, return the k closest points to the origin (0, 0).
 *
 * The distance between two points on the X-Y plane is the Euclidean distance:
 * sqrt((x1 - x2)^2 + (y1 - y2)^2).
 *
 * Approach:
 * - Use a Max-Heap of size k.
 * - The Euclidean distance from (x, y) to (0, 0) is sqrt(x^2 + y^2).
 *   Comparing distances is mathematically equivalent to comparing squared distances (x^2 + y^2),
 *   avoiding floating-point precision issues and the expensive square root operation.
 * - Max-Heap property:
 *     1. Elements are ordered such that the point with the maximum distance from the origin
 *        resides at the top of the heap.
 *     2. As we iterate through all points, we add each point to the heap.
 *     3. Whenever the heap size exceeds k, we remove (poll) the top element (farthest point).
 *     4. After scanning all N points, the heap retains the k closest points to the origin.
 *
 * Complexity Analysis:
 * - Time Complexity: O(N * log k), where N is the total number of points.
 *   Each insertion and removal operation in a heap containing at most k + 1 elements takes O(log k) time.
 * - Space Complexity: O(k) auxiliary space to maintain the max-heap containing at most k elements.
 */
public class KClosestPointsToOrigin {

    /**
     * Returns the k closest points to the origin (0, 0).
     *
     * @param points A 2D array where points[i] = [x, y] represents a point on the plane.
     * @param k      The number of closest points to return.
     * @return       A 2D array containing the k closest points to the origin.
     */
    public int[][] kClosest(int[][] points, int k) {
        // Edge cases: null or empty input, or invalid k
        if (points == null || points.length == 0 || k <= 0) {
            return new int[0][0];
        }

        // If k is greater than or equal to total points, all points are within the k closest
        if (k >= points.length) {
            return points;
        }

        // Initialize a Max-Heap with custom comparator using squared distance.
        // In Java's PriorityQueue, the head is the smallest element by default.
        // By evaluating Integer.compare(squaredDistance(b), squaredDistance(a)),
        // the point with the largest distance is prioritized to the head of the queue.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(squaredDistance(b), squaredDistance(a))
        );

        // Iterate through all points and maintain a heap of size at most k
        for (int[] point : points) {
            maxHeap.offer(point);
            // If the heap size exceeds k, evict the point farthest from the origin
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // Extract the k closest points from the heap into the result array
        int[][] result = new int[k][2];
        int index = 0;
        while (!maxHeap.isEmpty()) {
            result[index++] = maxHeap.poll();
        }

        return result;
    }

    /**
     * Calculates the squared Euclidean distance of a 2D point from the origin (0, 0).
     * Squared distance = x^2 + y^2.
     *
     * @param point An integer array of size 2 representing [x, y].
     * @return The squared distance from the origin as an integer.
     */
    private int squaredDistance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    /**
     * Optional main method demonstrating usage and verification with sample test cases.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        KClosestPointsToOrigin solver = new KClosestPointsToOrigin();

        // Test Case 1: points = [[1,3],[-2,2]], k = 1 -> Expected: [[-2,2]]
        int[][] points1 = {{1, 3}, {-2, 2}};
        int k1 = 1;
        int[][] res1 = solver.kClosest(points1, k1);
        System.out.println("Test Case 1: " + Arrays.deepToString(res1));

        // Test Case 2: points = [[3,3],[5,-1],[-2,4]], k = 2 -> Expected: [[3,3],[-2,4]] (in any order)
        int[][] points2 = {{3, 3}, {5, -1}, {-2, 4}};
        int k2 = 2;
        int[][] res2 = solver.kClosest(points2, k2);
        System.out.println("Test Case 2: " + Arrays.deepToString(res2));
    }
}
