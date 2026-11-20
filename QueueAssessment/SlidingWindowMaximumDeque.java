import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * ============================================================================
 * Problem: Sliding Window Maximum (Maximum of all subarrays of size k)
 * LeetCode 239 / Classic Algorithmic Problem
 * ============================================================================
 *
 * Problem Statement:
 * Given an integer array `arr` and a sliding window size `k`, the window slides
 * from the very left of the array to the very right. You can only see the `k`
 * numbers in the window. Each time the sliding window moves right by one
 * position, return the maximum element in the window.
 *
 * ----------------------------------------------------------------------------
 * Key Insights & Algorithm Design: Monotonic Deque
 * ----------------------------------------------------------------------------
 * A naive solution checks all k elements in each window, taking O(n * k) time.
 * A max-heap / priority queue takes O(n * log(k)) time and requires handling
 * removals of stale elements.
 *
 * An optimal O(n) solution uses a Double-Ended Queue (Deque) designed as a
 * MONOTONICALLY DECREASING DEQUE.
 *
 * Why store INDICES instead of values in the deque?
 * 1. Indices allow us to easily retrieve the value: arr[index].
 * 2. Indices allow us to determine when an element is OUT OF THE CURRENT WINDOW:
 *    If an element's index is <= (current_index - k), it has expired and must
 *    be discarded from the front of the deque.
 *
 * Monotonic Decreasing Invariant:
 * - Elements in the deque are maintained in strictly decreasing order of their
 *   values: arr[deque.peekFirst()] > arr[next] > ... > arr[deque.peekLast()].
 * - The front (first element) of the deque ALWAYS holds the index of the
 *   maximum element for the current window.
 *
 * Intuition ("The Younger and Stronger Principle"):
 * - Suppose element A is at index `i` with value `arr[i]`, and element B is at
 *   index `j` with value `arr[j]`, where `i < j` and `arr[i] <= arr[j]`.
 * - Element B was added after element A (it is "younger"), so it will stay in
 *   the sliding window longer than A.
 * - Moreover, B is greater than or equal to A in value (it is "stronger").
 * - Therefore, A can NEVER become the maximum of the current or any future
 *   window! A is obsolete and can safely be removed from the back of the deque.
 *
 * ----------------------------------------------------------------------------
 * Step-by-Step Walkthrough Example:
 * Array: [1, 3, -1, -3, 5, 3, 6, 7],  k = 3
 * Array length n = 8, Result array length = n - k + 1 = 8 - 3 + 1 = 6.
 *
 * Window Range for step i: [i - k + 1, i]
 * Valid window boundary check: index >= i - k + 1
 *
 * ----------------------------------------------------------------------------------------------------
 * Step | Val (arr[i]) | Window Range | Front Evictions   | Back Evictions        | Deque (indices:vals)| Max
 * ----------------------------------------------------------------------------------------------------
 * i=0  | arr[0] = 1   | [0] (forming) | None              | None                  | [0 : 1]             | -
 * i=1  | arr[1] = 3   | [0..1](forming| None              | arr[0]=1 <= 3 (pop 0) | [1 : 3]             | -
 * i=2  | arr[2] = -1  | [0..2] (full) | None              | None (3 > -1)         | [1 : 3, 2 : -1]     | 3
 *      -> Window 1: [1, 3, -1] -> Max = arr[deque.peekFirst()] = arr[1] = 3. Recorded at result[0].
 *
 * i=3  | arr[3] = -3  | [1..3] (full) | None (1 >= 3-3+1) | None (-1 > -3)        | [1 : 3, 2 : -1, 3 : -3] | 3
 *      -> Window 2: [3, -1, -3] -> Max = arr[deque.peekFirst()] = arr[1] = 3. Recorded at result[1].
 *
 * i=4  | arr[4] = 5   | [2..4] (full) | Index 1 < 2!      | arr[3]=-3 <= 5 (pop 3)| [4 : 5]             | 5
 *      |              |               | Pop front index 1 | arr[2]=-1 <= 5 (pop 2)|                     |
 *      -> Window 3: [-1, -3, 5] -> Max = arr[deque.peekFirst()] = arr[4] = 5. Recorded at result[2].
 *
 * i=5  | arr[5] = 3   | [3..5] (full) | None (4 >= 3)     | None (5 > 3)          | [4 : 5, 5 : 3]      | 5
 *      -> Window 4: [-3, 5, 3] -> Max = arr[deque.peekFirst()] = arr[4] = 5. Recorded at result[3].
 *
 * i=6  | arr[6] = 6   | [4..6] (full) | None (4 >= 4)     | arr[5]=3 <= 6 (pop 5) | [6 : 6]             | 6
 *      |              |               |                   | arr[4]=5 <= 6 (pop 4)|                     |
 *      -> Window 5: [5, 3, 6] -> Max = arr[deque.peekFirst()] = arr[6] = 6. Recorded at result[4].
 *
 * i=7  | arr[7] = 7   | [5..7] (full) | None (6 >= 5)     | arr[6]=6 <= 7 (pop 6) | [7 : 7]             | 7
 *      -> Window 6: [3, 6, 7] -> Max = arr[deque.peekFirst()] = arr[7] = 7. Recorded at result[5].
 * ----------------------------------------------------------------------------------------------------
 * Final Result Array: [3, 3, 5, 5, 6, 7]
 *
 * ----------------------------------------------------------------------------
 * Complexity Analysis:
 * - Time Complexity: O(n)
 *   Every index is pushed into the deque exactly once and popped from the
 *   deque at most once. Therefore, across all n iterations, the total number
 *   of deque operations is bounded by 2 * n, which is O(n).
 * - Space Complexity: O(k)
 *   The deque stores at most k indices at any given moment.
 *   The output array takes O(n - k + 1) space.
 * ============================================================================
 */
public class SlidingWindowMaximumDeque {

    /**
     * Computes the maximum element in each sliding window of size k.
     *
     * @param arr the input array of integers
     * @param k   the size of the sliding window
     * @return an array containing the maximum of each sliding window,
     *         or an empty array if the input is invalid or k <= 0
     */
    public int[] slidingWindowMax(int[] arr, int k) {
        // Edge Case 1: Null check, empty array, or non-positive window size
        if (arr == null || arr.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = arr.length;

        // Edge Case 2: Window size is 1; every element is its own maximum
        if (k == 1) {
            return arr.clone();
        }

        // Edge Case 3: Window size larger than array length; single window over whole array
        if (k > n) {
            int maxVal = arr[0];
            for (int i = 1; i < n; i++) {
                if (arr[i] > maxVal) {
                    maxVal = arr[i];
                }
            }
            return new int[]{maxVal};
        }

        // Result array holds exactly (n - k + 1) maximum values
        int[] result = new int[n - k + 1];

        // Deque to store indices of array elements.
        // Invariant: The elements corresponding to these indices are in strictly
        // decreasing order: arr[deque.peekFirst()] > ... > arr[deque.peekLast()]
        Deque<Integer> deque = new ArrayDeque<>();

        // Process each element of the array
        for (int i = 0; i < n; i++) {

            // Step 1: Remove indices that are outside the current window.
            // The current window covers indices [i - k + 1, i].
            // If the oldest index in deque is < (i - k + 1), it has slipped out.
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Step 2: Maintain the monotonic decreasing property.
            // Remove indices from the back whose values are <= the current value arr[i].
            // Reason: arr[i] is newer and greater than or equal to them, so those
            // elements can never be the maximum of this or any subsequent window.
            while (!deque.isEmpty() && arr[deque.peekLast()] <= arr[i]) {
                deque.pollLast();
            }

            // Step 3: Add the current element's index to the back of the deque.
            deque.offerLast(i);

            // Step 4: Record the maximum value once the first window is fully formed.
            // The first window [0, k-1] completes when i == k - 1.
            // The front of the deque always holds the index of the maximum element.
            if (i >= k - 1) {
                result[i - k + 1] = arr[deque.peekFirst()];
            }
        }

        return result;
    }

    /**
     * Helper method to print input and output arrays clearly.
     *
     * @param label descriptive label for the test case
     * @param arr   input array
     * @param k     window size
     * @param res   resulting sliding window maximums
     */
    private static void printTestCase(String label, int[] arr, int k, int[] res) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Test: " + label);
        System.out.println("Input Array: " + Arrays.toString(arr));
        System.out.println("Window Size (k): " + k);
        System.out.println("Sliding Window Max: " + Arrays.toString(res));
    }

    /**
     * Demo main method executing multiple representative scenarios.
     */
    public static void main(String[] args) {
        SlidingWindowMaximumDeque solver = new SlidingWindowMaximumDeque();

        System.out.println("================================================================");
        System.out.println("    SLIDING WINDOW MAXIMUM DEMONSTRATION (MONOTONIC DEQUE)      ");
        System.out.println("================================================================");

        // Test 1: Classic LeetCode 239 example with positive and negative numbers
        int[] arr1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;
        int[] res1 = solver.slidingWindowMax(arr1, k1);
        printTestCase("Standard Example with Fluctuation", arr1, k1, res1);
        // Expected: [3, 3, 5, 5, 6, 7]

        // Test 2: Strictly increasing array
        // Each new element entering the window is greater than all previous elements
        int[] arr2 = {1, 2, 3, 4, 5, 6};
        int k2 = 3;
        int[] res2 = solver.slidingWindowMax(arr2, k2);
        printTestCase("Strictly Increasing Array", arr2, k2, res2);
        // Expected: [3, 4, 5, 6]

        // Test 3: Strictly decreasing array
        // Old elements are never eliminated from the back; they expire only from the front
        int[] arr3 = {10, 9, 8, 7, 6, 5};
        int k3 = 3;
        int[] res3 = solver.slidingWindowMax(arr3, k3);
        printTestCase("Strictly Decreasing Array", arr3, k3, res3);
        // Expected: [10, 9, 8, 7]

        // Test 4: Array with duplicate elements
        int[] arr4 = {4, 4, 4, 2, 5, 2, 5};
        int k4 = 3;
        int[] res4 = solver.slidingWindowMax(arr4, k4);
        printTestCase("Array with Duplicates", arr4, k4, res4);
        // Expected: [4, 4, 5, 5, 5]

        // Test 5: Window size k = 1
        // Each element is its own window maximum
        int[] arr5 = {7, 2, 4};
        int k5 = 1;
        int[] res5 = solver.slidingWindowMax(arr5, k5);
        printTestCase("Window Size k = 1", arr5, k5, res5);
        // Expected: [7, 2, 4]

        // Test 6: Window size k equal to array length
        // Result is a single number representing the overall maximum
        int[] arr6 = {2, 8, 4, 1, 9, 3};
        int k6 = arr6.length;
        int[] res6 = solver.slidingWindowMax(arr6, k6);
        printTestCase("Window Size Equal to Array Length", arr6, k6, res6);
        // Expected: [9]

        System.out.println("----------------------------------------------------------------");
        System.out.println("All demonstration tests completed successfully!");
    }
}
