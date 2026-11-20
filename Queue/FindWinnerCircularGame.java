import java.util.ArrayDeque;
import java.util.Queue;

/**
 * LeetCode 1823: Find the Winner of the Circular Game
 * 
 * Problem Statement:
 * There are n friends sitting in a circle numbered from 1 to n in clockwise order.
 * Starting from the 1st friend, count the next k friends in clockwise direction
 * (including the friend you started at). The k-th friend leaves the circle.
 * The process repeats with the remaining friends starting from the friend immediately
 * clockwise of the friend who just left, until only one friend remains.
 * Return the winner of the game.
 * 
 * File Name: FindWinnerCircularGame.java
 */
public class FindWinnerCircularGame {

    /**
     * Approach 1: Queue Simulation Approach (Primary Solution)
     * 
     * Simulation Mechanism:
     * - A Queue (FIFO) is used to simulate the circular arrangement of friends.
     * - We enqueue all numbers from 1 to n.
     * - In each elimination round:
     *     1. The first (k - 1) friends are popped from the front and pushed to the back.
     *        This simulates moving clockwise around the circle.
     *     2. The k-th friend is at the front of the queue and is eliminated (polled).
     * - The process repeats until only 1 friend remains in the queue.
     * - The remaining element is the winner.
     * 
     * Complexity Analysis:
     * - Time Complexity: O(n * k)
     *   There are (n - 1) eliminations. In each round, (k - 1) elements are rotated
     *   and 1 element is removed. Total operations ~ (n - 1) * k.
     * - Space Complexity: O(n)
     *   The queue stores n elements initially.
     * 
     * @param n Number of friends (1-indexed from 1 to n)
     * @param k Step count for elimination
     * @return The 1-indexed number of the winning friend
     */
    public int findTheWinner(int n, int k) {
        // Edge cases
        if (n <= 0 || k <= 0) {
            throw new IllegalArgumentException("n and k must be positive integers.");
        }
        if (n == 1) {
            return 1;
        }

        // Initialize the queue with friends numbered 1 to n
        Queue<Integer> queue = new ArrayDeque<>(n);
        for (int i = 1; i <= n; i++) {
            queue.offer(i);
        }

        // Simulate the elimination process until only one winner is left
        while (queue.size() > 1) {
            // Rotate (k - 1) friends from the front to the back of the queue
            for (int i = 0; i < k - 1; i++) {
                queue.offer(queue.poll());
            }
            // The k-th friend is now at the head; eliminate them from the circle
            queue.poll();
        }

        // The last remaining friend is the winner
        return queue.peek();
    }

    /**
     * Approach 2: Mathematical Josephus Problem Approach (Alternative / Optimal)
     * 
     * Mathematical Background:
     * This problem is the classic Josephus Problem.
     * In a 0-indexed system with n people and step size k:
     * - Base Case:
     *     f(1, k) = 0  (With 1 person, the survivor is at index 0)
     * - Recurrence Relation:
     *     f(i, k) = (f(i - 1, k) + k) % i  for i = 2, 3, ..., n
     * 
     * Intuition:
     * After eliminating the first person at index (k - 1) % n, the next person starts
     * a subproblem of size (n - 1) with an index shifted by k. Thus, the winning index
     * in the subproblem maps back to the current circle by adding k and taking modulo i.
     * 
     * Finally, convert the 0-indexed winner back to 1-indexed by adding 1.
     * 
     * Complexity Analysis:
     * - Time Complexity: O(n)
     *   Single loop running from 2 to n.
     * - Space Complexity: O(1)
     *   Uses only a few scalar variables.
     * 
     * @param n Number of friends
     * @param k Step count
     * @return The 1-indexed number of the winning friend
     */
    public int findTheWinnerJosephus(int n, int k) {
        if (n <= 0 || k <= 0) {
            throw new IllegalArgumentException("n and k must be positive integers.");
        }

        // 0-indexed survivor position for base case of 1 person
        int winner = 0;

        // Iteratively compute survivor index for group sizes from 2 up to n
        for (int i = 2; i <= n; i++) {
            winner = (winner + k) % i;
        }

        // Convert 0-indexed position back to 1-indexed
        return winner + 1;
    }

    /**
     * Main method demonstrating and testing both approaches.
     */
    public static void main(String[] args) {
        FindWinnerCircularGame game = new FindWinnerCircularGame();

        // Test Case 1: n = 5, k = 2
        // Circle: [1, 2, 3, 4, 5]
        // Eliminations: 2 -> 4 -> 1 -> 5. Survivor: 3
        int n1 = 5, k1 = 2;
        System.out.println("Test Case 1 (n = 5, k = 2):");
        System.out.println("Queue Simulation Result: " + game.findTheWinner(n1, k1));
        System.out.println("Josephus Math Result:    " + game.findTheWinnerJosephus(n1, k1));
        System.out.println("Expected: 3\n");

        // Test Case 2: n = 6, k = 5
        // Circle: [1, 2, 3, 4, 5, 6]
        // Survivor: 1
        int n2 = 6, k2 = 5;
        System.out.println("Test Case 2 (n = 6, k = 5):");
        System.out.println("Queue Simulation Result: " + game.findTheWinner(n2, k2));
        System.out.println("Josephus Math Result:    " + game.findTheWinnerJosephus(n2, k2));
        System.out.println("Expected: 1\n");

        // Test Case 3: Edge Case n = 1, k = 1
        int n3 = 1, k3 = 1;
        System.out.println("Test Case 3 (n = 1, k = 1):");
        System.out.println("Queue Simulation Result: " + game.findTheWinner(n3, k3));
        System.out.println("Josephus Math Result:    " + game.findTheWinnerJosephus(n3, k3));
        System.out.println("Expected: 1");
    }
}
