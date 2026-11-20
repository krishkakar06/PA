// File: TopKFrequentElements.java

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * LeetCode 347: Top K Frequent Elements
 * 
 * Problem Statement:
 * Given an integer array nums and an integer k, return the k most frequent elements.
 * You may return the answer in any order.
 * 
 * Approach:
 * 1. Frequency Counting:
 *    - Use a HashMap to store the frequency of each unique element in nums.
 * 
 * 2. Min-Heap of Size K:
 *    - Maintain a PriorityQueue (min-heap) ordered by element frequency in ascending order.
 *    - Iterate through each unique element from the frequency map and offer it into the min-heap.
 *    - If the size of the heap exceeds k, poll the element with the lowest frequency.
 *    - After iterating through all unique elements, the heap retains exactly the k most frequent elements.
 * 
 * 3. Result Extraction:
 *    - Extract the k elements from the heap into an integer array.
 * 
 * Complexity Analysis:
 * - Time Complexity: O(N log k), where N is the number of elements in nums.
 *   - Building the frequency map takes O(N) time.
 *   - Inserting up to N unique elements into a heap of maximum size k takes O(N log k) time.
 *   - Extracting k elements takes O(k log k) time.
 *   - Overall Time Complexity: O(N log k), which is asymptotically faster than O(N log N) sorting when k < N.
 * - Space Complexity: O(N + k)
 *   - HashMap stores up to N distinct elements: O(N).
 *   - Min-heap stores at most k + 1 elements: O(k).
 *   - Overall Space Complexity: O(N) auxiliary space.
 */
public class TopKFrequentElements {

    /**
     * Finds the k most frequent elements in the given array.
     *
     * @param nums Array of integers.
     * @param k    The number of top frequent elements to return.
     * @return An array containing the k most frequent elements.
     */
    public int[] topKFrequent(int[] nums, int k) {
        // Step 0: Edge cases validation
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        // Step 1: Count the frequency of each number using a HashMap
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Step 2: Initialize a min-heap ordered by frequency in ascending order.
        // Elements with smaller frequency will have higher priority to be evicted first.
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(frequencyMap.get(a), frequencyMap.get(b))
        );

        // Step 3: Maintain the heap of size k with the highest frequency elements
        for (int num : frequencyMap.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict the least frequent element currently in the heap
            }
        }

        // Step 4: Extract the top k frequent elements from the min-heap into the result array
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }

        return result;
    }

    /**
     * Main method to test and demonstrate the solution with example test cases.
     */
    public static void main(String[] args) {
        TopKFrequentElements solution = new TopKFrequentElements();

        // Example 1:
        // Input: nums = [1, 1, 1, 2, 2, 3], k = 2
        // Output: [2, 1] or [1, 2] (in any order)
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        int[] result1 = solution.topKFrequent(nums1, k1);
        System.out.println("Example 1 Output: " + Arrays.toString(result1));

        // Example 2:
        // Input: nums = [1], k = 1
        // Output: [1]
        int[] nums2 = {1};
        int k2 = 1;
        int[] result2 = solution.topKFrequent(nums2, k2);
        System.out.println("Example 2 Output: " + Arrays.toString(result2));

        // Example 3: Handling negative numbers
        // Input: nums = [4, 1, -1, 2, -1, 2, 3], k = 2
        // Output: [2, -1] or [-1, 2] (in any order)
        int[] nums3 = {4, 1, -1, 2, -1, 2, 3};
        int k3 = 2;
        int[] result3 = solution.topKFrequent(nums3, k3);
        System.out.println("Example 3 Output: " + Arrays.toString(result3));
    }
}
