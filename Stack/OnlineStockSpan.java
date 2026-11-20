import java.util.ArrayDeque;
import java.util.Deque;

/**
 * File: OnlineStockSpan.java
 * LeetCode 901 - Online Stock Span
 *
 * Problem Description:
 * Design an algorithm that collects daily price quotes for some stock and returns
 * the span of that stock's price for the current day.
 *
 * The span of the stock's price in one day is the maximum number of consecutive
 * days (starting from that day and going backward) for which the stock price was
 * less than or equal to the price of that day.
 *
 * Approach:
 * Monotonic Decreasing Stack of [price, span] pairs.
 * - Each element stored in the stack is an int[] array of size 2:
 *     element[0] = price of the stock on a particular day
 *     element[1] = span accumulated up to and including that day
 *
 * Algorithm:
 * 1. Initialize span = 1 for the current day (the day itself).
 * 2. While the stack is not empty and the price at the top of the stack is
 *    less than or equal to the current price (stack.peek()[0] <= price):
 *      - The current day's price is greater than or equal to that previous day's price,
 *        which means the current day can span over all consecutive days that the
 *        previous day spanned over.
 *      - Pop that pair and add its span to the current day's span: span += stack.pop()[1].
 * 3. Push the new pair [price, span] onto the stack.
 * 4. Return the computed span.
 *
 * Complexity Analysis:
 * - Time Complexity:
 *     Amortized O(1) per call to next(int price).
 *     Each price quote is pushed onto the stack exactly once and popped at most once.
 *     Across N calls to next(), the while loop executes at most N pop operations total.
 *     Thus, the total time across N calls is O(N), yielding an amortized O(1) time per call.
 * - Space Complexity:
 *     O(N) worst-case space complexity, where N is the number of calls to next().
 *     In the worst case (prices in strictly decreasing order, e.g., [100, 90, 80, ...]),
 *     no elements are popped, and the stack stores all N [price, span] pairs.
 */
class StockSpanner {

    /**
     * Monotonic decreasing stack storing pairs of [price, span].
     *
     * ArrayDeque is used over java.util.Stack because it is faster,
     * non-synchronized, and the standard recommendation for LIFO stack
     * operations in modern Java.
     *
     * In each pair:
     *   pair[0] -> stock price
     *   pair[1] -> span of days
     */
    private final Deque<int[]> stack;

    /**
     * Initializes the StockSpanner object.
     * Sets up an empty stack to collect daily stock quotes and their spans.
     */
    public StockSpanner() {
        this.stack = new ArrayDeque<>();
    }

    /**
     * Collects daily price quotes for the stock and returns the span of that
     * stock's price for the current day.
     *
     * @param price the price of the stock for the current day
     * @return the maximum number of consecutive days (starting today and going
     *         backward) for which the stock price was <= current price
     */
    public int next(int price) {
        // The current day always counts towards its own span (at least 1 day).
        int span = 1;

        // Maintain the monotonic decreasing property of the stack.
        // Pop all previous entries whose price is <= current price and
        // aggregate their spans into the current day's span.
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }

        // Push the current price and its accumulated span onto the stack.
        // Future quotes that exceed or equal 'price' will be able to inherit
        // this total span in O(1) amortized time without re-examining the popped elements.
        stack.push(new int[]{price, span});

        return span;
    }
}
