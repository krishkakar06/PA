import java.util.Stack;

/**
 * Solution for LeetCode 735 - Asteroid Collision.
 *
 * Problem Description:
 * We are given an array 'asteroids' of integers representing asteroids in a row.
 * For each asteroid, the absolute value represents its size, and the sign represents
 * its direction (positive = moving right, negative = moving left).
 * Each asteroid moves at the same speed.
 *
 * Find out the state of the asteroids after all collisions.
 * - When two asteroids collide, the smaller one explodes.
 * - If both are the same size, both explode.
 * - Two asteroids moving in the same direction will never collide.
 *
 * Collision Condition:
 * Asteroids only collide when an asteroid moving right (+) is to the left of an
 * asteroid moving left (-). In sequential processing, this happens when the top of
 * our stack is positive (moving right) and the incoming asteroid is negative (moving left).
 * All other pairings (right-right, left-left, left-right) move apart or in parallel
 * and will never collide.
 */
public class AsteroidCollision {

    /**
     * Simulates asteroid collisions using a Stack and returns the surviving asteroids.
     *
     * Algorithm:
     * 1. Initialize a Stack<Integer> to store surviving asteroids in order of appearance.
     * 2. Iterate through each asteroid 'ast' in the input array.
     * 3. Use a boolean flag 'alive' (initially true) to track if the current asteroid
     *    survives the encounters with previously stacked asteroids.
     * 4. While 'alive' is true, the stack is non-empty, the top asteroid is moving right (> 0),
     *    and the current asteroid is moving left (< 0):
     *    a. Compare the top asteroid's size (stack.peek()) with the current asteroid's size (|ast|).
     *    b. If stack.peek() < |ast|:
     *       The top asteroid is smaller and explodes. We pop it from the stack.
     *       The current asteroid remains alive and may collide with the next asteroid on the stack.
     *    c. If stack.peek() == |ast|:
     *       Both asteroids are equal in size and both explode.
     *       We pop the top asteroid and mark 'alive = false' for the current asteroid.
     *    d. If stack.peek() > |ast|:
     *       The top asteroid is larger. The current asteroid explodes ('alive = false').
     *       The top asteroid remains on the stack.
     * 5. If the current asteroid survived all possible collisions (alive == true), push it onto the stack.
     * 6. Once all asteroids are processed, construct the result array from the stack.
     *    Because popping elements from a stack yields reverse order, we fill the array
     *    from right to left (index stack.size() - 1 down to 0).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of asteroids. Each asteroid is pushed onto
     *   the stack at most once and popped at most once across the entire execution.
     * - Space Complexity: O(N) auxiliary space used by the Stack to store asteroids in the
     *   worst case (e.g., when no collisions occur).
     *
     * @param asteroids array of integers representing asteroid sizes and directions
     * @return array of integers representing the asteroids remaining after all collisions
     */
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for (int ast : asteroids) {
            boolean alive = true;

            // A collision occurs ONLY when the top of the stack is moving right (> 0)
            // and the incoming asteroid is moving left (< 0).
            while (alive && !stack.isEmpty() && stack.peek() > 0 && ast < 0) {
                int top = stack.peek();
                int currentSize = -ast; // Size of the incoming left-moving asteroid

                if (top < currentSize) {
                    // Top asteroid is smaller; it explodes.
                    // Pop it and continue checking against the next asteroid on the stack.
                    stack.pop();
                } else if (top == currentSize) {
                    // Both asteroids are the same size; both explode.
                    stack.pop();
                    alive = false;
                } else {
                    // Top asteroid is larger; incoming asteroid explodes.
                    alive = false;
                }
            }

            // If the incoming asteroid survived all collisions (or had none), add it to the stack.
            if (alive) {
                stack.push(ast);
            }
        }

        // Convert the stack of surviving asteroids into an array.
        // Elements are popped in LIFO order (last in, first out),
        // so we populate the array from back to front.
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }
}
