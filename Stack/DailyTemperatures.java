import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 739 - Daily Temperatures
 *
 * Problem:
 * Given an array of integers 'temperatures' representing daily temperatures,
 * return an array 'answer' such that answer[i] is the number of days you have
 * to wait after the i-th day to get a warmer temperature. If there is no
 * future day for which this is possible, keep answer[i] == 0 instead.
 *
 * Monotonic Stack Approach:
 * -------------------------
 * We maintain a "monotonic decreasing stack" that stores the INDICES of the days.
 * The invariant maintained is that the temperatures corresponding to the indices
 * in the stack are in non-increasing order from bottom to top.
 *
 * Algorithm:
 * 1. Initialize an array 'answer' of the same length as 'temperatures' (initialized to 0).
 * 2. Traverse the temperatures array from index 0 to n - 1.
 * 3. For each day 'i' with temperature temperatures[i]:
 *    - While the stack is not empty and the current temperature is strictly greater
 *      than the temperature at the index stored at the top of the stack (temperatures[stack.peek()]):
 *        a. We have found the "next warmer day" for the day at stack.pop().
 *        b. Let prevIndex = stack.pop().
 *        c. The wait time for prevIndex is the distance between current day 'i' and prevIndex:
 *           answer[prevIndex] = i - prevIndex.
 *    - Push the current day index 'i' onto the stack.
 * 4. Any indices remaining on the stack at the end of traversal do not have a warmer
 *    temperature in the future. Their answer entries remain 0 (default in Java).
 *
 * Complexity Analysis:
 * - Time Complexity: O(n), where n is the length of temperatures. Each index is pushed
 *   onto the stack at most once and popped at most once.
 * - Space Complexity: O(n), to store indices in the stack in the worst case (temperatures
 *   in non-increasing order).
 */
public class DailyTemperatures {

    /**
     * Finds the number of days until a warmer day for each daily temperature.
     *
     * @param temperatures array of daily temperatures
     * @return array where answer[i] is the number of days to wait for a warmer day
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            return new int[0];
        }

        int n = temperatures.length;
        int[] answer = new int[n];

        // ArrayDeque is used as an efficient, modern stack implementation
        Deque<Integer> stack = new ArrayDeque<>();

        // Iterate through each day by index
        for (int i = 0; i < n; i++) {
            // While today is warmer than the day at the top of the stack,
            // today is the next warmer day for stack.peek().
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevDay = stack.pop();
                answer[prevDay] = i - prevDay;
            }

            // Push current day index onto the stack
            stack.push(i);
        }

        // Indices remaining in the stack have no warmer future day and remain 0.
        return answer;
    }
}