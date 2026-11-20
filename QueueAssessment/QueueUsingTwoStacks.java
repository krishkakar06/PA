import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * QueueUsingTwoStacks
 *
 * An implementation of a FIFO (First-In, First-Out) Queue data structure
 * using two LIFO (Last-In, First-Out) java.util.Stack objects.
 *
 * -------------------------------------------------------------------------
 * CORE CONCEPT & MECHANISM:
 * -------------------------------------------------------------------------
 * A stack is a LIFO structure. When elements are pushed onto a stack and then
 * popped off, their order is reversed.
 * If we pop elements from the first stack and push them onto a second stack,
 * their order is reversed a second time.
 * Because reversing a sequence twice restores its original sequence, the second
 * stack will yield elements in true FIFO (Queue) order!
 *
 * We maintain two stacks:
 *  1. inStack  (Inbox):  Exclusively used for incoming elements during enqueue.
 *  2. outStack (Outbox): Exclusively used for outgoing elements during dequeue/peek.
 *
 * CRITICAL INVARIANT:
 * Elements are transferred from inStack to outStack ONLY WHEN outStack is empty.
 * If outStack already has elements, popping inStack onto outStack would place older
 * elements on top of even older elements or newer elements below older elements,
 * violating FIFO ordering.
 *
 * -------------------------------------------------------------------------
 * TIME AND SPACE COMPLEXITY ANALYSIS:
 * -------------------------------------------------------------------------
 * 1. enqueue(int val):
 *    - Time Complexity: O(1) worst-case.
 *    - Pushing an element onto inStack is a single stack operation.
 *
 * 2. dequeue():
 *    - Time Complexity: Amortized O(1), Worst-case O(N).
 *    - If outStack is not empty: O(1) time (simply pop outStack).
 *    - If outStack is empty: O(N) time, where N is the number of elements in inStack,
 *      because all N elements must be popped from inStack and pushed to outStack.
 *
 * 3. peek():
 *    - Time Complexity: Amortized O(1), Worst-case O(N).
 *    - Same transfer logic as dequeue, inspecting the top of outStack without removal.
 *
 * 4. isEmpty():
 *    - Time Complexity: O(1).
 *    - Evaluates whether both inStack and outStack are empty.
 *
 * 5. size():
 *    - Time Complexity: O(1).
 *    - Evaluates inStack.size() + outStack.size(), both of which are O(1) in Java.
 *
 * 6. Space Complexity:
 *    - O(N) overall auxiliary space, where N is the total number of elements
 *      currently present in the queue across both stacks.
 *
 * -------------------------------------------------------------------------
 * DETAILED EXPLANATION OF AMORTIZED O(1) COMPLEXITY:
 * -------------------------------------------------------------------------
 * While an individual dequeue() or peek() operation can take O(N) time in the
 * worst case (when outStack is empty and must be populated from inStack), the
 * amortized (average per operation over any arbitrary sequence) time is O(1).
 *
 * We can prove this using the Accounting Method (or Aggregate Analysis):
 * Consider the lifecycle of any single element inserted into the queue:
 *   Step 1: Pushed onto inStack    -> exactly 1 push operation (in enqueue).
 *   Step 2: Popped from inStack    -> exactly 1 pop operation  (in transfer).
 *   Step 3: Pushed onto outStack   -> exactly 1 push operation (in transfer).
 *   Step 4: Popped from outStack   -> exactly 1 pop operation  (in dequeue).
 *
 * Notice that every element experiences AT MOST 4 stack operations during its
 * entire lifetime in the queue:
 *   Total operations per element = 2 pushes + 2 pops = 4 operations = O(1).
 *
 * For any sequence of M operations (enqueues, dequeues, peeks), no element can
 * be transferred more than once. The total work performed across all operations
 * is bounded by 4 * (number of enqueued elements) <= 4 * M.
 *
 * Dividing the total time O(M) by the number of operations M gives:
 *   Amortized Time per Operation = O(M) / M = O(1).
 */
public class QueueUsingTwoStacks {

    // Stack dedicated to accepting newly enqueued elements.
    private final Stack<Integer> inStack;

    // Stack dedicated to serving peek and dequeue operations in FIFO order.
    private final Stack<Integer> outStack;

    /**
     * Initializes an empty queue using two stacks.
     */
    public QueueUsingTwoStacks() {
        this.inStack = new Stack<>();
        this.outStack = new Stack<>();
    }

    /**
     * Inserts an element to the back of the queue.
     *
     * Complexity:
     * - Time: O(1)
     * - Space: O(1) auxiliary
     *
     * @param val the integer value to be added.
     */
    public void enqueue(int val) {
        inStack.push(val);
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * Complexity:
     * - Time: Amortized O(1), Worst-case O(N)
     * - Space: O(1) auxiliary
     *
     * @return the element at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public int dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue: Queue is empty.");
        }
        shiftStacks();
        return outStack.pop();
    }

    /**
     * Retrieves, but does not remove, the element at the front of the queue.
     *
     * Complexity:
     * - Time: Amortized O(1), Worst-case O(N)
     * - Space: O(1) auxiliary
     *
     * @return the element at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek: Queue is empty.");
        }
        shiftStacks();
        return outStack.peek();
    }

    /**
     * Checks if the queue contains no elements.
     *
     * Complexity:
     * - Time: O(1)
     * - Space: O(1)
     *
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    /**
     * Returns the total number of elements currently stored in the queue.
     *
     * Complexity:
     * - Time: O(1)
     * - Space: O(1)
     *
     * @return the total number of elements.
     */
    public int size() {
        return inStack.size() + outStack.size();
    }

    /**
     * Helper method to shift elements from inStack to outStack.
     * Transfers elements ONLY if outStack is currently empty.
     * This preserves the correct FIFO ordering.
     */
    private void shiftStacks() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }

    /**
     * Demonstration driver showing queue operations, FIFO behavior,
     * interleaved operations, and edge-case exception handling.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  DEMONSTRATION: Queue Implemented Using Two Stacks");
        System.out.println("==================================================");

        QueueUsingTwoStacks queue = new QueueUsingTwoStacks();

        // 1. Initial State
        System.out.println("\n[1] Checking initial empty queue:");
        System.out.println("Is queue empty? " + queue.isEmpty()); // Expected: true
        System.out.println("Queue size: " + queue.size());         // Expected: 0

        // 2. Enqueue Elements
        System.out.println("\n[2] Enqueueing elements: 10, 20, 30");
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("Is queue empty? " + queue.isEmpty()); // Expected: false
        System.out.println("Queue size after 3 enqueues: " + queue.size()); // Expected: 3

        // 3. Peek Front Element
        System.out.println("\n[3] Peeking front element:");
        System.out.println("Front element (peek): " + queue.peek()); // Expected: 10
        System.out.println("Queue size after peek (should be unchanged): " + queue.size()); // Expected: 3

        // 4. Dequeue Elements (Verifying FIFO)
        System.out.println("\n[4] Dequeueing two elements (FIFO order):");
        System.out.println("Dequeued: " + queue.dequeue()); // Expected: 10
        System.out.println("Dequeued: " + queue.dequeue()); // Expected: 20
        System.out.println("Remaining size: " + queue.size()); // Expected: 1
        System.out.println("Current front (peek): " + queue.peek()); // Expected: 30

        // 5. Interleaved Operations (Testing inStack and outStack co-existence)
        System.out.println("\n[5] Interleaving enqueues while outStack still has elements:");
        System.out.println("Enqueueing: 40, 50");
        queue.enqueue(40);
        queue.enqueue(50);
        System.out.println("Queue size: " + queue.size()); // Expected: 3 (30 in outStack; 40, 50 in inStack)

        System.out.println("Dequeueing remaining elements:");
        while (!queue.isEmpty()) {
            System.out.println("Dequeued: " + queue.dequeue() + " (Remaining size: " + queue.size() + ")");
        }
        // Expected dequeue order: 30, then 40, then 50.

        // 6. Verify Queue is Empty
        System.out.println("\n[6] Queue state after draining:");
        System.out.println("Is queue empty? " + queue.isEmpty()); // Expected: true
        System.out.println("Queue size: " + queue.size());         // Expected: 0

        // 7. Edge Case: Dequeue on Empty Queue
        System.out.println("\n[7] Testing exception handling on empty queue dequeue:");
        try {
            queue.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println("Successfully caught expected exception: " + e.getMessage());
        }

        // 8. Edge Case: Peek on Empty Queue
        System.out.println("\n[8] Testing exception handling on empty queue peek:");
        try {
            queue.peek();
        } catch (NoSuchElementException e) {
            System.out.println("Successfully caught expected exception: " + e.getMessage());
        }

        System.out.println("\n==================================================");
        System.out.println("  ALL DEMONSTRATION TESTS COMPLETED SUCCESSFULLY");
        System.out.println("==================================================");
    }
}
