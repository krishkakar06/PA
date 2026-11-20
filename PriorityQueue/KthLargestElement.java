import java.util.PriorityQueue;

/**
 * LeetCode 215: Kth Largest Element in an Array
 *
 * Problem Description:
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * Note that it is the kth largest element in sorted order, not the kth distinct element.
 *
 * Approach: Min-Heap of size k (PriorityQueue)
 * -------------------------------------------------------------
 * 1. Maintain a min-heap (PriorityQueue in Java defaults to min-heap) to keep track
 *    of the k largest elements seen so far.
 * 2. As we iterate through the array:
 *    - Add the current number to the min-heap.
 *    - If the size of the min-heap exceeds k, remove (poll) the smallest element.
 *    - By doing this, the min-heap will always contain at most k elements.
 * 3. After processing all elements in nums, the heap contains the k largest elements
 *    from the entire array.
 * 4. Since it is a min-heap, the root (peek) of the heap will be the smallest of these
 *    k largest elements, which is precisely the kth largest element in the entire array.
 *
 * Complexity Analysis:
 * - Time Complexity: O(N * log(k)), where N is the length of the nums array.
 *   Each insertion/removal operation in a heap of size at most (k + 1) takes O(log k) time.
 *   Iterating over N elements yields O(N * log k). This is faster than sorting (O(N * log N))
 *   when k is significantly smaller than N.
 * - Space Complexity: O(k) auxiliary space, as the min-heap stores at most (k + 1) elements
 *   at any point in time.
 */
public class KthLargestElement {

    /**
     * Finds the kth largest element in the given integer array.
     *
     * @param nums The array of integers.
     * @param k    The rank (1-indexed) of the largest element to find.
     * @return The kth largest element in the array.
     * @throws IllegalArgumentException if the input array is null, has fewer than k elements, or k <= 0.
     */
    public int findKthLargest(int[] nums, int k) {
        // Validate input constraints
        if (nums == null || nums.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input: nums must contain at least k elements and k > 0.");
        }

        // Java's PriorityQueue implements a min-heap by default (natural ordering).
        // Initial capacity is set to k + 1 to avoid internal array resizes during insertions.
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k + 1);

        // Process each number in the array
        for (int num : nums) {
            // Add the current number to the min-heap
            minHeap.offer(num);

            // If the heap contains more than k elements, evict the smallest element
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // The top of the min-heap is the smallest among the k largest elements,
        // which represents the kth largest element overall.
        return minHeap.peek();
    }

    /**
     * Main method providing test cases for demonstration.
     */
    public static void main(String[] args) {
        KthLargestElement solver = new KthLargestElement();

        // Example 1:
        // Input: nums = [3, 2, 1, 5, 6, 4], k = 2
        // Output: 5
        int[] nums1 = {3, 2, 1, 5, 6, 4};
        int k1 = 2;
        System.out.println("Test 1 Result: " + solver.findKthLargest(nums1, k1)); // Expected: 5

        // Example 2:
        // Input: nums = [3, 2, 3, 1, 2, 4, 5, 5, 6], k = 4
        // Output: 4
        int[] nums2 = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k2 = 4;
        System.out.println("Test 2 Result: " + solver.findKthLargest(nums2, k2)); // Expected: 4
    }
}
