import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 239 - Sliding Window Maximum
 * 
 * Approach: Monotonic Deque (Double-ended Queue)
 * ------------------------------------------------
 * A monotonic decreasing deque is maintained to keep track of useful elements
 * in the current sliding window. The deque stores indices rather than values
 * so that we can easily verify whether an element is still within the window bounds.
 * 
 * Invariant maintained:
 * 1. Indices in the deque correspond to array elements in strictly decreasing order:
 *    nums[deque.peekFirst()] > nums[...] > nums[deque.peekLast()].
 * 2. The front of the deque (peekFirst) always stores the index of the maximum element
 *    in the current window.
 * 3. All stored indices are within the current window range [i - k + 1, i].
 * 
 * Operations per element at index i:
 * 1. Evict elements outside the current window:
 *    If the element at the front of the deque has an index <= i - k, it falls
 *    outside the sliding window and must be removed using pollFirst().
 * 
 * 2. Maintain monotonic decreasing order:
 *    While the deque is not empty and the value at the end of the deque
 *    (nums[deque.peekLast()]) is less than or equal to nums[i], remove it using pollLast().
 *    Reasoning: nums[i] is newer and greater than or equal to those elements, meaning those
 *    smaller elements can never be the maximum in the current or any future window.
 * 
 * 3. Add current index:
 *    Add index i to the back of the deque using offerLast(i).
 * 
 * 4. Record the result:
 *    Once the index i reaches at least k - 1 (the first full window is formed),
 *    the maximum for the window ending at i is at nums[deque.peekFirst()].
 *    Record this at result[i - k + 1].
 * 
 * Complexity:
 * - Time Complexity: O(n), where n is the number of elements in nums. Each index is pushed
 *   into the deque once and popped at most once across the entire iteration.
 * - Auxiliary Space: O(k) for the deque, which stores at most k indices at any time.
 */
public class SlidingWindowMaximum {

    /**
     * Finds the maximum value in each sliding window of size k.
     *
     * @param nums Array of integers.
     * @param k    Window size.
     * @return Array containing the maximum of each sliding window.
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        // Edge cases check: empty array or invalid window size
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        // There are n - k + 1 windows in total
        int[] result = new int[n - k + 1];

        // Monotonic deque storing indices of elements.
        // Elements in nums corresponding to indices in the deque are in descending order.
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Step 1: Remove indices that are out of the current window [i - k + 1, i]
            // The oldest element in the window is at i - k + 1. Any index <= i - k is expired.
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // Step 2: Remove indices of all elements smaller than or equal to nums[i]
            // from the back of the deque to maintain the monotonic decreasing property.
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            // Step 3: Add the current element's index to the end of the deque
            deque.offerLast(i);

            // Step 4: Record the maximum value once the first valid window of size k is formed
            if (i >= k - 1) {
                // The element at the front of the deque is always the maximum in the current window
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}