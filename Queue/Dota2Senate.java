import java.util.ArrayDeque;
import java.util.Queue;

/**
 * LeetCode 649 - Dota2 Senate
 *
 * Problem Summary:
 * In the world of Dota2, there are two parties: Radiant ('R') and Dire ('D').
 * The Senate consists of senators from these two parties who vote in rounds.
 * In each round, each active senator can:
 *   1. Ban one opposing senator's right: Cause another senator to lose all voting
 *      rights in this and all subsequent rounds.
 *   2. Announce victory: If all remaining senators eligible to vote belong to the
 *      same party, that party announces victory.
 *
 * Given a string 'senate' representing the initial voting order of senators,
 * predict which party will win assuming all senators use an optimal greedy strategy.
 *
 * Algorithm / Approach: Two Queues with Index Tracking
 * - We use two FIFO queues:
 *     - 'radiant': holds the active indices of Radiant senators.
 *     - 'dire': holds the active indices of Dire senators.
 * - Initial state: Populate 'radiant' and 'dire' with the 0-based indices from the string.
 * - Greedy Choice: A senator should always ban the NEXT opposing senator whose turn
 *   is upcoming (either later in the current round or earliest in the next round).
 *   This prevents that opponent from banning an ally.
 * - Round Simulation:
 *   - While both queues are non-empty:
 *     - Poll rIndex from 'radiant' and dIndex from 'dire'.
 *     - Compare rIndex and dIndex:
 *         * If rIndex < dIndex: The Radiant senator votes first. They ban the Dire
 *           senator (dIndex is dropped). The Radiant senator is eligible to vote again
 *           in the next round, so they are re-enqueued with index (rIndex + n).
 *         * If dIndex < rIndex: The Dire senator votes first. They ban the Radiant
 *           senator (rIndex is dropped). The Dire senator is eligible to vote again
 *           in the next round, so they are re-enqueued with index (dIndex + n).
 * - Termination: When one queue is empty, the party with remaining senators wins.
 *
 * Complexity:
 * - Time Complexity: O(n), where n = senate.length(). Each comparison eliminates
 *   one senator permanently, so there are at most n comparisons.
 * - Space Complexity: O(n), storing at most n indices in the two queues.
 */
public class Dota2Senate {

    /**
     * Predicts which party will win the Dota2 Senate vote.
     *
     * @param senate String representing the party affiliation of each senator in order.
     * @return "Radiant" if Radiant wins, or "Dire" if Dire wins.
     */
    public String predictPartyVictory(String senate) {
        int n = senate.length();

        // Queues to store the current voting indices of senators from each party.
        Queue<Integer> radiant = new ArrayDeque<>();
        Queue<Integer> dire = new ArrayDeque<>();

        // Initialize queues with the 0-based indices of senators in the first round.
        for (int i = 0; i < n; i++) {
            if (senate.charAt(i) == 'R') {
                radiant.offer(i);
            } else {
                dire.offer(i);
            }
        }

        // Simulate voting rounds until one party has eliminated all opposing senators.
        while (!radiant.isEmpty() && !dire.isEmpty()) {
            int rIndex = radiant.poll();
            int dIndex = dire.poll();

            // The senator with the smaller index acts first.
            // That senator bans the opposing senator and qualifies for the next round.
            // Adding 'n' to the index places the senator at the end of the next round
            // while preserving their relative order with respect to other survivors.
            if (rIndex < dIndex) {
                radiant.offer(rIndex + n);
            } else {
                dire.offer(dIndex + n);
            }
        }

        // The party with senators remaining in its queue wins.
        return radiant.isEmpty() ? "Dire" : "Radiant";
    }

    /**
     * Main method to test and demonstrate the solution with standard test cases.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Dota2Senate solution = new Dota2Senate();

        // Test 1: "RD" -> Radiant bans Dire -> "Radiant"
        System.out.println("Input: \"RD\"    -> Output: " + solution.predictPartyVictory("RD"));

        // Test 2: "RDD" -> Radiant bans Dire, second Dire bans Radiant -> "Dire"
        System.out.println("Input: \"RDD\"   -> Output: " + solution.predictPartyVictory("RDD"));

        // Test 3: "DDRRR" -> "Dire"
        System.out.println("Input: \"DDRRR\" -> Output: " + solution.predictPartyVictory("DDRRR"));
    }
}
