import java.util.EmptyStackException;

/**
 * A generic implementation of a Last-In-First-Out (LIFO) Stack using a fixed-size array.
 *
 * <p>A stack is a linear data structure where elements are inserted and deleted from
 * the same end, commonly referred to as the "top" of the stack. This implementation
 * uses a generic array to store elements of any reference type.</p>
 *
 * <p><b>Time Complexities:</b></p>
 * <ul>
 *   <li>push(T item): O(1) - Constant time</li>
 *   <li>pop(): O(1) - Constant time</li>
 *   <li>peek(): O(1) - Constant time</li>
 *   <li>isEmpty(): O(1) - Constant time</li>
 *   <li>isFull(): O(1) - Constant time</li>
 *   <li>size(): O(1) - Constant time</li>
 * </ul>
 *
 * <p><b>Space Complexity:</b> O(N) where N is the maximum capacity of the stack.</p>
 *
 * @param <T> The type of elements held in this stack.
 */
public class StackUsingArray<T> {

    /** Default initial capacity if none is specified by the user. */
    private static final int DEFAULT_CAPACITY = 10;

    /** Internal array representing the storage for stack elements. */
    private final T[] elements;

    /** Maximum number of elements this stack can hold. */
    private final int capacity;

    /**
     * Index of the current top element in the stack.
     * Initialized to -1 to represent an empty stack.
     */
    private int top;

    /**
     * Constructs an empty stack with the specified maximum capacity.
     *
     * <p><b>Time Complexity:</b> O(1)</p>
     * <p><b>Space Complexity:</b> O(N) where N is the given capacity.</p>
     *
     * @param capacity The maximum number of elements the stack can accommodate.
     * @throws IllegalArgumentException If capacity is less than or equal to 0.
     */
    @SuppressWarnings("unchecked")
    public StackUsingArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0. Provided: " + capacity);
        }
        this.capacity = capacity;
        // Generic arrays cannot be created directly in Java due to type erasure (new T[capacity] is illegal).
        // The standard approach is to create an Object array and cast it to T[].
        this.elements = (T[]) new Object[capacity];
        this.top = -1; // -1 signifies that the stack is currently empty
    }

    /**
     * Constructs an empty stack with the default capacity of 10.
     *
     * <p><b>Time Complexity:</b> O(1)</p>
     * <p><b>Space Complexity:</b> O(1)</p>
     */
    public StackUsingArray() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Pushes an item onto the top of this stack.
     *
     * <p><b>Operation:</b> Checks if the stack has reached maximum capacity. If full,
     * an {@link IllegalStateException} is thrown to prevent Stack Overflow. Otherwise,
     * increments the top pointer and stores the new element at the updated top index.</p>
     *
     * <p><b>Time Complexity:</b> O(1) - Direct array indexing and assignment.</p>
     * <p><b>Space Complexity:</b> O(1) - In-place operation requiring no additional memory.</p>
     *
     * @param item The element to be pushed onto the stack.
     * @throws IllegalStateException If the stack is already full (Stack Overflow).
     */
    public void push(T item) {
        if (isFull()) {
            throw new IllegalStateException("Stack Overflow: Cannot push '" + item + "'. Stack has reached maximum capacity of " + capacity + ".");
        }
        elements[++top] = item;
    }

    /**
     * Removes and returns the element at the top of this stack.
     *
     * <p><b>Operation:</b> Checks if the stack is empty. If empty, throws an
     * {@link EmptyStackException} to signal Stack Underflow. Otherwise, captures the
     * top element, clears the array reference at that index to allow Garbage Collection
     * (preventing memory leaks), decrements the top index, and returns the removed element.</p>
     *
     * <p><b>Time Complexity:</b> O(1) - Direct array access and index decrement.</p>
     * <p><b>Space Complexity:</b> O(1) - Constant auxiliary space.</p>
     *
     * @return The element removed from the top of the stack.
     * @throws EmptyStackException If the stack is empty (Stack Underflow).
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = elements[top];
        elements[top] = null; // Dereference element to prevent memory leak / facilitate GC
        top--;
        return item;
    }

    /**
     * Looks at the element at the top of this stack without removing it.
     *
     * <p><b>Operation:</b> Checks if the stack is empty. If empty, throws an
     * {@link EmptyStackException} (Stack Underflow). Otherwise, returns the element
     * currently situated at index {@code top}.</p>
     *
     * <p><b>Time Complexity:</b> O(1) - Direct array access.</p>
     * <p><b>Space Complexity:</b> O(1) - Constant auxiliary space.</p>
     *
     * @return The element at the top of the stack.
     * @throws EmptyStackException If the stack is empty (Stack Underflow).
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements[top];
    }

    /**
     * Tests if this stack is empty.
     *
     * <p><b>Operation:</b> Checks whether the top index is equal to -1.</p>
     *
     * <p><b>Time Complexity:</b> O(1) - Single integer comparison.</p>
     * <p><b>Space Complexity:</b> O(1) - No extra memory.</p>
     *
     * @return {@code true} if the stack contains no elements, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Tests if this stack has reached its maximum allocated capacity.
     *
     * <p><b>Operation:</b> Compares the top index with {@code capacity - 1}.</p>
     *
     * <p><b>Time Complexity:</b> O(1) - Single integer comparison.</p>
     * <p><b>Space Complexity:</b> O(1) - No extra memory.</p>
     *
     * @return {@code true} if the stack cannot accommodate any more elements, {@code false} otherwise.
     */
    public boolean isFull() {
        return top == capacity - 1;
    }

    /**
     * Returns the current number of elements in this stack.
     *
     * <p><b>Operation:</b> Computes {@code top + 1}.</p>
     *
     * <p><b>Time Complexity:</b> O(1) - Simple arithmetic calculation.</p>
     * <p><b>Space Complexity:</b> O(1) - No extra memory.</p>
     *
     * @return The number of elements currently stored in the stack.
     */
    public int size() {
        return top + 1;
    }

    /**
     * Returns the maximum capacity of this stack.
     *
     * <p><b>Time Complexity:</b> O(1)</p>
     *
     * @return The maximum capacity of the stack.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns a string representation of the stack contents from bottom to top.
     *
     * <p><b>Time Complexity:</b> O(N) where N is the current size of the stack.</p>
     * <p><b>Space Complexity:</b> O(N) for constructing the string buffer.</p>
     *
     * @return A string representation formatted as {@code [bottom, ..., top (peek)]}.
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[] (Empty Stack)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i <= top; i++) {
            sb.append(elements[i]);
            if (i < top) {
                sb.append(", ");
            }
        }
        sb.append("] <- TOP");
        return sb.toString();
    }

    /**
     * Demonstration method exercising all Stack operations, including edge cases
     * such as Stack Overflow and Stack Underflow.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  STACK USING ARRAY DEMO (GENERIC IMPLEMENTATION)  ");
        System.out.println("==================================================\n");

        // 1. Create a Stack of Integers with capacity 5
        int demoCapacity = 5;
        System.out.println("Creating an Integer stack with capacity = " + demoCapacity + "...");
        StackUsingArray<Integer> intStack = new StackUsingArray<>(demoCapacity);

        // Verify initial state
        System.out.println("Initial state: " + intStack);
        System.out.println("isEmpty(): " + intStack.isEmpty()); // Expected: true
        System.out.println("isFull():  " + intStack.isFull());  // Expected: false
        System.out.println("size():    " + intStack.size());    // Expected: 0
        System.out.println();

        // 2. Push elements into the stack
        System.out.println("--- Pushing Elements ---");
        int[] valuesToPush = {10, 20, 30, 40, 50};
        for (int val : valuesToPush) {
            System.out.println("Pushing: " + val);
            intStack.push(val);
            System.out.println("Current Stack: " + intStack + " | Size: " + intStack.size());
        }
        System.out.println();

        // 3. Inspect state when stack is full
        System.out.println("--- Inspecting Full Stack ---");
        System.out.println("isEmpty(): " + intStack.isEmpty()); // Expected: false
        System.out.println("isFull():  " + intStack.isFull());  // Expected: true
        System.out.println("size():    " + intStack.size());    // Expected: 5
        System.out.println("peek():    " + intStack.peek());    // Expected: 50
        System.out.println();

        // 4. Test Stack Overflow handling
        System.out.println("--- Testing Stack Overflow Handling ---");
        try {
            System.out.println("Attempting to push 60 into full stack...");
            intStack.push(60);
        } catch (IllegalStateException e) {
            System.out.println("SUCCESSFULLY CAUGHT OVERFLOW EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // 5. Peek and Pop elements
        System.out.println("--- Popping Elements ---");
        System.out.println("Current top (peek): " + intStack.peek());
        System.out.println("Popped element: " + intStack.pop()); // Expected: 50
        System.out.println("Current Stack: " + intStack + " | Size: " + intStack.size());
        System.out.println("Popped element: " + intStack.pop()); // Expected: 40
        System.out.println("Current Stack: " + intStack + " | Size: " + intStack.size());
        System.out.println();

        // 6. Push a new element after popping
        System.out.println("--- Pushing New Element After Pop ---");
        System.out.println("Pushing: 99");
        intStack.push(99);
        System.out.println("Current Stack: " + intStack + " | Size: " + intStack.size());
        System.out.println();

        // 7. Empty the stack completely
        System.out.println("--- Emptying Stack Completely ---");
        while (!intStack.isEmpty()) {
            System.out.println("Popped: " + intStack.pop() + " | Remaining size: " + intStack.size());
        }
        System.out.println("Current Stack: " + intStack);
        System.out.println("isEmpty(): " + intStack.isEmpty()); // Expected: true
        System.out.println();

        // 8. Test Stack Underflow handling for pop() and peek()
        System.out.println("--- Testing Stack Underflow Handling ---");
        try {
            System.out.println("Attempting to pop from an empty stack...");
            intStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("SUCCESSFULLY CAUGHT UNDERFLOW ON POP: java.util.EmptyStackException");
        }

        try {
            System.out.println("Attempting to peek into an empty stack...");
            intStack.peek();
        } catch (EmptyStackException e) {
            System.out.println("SUCCESSFULLY CAUGHT UNDERFLOW ON PEEK: java.util.EmptyStackException");
        }
        System.out.println();

        // 9. Demonstration with generic String type
        System.out.println("--- Demonstration with String Stack (Generics) ---");
        StackUsingArray<String> stringStack = new StackUsingArray<>(3);
        stringStack.push("Java");
        stringStack.push("Data");
        stringStack.push("Structures");
        System.out.println("String Stack: " + stringStack);
        System.out.println("Top word: " + stringStack.peek());
        System.out.println("Popping word: " + stringStack.pop());
        System.out.println("String Stack after pop: " + stringStack);
        System.out.println("\nDemo completed successfully.");
    }
}
