import java.util.ArrayDeque;
import java.util.Queue;

/**
 * LeetCode 346: Moving Average from Data Stream
 *
 * Problem Description:
 * Given a stream of integers and a window size, calculate the moving average
 * of all integers in the sliding window.
 *
 * Approach:
 * - We maintain a sliding window of elements using a First-In-First-Out (FIFO) Queue.
 * - To achieve O(1) time complexity for each call to next(), we maintain a running sum
 *   of the elements currently present in the window.
 * - When a new element arrives:
 *   1. If the queue has reached the maximum window capacity, remove (dequeue) the oldest
 *      element from the queue and subtract its value from the running sum.
 *   2. Add (enqueue) the new element to the queue and add its value to the running sum.
 *   3. The moving average is the running sum divided by the current number of elements
 *      in the queue.
 *
 * Complexity Analysis:
 * - Time Complexity:
 *   - Constructor MovingAverage(size): O(1)
 *   - next(val): O(1) since queue operations (offer and poll) and arithmetic operations
 *     are performed in constant time.
 * - Space Complexity:
 *   - O(N), where N is the maximum size of the sliding window, to store elements in the queue.
 */
public class MovingAverage {

    // Queue to hold elements within the current sliding window
    private final Queue<Integer> window;

    // Maximum capacity (window size)
    private final int capacity;

    // Running sum of the elements currently in the sliding window
    private double windowSum;

    /**
     * Initializes the MovingAverage data structure with a specified window size.
     *
     * @param size The maximum number of elements in the sliding window.
     */
    public MovingAverage(int size) {
        this.capacity = size;
        this.window = new ArrayDeque<>(size);
        this.windowSum = 0.0;
    }

    /**
     * Consumes the next integer from the data stream and calculates the moving average
     * of the elements in the current sliding window.
     *
     * @param val The incoming value from the data stream.
     * @return The moving average of the numbers in the current sliding window.
     */
    public double next(int val) {
        // If the window has reached its maximum size, evict the oldest element
        if (window.size() == capacity) {
            windowSum -= window.poll();
        }

        // Add the new element to the window and update the running sum
        window.offer(val);
        windowSum += val;

        // Calculate and return the average across the current number of elements
        return windowSum / window.size();
    }
}