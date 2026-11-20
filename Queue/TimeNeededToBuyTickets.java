/**
 * LeetCode 2073: Time Needed to Buy Tickets
 * 
 * Problem Summary:
 * There are n people in a line queueing to buy tickets, where the 0th person is at the front
 * and the (n - 1)th person is at the back. Each person takes 1 second to buy a single ticket.
 * After buying a ticket, if a person still needs more tickets, they move to the end of the line.
 * If they have bought all required tickets, they leave the line.
 * 
 * We need to calculate the total time taken for the person at index k to finish buying all their tickets.
 * 
 * Approach: One-pass Mathematical Approach (Optimal O(n) Time, O(1) Space)
 * -----------------------------------------------------------------------
 * Instead of simulating the queue step-by-step with a Queue data structure (which would take
 * O(n * max(tickets)) time), we can calculate the contribution of each person directly in a single pass:
 * 
 * Let target = tickets[k] be the number of tickets person k needs to buy.
 * The process stops the exact moment person k buys their target-th ticket.
 * 
 * 1. For any person i before or at index k (i <= k):
 *    - In each round where person k buys a ticket, person i has an opportunity to buy a ticket.
 *    - Person i can buy at most target tickets before person k finishes.
 *    - If person i needs fewer than target tickets (tickets[i] < target), they buy tickets[i] and leave.
 *    - Therefore, person i contributes: Math.min(tickets[i], target) seconds.
 * 
 * 2. For any person i after index k (i > k):
 *    - Person i stands behind person k.
 *    - In the final round, as soon as person k buys their last ticket, the process ends immediately.
 *    - Thus, person i does NOT get a turn in the final (target-th) round.
 *    - Person i gets at most (target - 1) turns.
 *    - Therefore, person i contributes: Math.min(tickets[i], target - 1) seconds.
 * 
 * Complexity:
 * - Time Complexity: O(n) where n is the number of people in the tickets array.
 *   We only iterate through the array once.
 * - Space Complexity: O(1) auxiliary space as we only use a few integer variables.
 */
public class TimeNeededToBuyTickets {

    /**
     * Calculates the total time needed for the person at position k to buy all their tickets.
     *
     * @param tickets An array where tickets[i] is the number of tickets person i wants to buy.
     * @param k The 0-based index of the target person.
     * @return Total time in seconds for person k to finish buying tickets.
     */
    public int timeRequiredToBuy(int[] tickets, int k) {
        int totalTime = 0;
        int targetTickets = tickets[k];

        // Traverse through each person in the line
        for (int i = 0; i < tickets.length; i++) {
            if (i <= k) {
                // People in front of k (and k themselves) get a turn in the final round.
                // They can buy at most targetTickets tickets.
                totalTime += Math.min(tickets[i], targetTickets);
            } else {
                // People behind k do not get a turn in the final round because
                // the simulation terminates immediately once person k buys their last ticket.
                // They can buy at most targetTickets - 1 tickets.
                totalTime += Math.min(tickets[i], targetTickets - 1);
            }
        }

        return totalTime;
    }

    /**
     * Optional Alternative: Queue Simulation Approach (O(n * max(tickets)) Time, O(n) Space)
     * Provided for reference and comparison with the mathematical approach.
     *
     * @param tickets An array where tickets[i] is the number of tickets person i wants to buy.
     * @param k The 0-based index of the target person.
     * @return Total time in seconds for person k to finish buying tickets using a Queue.
     */
    public int timeRequiredToBuyQueueSimulation(int[] tickets, int k) {
        // Queue stores the indices of people currently in line
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        for (int i = 0; i < tickets.length; i++) {
            queue.offer(i);
        }

        int time = 0;
        // Make a copy or modify tickets in-place
        int[] remaining = tickets.clone();

        while (!queue.isEmpty()) {
            int person = queue.poll();
            remaining[person]--;
            time++;

            // If the target person has bought all their tickets, we are done
            if (person == k && remaining[person] == 0) {
                return time;
            }

            // If the person still needs tickets, re-enqueue them at the end of the line
            if (remaining[person] > 0) {
                queue.offer(person);
            }
        }

        return time;
    }

    // Main method for testing and demonstration
    public static void main(String[] args) {
        TimeNeededToBuyTickets solver = new TimeNeededToBuyTickets();

        // Example 1:
        // Input: tickets = [2, 3, 2], k = 2
        // Explanation:
        // Round 1: Person 0 buys (needs 1), Person 1 buys (needs 2), Person 2 buys (needs 1) -> time = 3
        // Round 2: Person 0 buys (needs 0), Person 1 buys (needs 1), Person 2 buys (needs 0) -> time = 6
        // Output: 6
        int[] tickets1 = {2, 3, 2};
        int k1 = 2;
        System.out.println("Example 1 (Math):  " + solver.timeRequiredToBuy(tickets1, k1)); // Expected: 6
        System.out.println("Example 1 (Queue): " + solver.timeRequiredToBuyQueueSimulation(tickets1, k1)); // Expected: 6

        // Example 2:
        // Input: tickets = [5, 1, 1, 1], k = 0
        // Explanation:
        // Round 1: Person 0 buys 1 (needs 4), Person 1 buys 1 (done), Person 2 buys 1 (done), Person 3 buys 1 (done) -> 4 sec
        // Remaining rounds: Only Person 0 is left to buy 4 more tickets -> 4 sec
        // Total: 8 sec
        // Output: 8
        int[] tickets2 = {5, 1, 1, 1};
        int k2 = 0;
        System.out.println("Example 2 (Math):  " + solver.timeRequiredToBuy(tickets2, k2)); // Expected: 8
        System.out.println("Example 2 (Queue): " + solver.timeRequiredToBuyQueueSimulation(tickets2, k2)); // Expected: 8
    }
}
