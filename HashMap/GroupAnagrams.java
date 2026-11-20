import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * File: GroupAnagrams.java
 *
 * Problem: LeetCode 49 - Group Anagrams
 *
 * Description:
 * Given an array of strings `strs`, group the anagrams together.
 * You can return the answer in any order.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of
 * a different word or phrase, typically using all the original letters exactly once.
 *
 * Approach:
 * - We use a HashMap<String, List<String>> where:
 *     - Key: The sorted string of characters (canonical representation of an anagram).
 *     - Value: A list of strings from the input array that match this anagram signature.
 * - For example, "eat", "tea", and "ate" all sort to "aet". Thus, they map to the same key.
 *
 * Complexity:
 * - Time Complexity: O(N * K log K), where N is the number of strings in strs,
 *   and K is the maximum length of a string in strs. Sorting each string takes O(K log K) time.
 * - Space Complexity: O(N * K), to store the grouped strings and keys in the HashMap.
 */
public class GroupAnagrams {

    /**
     * Groups anagrams together from the provided array of strings.
     *
     * @param strs Array of strings to group.
     * @return List of lists of strings, where each list contains anagrams.
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        // Guard against null or empty input
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }

        // HashMap storing sorted character string as key and list of anagrams as value
        HashMap<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            // Convert the string to a character array
            char[] chars = s.toCharArray();

            // Sort the character array in alphabetical order
            Arrays.sort(chars);

            // Reconstruct the key from the sorted character array
            String sortedKey = String.valueOf(chars);

            // If the key is not present in the map, initialize a new ArrayList
            if (!map.containsKey(sortedKey)) {
                map.put(sortedKey, new ArrayList<>());
            }

            // Append the original string to the corresponding anagram group
            map.get(sortedKey).add(s);
        }

        // Return all the grouped anagram lists
        return new ArrayList<>(map.values());
    }

    /**
     * Main method to test and demonstrate the functionality with standard test cases.
     */
    public static void main(String[] args) {
        GroupAnagrams solution = new GroupAnagrams();

        // Test Case 1: Standard example
        String[] test1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Test 1 Result: " + solution.groupAnagrams(test1));

        // Test Case 2: Empty string
        String[] test2 = {""};
        System.out.println("Test 2 Result: " + solution.groupAnagrams(test2));

        // Test Case 3: Single character
        String[] test3 = {"a"};
        System.out.println("Test 3 Result: " + solution.groupAnagrams(test3));
    }
}