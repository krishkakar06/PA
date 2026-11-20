import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 503 - Next Greater Element II
 * 
 * Problem Statement:
 * Given a circular integer array nums (i.e., the next element of nums[nums.length - 1]
 * is nums[0]), return the next greater number for every element in nums.
 * 
 * The next greater number of a number x is the first greater number to its
 * traversing-order next in the array, which means you could search circularly to find
 * its next greater number. If it doesn't exist, return -1 for this number.
 * 
 * Approach:
 * - Circular Array via Index Doubling Trick:
 *   Instead of physically copying the array to double its size, we simulate traversing
 *   an array of size 2 * n by looping from index 0 to 2 * n - 1 and accessing elements
 *   using the modulo operator (i % n). This allows circular lookahead seamlessly.
 * 
 * - Monotonic Decreasing Stack:
 *   We maintain a stack storing indices of elements in strictly non-increasing / decreasing
 *   order of their values.
 *   For each element nums[i % n]:
 *     1. While the stack is not empty and nums[stack.peek()] < nums[i % n], we have found
 *        the next greater element for the index at the top of the stack. We pop that index
 *        and record nums[i % n] in the result array at that index.
 *     2. In the first pass (i < n), we push the current index i onto the stack because we
 *        need to find the next greater element for every original position.
 *     3. In the second pass (i >= n), we do not push any new indices; we only use the
 *        second pass elements to resolve any remaining unresolved elements in the stack.
 * 
 * - Result Initialization:
 *   The result array is initialized with -1. Any elements that do not find a greater element
 *   after the full 2 * n iterations will automatically remain -1.
 * 
 * Complexity Analysis:
 * - Time Complexity: O(n), where n is the length of the array nums.
 *   We iterate 2 * n times. Each index from 0 to n - 1 is pushed onto the stack at most once
 *   (during the first pass) and popped at most once. Hence, all stack operations take O(n) time.
 * - Space Complexity: O(n) auxiliary space for the monotonic stack in the worst case
 *   (e.g., when elements are sorted in descending order), excluding the returned result array.
 */
public class NextGreaterElementII {

    /**
     * Finds the next greater element for every element in a circular array.
     *
     * @param nums the circular array of integers
     * @return an integer array containing the next greater elements, or -1 if none exists
     */
    public int[] nextGreaterElements(int[] nums) {
        // Defensive check for edge cases
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n];
        // Initialize all results to -1 (default when no greater element exists)
        Arrays.fill(result, -1);

        // Monotonic stack to store array indices
        // ArrayDeque is preferred over java.util.Stack for performance and standard practices
        Deque<Integer> stack = new ArrayDeque<>();

        // Index doubling trick: iterate through 2 * n elements using modulo (i % n)
        for (int i = 0; i < 2 * n; i++) {
            int currentNum = nums[i % n];

            // Resolve any previous indices whose values are strictly smaller than the current number
            while (!stack.isEmpty() && nums[stack.peek()] < currentNum) {
                int prevIndex = stack.pop();
                result[prevIndex] = currentNum;
            }

            // Only push indices during the first traversal (0 to n - 1)
            // The second traversal (n to 2 * n - 1) is solely for resolving remaining indices
            if (i < n) {
                stack.push(i);
            }
        }

        return result;
    }

    /**
     * Main method demonstrating the solution with sample test cases.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        NextGreaterElementII solver = new NextGreaterElementII();

        // Test Case 1: Standard circular example
        int[] nums1 = {1, 2, 1};
        int[] res1 = solver.nextGreaterElements(nums1);
        System.out.println("Input:  " + Arrays.toString(nums1));
        System.out.println("Output: " + Arrays.toString(res1)); // Expected: [2, -1, 2]

        // Test Case 2: Array with duplicate values and circular wrap-around
        int[] nums2 = {1, 2, 3, 4, 3};
        int[] res2 = solver.nextGreaterElements(nums2);
        System.out.println("Input:  " + Arrays.toString(nums2));
        System.out.println("Output: " + Arrays.toString(res2)); // Expected: [2, 3, 4, -1, 4]

        // Test Case 3: Strictly decreasing array
        int[] nums3 = {5, 4, 3, 2, 1};
        int[] res3 = solver.nextGreaterElements(nums3);
        System.out.println("Input:  " + Arrays.toString(nums3));
        System.out.println("Output: " + Arrays.toString(res3)); // Expected: [-1, 5, 5, 5, 5]

        // Test Case 4: All identical elements
        int[] nums4 = {7, 7, 7, 7};
        int[] res4 = solver.nextGreaterElements(nums4);
        System.out.println("Input:  " + Arrays.toString(nums4));
        System.out.println("Output: " + Arrays.toString(res4)); // Expected: [-1, -1, -1, -1]
    }
}
