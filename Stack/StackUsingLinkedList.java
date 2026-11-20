import java.util.EmptyStackException;

/**
 * A generic Stack implementation using a singly linked list.
 *
 * A Stack is a Last-In, First-Out (LIFO) data structure where elements
 * are added and removed from the same end, called the "top".
 *
 * By using a linked list, this stack allows dynamic sizing without the need
 * for resizing operations (unlike array-based stacks), and all primary
 * operations (push, pop, peek, isEmpty, size) run in O(1) constant time.
 *
 * @param <T> the type of elements held in this stack
 */
public class StackUsingLinkedList<T> {

    /**
     * Inner class representing a node in the singly linked list.
     * Each node holds a data element and a reference to the next node down the stack.
     *
     * @param <T> the type of data stored in the node
     */
    private static class Node<T> {
        private final T data;      // Data stored in this node
        private Node<T> next;      // Reference to the next node in the stack

        /**
         * Constructs a new Node with the specified data.
         *
         * @param data the value to be stored in this node
         */
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Pointer to the top node of the stack
    private Node<T> top;

    // Number of elements currently in the stack
    private int size;

    /**
     * Constructs an empty stack.
     */
    public StackUsingLinkedList() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Pushes an item onto the top of this stack.
     *
     * Operation details:
     * 1. A new node is allocated with the given item.
     * 2. The new node's 'next' pointer is set to point to the current 'top'.
     * 3. The 'top' pointer is updated to point to the new node.
     * 4. The size counter is incremented by 1.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     *
     * @param item the item to be pushed onto the stack
     */
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top; // Link the new node to the previous top
        top = newNode;      // Update top to be the new node
        size++;             // Increment the number of elements
    }

    /**
     * Removes and returns the item at the top of this stack.
     *
     * Operation details:
     * 1. Checks if the stack is empty; if so, throws an EmptyStackException.
     * 2. Retrieves the data from the current 'top' node.
     * 3. Updates the 'top' pointer to 'top.next' (removing the top node).
     * 4. Decrements the size counter by 1.
     * 5. Returns the retrieved data.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     *
     * @return the item removed from the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = top.data;  // Retrieve data from top node
        top = top.next;     // Move top pointer down to the next node
        size--;             // Decrement the number of elements
        return item;
    }

    /**
     * Looks at the item at the top of this stack without removing it.
     *
     * Operation details:
     * 1. Checks if the stack is empty; if so, throws an EmptyStackException.
     * 2. Returns the data from the current 'top' node.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     *
     * @return the item at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;    // Return data from top node without removing it
    }

    /**
     * Tests if this stack is empty.
     *
     * Time Complexity: O(1)
     *
     * @return {@code true} if this stack contains no items; {@code false} otherwise
     */
    public boolean isEmpty() {
        return top == null; // Alternatively: return size == 0;
    }

    /**
     * Returns the number of items in this stack.
     *
     * Time Complexity: O(1)
     *
     * @return the number of elements currently stored in the stack
     */
    public int size() {
        return size;
    }

    /**
     * Demonstration main method to test and showcase all operations of StackUsingLinkedList.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   Stack Using Linked List Demonstration (Java)   ");
        System.out.println("==================================================\n");

        // 1. Instantiate a new generic stack of Strings
        StackUsingLinkedList<String> stack = new StackUsingLinkedList<>();

        // Verify initial state
        System.out.println("Initial State:");
        System.out.println("  isEmpty(): " + stack.isEmpty());
        System.out.println("  size():    " + stack.size());
        System.out.println();

        // 2. Demonstrate push operations
        System.out.println("Pushing elements onto the stack:");
        String[] itemsToPush = {"First (Base)", "Second", "Third", "Fourth (Top)"};
        for (String item : itemsToPush) {
            System.out.println("  Pushing: \"" + item + "\"");
            stack.push(item);
            System.out.println("    Current size: " + stack.size() + ", Top element: \"" + stack.peek() + "\"");
        }
        System.out.println();

        // 3. Demonstrate peek operation
        System.out.println("Peeking at the top element:");
        System.out.println("  peek(): \"" + stack.peek() + "\"");
        System.out.println("  size() after peek: " + stack.size());
        System.out.println("  isEmpty(): " + stack.isEmpty());
        System.out.println();

        // 4. Demonstrate pop operations
        System.out.println("Popping elements from the stack (LIFO order):");
        while (!stack.isEmpty()) {
            String poppedItem = stack.pop();
            System.out.println("  Popped: \"" + poppedItem + "\" | Remaining size: " + stack.size());
        }
        System.out.println();

        // 5. Verify stack is empty after popping all elements
        System.out.println("State after popping all elements:");
        System.out.println("  isEmpty(): " + stack.isEmpty());
        System.out.println("  size():    " + stack.size());
        System.out.println();

        // 6. Demonstrate exception handling when popping from an empty stack
        System.out.println("Demonstrating exception handling on empty stack:");
        try {
            System.out.println("  Attempting to pop from empty stack...");
            stack.pop();
        } catch (EmptyStackException e) {
            System.out.println("  Caught expected EmptyStackException: Stack is empty!");
        }

        try {
            System.out.println("  Attempting to peek on empty stack...");
            stack.peek();
        } catch (EmptyStackException e) {
            System.out.println("  Caught expected EmptyStackException: Stack is empty!");
        }
        System.out.println();

        // 7. Demonstrate with another data type (Integer) to show generic capability
        System.out.println("Demonstrating generic support with Integers:");
        StackUsingLinkedList<Integer> intStack = new StackUsingLinkedList<>();
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);
        System.out.println("  Pushed 10, 20, 30. Size: " + intStack.size());
        System.out.println("  Popped from intStack: " + intStack.pop());
        System.out.println("  Current top of intStack: " + intStack.peek());
        System.out.println("  Current size: " + intStack.size());

        System.out.println("\nAll StackUsingLinkedList operations completed successfully.");
    }
}
