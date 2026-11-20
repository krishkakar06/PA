/**
 * File Name: CircularQueueImplementation.java
 *
 * This file contains a complete, robust, and well-commented implementation
 * of a Circular Queue data structure using a fixed-size array in Java.
 *
 * Key Concepts Demonstrated:
 * 1. Circular Indexing using Modular Arithmetic: (index + 1) % capacity.
 * 2. Efficient O(1) time complexity for all standard queue operations.
 * 3. Prevention of "false overflow" encountered in standard linear array queues.
 * 4. Comprehensive edge-case handling (overflow, underflow, single-element queue).
 */

public class CircularQueueImplementation extends CircularQueue {

    /**
     * Constructs a CircularQueueImplementation with a specified capacity.
     *
     * @param capacity The maximum number of elements the queue can hold.
     */
    public CircularQueueImplementation(int capacity) {
        super(capacity);
    }

    /**
     * Default constructor with a default capacity of 5.
     */
    public CircularQueueImplementation() {
        super();
    }

    /**
     * Main method serving as an entry point when running CircularQueueImplementation.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        CircularQueue.main(args);
    }
}

/**
 * Class representing a Circular Queue data structure using an array.
 *
 * In a traditional linear queue implemented with an array, dequeuing elements
 * causes the front pointer to advance forward. The slots before the front pointer
 * become unoccupied, but cannot be reused without shifting elements (which costs O(N)).
 * Eventually, the rear pointer reaches the end of the array, causing a "false overflow"
 * even when the queue has empty space.
 *
 * A Circular Queue solves this problem by logically treating the array as a ring:
 * The next position after index (capacity - 1) wraps around to index 0 using
 * modular arithmetic:
 *
 *                  next_index = (current_index + 1) % capacity
 *
 * Example (capacity = 5):
 *   Index:       0    1    2    3    4
 *   Wrap-around: (4 + 1) % 5 = 0 (wraps back to the start!)
 */
class CircularQueue {

    // Internal array buffer storing the queue elements
    private int[] queue;

    // Index pointing to the front (oldest) element in the queue
    private int front;

    // Index pointing to the rear (most recently added) element in the queue
    private int rear;

    // Number of active elements currently stored in the queue
    private int count;

    // Maximum capacity of the queue
    private int capacity;

    // Default capacity if none is specified
    private static final int DEFAULT_CAPACITY = 5;

    /**
     * Constructor to initialize the circular queue with a specified capacity.
     *
     * @param capacity Maximum number of elements the queue can store.
     * @throws IllegalArgumentException if capacity is less than or equal to 0.
     */
    public CircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Queue capacity must be greater than 0.");
        }
        this.capacity = capacity;
        this.queue = new int[capacity];
        // Initialize front and rear pointers to -1 to represent an empty queue state.
        this.front = -1;
        this.rear = -1;
        this.count = 0;
    }

    /**
     * Default constructor initializing the circular queue with DEFAULT_CAPACITY (5).
     */
    public CircularQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Checks whether the circular queue is empty.
     *
     * Mathematical Condition:
     * - Either count == 0, or front == -1.
     * Both conditions reliably indicate an empty queue.
     *
     * @return true if the queue contains 0 elements, false otherwise.
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Checks whether the circular queue is full.
     *
     * Mathematical Condition using Pointers & Modular Arithmetic:
     * - Using count: count == capacity.
     * - Using pointers: (rear + 1) % capacity == front.
     *   If advancing the rear pointer by 1 lands exactly on the front pointer,
     *   the buffer has no available slots left without overwriting unread data.
     *
     * @return true if the queue has reached its maximum capacity, false otherwise.
     */
    public boolean isFull() {
        return count == capacity;
    }

    /**
     * Returns the current number of elements present in the queue.
     *
     * Alternative formula using pointers without maintaining a count variable:
     *   if (isEmpty()) return 0;
     *   return (rear - front + capacity) % capacity + 1;
     *
     * @return The number of elements currently in the queue.
     */
    public int size() {
        return count;
    }

    /**
     * Inserts an element at the rear of the circular queue.
     *
     * Circular Indexing Logic:
     * 1. Check if the queue is full using isFull(). If full, overflow occurs.
     * 2. If the queue is empty, initialize both front and rear pointers to index 0.
     * 3. If the queue is not empty, calculate the next rear index using modular arithmetic:
     *       rear = (rear + 1) % capacity;
     *    This ensures that when rear == capacity - 1, (capacity - 1 + 1) % capacity
     *    evaluates to 0, wrapping the insertion back to the beginning of the array.
     * 4. Place val into queue[rear] and increment count.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     *
     * @param val The integer value to be added.
     * @return true if the element was successfully enqueued, false if queue is full.
     */
    public boolean enqueue(int val) {
        // Step 1: Check for queue overflow
        if (isFull()) {
            System.out.println("[OVERFLOW] Queue is full! Cannot enqueue: " + val);
            return false;
        }

        // Step 2: Handle first element insertion
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            // Step 3: Advance rear pointer circularly
            rear = (rear + 1) % capacity;
        }

        // Step 4: Insert the value and update element count
        queue[rear] = val;
        count++;

        System.out.println("[ENQUEUE] Enqueued: " + val 
                + " | rear index: " + rear 
                + " | front index: " + front 
                + " | size: " + count + "/" + capacity);
        return true;
    }

    /**
     * Removes and returns the element at the front of the circular queue.
     *
     * Circular Indexing Logic:
     * 1. Check if the queue is empty using isEmpty(). If empty, underflow occurs.
     * 2. Save the value stored at queue[front].
     * 3. If front == rear (the queue had exactly one element), the queue becomes
     *    empty after this removal. Reset front = -1 and rear = -1.
     * 4. Otherwise, advance front pointer circularly using modular arithmetic:
     *       front = (front + 1) % capacity;
     *    When front reaches capacity - 1, (capacity - 1 + 1) % capacity evaluates
     *    to 0, wrapping the front pointer back to the start of the array.
     * 5. Decrement count and return the removed value.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     *
     * @return The dequeued integer value, or -1 if the queue is empty.
     */
    public int dequeue() {
        // Step 1: Check for queue underflow
        if (isEmpty()) {
            System.out.println("[UNDERFLOW] Queue is empty! Cannot dequeue.");
            return -1;
        }

        // Step 2: Retrieve front element
        int removedVal = queue[front];

        // Step 3: Check if this was the last remaining element
        if (front == rear) {
            // Queue is now empty, reset pointers to -1
            front = -1;
            rear = -1;
        } else {
            // Step 4: Advance front pointer circularly
            front = (front + 1) % capacity;
        }

        // Step 5: Decrement count
        count--;

        System.out.println("[DEQUEUE] Dequeued: " + removedVal 
                + " | new front index: " + front 
                + " | rear index: " + rear 
                + " | size: " + count + "/" + capacity);
        return removedVal;
    }

    /**
     * Retrieves, but does not remove, the front element of the circular queue.
     *
     * Time Complexity: O(1)
     *
     * @return The element at the front of the queue, or -1 if queue is empty.
     */
    public int front() {
        if (isEmpty()) {
            System.out.println("[PEEK FRONT] Queue is empty! No front element.");
            return -1;
        }
        return queue[front];
    }

    /**
     * Retrieves, but does not remove, the rear (most recently added) element
     * of the circular queue.
     *
     * Time Complexity: O(1)
     *
     * @return The element at the rear of the queue, or -1 if queue is empty.
     */
    public int rear() {
        if (isEmpty()) {
            System.out.println("[PEEK REAR] Queue is empty! No rear element.");
            return -1;
        }
        return queue[rear];
    }

    /**
     * Utility method to display the internal array layout and logical FIFO contents.
     * Shows how circular traversal is achieved by iterating from 0 to size - 1:
     *      actual_array_index = (front + i) % capacity
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Queue Status: [EMPTY] (front = " + front + ", rear = " + rear + ", size = 0)");
            return;
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Queue State: size = " + count + "/" + capacity 
                + ", front = " + front + ", rear = " + rear);

        // Logical FIFO order display
        System.out.print("Logical Queue (Front to Rear): [ ");
        for (int i = 0; i < count; i++) {
            int actualIdx = (front + i) % capacity;
            System.out.print(queue[actualIdx] + (i < count - 1 ? " -> " : ""));
        }
        System.out.println(" ]");

        // Physical Array Buffer visualization
        System.out.print("Physical Array Buffer: [ ");
        for (int i = 0; i < capacity; i++) {
            boolean isElementPresent = false;
            if (!isEmpty()) {
                if (front <= rear) {
                    isElementPresent = (i >= front && i <= rear);
                } else {
                    isElementPresent = (i >= front || i <= rear);
                }
            }
            if (isElementPresent) {
                System.out.print(queue[i]);
            } else {
                System.out.print("_"); // empty / inactive slot
            }
            if (i == front && i == rear) {
                System.out.print("(F,R)");
            } else if (i == front) {
                System.out.print("(F)");
            } else if (i == rear) {
                System.out.print("(R)");
            }
            System.out.print(i < capacity - 1 ? ", " : "");
        }
        System.out.println(" ]");
        System.out.println("--------------------------------------------------");
    }

    /**
     * Comprehensive main() demonstration showing:
     * 1. Basic enqueue operations until full.
     * 2. Overflow handling.
     * 3. Dequeue operations and front pointer advancement.
     * 4. Circular wrap-around: enqueuing when rear reaches end of array but front has space.
     * 5. Underflow handling on empty queue.
     * 6. Reusability after complete drainage.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       CIRCULAR QUEUE COMPREHENSIVE DEMO          ");
        System.out.println("==================================================\n");

        // Step 1: Initialize Circular Queue with capacity 5
        int capacity = 5;
        System.out.println(">> Creating CircularQueue with capacity = " + capacity);
        CircularQueue cq = new CircularQueue(capacity);
        cq.display();

        System.out.println("\nInitial Checks:");
        System.out.println("isEmpty(): " + cq.isEmpty()); // Expected: true
        System.out.println("isFull() : " + cq.isFull());  // Expected: false
        System.out.println("size()   : " + cq.size());    // Expected: 0

        // Step 2: Enqueue elements to fill the queue
        System.out.println("\n>> Enqueuing elements: 10, 20, 30, 40, 50");
        cq.enqueue(10);
        cq.enqueue(20);
        cq.enqueue(30);
        cq.enqueue(40);
        cq.enqueue(50);
        cq.display();

        System.out.println("\nState after filling queue:");
        System.out.println("isEmpty(): " + cq.isEmpty()); // Expected: false
        System.out.println("isFull() : " + cq.isFull());  // Expected: true
        System.out.println("size()   : " + cq.size());    // Expected: 5
        System.out.println("front()  : " + cq.front());   // Expected: 10
        System.out.println("rear()   : " + cq.rear());    // Expected: 50

        // Step 3: Demonstrate Overflow Handling
        System.out.println("\n>> Attempting to enqueue into a FULL queue (Demonstrating Overflow):");
        boolean enqueueResult = cq.enqueue(60);
        System.out.println("Enqueue 60 result: " + enqueueResult); // Expected: false

        // Step 4: Dequeue elements to free space at the beginning of the array
        System.out.println("\n>> Dequeuing 2 elements (10 and 20):");
        int d1 = cq.dequeue(); // Expected: 10
        int d2 = cq.dequeue(); // Expected: 20
        System.out.println("Dequeued values: " + d1 + ", " + d2);
        cq.display();

        System.out.println("\nState after 2 dequeues:");
        System.out.println("front()  : " + cq.front()); // Expected: 30 (at index 2)
        System.out.println("rear()   : " + cq.rear());  // Expected: 50 (at index 4)
        System.out.println("size()   : " + cq.size());  // Expected: 3

        // Step 5: Demonstrate Circular Wrap-around!
        // At this point, rear is at index 4 (last index). In a linear queue, we could not add more.
        // In a circular queue, (4 + 1) % 5 = 0, wrapping around to index 0!
        System.out.println("\n>> Demonstrating Circular Wrap-around!");
        System.out.println("Notice: rear is currently at index 4.");
        System.out.println("Next insertion index will be (4 + 1) % 5 = 0 (wraps around!)");
        cq.enqueue(60); // should be placed at index 0
        cq.display();

        System.out.println("\n>> Enqueuing 70 (should be placed at index (0 + 1) % 5 = 1):");
        cq.enqueue(70); // should be placed at index 1
        cq.display();

        System.out.println("\nState after wrap-around enqueues:");
        System.out.println("isFull() : " + cq.isFull()); // Expected: true (slots 2, 3, 4, 0, 1 are all used)
        System.out.println("front()  : " + cq.front());  // Expected: 30
        System.out.println("rear()   : " + cq.rear());   // Expected: 70
        System.out.println("size()   : " + cq.size());   // Expected: 5

        // Step 6: Dequeue all elements to empty the queue
        System.out.println("\n>> Dequeuing all elements to observe FIFO order and pointer resets:");
        while (!cq.isEmpty()) {
            cq.dequeue();
        }
        cq.display();

        // Step 7: Demonstrate Underflow Handling
        System.out.println("\n>> Attempting to dequeue from an EMPTY queue (Demonstrating Underflow):");
        int underflowVal = cq.dequeue();
        System.out.println("Dequeue result: " + underflowVal); // Expected: -1

        System.out.println("front() on empty queue: " + cq.front()); // Expected: -1
        System.out.println("rear() on empty queue : " + cq.rear());  // Expected: -1

        // Step 8: Verify reusability after being emptied
        System.out.println("\n>> Re-enqueuing into the emptied queue to verify complete reusability:");
        cq.enqueue(100);
        cq.enqueue(200);
        cq.display();
        System.out.println("front() : " + cq.front()); // Expected: 100
        System.out.println("rear()  : " + cq.rear());  // Expected: 200
        System.out.println("size()  : " + cq.size());  // Expected: 2

        System.out.println("\n==================================================");
        System.out.println("       DEMO COMPLETED SUCCESSFULLY!               ");
        System.out.println("==================================================");
    }
}
