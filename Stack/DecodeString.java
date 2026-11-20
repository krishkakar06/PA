import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 394 - Decode String
 *
 * Problem Description:
 * Given an encoded string, return its decoded string.
 * The encoding rule is: k[encoded_string], where the encoded_string inside
 * the square brackets is being repeated exactly k times.
 * k is guaranteed to be a positive integer.
 *
 * Example:
 * Input: s = "3[a2[c]]"
 * Output: "accaccacc"
 *
 * Approach:
 * Two Stacks (Counts & Strings)
 * - countStack: Stores multiplier integers (k) encountered before '['.
 * - stringStack: Stores the intermediate decoded strings built prior to the current bracket depth.
 *
 * Time Complexity:  O(N * maxK), where N is the length of the string and maxK is the maximum count.
 * Space Complexity: O(N), for the stack space and string builder representations.
 */
public class DecodeString {

    /**
     * Decodes an encoded string using two stacks.
     *
     * @param s The encoded string input.
     * @return The fully decoded string.
     */
    public String decodeString(String s) {
        // Stack to store repeat multiplier numbers (k)
        Deque<Integer> countStack = new ArrayDeque<>();

        // Stack to store string prefixes accumulated before entering nested brackets
        Deque<StringBuilder> stringStack = new ArrayDeque<>();

        // Tracks the string currently being formed at the present nesting level
        StringBuilder currentString = new StringBuilder();

        // Tracks the current integer multiplier (handles multi-digit numbers like "10[a]")
        int currentK = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                // If the character is a digit, accumulate it into currentK
                // Multiplies existing value by 10 to support multi-digit integers
                currentK = currentK * 10 + (ch - '0');
            } else if (ch == '[') {
                // Entering a new nested scope:
                // 1. Push the accumulated multiplier onto countStack
                // 2. Push the currently built string onto stringStack
                countStack.push(currentK);
                stringStack.push(currentString);

                // Reset trackers for the content inside this new scope
                currentK = 0;
                currentString = new StringBuilder();
            } else if (ch == ']') {
                // Exiting the current nested scope:
                // 1. Pop the repeat count corresponding to this bracket
                // 2. Pop the preceding string prefix
                int count = countStack.pop();
                StringBuilder decodedPrefix = stringStack.pop();

                // Repeat currentString 'count' times and append to the prefix
                for (int j = 0; j < count; j++) {
                    decodedPrefix.append(currentString);
                }

                // Update currentString to be the combined result
                currentString = decodedPrefix;
            } else {
                // Normal alphabetical character; append to current string segment
                currentString.append(ch);
            }
        }

        return currentString.toString();
    }

    /**
     * Main method demonstrating sample test cases.
     */
    public static void main(String[] args) {
        DecodeString decoder = new DecodeString();

        // Test Case 1: Nested brackets
        String test1 = "3[a2[c]]";
        System.out.println("Input:  " + test1);
        System.out.println("Output: " + decoder.decodeString(test1)); // Expected: "accaccacc"

        // Test Case 2: Consecutive bracket groups
        String test2 = "2[abc]3[cd]ef";
        System.out.println("Input:  " + test2);
        System.out.println("Output: " + decoder.decodeString(test2)); // Expected: "abcabccdcdcdef"

        // Test Case 3: Multi-digit count
        String test3 = "10[a]";
        System.out.println("Input:  " + test3);
        System.out.println("Output: " + decoder.decodeString(test3)); // Expected: "aaaaaaaaaa"
    }
}
