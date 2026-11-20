import java.util.Collections;
import java.util.PriorityQueue;

/**
 * LeetCode 1046: Last Stone Weight
 * 
 * Problem Statement:
 * You are given an array of integers 'stones' where stones[i] is the weight of the ith stone.
 * We are playing a game with the stones. On each turn, we choose the heaviest two stones
 * and smash them together. Suppose the heaviest two stones have weights x and y with x <= y.
 * The result of this smash is:
 * - If x == y, both stones are destroyed.
 * - If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
 * 
 * At the end of the game, there is at most one stone left.
 * Return the weight of the last remaining stone. If there are no stones left, return 0.
 * 
 * Approach (Max Heap / PriorityQueue):
 * 1. By default, Java's PriorityQueue is a min-heap. We can invert the ordering by passing
 *    Collections.reverseOrder() into the constructor to create a max-heap.
 * 2. Insert all the stones into the max-heap.
 * 3. While the heap contains more than 1 stone:
 *    - Extract the two largest stones: y (the heaviest) and x (the second heaviest).
 *    - If y != x, the resulting stone of weight (y - x) is inserted back into the max-heap.
 *    - If y == x, both stones are destroyed, so neither is re-inserted.
 * 4. Once the loop ends, either 1 stone remains or 0 stones remain.
 *    - If the heap is not empty, return the single remaining stone (heap.poll()).
 *    - If the heap is empty, return 0.
 * 
 * Complexity Analysis:
 * - Time Complexity: O(N log N), where N is the number of stones.
 *   - Inserting N elements into the PriorityQueue takes O(N log N) time.
 *   - In each smash turn, we remove 2 stones and insert at most 1 stone back.
 *     Thus, the number of stones decreases by at least 1 in each step, resulting in at most N - 1 turns.
 *   - Each heap operation (poll / offer) takes O(log K) where K <= N.
 *   - Total time: O(N log N).
 * - Space Complexity: O(N), to store up to N stones in the PriorityQueue.
 */
public class LastStoneWeight {

    /**
     * Computes the weight of the last remaining stone after simulating the smashing process.
     *
     * @param stones Array of positive integers representing stone weights.
     * @return The weight of the last stone, or 0 if no stones are left.
     */
    public int lastStoneWeight(int[] stones) {
        // Base / edge case: if array is null or empty, return 0
        if (stones == null || stones.length == 0) {
            return 0;
        }

        // Initialize a max-heap using PriorityQueue with reverse order comparator.
        // Initial capacity is set to stones.length to avoid dynamic resizing.
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(stones.length, Collections.reverseOrder());

        // Add all stone weights into the max-heap.
        for (int stone : stones) {
            maxHeap.offer(stone);
        }

        // Continue smashing as long as there are at least two stones available.
        while (maxHeap.size() > 1) {
            // Poll the two largest stones.
            // 'first' is the heaviest (y), 'second' is the second heaviest (x).
            int first = maxHeap.poll();
            int second = maxHeap.poll();

            // If the weights are different, the heavier stone is reduced by the weight
            // of the lighter stone and pushed back into the heap.
            // If they are equal, both are destroyed (no re-insertion).
            if (first != second) {
                maxHeap.offer(first - second);
            }
        }

        // Return the weight of the last remaining stone, or 0 if all stones were destroyed.
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }

    /**
     * Main method to test and demonstrate the LastStoneWeight solution.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        LastStoneWeight solver = new LastStoneWeight();

        // Test Case 1: Standard case with multiple collisions
        // Steps:
        // [2, 7, 4, 1, 8, 1] -> combine 7 and 8 -> 1, heap: [2, 4, 1, 1, 1]
        // combine 2 and 4 -> 2, heap: [2, 1, 1, 1]
        // combine 2 and 1 -> 1, heap: [1, 1, 1]
        // combine 1 and 1 -> 0, heap: [1]
        // result = 1
        int[] stones1 = {2, 7, 4, 1, 8, 1};
        System.out.println("Test 1 Result: " + solver.lastStoneWeight(stones1) + " (Expected: 1)");

        // Test Case 2: Single stone
        int[] stones2 = {1};
        System.out.println("Test 2 Result: " + solver.lastStoneWeight(stones2) + " (Expected: 1)");

        // Test Case 3: All stones collide and destroy each other
        int[] stones3 = {2, 2};
        System.out.println("Test 3 Result: " + solver.lastStoneWeight(stones3) + " (Expected: 0)");

        // Test Case 4: Larger weights
        int[] stones4 = {10, 4, 2, 10};
        // 10 and 10 collide -> destroyed; remaining: 4, 2 -> 4 - 2 = 2; result = 2
        System.out.println("Test 4 Result: " + solver.lastStoneWeight(stones4) + " (Expected: 2)");
    }
}
