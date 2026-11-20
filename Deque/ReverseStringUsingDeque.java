import java.util.ArrayDeque;
import java.util.Deque;

/**
 * File Name: ReverseStringUsingDeque.java
 *
 * Problem Statement:
 * Reverse a given string using a Deque (Double-Ended Queue) data structure.
 *
 * Algorithm & Approach:
 * 1. A Deque provides LIFO (Last-In-First-Out) stack functionality via
 *    the push() and pop() methods.
 * 2. Traverse the input string character-by-character and push each character
 *    onto the Deque (which inserts at the front/head, acting as the top of the stack).
 * 3. Pop each character from the stack-end (head) and append it to a StringBuilder.
 *    Because the most recently pushed characters are popped first, the reconstructed
 *    string is in reverse order.
 *
 * Complexity Analysis:
 * - Time Complexity: O(n), where n is the length of the string.
 *   Each character is pushed and popped exactly once.
 * - Auxiliary Space Complexity: O(n) for the Deque and the resulting StringBuilder.
 */
public class ReverseStringUsingDeque {

    /**
     * Reverses the input string using a Deque as a LIFO stack.
     *
     * @param s the input string to reverse
     * @return the reversed string, or null if the input string is null
     */
    public String reverse(String s) {
        // Base case: check for null reference
        if (s == null) {
            return null;
        }

        // Initialize an ArrayDeque of Characters.
        // ArrayDeque is preferred over the legacy java.util.Stack class because
        // it is not synchronized (less overhead) and provides faster LIFO operations.
        Deque<Character> deque = new ArrayDeque<>();

        // Step 1: Push each character of the string onto the Deque (stack).
        // push() inserts the element at the front (head) of the deque,
        // which represents the top of the stack.
        for (int i = 0; i < s.length(); i++) {
            deque.push(s.charAt(i));
        }

        // Step 2: Pop characters from the stack-end to reverse the string.
        // pop() retrieves and removes the element at the front (head) of the deque.
        // Since LIFO ordering is maintained, characters come out in reverse order.
        StringBuilder reversed = new StringBuilder(s.length());
        while (!deque.isEmpty()) {
            reversed.append(deque.pop());
        }

        return reversed.toString();
    }

    /**
     * Demonstration of string reversal using various test scenarios.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        ReverseStringUsingDeque reverser = new ReverseStringUsingDeque();

        // Test cases covering standard input, palindromes, single char, empty, spaces, and null
        String[] testCases = {
            "Hello, World!",
            "Java Deque",
            "racecar",
            "A",
            "",
            "12345!@#$%",
            "   leading and trailing spaces   ",
            null
        };

        System.out.println("=== Reverse String Using Deque Demo ===");
        for (String testCase : testCases) {
            String result = reverser.reverse(testCase);
            System.out.println("Original: " + (testCase == null ? "null" : "\"" + testCase + "\""));
            System.out.println("Reversed: " + (result == null ? "null" : "\"" + result + "\""));
            System.out.println("----------------------------------------");
        }
    }
}