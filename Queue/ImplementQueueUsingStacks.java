import java.util.Stack;

/**
 * LeetCode 232 - Implement Queue using Stacks
 * File: ImplementQueueUsingStacks.java
 *
 * Problem Description:
 * Implement a first in first out (FIFO) queue using only two stacks.
 * The implemented queue should support all the functions of a normal queue
 * (push, peek, pop, and empty).
 *
 * Implement the MyQueue class:
 * - void push(int x)   : Pushes element x to the back of the queue.
 * - int pop()          : Removes the element from the front of the queue and returns it.
 * - int peek()         : Returns the element at the front of the queue.
 * - boolean empty()    : Returns true if the queue is empty, false otherwise.
 *
 * Constraints & Rules:
 * - You must use only standard operations of a stack:
 *     push to top, peek/pop from top, size, and is empty operations.
 * - 1 <= x <= 9
 * - At most 100 calls will be made to push, pop, peek, and empty.
 * - All calls to pop and peek are valid (queue is not empty when called).
 *
 * Approach: Two Stacks with Lazy Transfer (Amortized O(1))
 * -------------------------------------------------------
 * We maintain two separate stacks:
 * 1. inStack  (Input Stack) : Dedicated exclusively to receiving newly pushed elements.
 * 2. outStack (Output Stack): Dedicated exclusively to serving pop() and peek() requests.
 *
 * Why "Lazy Transfer"?
 * - Instead of moving elements back and forth on every push or pop (which would take O(n) each time),
 *   we only transfer elements from inStack to outStack when outStack becomes completely EMPTY.
 * - Because stacks are LIFO (Last-In-First-Out), pouring all elements from inStack into outStack
 *   reverses their order. The element pushed first into inStack ends up at the top of outStack,
 *   aligning perfectly with FIFO (First-In-First-Out) queue semantics.
 * - As long as outStack still contains elements, subsequent pop() and peek() calls simply access
 *   the top of outStack in O(1) time without triggering any transfers.
 *
 * Amortized Time Complexity Analysis:
 * -----------------------------------
 * - Each element is pushed onto inStack exactly once:   1 push  = O(1)
 * - Each element is popped from inStack exactly once:   1 pop   = O(1)
 * - Each element is pushed onto outStack exactly once:  1 push  = O(1)
 * - Each element is popped from outStack exactly once:  1 pop   = O(1)
 *
 * Total operations per element over its entire lifetime in the queue = 4 operations = O(1).
 * Thus, across any sequence of N operations, the total time spent is O(N), yielding an
 * AMORTIZED time complexity of O(1) per operation for push, pop, and peek.
 *
 * Complexity Summary:
 * - push(int x) : Time Complexity: O(1)
 * - pop()       : Time Complexity: Amortized O(1), Worst-Case O(n)
 * - peek()      : Time Complexity: Amortized O(1), Worst-Case O(n)
 * - empty()     : Time Complexity: O(1)
 * - Space       : Overall Space Complexity: O(n) to store n elements.
 *                 Auxiliary Space per operation: O(1).
 */
class MyQueue {

    /**
     * Input stack to buffer incoming elements pushed to the queue.
     */
    private final Stack<Integer> inStack;

    /**
     * Output stack to hold elements in FIFO order for pop and peek operations.
     */
    private final Stack<Integer> outStack;

    /**
     * Initializes the MyQueue data structure.
     * Instantiates two empty stacks: inStack and outStack.
     */
    public MyQueue() {
        this.inStack = new Stack<>();
        this.outStack = new Stack<>();
    }

    /**
     * Pushes element x to the back of the queue.
     *
     * In the lazy transfer approach, new elements are always pushed directly
     * onto the inStack without modifying outStack.
     *
     * @param x The integer element to be pushed into the queue.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1) auxiliary
     */
    public void push(int x) {
        inStack.push(x);
    }

    /**
     * Removes the element from the front of the queue and returns it.
     *
     * If outStack is empty, all elements currently buffered in inStack are
     * lazily transferred to outStack. The top of outStack is then popped.
     *
     * @return The element that was removed from the front of the queue.
     *
     * Time Complexity: Amortized O(1), Worst-Case O(n) when transfer occurs.
     * Space Complexity: O(1) auxiliary
     */
    public int pop() {
        shiftStacks();
        return outStack.pop();
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * If outStack is empty, all elements currently buffered in inStack are
     * lazily transferred to outStack. The top of outStack is then inspected.
     *
     * @return The element at the front of the queue.
     *
     * Time Complexity: Amortized O(1), Worst-Case O(n) when transfer occurs.
     * Space Complexity: O(1) auxiliary
     */
    public int peek() {
        shiftStacks();
        return outStack.peek();
    }

    /**
     * Returns true if the queue is empty, false otherwise.
     *
     * The queue is empty if and only if both inStack and outStack contain no elements.
     *
     * @return true if the queue contains no elements; false otherwise.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1) auxiliary
     */
    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    /**
     * Helper method to lazily transfer elements from inStack to outStack.
     *
     * Invariant & Order Preservation:
     * - Transfer occurs ONLY when outStack is completely empty.
     * - If outStack already contains elements, transferring from inStack would
     *   place newer elements on top of older elements, violating FIFO order.
     * - When outStack is empty, popping from inStack and pushing onto outStack
     *   reverses the element order, placing the oldest element at the top of outStack.
     *
     * Time Complexity: O(k) where k is the number of elements in inStack (at most n).
     * Space Complexity: O(1) auxiliary
     */
    private void shiftStacks() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }
}
