import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 225 - Implement Stack using Queues
 * File: ImplementStackUsingQueues.java
 *
 * Implementation of a Last-In-First-Out (LIFO) stack using a single
 * First-In-First-Out (FIFO) queue.
 *
 * Approach:
 * - We maintain all stack elements inside a single Queue instance.
 * - In a standard queue, elements are removed in FIFO order (the oldest element first).
 * - To simulate LIFO behavior with a single queue, we rotate the queue on every push operation.
 * - When a new element 'x' is pushed:
 *     1. Enqueue 'x' at the back of the queue.
 *     2. Let n be the number of elements in the queue before adding 'x'.
 *     3. Dequeue from the front and enqueue back to the rear n times (size - 1 times).
 *     4. This moves 'x' to the front of the queue, while preserving the relative LIFO
 *        order of all previously added elements behind it.
 * - As a result of this invariant, the "top" of the stack is always located at the
 *   front of the queue, allowing pop(), top(), and empty() to operate in O(1) time.
 *
 * Complexity Analysis:
 * - push(int x):
 *     Time Complexity: O(n), where n is the number of elements currently in the stack.
 *     Space Complexity: O(1) auxiliary space.
 * - pop():
 *     Time Complexity: O(1), as the element is retrieved directly from the front of the queue.
 *     Space Complexity: O(1) auxiliary space.
 * - top():
 *     Time Complexity: O(1), as we peek directly at the front of the queue.
 *     Space Complexity: O(1) auxiliary space.
 * - empty():
 *     Time Complexity: O(1), directly checks if the queue is empty.
 *     Space Complexity: O(1) auxiliary space.
 * - Overall Space Complexity: O(n) to store n elements in the queue.
 */
class MyStack {

    /**
     * Single queue used to store and manage the stack elements.
     */
    private final Queue<Integer> queue;

    /**
     * Initializes the stack data structure.
     */
    public MyStack() {
        this.queue = new LinkedList<>();
    }

    /**
     * Pushes element x onto the stack.
     *
     * To ensure LIFO order with a single queue:
     * 1. Add the new element x to the end of the queue.
     * 2. Rotate all preceding elements by removing them from the front
     *    and re-inserting them at the rear (size - 1 times).
     * 3. This places x at the front of the queue, ready to be popped or inspected.
     *
     * Example:
     *   Initial queue: [2, 1] (front is 2)
     *   Push 3:
     *     - Add 3 to back: [2, 1, 3]
     *     - Rotate 2:      [1, 3, 2]
     *     - Rotate 1:      [3, 2, 1]
     *   Queue is now [3, 2, 1] with 3 at the front.
     *
     * @param x The integer element to be pushed onto the stack.
     */
    public void push(int x) {
        // Step 1: Add the newly arrived element to the back of the queue
        queue.add(x);

        // Step 2: Rotate the preceding elements behind the new element
        int rotations = queue.size() - 1;
        for (int i = 0; i < rotations; i++) {
            queue.add(queue.remove());
        }
    }

    /**
     * Removes the element on the top of the stack and returns it.
     *
     * Since push() ensures that the top of the stack is always at
     * the front of the queue, we simply remove the front element.
     *
     * @return The element that was removed from the top of the stack.
     */
    public int pop() {
        return queue.remove();
    }

    /**
     * Returns the element on the top of the stack without removing it.
     *
     * Since the top of the stack is always at the front of the queue,
     * we inspect the front element.
     *
     * @return The element at the top of the stack.
     */
    public int top() {
        return queue.peek();
    }

    /**
     * Returns true if the stack is empty, false otherwise.
     *
     * @return true if the stack contains no elements; false otherwise.
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}