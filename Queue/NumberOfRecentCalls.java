import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 933 - Number of Recent Calls
 *
 * Problem Description:
 * You have a RecentCounter class which counts the number of recent requests
 * within a certain time frame.
 *
 * Implement the RecentCounter class:
 * - RecentCounter() Initializes the counter with zero recent requests.
 * - int ping(int t) Adds a new request at time t, where t represents some time
 *   in milliseconds, and returns the number of requests that have happened in
 *   the past 3000 milliseconds (including the new request). Specifically,
 *   return the number of requests that have happened in the inclusive range
 *   [t - 3000, t].
 *
 * It is guaranteed that every call to ping uses a strictly larger value of t
 * than the previous call.
 *
 * Constraints:
 * - 1 <= t <= 10^9
 * - Each test case will call ping with strictly increasing values of t.
 * - At most 10^4 calls will be made to ping.
 */
class RecentCounter {

    // Queue to store the timestamps of incoming requests in chronological order.
    private final Queue<Integer> queue;

    /**
     * Initializes the RecentCounter object with an empty queue of requests.
     */
    public RecentCounter() {
        // LinkedList implements the Queue interface in Java.
        this.queue = new LinkedList<>();
    }

    /**
     * Adds a new request at timestamp t and returns the number of requests
     * that occurred in the inclusive time window [t - 3000, t].
     *
     * Approach:
     * - We maintain a sliding window of request timestamps using a First-In-First-Out (FIFO) Queue.
     * - Because ping timestamps are guaranteed to be strictly increasing, the timestamps in our
     *   queue are always sorted in ascending order.
     * - When a new timestamp 't' arrives:
     *   1. Add 't' to the end of the queue.
     *   2. Remove all outdated timestamps from the front of the queue that are strictly less
     *      than (t - 3000), as they fall outside the current time window [t - 3000, t] and will
     *      never be part of any future window.
     *   3. The number of elements remaining in the queue is the exact count of requests in [t - 3000, t].
     *
     * Complexity Analysis:
     * - Time Complexity:
     *   - Amortized O(1) per ping call. Each timestamp is added to the queue exactly once
     *     via offer() and removed from the queue at most once via poll(). Over N calls to ping,
     *     at most N insertions and N deletions take place, giving O(N) total time across N calls.
     *   - Worst-case time for a single call is O(N) when many expired elements are removed at once.
     * - Space Complexity:
     *   - O(W), where W is the maximum number of requests within any 3000ms window.
     *   - In the worst case, at most 3001 requests can be stored simultaneously
     *     (since timestamps are distinct integers in milliseconds).
     *
     * @param t The current request timestamp in milliseconds (strictly increasing).
     * @return The number of requests in the time window [t - 3000, t].
     */
    public int ping(int t) {
        // Step 1: Add the current request timestamp to the back of the queue.
        queue.offer(t);

        // Step 2: Remove timestamps that are older than (t - 3000).
        // Since timestamps are strictly increasing, elements at the front of the queue
        // are the oldest. We evict any timestamp that falls outside [t - 3000, t].
        while (!queue.isEmpty() && queue.peek() < t - 3000) {
            queue.poll();
        }

        // Step 3: Return the count of valid timestamps currently in the sliding window.
        return queue.size();
    }
}