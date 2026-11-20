import java.util.HashMap;
import java.util.Map;

/**
 * Problem: LeetCode 454 - 4Sum II
 * 
 * Given four integer arrays nums1, nums2, nums3, and nums4 all of length n,
 * return the number of tuples (i, j, k, l) such that:
 * 0 <= i, j, k, l < n
 * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
 * 
 * Approach:
 * We can reduce the problem complexity from O(n^4) brute force down to O(n^2)
 * using a Hash Map (meet-in-the-middle / frequency counting strategy).
 * 
 * 1. Divide the 4 arrays into two groups: (nums1, nums2) and (nums3, nums4).
 * 2. Traverse all pairs (a, b) where a in nums1 and b in nums2. Compute their sum (a + b).
 *    Store the frequency of each sum in a hash map:
 *    map.put(sum, map.getOrDefault(sum, 0) + 1).
 * 3. Traverse all pairs (c, d) where c in nums3 and d in nums4.
 *    For each pair, we need nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0,
 *    which means nums1[i] + nums2[j] == -(nums3[k] + nums4[l]).
 *    Target sum = -(c + d).
 *    If the target sum exists in the hash map, add its frequency to the total count.
 * 4. Return the total count.
 * 
 * Complexity:
 * - Time Complexity: O(n^2), where n is the length of each array.
 *   Generating all pairs of (nums1, nums2) takes O(n^2) time.
 *   Iterating through all pairs of (nums3, nums4) with O(1) hash map lookups takes O(n^2) time.
 * - Space Complexity: O(n^2), since there can be at most n^2 unique pairwise sums
 *   stored in the hash map.
 */
public class FourSumII {

    /**
     * Counts the number of tuples (i, j, k, l) such that nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0.
     *
     * @param nums1 First integer array of length n
     * @param nums2 Second integer array of length n
     * @param nums3 Third integer array of length n
     * @param nums4 Fourth integer array of length n
     * @return Total number of 4-tuples that sum to zero
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        // Map to store the frequency of each pairwise sum of (nums1[i] + nums2[j])
        Map<Integer, Integer> sumCountMap = new HashMap<>();

        // Step 1: Iterate through all pairs in nums1 and nums2, and record the frequency of each sum
        for (int a : nums1) {
            for (int b : nums2) {
                int sum = a + b;
                sumCountMap.put(sum, sumCountMap.getOrDefault(sum, 0) + 1);
            }
        }

        int count = 0;

        // Step 2: Iterate through all pairs in nums3 and nums4
        // Check if the negative of their sum exists in the map
        for (int c : nums3) {
            for (int d : nums4) {
                int target = -(c + d);
                // If target is found in the map, add the number of matching pairs to count
                count += sumCountMap.getOrDefault(target, 0);
            }
        }

        return count;
    }
}