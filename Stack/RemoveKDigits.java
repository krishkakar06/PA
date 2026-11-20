import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 402 - Remove K Digits
 *
 * Problem Statement:
 * Given a string 'num' representing a non-negative integer and an integer 'k',
 * return the smallest possible integer after removing 'k' digits from 'num'.
 *
 * Approach:
 * Greedy Algorithm with a Monotonic Stack (Increasing).
 *
 * Key Insights:
 * 1. For two numbers of the same length, the one with a smaller digit at the
 *    most significant position (leftmost) is smaller. Therefore, our primary goal
 *    is to make earlier digits as small as possible.
 * 2. As we scan digits from left to right, whenever we encounter a digit smaller
 *    than the previous one, removing the previous (larger) digit gives a smaller
 *    number.
 * 3. We maintain a monotonic increasing stack of digits. For each digit 'c' in 'num':
 *    - While k > 0, stack is non-empty, and stack.peek() > c:
 *        pop the top digit from the stack and decrement k.
 *    - Push 'c' onto the stack.
 * 4. If k > 0 remains after scanning all characters (for instance, when all digits
 *    in the stack are in non-decreasing order like "12345"), we pop the remaining
 *    k digits from the top of the stack (the largest / least significant digits).
 * 5. Reconstruct the final string by reading digits from bottom to top (most significant
 *    to least significant) of the stack, skipping any leading zeros.
 * 6. If all digits are removed or the result consists entirely of leading zeros,
 *    return "0".
 *
 * Complexity:
 * - Time Complexity: O(N), where N is the length of the string 'num'. Each digit
 *   is pushed onto the stack once and popped at most once.
 * - Space Complexity: O(N) to store digits in the stack and construct the output string.
 */
public class RemoveKDigits {

    /**
     * Removes k digits from the given non-negative integer string num to form the smallest number.
     *
     * @param num The input string representing a non-negative integer.
     * @param k   The number of digits to remove.
     * @return The smallest possible number as a string.
     */
    public String removeKdigits(String num, int k) {
        // Base case: If k is greater than or equal to the length of num,
        // all digits are removed, resulting in "0".
        if (num == null || k >= num.length()) {
            return "0";
        }

        // Use a Deque as a monotonic increasing stack to store characters.
        // Stack top represents the most recently added digit.
        Deque<Character> stack = new ArrayDeque<>();

        // Process each digit in the input string
        for (int i = 0; i < num.length(); i++) {
            char currentDigit = num.charAt(i);

            // While we still need to remove digits (k > 0), the stack is not empty,
            // and the previous digit (stack top) is strictly greater than the current digit:
            // Pop the larger previous digit to place the smaller current digit in a higher-order position.
            while (!stack.isEmpty() && k > 0 && stack.peek() > currentDigit) {
                stack.pop();
                k--;
            }

            // Push the current digit onto the monotonic stack
            stack.push(currentDigit);
        }

        // Corner Case: If k digits have not been removed yet (e.g., input was monotonically increasing),
        // remove the remaining k digits from the top of the stack (the largest digits at the end).
        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }

        // Build the final number from the stack.
        // Since stack.push() adds elements to the front (head), the bottom of the stack
        // (most significant digits) is at the tail of the Deque.
        // We poll from the last (tail) to get digits in most-significant to least-significant order.
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            char digit = stack.pollLast();

            // Strip leading zeros: skip '0' if no non-zero digit has been appended yet
            if (result.length() == 0 && digit == '0') {
                continue;
            }

            result.append(digit);
        }

        // If the resulting string is empty (all digits were '0' or removed), return "0"
        return result.length() == 0 ? "0" : result.toString();
    }

    /**
     * Main method demonstrating usage and test cases.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        RemoveKDigits solver = new RemoveKDigits();

        // Test Case 1: Standard case
        // Input: num = "1432219", k = 3 -> Output: "1219"
        String result1 = solver.removeKdigits("1432219", 3);
        System.out.println("Test 1: num = \"1432219\", k = 3 => " + result1);

        // Test Case 2: Stripping leading zeros
        // Input: num = "10200", k = 1 -> Output: "200"
        String result2 = solver.removeKdigits("10200", 1);
        System.out.println("Test 2: num = \"10200\", k = 1 => " + result2);

        // Test Case 3: Removing all digits
        // Input: num = "10", k = 2 -> Output: "0"
        String result3 = solver.removeKdigits("10", 2);
        System.out.println("Test 3: num = \"10\", k = 2 => " + result3);

        // Test Case 4: Result reduces to zero after stripping leading zeros
        // Input: num = "10", k = 1 -> Output: "0"
        String result4 = solver.removeKdigits("10", 1);
        System.out.println("Test 4: num = \"10\", k = 1 => " + result4);

        // Test Case 5: Already sorted in increasing order
        // Input: num = "123456", k = 3 -> Output: "123"
        String result5 = solver.removeKdigits("123456", 3);
        System.out.println("Test 5: num = \"123456\", k = 3 => " + result5);

        // Test Case 6: Already sorted in decreasing order
        // Input: num = "987654", k = 3 -> Output: "654"
        String result6 = solver.removeKdigits("987654", 3);
        System.out.println("Test 6: num = \"987654\", k = 3 => " + result6);
    }
}