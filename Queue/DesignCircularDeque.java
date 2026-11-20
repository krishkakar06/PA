/**
 * LeetCode 641: Design Circular Deque
 * File: DesignCircularDeque.java
 *
 * Problem Description:
 * Design your implementation of the circular double-ended queue (deque).
 *
 * Implement the MyCircularDeque class:
 * - MyCircularDeque(int k): Initializes the deque with a maximum size of k.
 * - boolean insertFront(int value): Adds an item at the front of Deque. Returns true if the operation is successful, or false otherwise.
 * - boolean insertLast(int value): Adds an item at the rear of Deque. Returns true if the operation is successful, or false otherwise.
 * - boolean deleteFront(): Deletes an item from the front of Deque. Returns true if the operation is successful, or false otherwise.
 * - boolean deleteLast(): Deletes an item from the rear of Deque. Returns true if the operation is successful, or false otherwise.
 * - int getFront(): Returns the front item from the Deque. Returns -1 if the deque is empty.
 * - int getRear(): Returns the last item from Deque. Returns -1 if the deque is empty.
 * - boolean isEmpty(): Returns true if the deque is empty, or false otherwise.
 * - boolean isFull(): Returns true if the deque is full, or false otherwise.
 *
 * Architectural & Design Choice: Circular Array (Ring Buffer)
 * -------------------------------------------------------------
 * A circular deque can be implemented using either a doubly-linked list or a circular array.
 * We choose the Circular Array (Ring Buffer) approach for the following reasons:
 * 1. Memory Efficiency & Cache Locality:
 *    - All elements are stored contiguously in a single flat integer array.
 *    - No dynamic heap allocations or Node object wrappers per element, avoiding garbage
 *      collection overhead and reference pointer overhead (8-16 bytes per reference).
 * 2. Predictable O(1) Time Complexity:
 *    - Direct index calculations via modulo arithmetic guarantee strictly O(1) worst-case
 *      time complexity for all 8 operations.
 * 3. Exact State Management:
 *    - By maintaining a separate `size` variable along with `front` pointer and `capacity`,
 *      we completely eliminate the classic "full vs empty" disambiguation ambiguity that
 *      typically requires allocating an extra buffer slot (k + 1).
 *
 * Index Calculations:
 * - Current Front element: buffer[front]
 * - Next Front insertion:  (front - 1 + capacity) % capacity
 * - Front deletion:        (front + 1) % capacity
 * - Current Rear element:  buffer[(front + size - 1) % capacity]
 * - Next Rear insertion:   (front + size) % capacity
 * - Rear deletion:         simply decrement size (the slot will be overwritten on future insertions)
 *
 * Complexity Analysis:
 * - Time Complexity:
 *     - MyCircularDeque(k): O(1)
 *     - insertFront(value): O(1)
 *     - insertLast(value):  O(1)
 *     - deleteFront():      O(1)
 *     - deleteLast():       O(1)
 *     - getFront():         O(1)
 *     - getRear():          O(1)
 *     - isEmpty():          O(1)
 *     - isFull():           O(1)
 * - Space Complexity:
 *     - Overall Space: O(k) where k is the maximum capacity specified for the deque.
 *     - Auxiliary Space: O(1) per operation.
 */
class MyCircularDeque {

    /** Internal fixed-size array buffer to store deque elements */
    private final int[] buffer;

    /** Maximum capacity of the circular deque */
    private final int capacity;

    /** Pointer pointing to the index of the current front element */
    private int front;

    /** Current number of active elements in the deque */
    private int size;

    /**
     * Initializes the circular deque with a maximum size of k.
     *
     * @param k The maximum capacity of the deque (1 <= k <= 1000).
     *
     * Time Complexity: O(1)
     * Space Complexity: O(k) for allocating the internal array buffer.
     */
    public MyCircularDeque(int k) {
        this.capacity = k;
        this.buffer = new int[k];
        this.front = 0;
        this.size = 0;
    }

    /**
     * Adds an item at the front of Deque.
     *
     * In a circular array, inserting at the front moves the front pointer
     * one step counter-clockwise: (front - 1 + capacity) % capacity.
     *
     * @param value The value to be added at the front (0 <= value <= 1000).
     * @return true if the operation is successful, or false if the deque is full.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean insertFront(int value) {
        // If the deque is full, we cannot accept any new elements
        if (isFull()) {
            return false;
        }

        // Move the front pointer counter-clockwise with wrap-around
        this.front = (this.front - 1 + this.capacity) % this.capacity;
        this.buffer[this.front] = value;
        this.size++;

        return true;
    }

    /**
     * Adds an item at the rear of Deque.
     *
     * The next available rear position is located immediately after the
     * last element: (front + size) % capacity.
     *
     * @param value The value to be added at the rear (0 <= value <= 1000).
     * @return true if the operation is successful, or false if the deque is full.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean insertLast(int value) {
        // If the deque is full, we cannot accept any new elements
        if (isFull()) {
            return false;
        }

        // Calculate the next open rear slot using modulo arithmetic
        int rearIndex = (this.front + this.size) % this.capacity;
        this.buffer[rearIndex] = value;
        this.size++;

        return true;
    }

    /**
     * Deletes an item from the front of Deque.
     *
     * Moves the front pointer one step clockwise: (front + 1) % capacity.
     *
     * @return true if the operation is successful, or false if the deque is empty.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean deleteFront() {
        // If the deque is empty, no element can be deleted
        if (isEmpty()) {
            return false;
        }

        // Advance front pointer clockwise to discard the front element
        this.front = (this.front + 1) % this.capacity;
        this.size--;

        return true;
    }

    /**
     * Deletes an item from the rear of Deque.
     *
     * Simply decrements the size count. The discarded rear slot will be
     * overwritten by future insertions.
     *
     * @return true if the operation is successful, or false if the deque is empty.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean deleteLast() {
        // If the deque is empty, no element can be deleted
        if (isEmpty()) {
            return false;
        }

        // Decrement size to logically remove the rear element
        this.size--;

        return true;
    }

    /**
     * Gets the front item from the Deque.
     *
     * @return The value at the front of the deque, or -1 if the deque is empty.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public int getFront() {
        // Return -1 if the deque contains no elements
        if (isEmpty()) {
            return -1;
        }

        // Return the item at the front index
        return this.buffer[this.front];
    }

    /**
     * Gets the last item from Deque.
     *
     * The last element is located at index: (front + size - 1) % capacity.
     *
     * @return The value at the rear of the deque, or -1 if the deque is empty.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public int getRear() {
        // Return -1 if the deque contains no elements
        if (isEmpty()) {
            return -1;
        }

        // Compute the index of the most recently inserted rear element
        int rearIndex = (this.front + this.size - 1) % this.capacity;
        return this.buffer[rearIndex];
    }

    /**
     * Checks whether the circular deque is empty.
     *
     * @return true if the deque contains 0 elements, or false otherwise.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Checks whether the circular deque is full.
     *
     * @return true if the number of stored elements equals capacity, or false otherwise.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean isFull() {
        return this.size == this.capacity;
    }
}
