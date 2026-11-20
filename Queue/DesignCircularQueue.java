/**
 * LeetCode 622: Design Circular Queue
 *
 * Problem Description:
 * Design your implementation of the circular queue. The circular queue is a linear
 * data structure in which operations are performed based on the FIFO (First In First Out)
 * principle, and the last position is connected back to the first position to form a circle.
 * It is also referred to as a "Ring Buffer".
 *
 * Advantages of a Circular Queue:
 * - Efficient memory utilization: Reuses vacated slots at the front of the queue after dequeue
 *   operations without requiring O(n) element shifting.
 * - Predictable O(1) worst-case time complexity for all operations: enqueue, dequeue, front,
 *   rear, isEmpty, and isFull.
 *
 * Implementation Details:
 * - An internal fixed-size array `data` stores the queue elements.
 * - `head` tracks the index of the current front (oldest) element.
 * - `count` maintains the current number of elements present in the queue.
 * - `capacity` represents the maximum number of elements the queue can accommodate.
 * - Modulo arithmetic (`% capacity`) ensures seamless index wrapping between the end
 *   and beginning of the array.
 *
 * Space Complexity:
 * - O(k) total space where k is the specified maximum capacity of the queue.
 * - O(1) auxiliary space per operation.
 */
class MyCircularQueue {

    /** Internal array buffer to store elements */
    private final int[] data;

    /** Maximum capacity of the circular queue */
    private final int capacity;

    /** Index pointing to the front (first/oldest) element in the queue */
    private int head;

    /** Current number of active elements currently stored in the queue */
    private int count;

    /**
     * Initializes the circular queue data structure with a fixed capacity of k.
     *
     * @param k The maximum capacity of the circular queue (1 <= k <= 1000).
     *
     * Time Complexity: O(1)
     * Space Complexity: O(k) for the underlying array allocation.
     */
    public MyCircularQueue(int k) {
        this.capacity = k;
        this.data = new int[k];
        this.head = 0;
        this.count = 0;
    }

    /**
     * Inserts an element into the circular queue.
     *
     * The insertion position (rear/tail) is computed dynamically using modulo arithmetic:
     * tailIndex = (head + count) % capacity.
     *
     * @param value The integer value to be enqueued (0 <= value <= 1000).
     * @return true if the element was successfully added; false if the queue is already full.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean enQueue(int value) {
        // If the queue has reached capacity, insertion cannot be performed
        if (isFull()) {
            return false;
        }

        // Calculate the next available rear slot using modulo arithmetic to wrap around
        int tail = (this.head + this.count) % this.capacity;
        this.data[tail] = value;

        // Increment the count of stored elements
        this.count++;

        return true;
    }

    /**
     * Deletes an element from the circular queue.
     *
     * Advances the head pointer clockwise by 1 slot: (head + 1) % capacity.
     *
     * @return true if the element was successfully removed; false if the queue is empty.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean deQueue() {
        // If the queue contains no elements, deletion cannot be performed
        if (isEmpty()) {
            return false;
        }

        // Advance the head pointer circularly to the next element
        this.head = (this.head + 1) % this.capacity;

        // Decrement the count of stored elements
        this.count--;

        return true;
    }

    /**
     * Gets the front (first) item from the circular queue.
     *
     * @return The value at the front of the queue, or -1 if the queue is empty.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public int Front() {
        // If the queue is empty, there is no front element
        if (isEmpty()) {
            return -1;
        }

        // Return the element at the current head position
        return this.data[this.head];
    }

    /**
     * Gets the rear (last) item from the circular queue.
     *
     * The rear element is located at index: (head + count - 1) % capacity.
     * Since count >= 1 when not empty and head >= 0, (head + count - 1) >= 0.
     *
     * @return The value at the rear of the queue, or -1 if the queue is empty.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public int Rear() {
        // If the queue is empty, there is no rear element
        if (isEmpty()) {
            return -1;
        }

        // Calculate the position of the most recently inserted element
        int tail = (this.head + this.count - 1) % this.capacity;

        return this.data[tail];
    }

    /**
     * Checks whether the circular queue is empty.
     *
     * @return true if the queue contains 0 elements; false otherwise.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * Checks whether the circular queue has reached its maximum capacity.
     *
     * @return true if the number of elements equals the capacity; false otherwise.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean isFull() {
        return this.count == this.capacity;
    }
}
