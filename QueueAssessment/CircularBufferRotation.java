/**
 * CircularBufferRotation.java
 *
 * Implementation of a fixed-size Circular Buffer (Circular Queue) with automatic
 * overwriting of the oldest data when the buffer becomes full.
 *
 * Characteristics:
 * - Fixed capacity defined upon instantiation.
 * - FIFO (First-In-First-Out) ordering under normal conditions.
 * - When full, inserting a new element overwrites the oldest element (head)
 *   and advances the head pointer accordingly.
 * - O(1) time complexity for add, get, size, and isFull operations.
 */
public class CircularBufferRotation {

    // Internal storage array for buffer elements
    private final int[] buffer;

    // Maximum number of elements the buffer can hold
    private final int capacity;

    // Index pointing to the oldest element (read position)
    private int head;

    // Index pointing to the next insertion position (write position)
    private int tail;

    // Current number of valid elements present in the buffer
    private int size;

    /**
     * Constructs a CircularBufferRotation instance with the specified fixed capacity.
     *
     * @param capacity Maximum capacity of the buffer (must be greater than 0).
     * @throws IllegalArgumentException if capacity is less than or equal to 0.
     */
    public CircularBufferRotation(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Buffer capacity must be greater than 0. Provided: " + capacity);
        }
        this.capacity = capacity;
        this.buffer = new int[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    /**
     * Adds an element to the circular buffer.
     * If the buffer is full, it automatically overwrites the oldest element
     * and shifts the head pointer forward to maintain consistency.
     *
     * @param val The integer value to insert into the buffer.
     */
    public void add(int val) {
        // Write the new value at the current tail position
        buffer[tail] = val;

        // Advance the tail pointer cyclically
        tail = (tail + 1) % capacity;

        if (isFull()) {
            // When full, overwriting the oldest element means the head must also advance
            head = (head + 1) % capacity;
        } else {
            // Buffer is not full yet, increment element count
            size++;
        }
    }

    /**
     * Retrieves the element at the specified logical index.
     * Index 0 represents the oldest element (head), and index (size - 1)
     * represents the most recently added element.
     *
     * @param index 0-based logical index relative to the oldest element.
     * @return The element stored at the given logical index.
     * @throws IndexOutOfBoundsException if index is negative or >= current size.
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for buffer size " + size);
        }
        // Map logical index to physical array index using circular arithmetic
        int physicalIndex = (head + index) % capacity;
        return buffer[physicalIndex];
    }

    /**
     * Returns the current number of elements in the buffer.
     *
     * @return Current size of the buffer (ranges from 0 to capacity).
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the buffer has reached its maximum capacity.
     *
     * @return true if the buffer is full, false otherwise.
     */
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Checks whether the buffer is currently empty.
     *
     * @return true if the buffer contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the maximum fixed capacity of the buffer.
     *
     * @return Fixed capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Displays the current contents of the buffer in logical order (oldest to newest),
     * along with internal state metadata (size, capacity, head, tail).
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Buffer is empty [] (Size: 0/" + capacity + ", Head: " + head + ", Tail: " + tail + ")");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Buffer [");
        for (int i = 0; i < size; i++) {
            sb.append(get(i));
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("] (Size: ")
          .append(size)
          .append("/")
          .append(capacity)
          .append(", Head: ")
          .append(head)
          .append(", Tail: ")
          .append(tail)
          .append(", Full: ")
          .append(isFull())
          .append(")");

        System.out.println(sb.toString());
    }

    /**
     * Demonstrates the functionality of CircularBufferRotation with various scenarios:
     * 1. Insertion without overflow
     * 2. Full state detection
     * 3. Overwriting oldest data on rotation
     * 4. Element retrieval by logical index
     * 5. Exception handling for invalid index access
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   CIRCULAR BUFFER ROTATION DEMO (CAPACITY = 3)  ");
        System.out.println("=================================================\n");

        // Step 1: Create a circular buffer with capacity 3
        CircularBufferRotation cb = new CircularBufferRotation(3);
        System.out.println("Initial State:");
        cb.display();
        System.out.println("isFull(): " + cb.isFull() + ", size(): " + cb.size() + "\n");

        // Step 2: Add elements until full
        System.out.println("--- Adding elements 10, 20, 30 ---");
        cb.add(10);
        cb.display();

        cb.add(20);
        cb.display();

        cb.add(30);
        cb.display();

        System.out.println("\nStatus after filling:");
        System.out.println("isFull(): " + cb.isFull() + ", size(): " + cb.size() + "\n");

        // Step 3: Access elements using get(index)
        System.out.println("--- Reading elements via get(index) ---");
        for (int i = 0; i < cb.size(); i++) {
            System.out.println("get(" + i + ") = " + cb.get(i) + (i == 0 ? " (oldest)" : (i == cb.size() - 1 ? " (newest)" : "")));
        }
        System.out.println();

        // Step 4: Overwrite oldest data by adding when full
        System.out.println("--- Adding 40 (Buffer is full, 10 will be overwritten) ---");
        cb.add(40);
        cb.display();
        System.out.println("Oldest element (get(0)): " + cb.get(0));
        System.out.println("Newest element (get(2)): " + cb.get(2) + "\n");

        System.out.println("--- Adding 50 (Buffer is full, 20 will be overwritten) ---");
        cb.add(50);
        cb.display();
        System.out.println("Oldest element (get(0)): " + cb.get(0));
        System.out.println("Newest element (get(2)): " + cb.get(2) + "\n");

        System.out.println("--- Adding 60 (Buffer is full, 30 will be overwritten) ---");
        cb.add(60);
        cb.display();
        System.out.println("Oldest element (get(0)): " + cb.get(0));
        System.out.println("Newest element (get(2)): " + cb.get(2) + "\n");

        // Step 5: Test edge cases and exceptions
        System.out.println("--- Testing Error Handling ---");
        try {
            System.out.print("Attempting to get(-1): ");
            cb.get(-1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught Expected Exception -> " + e.getMessage());
        }

        try {
            System.out.print("Attempting to get(3) [size is 3]: ");
            cb.get(3);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught Expected Exception -> " + e.getMessage());
        }

        try {
            System.out.print("Attempting to create buffer with capacity 0: ");
            new CircularBufferRotation(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Expected Exception -> " + e.getMessage());
        }

        System.out.println("\nDemo completed successfully.");
    }
}
