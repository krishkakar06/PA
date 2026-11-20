import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 950: Reveal Cards In Increasing Order
 *
 * Problem Description:
 * You are given an integer array deck where every card has a unique integer.
 * Initially, all cards start face down in one deck.
 *
 * You repeat the following steps until all cards are revealed:
 * 1. Take the top card of the deck, reveal it, and take it out of the deck.
 * 2. If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
 * 3. If there are still unrevealed cards, go back to step 1. Otherwise, stop.
 *
 * Return an ordering of the deck that would reveal the cards in increasing order.
 * The first entry in the answer is considered to be the top of the deck.
 *
 * Approach - Simulation with Deque (Index-based queue simulation):
 * 1. We want the cards to be revealed in strictly ascending order. Therefore, the first card
 *    revealed must be the minimum element of the deck, the second card revealed must be the second
 *    smallest, and so on.
 * 2. Sorting the given deck in ascending order gives us the exact sequence of card values
 *    as they should be revealed.
 * 3. The cards' final positions in the initial deck configuration dictate when they will be revealed.
 *    To determine which position in the original deck gets revealed at which step, we can simulate
 *    the reveal process on the indices [0, 1, 2, ..., n - 1] using a double-ended queue (Deque).
 * 4. We initialize a Deque with indices 0 through n - 1.
 * 5. For each card in the sorted deck:
 *    a. The index at the front of the deque is the position where the current smallest card must be placed.
 *       We poll this index and assign result[index] = card.
 *    b. If there are still unrevealed card positions remaining in the deque, we simulate moving
 *       the next top card to the bottom by polling the front index and appending it to the rear.
 * 6. Once all sorted cards are assigned to their simulated positions, the result array represents
 *    the required initial deck ordering.
 *
 * Complexity:
 * - Time Complexity: O(N log N), where N is the number of cards in the deck.
 *   Sorting takes O(N log N) time. The deque simulation takes O(N) operations because each index is
 *   polled and enqueued at most once.
 * - Space Complexity: O(N), for the Deque storing N indices and the output result array.
 */
public class RevealCardsInIncreasingOrder {

    /**
     * Orders the deck so that revealing cards according to the rule produces an increasing sequence.
     *
     * @param deck an array of unique card values
     * @return the initial deck arrangement that reveals cards in increasing order
     */
    public int[] deckRevealedIncreasing(int[] deck) {
        if (deck == null || deck.length == 0) {
            return new int[0];
        }

        int n = deck.length;

        // Step 1: Sort the deck in ascending order to know the desired reveal sequence.
        Arrays.sort(deck);

        // Step 2: Use a Deque to simulate the reveal order of original deck indices.
        Deque<Integer> indexDeque = new ArrayDeque<>(n);
        for (int i = 0; i < n; i++) {
            indexDeque.addLast(i);
        }

        int[] result = new int[n];

        // Step 3: Simulate the card reveal process and fill result array with sorted values.
        for (int card : deck) {
            // Reveal the top card: assign the current smallest value to the polled index.
            int revealedIndex = indexDeque.pollFirst();
            result[revealedIndex] = card;

            // If cards remain, move the next card from the top to the bottom of the deck.
            if (!indexDeque.isEmpty()) {
                indexDeque.addLast(indexDeque.pollFirst());
            }
        }

        return result;
    }

    /**
     * Demonstration and basic test cases for verification.
     */
    public static void main(String[] args) {
        RevealCardsInIncreasingOrder solution = new RevealCardsInIncreasingOrder();

        // Test Case 1: Standard example from LeetCode
        int[] deck1 = {17, 13, 11, 2, 3, 5, 7};
        int[] result1 = solution.deckRevealedIncreasing(deck1);
        System.out.println("Input:  [17, 13, 11, 2, 3, 5, 7]");
        System.out.println("Output: " + Arrays.toString(result1));
        // Expected: [2, 13, 3, 11, 5, 17, 7]

        // Test Case 2: Two cards
        int[] deck2 = {1, 1000};
        int[] result2 = solution.deckRevealedIncreasing(deck2);
        System.out.println("Input:  [1, 1000]");
        System.out.println("Output: " + Arrays.toString(result2));
        // Expected: [1, 1000]

        // Test Case 3: Single card
        int[] deck3 = {42};
        int[] result3 = solution.deckRevealedIncreasing(deck3);
        System.out.println("Input:  [42]");
        System.out.println("Output: " + Arrays.toString(result3));
        // Expected: [42]
    }
}