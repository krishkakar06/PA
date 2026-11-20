/**
 * Problem: LeetCode 621 - Task Scheduler
 *
 * Problem Description:
 * You are given an array of CPU tasks, each represented by letters A to Z, and a cooldown period n.
 * Each CPU interval can either complete one task or remain idle.
 * Identical tasks must be separated by at least n intervals due to cooling constraints.
 * Return the minimum number of CPU intervals required to complete all tasks.
 *
 * Approach: Frequency Counting & Greedy Math
 * ----------------------------------------------------
 * 1. Count the frequencies of each task (using a frequency array of size 26).
 * 2. Identify the maximum frequency of any task, denoted as `maxFreq`.
 * 3. Count how many tasks share this maximum frequency, denoted as `countMaxFreq`.
 * 4. We can visualize the schedule structured by the most frequent task(s):
 *    - There will be `(maxFreq - 1)` blocks/frames of length `(n + 1)`.
 *    - Each block starts with the most frequent task, followed by `n` slots (either other tasks or idle intervals).
 *    - The final block contains only the tasks that appear `maxFreq` times.
 *    - Thus, the minimum theoretical intervals dictated by the most frequent tasks is:
 *      (maxFreq - 1) * (n + 1) + countMaxFreq
 * 5. If there are more tasks than the slots created in this structure, we can simply distribute the extra
 *    tasks into the existing blocks by expanding the frame size without causing cooldown violations.
 *    In that scenario, no idle time is required, and the total intervals will just be the total number of tasks.
 * 6. Therefore, the result is:
 *      Math.max(tasks.length, (maxFreq - 1) * (n + 1) + countMaxFreq)
 *
 * Complexity:
 * - Time Complexity: O(N), where N is the number of tasks in the input array.
 *   Iterating through `tasks` takes O(N), and scanning the 26-element frequency array takes O(1).
 * - Space Complexity: O(1) auxiliary space, as the frequency array size is fixed at 26 (A-Z).
 */
public class TaskScheduler {

    /**
     * Computes the least number of intervals to complete all tasks with cooldown period n.
     *
     * @param tasks Array of characters representing CPU tasks.
     * @param n     The non-negative cooldown period required between identical tasks.
     * @return Minimum units of time required to complete all tasks.
     */
    public int leastInterval(char[] tasks, int n) {
        // Base case: if tasks is null or empty, no intervals are required.
        if (tasks == null || tasks.length == 0) {
            return 0;
        }

        // Frequency array for uppercase English letters 'A' through 'Z'.
        int[] freq = new int[26];
        int maxFreq = 0;

        // Count frequency of each task and track the maximum frequency observed.
        for (char task : tasks) {
            freq[task - 'A']++;
            if (freq[task - 'A'] > maxFreq) {
                maxFreq = freq[task - 'A'];
            }
        }

        // Count how many tasks have the maximum frequency.
        int countMaxFreq = 0;
        for (int count : freq) {
            if (count == maxFreq) {
                countMaxFreq++;
            }
        }

        // Calculate minimum intervals required based on the bottleneck tasks:
        // - There are (maxFreq - 1) full chunks of length (n + 1).
        // - The last chunk has countMaxFreq tasks.
        int calculatedIntervals = (maxFreq - 1) * (n + 1) + countMaxFreq;

        // If the number of tasks exceeds the calculated intervals, no idle slots
        // are needed because the remaining tasks can be distributed without conflict.
        return Math.max(tasks.length, calculatedIntervals);
    }

    /**
     * Demonstration and testing of the leastInterval method with standard test cases.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        // Example 1:
        // tasks = ['A','A','A','B','B','B'], n = 2
        // Expected output: 8 (e.g., A -> B -> idle -> A -> B -> idle -> A -> B)
        char[] tasks1 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n1 = 2;
        System.out.println("Example 1 Result: " + scheduler.leastInterval(tasks1, n1) + " (Expected: 8)");

        // Example 2:
        // tasks = ['A','A','A','B','B','B'], n = 0
        // Expected output: 6 (no cooldown, any arrangement of 6 tasks works)
        char[] tasks2 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n2 = 0;
        System.out.println("Example 2 Result: " + scheduler.leastInterval(tasks2, n2) + " (Expected: 6)");

        // Example 3:
        // tasks = ['A','A','A','A','A','A','B','C','D','E','F','G'], n = 2
        // Expected output: 16
        char[] tasks3 = {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int n3 = 2;
        System.out.println("Example 3 Result: " + scheduler.leastInterval(tasks3, n3) + " (Expected: 16)");
    }
}
