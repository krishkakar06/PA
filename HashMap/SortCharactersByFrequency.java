import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * LeetCode 451: Sort Characters By Frequency
 *
 * Problem Description:
 * Given a string s, sort it in decreasing order based on the frequency of the characters.
 * The frequency of a character is the number of times it appears in the string.
 * Return the sorted string. If there are multiple valid answers, return any of them.
 *
 * Approaches:
 * 1. HashMap + PriorityQueue (Max-Heap) [Primary implementation]:
 *    - Step 1: Count the frequency of each character using a HashMap.
 *    - Step 2: Push all unique characters into a PriorityQueue (Max-Heap), customized with
 *              a comparator that orders characters by their frequencies in descending order.
 *    - Step 3: Repeatedly extract (poll) characters from the PriorityQueue and append each
 *              character to a StringBuilder according to its frequency count.
 *    - Step 4: Convert StringBuilder to String and return.
 *
 * 2. HashMap + Bucket Sort [Alternative O(N) solution]:
 *    - Count frequencies using a HashMap.
 *    - Create an array of buckets (lists) indexed from 0 to s.length().
 *    - Place each character into the bucket corresponding to its frequency.
 *    - Iterate backwards from the maximum possible frequency down to 1 and build the output string.
 *
 * Complexity Analysis (PriorityQueue Approach):
 * - Time Complexity: O(N + K log K)
 *   - Counting character frequencies takes O(N) time, where N is the length of s.
 *   - Inserting K distinct characters into the PriorityQueue takes O(K log K) time,
 *     where K is the number of unique characters in s.
 *   - Polling K characters from the heap and appending them takes O(N + K log K) time.
 *   - Since K is bounded by the character set size (e.g., at most 62 for alphanumeric,
 *     or at most 256 for extended ASCII), K log K is bounded by a constant, making the overall
 *     time complexity O(N).
 * - Space Complexity: O(N)
 *   - HashMap stores up to K entries: O(K) space.
 *   - PriorityQueue stores up to K entries: O(K) space.
 *   - StringBuilder stores the resulting string of length N: O(N) space.
 *   - Overall auxiliary space is O(N) (or O(K) auxiliary space excluding the output string).
 */
public class SortCharactersByFrequency {

    /**
     * Sorts the characters of the string in decreasing order based on their frequency.
     * Uses a HashMap for frequency counts and a PriorityQueue (Max-Heap) for ordering.
     *
     * @param s The input string to sort.
     * @return The string sorted by character frequency in descending order.
     */
    public String frequencySort(String s) {
        // Edge cases: null or strings of length 0 or 1 are already sorted
        if (s == null || s.length() <= 1) {
            return s;
        }

        // Step 1: Count the frequency of each character using a HashMap
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Step 2: Create a Max-Heap (PriorityQueue) to order characters by frequency descending.
        // If frequencies are equal, character order does not matter as per problem specifications.
        PriorityQueue<Character> maxHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(freqMap.get(b), freqMap.get(a))
        );

        // Add all unique characters into the max-heap
        maxHeap.addAll(freqMap.keySet());

        // Step 3: Build the result string by extracting characters with highest frequency first
        StringBuilder sb = new StringBuilder(s.length());
        while (!maxHeap.isEmpty()) {
            char c = maxHeap.poll();
            int count = freqMap.get(c);
            for (int i = 0; i < count; i++) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Alternative solution using Bucket Sort.
     * Demonstrates linear O(N) time complexity without heap overhead.
     *
     * @param s The input string to sort.
     * @return The string sorted by character frequency in descending order.
     */
    public String frequencySortBucketSort(String s) {
        // Edge cases
        if (s == null || s.length() <= 1) {
            return s;
        }

        // Step 1: Count frequencies
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Step 2: Bucket array where index represents frequency (0 to s.length())
        @SuppressWarnings("unchecked")
        List<Character>[] buckets = new ArrayList[s.length() + 1];

        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            char c = entry.getKey();
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(c);
        }

        // Step 3: Iterate backwards from maximum frequency down to 1 to build the result
        StringBuilder sb = new StringBuilder(s.length());
        for (int freq = buckets.length - 1; freq > 0; freq--) {
            if (buckets[freq] != null) {
                for (char c : buckets[freq]) {
                    for (int i = 0; i < freq; i++) {
                        sb.append(c);
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * Main method to test and demonstrate the solution with example test cases.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SortCharactersByFrequency solver = new SortCharactersByFrequency();

        // Test Case 1: Standard case with multiple characters
        // Input: "tree" -> Expected: "eert" or "eetr"
        String test1 = "tree";
        System.out.println("Test 1 (tree): " + solver.frequencySort(test1));

        // Test Case 2: Tied frequencies
        // Input: "cccaaa" -> Expected: "cccaaa" or "aaaccc"
        String test2 = "cccaaa";
        System.out.println("Test 2 (cccaaa): " + solver.frequencySort(test2));

        // Test Case 3: Case-sensitive characters
        // Input: "Aabb" -> Expected: "bbAa" or "bbaA"
        String test3 = "Aabb";
        System.out.println("Test 3 (Aabb): " + solver.frequencySort(test3));

        // Test Case 4: Single character string
        // Input: "a" -> Expected: "a"
        System.out.println("Test 4 (a): " + solver.frequencySort(test4));

        // Test Case 5: Empty string
        // Input: "" -> Expected: ""
        String test5 = "";
        System.out.println("Test 5 (empty): \"" + solver.frequencySort(test5) + "\"");

        // Verify Bucket Sort implementation yields identical valid results
        System.out.println("Bucket Sort Test 1 (tree): " + solver.frequencySortBucketSort(test1));
        System.out.println("Bucket Sort Test 3 (Aabb): " + solver.frequencySortBucketSort(test3));
    }
}
