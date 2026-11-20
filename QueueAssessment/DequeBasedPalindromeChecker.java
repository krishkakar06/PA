import java.util.ArrayDeque;

/**
 * DequeBasedPalindromeChecker
 * 
 * A complete solution for checking if a given string is a palindrome using a
 * Double-Ended Queue (Deque) data structure.
 * 
 * Palindrome Definition:
 * A palindrome is a word, phrase, number, or other sequence of characters
 * that reads the same forward and backward.
 * 
 * Algorithm Concept:
 * A double-ended queue (Deque) allows efficient O(1) insertion and deletion at both ends.
 * By populating a Deque with characters from the string (after ignoring spaces and 
 * converting to uniform case), we can repeatedly compare and remove the front (first) 
 * and rear (last) characters.
 * - If at any point the front and rear characters do not match, the string is not a palindrome.
 * - If all pairs match until the deque is empty (even length) or contains 1 element (odd length),
 *   the string is a palindrome.
 * 
 * Complexity:
 * - Time Complexity: O(n), where n is the length of the string.
 *   Iterating through string and inserting into ArrayDeque takes O(n).
 *   Comparing characters removes two elements per step in O(1) time each, taking at most n/2 steps.
 * - Space Complexity: O(n), to store the filtered characters in the ArrayDeque.
 */
public class DequeBasedPalindromeChecker {

    /**
     * Checks whether the provided string is a palindrome using an ArrayDeque<Character>.
     * Spaces are ignored and character comparison is case-insensitive.
     *
     * @param s The input string to check.
     * @return true if the string is a palindrome, false otherwise (including null input).
     */
    public boolean isPalindrome(String s) {
        // Handle null input safely
        if (s == null) {
            return false;
        }

        // Initialize a double-ended queue using ArrayDeque<Character>
        ArrayDeque<Character> deque = new ArrayDeque<>();

        // Normalize string: ignore spaces and convert characters to lowercase
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Ignore spaces (including general whitespace characters)
            if (Character.isWhitespace(c)) {
                continue;
            }
            // Add normalized character to the rear of the deque
            deque.addLast(Character.toLowerCase(c));
        }

        // Compare characters from both ends until 0 or 1 character remains
        while (deque.size() > 1) {
            char frontChar = deque.removeFirst();
            char rearChar = deque.removeLast();

            // If front and rear characters do not match, it is not a palindrome
            if (frontChar != rearChar) {
                return false;
            }
        }

        // All matched successfully or string has 0 or 1 character
        return true;
    }

    /**
     * Main method demonstrating the palindrome checker with multiple test cases.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        DequeBasedPalindromeChecker checker = new DequeBasedPalindromeChecker();

        // Array of diverse test cases
        String[] testCases = {
            "racecar",                   // Standard odd-length palindrome
            "noon",                      // Standard even-length palindrome
            "Radar",                     // Case-insensitive check
            "Never odd or even",         // Multiple words with spaces and mixed case
            "Was it a cat I saw",        // Sentence with spaces and mixed case
            "Do geese see God",          // Another classic palindrome with spaces
            "step on no pets",           // Palindromic phrase
            "A",                         // Single character
            "",                          // Empty string
            "   ",                       // Whitespace-only string
            "hello",                     // Non-palindrome
            "Java",                      // Non-palindrome
            "palindrome",                // Non-palindrome
            "not a palindrome",          // Non-palindrome phrase
            "abcca",                     // Near palindrome (mismatch in the middle)
            null                         // Edge case: null reference
        };

        System.out.println("======================================================================");
        System.out.println("          DEQUE-BASED PALINDROME CHECKER (ArrayDeque<Character>)      ");
        System.out.println("======================================================================");
        System.out.printf("%-35s | %-10s%n", "Input String", "Is Palindrome?");
        System.out.println("----------------------------------------------------------------------");

        for (String test : testCases) {
            boolean result = checker.isPalindrome(test);
            String displayInput = (test == null) ? "null" : "\"" + test + "\"";
            System.out.printf("%-35s | %-10b%n", displayInput, result);
        }

        System.out.println("======================================================================");
    }
}
