import java.util.Stack;

/**
 * LeetCode 155 - Min Stack
 *
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * Implement the MinStack class:
 * - MinStack() initializes the stack object.
 * - void push(int val) pushes the element val onto the stack.
 * - void pop() removes the element on the top of the stack.
 * - int top() gets the top element of the stack.
 * - int getMin() retrieves the minimum element in the stack.
 *
 * Constraints & Complexity:
 * - All operations (push, pop, top, getMin) must run in O(1) time complexity.
 * - Space complexity: O(n) to store the elements and historical minimums.
 * - Methods pop, top, and getMin are guaranteed to be called on non-empty stacks per problem constraints.
 *
 * Approach: Two Stacks
 * --------------------
 * We maintain two parallel stacks:
 * 1. mainStack: Stores all pushed elements to preserve standard LIFO order.
 * 2. minStack:  Stores the minimum element present in mainStack up to each corresponding level.
 *
 * Operations:
 * - push(val):
 *     Push `val` onto mainStack.
 *     For minStack, if it is empty, push `val`.
 *     Otherwise, push Math.min(val, minStack.peek()).
 * - pop():
 *     Pop the top element from both mainStack and minStack simultaneously.
 * - top():
 *     Return the top element of mainStack.
 * - getMin():
 *     Return the top element of minStack, which represents the minimum of all current elements.
 */
public class MinStack {

    // Main stack to store all values
    private Stack<Integer> mainStack;

    // Auxiliary stack to store the minimum value at each state of the stack
    private Stack<Integer> minStack;

    /**
     * Initializes the MinStack object with two empty stacks.
     */
    public MinStack() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }

    /**
     * Pushes the element val onto the stack.
     * Both stacks are updated to maintain matching heights.
     *
     * @param val the integer value to push
     */
    public void push(int val) {
        mainStack.push(val);
        // If minStack is empty, the first pushed element is the minimum.
        // Otherwise, compare with the current minimum and push the smaller one.
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            minStack.push(Math.min(val, minStack.peek()));
        }
    }

    /**
     * Removes the element on the top of the stack.
     * Pops from both stacks to keep them synchronized.
     */
    public void pop() {
        mainStack.pop();
        minStack.pop();
    }

    /**
     * Gets the top element of the stack.
     *
     * @return the value at the top of the main stack
     */
    public int top() {
        return mainStack.peek();
    }

    /**
     * Retrieves the minimum element in the stack in O(1) time.
     *
     * @return the value at the top of the min stack
     */
    public int getMin() {
        return minStack.peek();
    }
}
