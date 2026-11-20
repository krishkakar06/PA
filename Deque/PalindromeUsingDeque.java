import java.util.ArrayDeque;
import java.util.Deque;

/**
 * PalindromeUsingDeque
 *
 * A complete Java solution to check whether a string is a palindrome using a Deque.
 *
 * Palindrome Definition:
 * A palindrome is a word, phrase, number, or sequence of characters that reads
 * the same forward and backward.
 *
 * Why use a Deque?
 * A Deque (Double-Ended Queue) allows efficient O(1) insertion and removal
 * operations at both ends (front and back).
 *
 * Algorithm:
 * 1. Push / add all characters of the input string to the rear of the Deque.
 * 2. While the Deque contains more than 1 character (size > 1):
 *    a. Remove the front character (removeFirst()).
 *    b. Remove the rear character (removeLast()).
 *    c. If they do not match, the string is NOT a palindrome -> return false.
 * 3. If all pairs match and loop terminates (deque size 0 or 1), the string IS a palindrome -> return true.
 *
 * Complexity Analysis:
 * - Time Complexity: O(n), where n is the length of the string.
 *   - Adding n characters to the deque takes O(n) time.
 *   - Comparing and removing n / 2 pairs from front/back takes O(n) time.
 *   - Total time is linear: O(n).
 * - Space Complexity: O(n) auxiliary space to store n characters in the Deque.
 */
public class PalindromeUsingDeque {

    /**
     * Checks if the given string is a palindrome using a Deque.
     * All characters from the string are added to the deque, and then
     * characters from the front and back are iteratively compared.
     *
     * @param s The string to test.
     * @return true if the string is a palindrome, false otherwise (or if s is null).
     */
    public boolean isPalindrome(String s) {
        // Base validation: a null reference cannot be a palindrome
        if (s == null) {
            return false;
        }

        // Initialize a double-ended queue.
        // ArrayDeque is preferred over LinkedList for lower memory overhead and better cache performance.
        Deque<Character> deque = new ArrayDeque<>();

        // Step 1: Add all characters to the rear of the deque
        for (int i = 0; i < s.length(); i++) {
            deque.addLast(s.charAt(i));
        }

        // Step 2: Compare front and back characters
        // When size is 0 (even length) or 1 (odd length), all comparisons have passed
        while (deque.size() > 1) {
            char front = deque.removeFirst();
            char back = deque.removeLast();

            // If front and back characters do not match, it's not a palindrome
            if (front != back) {
                return false;
            }
        }

        // All matched successfully
        return true;
    }

    /**
     * Overloaded helper method: Checks if a string is a palindrome with optional
     * case-insensitivity and filtering of non-alphanumeric characters (spaces, punctuation).
     *
     * @param s The string to test.
     * @param ignoreCase If true, treats uppercase and lowercase characters as equal.
     * @param alphanumericOnly If true, skips whitespace and punctuation.
     * @return true if the processed string is a palindrome, false otherwise.
     */
    public boolean isPalindrome(String s, boolean ignoreCase, boolean alphanumericOnly) {
        if (s == null) {
            return false;
        }

        Deque<Character> deque = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Filter non-alphanumeric characters if requested
            if (alphanumericOnly && !Character.isLetterOrDigit(c)) {
                continue;
            }

            // Normalize case if requested
            if (ignoreCase) {
                c = Character.toLowerCase(c);
            }

            deque.addLast(c);
        }

        while (deque.size() > 1) {
            char front = deque.removeFirst();
            char back = deque.removeLast();

            if (front != back) {
                return false;
            }
        }

        return true;
    }

    /**
     * Main method demonstrating the palindrome checker with various test cases.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        PalindromeUsingDeque checker = new PalindromeUsingDeque();

        System.out.println("===============================================================");
        System.out.println("       Palindrome Verification using Deque (ArrayDeque)        ");
        System.out.println("===============================================================");
        System.out.println();

        // Array of test cases to verify standard isPalindrome(String s)
        String[] testStrings = {
            "racecar",      // Standard odd-length palindrome
            "radar",        // Standard odd-length palindrome
            "noon",         // Standard even-length palindrome
            "deified",      // Palindrome
            "civic",        // Palindrome
            "hello",        // Not a palindrome
            "openai",       // Not a palindrome
            "a",            // Single character (trivial palindrome)
            "",             // Empty string (vacuously a palindrome)
            "12321",        // Numeric palindrome
            "12345",        // Non-palindrome numeric sequence
            "Racecar",      // Case mismatch without normalization ('R' != 'r')
            null            // Null input
        };

        System.out.println("--- Standard isPalindrome(String s) Tests (Exact Character Match) ---");
        System.out.printf("%-15s | %-12s | %s%n", "Input String", "Result", "Explanation");
        System.out.println("---------------------------------------------------------------");

        for (String test : testStrings) {
            boolean result = checker.isPalindrome(test);
            String displayStr = (test == null) ? "null" : "\"" + test + "\"";
            String explanation;

            if (test == null) {
                explanation = "Null input handled safely (returns false)";
            } else if (result) {
                explanation = "Reads identically forwards and backwards";
            } else {
                explanation = "Characters do not match symmetrically";
            }

            System.out.printf("%-15s | %-12b | %s%n", displayStr, result, explanation);
        }

        // Demonstrating the overloaded variant for sentence/phrase palindromes
        System.out.println();
        System.out.println("--- Phrase Palindromes with Normalization (Case & Punctuation Insensitive) ---");
        String[] phrases = {
            "A man, a plan, a canal: Panama",
            "Was it a car or a cat I saw?",
            "No 'x' in Nixon",
            "Madam, I'm Adam",
            "Step on no pets",
            "This is not a palindrome"
        };

        System.out.printf("%-35s | %-8s%n", "Input Phrase", "Result");
        System.out.println("---------------------------------------------------------------");

        for (String phrase : phrases) {
            boolean result = checker.isPalindrome(phrase, true, true);
            System.out.printf("%-35s | %-8b%n", "\"" + phrase + "\"", result);
        }

        System.out.println();
        System.out.println("===============================================================");
        System.out.println("                      Demonstration Completed                  ");
        System.out.println("===============================================================");
    }
}
