import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * ============================================================================
 * CustomMaxHeap.java
 * ============================================================================
 * A complete, array-based implementation of a binary Max-Heap data structure.
 *
 * WHAT IS A MAX-HEAP?
 * -------------------
 * A Max-Heap is a complete binary tree where every parent node satisfies the
 * "Heap-Order Property":
 *      Value(Parent) >= Value(Child)
 * Consequently, the largest element in the heap is always located at the root
 * (index 0).
 *
 * A "complete binary tree" is completely filled on all levels except possibly
 * the lowest level, which is filled from left to right. This structural property
 * allows the tree to be efficiently mapped directly into a contiguous 1D array
 * without pointers or node references.
 *
 * ============================================================================
 * PARENT AND CHILD INDEX FORMULAS (0-BASED INDEXING)
 * ============================================================================
 * Given an element stored at index 'i':
 *
 *   1. Left Child Index:
 *      Formula: leftChild(i) = 2 * i + 1
 *
 *   2. Right Child Index:
 *      Formula: rightChild(i) = 2 * i + 2
 *
 *   3. Parent Index:
 *      Formula: parent(i) = (i - 1) / 2
 *
 * MATHEMATICAL DERIVATION:
 * ------------------------
 * Consider the natural 1-based indexing for a binary tree:
 *   - Root is at index 1.
 *   - For node at index 'k':
 *       * Left child  = 2 * k
 *       * Right child = 2 * k + 1
 *       * Parent      = k / 2   (integer division)
 *
 * To translate 1-based index 'k' to 0-based array index 'i', let k = i + 1:
 *
 *   - Left Child (0-based):
 *       k_left = 2 * k = 2 * (i + 1) = 2 * i + 2
 *       i_left = k_left - 1 = (2 * i + 2) - 1 = 2 * i + 1
 *
 *   - Right Child (0-based):
 *       k_right = 2 * k + 1 = 2 * (i + 1) + 1 = 2 * i + 3
 *       i_right = k_right - 1 = (2 * i + 3) - 1 = 2 * i + 2
 *
 *   - Parent (0-based):
 *       k_parent = floor(k / 2) = floor((i + 1) / 2)
 *       i_parent = k_parent - 1 = floor((i + 1) / 2) - 1
 *                = floor((i + 1 - 2) / 2) = floor((i - 1) / 2)
 *
 * In Java integer arithmetic (which truncates towards zero):
 * For i = 1: (1 - 1) / 2 = 0 / 2 = 0  (left child of 0 is 1)
 * For i = 2: (2 - 1) / 2 = 1 / 2 = 0  (right child of 0 is 2)
 * For i = 3: (3 - 1) / 2 = 2 / 2 = 1  (left child of 1 is 3)
 * For i = 4: (4 - 1) / 2 = 3 / 2 = 1  (right child of 1 is 4)
 * For i = 5: (5 - 1) / 2 = 4 / 2 = 2  (left child of 2 is 5)
 * For i = 6: (6 - 1) / 2 = 5 / 2 = 2  (right child of 2 is 6)
 *
 * ============================================================================
 * TIME & SPACE COMPLEXITIES
 * ============================================================================
 *   - insert(val):       O(log n) time | O(1) auxiliary space (amortized O(1) resizing)
 *   - extractMax():      O(log n) time | O(1) space
 *   - peek():            O(1)     time | O(1) space
 *   - heapifyUp(i):      O(log n) time | O(1) space
 *   - heapifyDown(i):    O(log n) time | O(1) space
 *   - size():            O(1)     time | O(1) space
 *   - display():         O(n)     time | O(1) space
 *   - Total Space:       O(n) where n is the number of elements in the heap.
 * ============================================================================
 */
public class CustomMaxHeap {

    /** Default initial capacity of the underlying array. */
    private static final int DEFAULT_CAPACITY = 10;

    /** Internal array storing heap elements. */
    private int[] heap;

    /** Current number of active elements in the heap. */
    private int size;

    /**
     * Constructs a CustomMaxHeap with the default initial capacity (10).
     */
    public CustomMaxHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs a CustomMaxHeap with a specified initial capacity.
     *
     * @param initialCapacity the initial array capacity (must be > 0)
     * @throws IllegalArgumentException if initialCapacity <= 0
     */
    public CustomMaxHeap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than zero. Provided: " + initialCapacity);
        }
        this.heap = new int[initialCapacity];
        this.size = 0;
    }

    // ========================================================================
    // INDEX HELPER METHODS
    // ========================================================================

    /**
     * Calculates the index of the parent node.
     * Formula: (i - 1) / 2
     *
     * @param i the index of the current node
     * @return the index of its parent
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Calculates the index of the left child.
     * Formula: 2 * i + 1
     *
     * @param i the index of the current node
     * @return the index of its left child
     */
    private int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * Calculates the index of the right child.
     * Formula: 2 * i + 2
     *
     * @param i the index of the current node
     * @return the index of its right child
     */
    private int rightChild(int i) {
        return 2 * i + 2;
    }

    /**
     * Checks if the node at index 'i' has a parent.
     * In 0-based indexing, the root is at index 0 and has no parent.
     *
     * @param i the node index
     * @return true if i > 0, false if i == 0
     */
    private boolean hasParent(int i) {
        return i > 0;
    }

    /**
     * Checks if the node at index 'i' has a left child within current heap bounds.
     *
     * @param i the node index
     * @return true if left child index < size, false otherwise
     */
    private boolean hasLeftChild(int i) {
        return leftChild(i) < size;
    }

    /**
     * Checks if the node at index 'i' has a right child within current heap bounds.
     *
     * @param i the node index
     * @return true if right child index < size, false otherwise
     */
    private boolean hasRightChild(int i) {
        return rightChild(i) < size;
    }

    /**
     * Swaps two elements in the heap array.
     *
     * @param i first element index
     * @param j second element index
     */
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Ensures the backing array has enough capacity for a new insertion.
     * Doubles the array capacity when the array becomes full.
     */
    private void ensureCapacity() {
        if (size == heap.length) {
            int newCapacity = heap.length * 2;
            heap = Arrays.copyOf(heap, newCapacity);
        }
    }

    // ========================================================================
    // CORE HEAP OPERATIONS
    // ========================================================================

    /**
     * Inserts a new value into the Max-Heap.
     *
     * ALGORITHM:
     * 1. Check array capacity and resize (double) if full.
     * 2. Place the new element at the next available leaf position (index 'size').
     * 3. Increment 'size'.
     * 4. Restore the Max-Heap property by bubbling the element up via heapifyUp().
     *
     * TIME COMPLEXITY:  O(log n) amortized
     * SPACE COMPLEXITY: O(1) auxiliary space
     *
     * @param val the integer value to insert
     */
    public void insert(int val) {
        ensureCapacity();
        heap[size] = val;
        heapifyUp(size);
        size++;
    }

    /**
     * Restores the Max-Heap property from the bottom-up, starting at index 'i'.
     *
     * ALGORITHM:
     * - Compare node at index 'i' with its parent at index parent(i).
     * - If node is greater than its parent, swap them.
     * - Update index 'i' to parent's index.
     * - Repeat until the root is reached (i == 0) or parent >= child.
     *
     * TIME COMPLEXITY:  O(log n) - In the worst case, travels the tree height.
     * SPACE COMPLEXITY: O(1) iterative
     *
     * @param i the index of the element to bubble up
     */
    public void heapifyUp(int i) {
        // While current node is not the root and violates the max-heap property
        while (hasParent(i) && heap[i] > heap[parent(i)]) {
            int parentIndex = parent(i);
            swap(i, parentIndex);
            i = parentIndex; // Move up to parent's index for the next iteration
        }
    }

    /**
     * Removes and returns the maximum element (the root) from the heap.
     *
     * ALGORITHM:
     * 1. Validate that the heap is not empty.
     * 2. Capture the root value at index 0.
     * 3. Move the last element (index size - 1) to the root position (index 0).
     * 4. Decrement 'size'.
     * 5. If the heap is not empty, restore the Max-Heap property by bubbling down
     *    the new root using heapifyDown(0).
     * 6. Return the captured maximum value.
     *
     * TIME COMPLEXITY:  O(log n)
     * SPACE COMPLEXITY: O(1)
     *
     * @return the maximum integer in the heap
     * @throws NoSuchElementException if the heap is empty
     */
    public int extractMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot extractMax from an empty heap.");
        }

        int max = heap[0];
        // Replace root with the last element in the complete binary tree
        heap[0] = heap[size - 1];
        size--;

        // Restore heap property from root down
        if (size > 0) {
            heapifyDown(0);
        }

        return max;
    }

    /**
     * Restores the Max-Heap property from top-down, starting at index 'i'.
     *
     * ALGORITHM:
     * - Identify the largest between the current node, its left child, and its right child.
     * - If the largest node is not the current node 'i', swap current node with
     *   the largest child.
     * - Continue the process iteratively down the branch where the swap occurred.
     * - Stop when the current node is greater than or equal to both children, or
     *   when it has reached a leaf position.
     *
     * TIME COMPLEXITY:  O(log n) - In worst case, travels down tree height.
     * SPACE COMPLEXITY: O(1) iterative
     *
     * @param i the index of the element to bubble down
     */
    public void heapifyDown(int i) {
        // As long as the node has at least a left child (it cannot have only a right child)
        while (hasLeftChild(i)) {
            int largest = i;
            int left = leftChild(i);
            int right = rightChild(i);

            // Compare with left child
            if (left < size && heap[left] > heap[largest]) {
                largest = left;
            }

            // Compare with right child
            if (right < size && heap[right] > heap[largest]) {
                largest = right;
            }

            // If the current node is already larger than both children, heap property holds
            if (largest == i) {
                break;
            }

            // Swap with the larger child and continue descending
            swap(i, largest);
            i = largest;
        }
    }

    /**
     * Returns the maximum element (the root) without removing it.
     *
     * TIME COMPLEXITY:  O(1)
     * SPACE COMPLEXITY: O(1)
     *
     * @return the maximum integer in the heap
     * @throws NoSuchElementException if the heap is empty
     */
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek into an empty heap.");
        }
        return heap[0];
    }

    /**
     * Returns the current number of elements in the heap.
     *
     * TIME COMPLEXITY:  O(1)
     * SPACE COMPLEXITY: O(1)
     *
     * @return number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the heap contains no elements.
     *
     * @return true if heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Displays the heap elements in array order (level-by-level) along with
     * an intuitive visual tree breakdown showing parent-child relationships.
     *
     * TIME COMPLEXITY:  O(n)
     * SPACE COMPLEXITY: O(1)
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Heap is empty: []");
            return;
        }

        // 1. Array representation
        StringBuilder sb = new StringBuilder();
        sb.append("Array representation [size=").append(size).append("]: [");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        System.out.println(sb.toString());

        // 2. Node relationships breakdown (Parent -> Left, Right)
        System.out.println("Node breakdown (Index: Value -> Left Child, Right Child):");
        for (int i = 0; i < size; i++) {
            String leftStr = hasLeftChild(i) ? String.valueOf(heap[leftChild(i)]) : "None";
            String rightStr = hasRightChild(i) ? String.valueOf(heap[rightChild(i)]) : "None";
            System.out.printf("  Index %2d | Val: %3d  -->  Left: %-4s | Right: %-4s%n",
                    i, heap[i], leftStr, rightStr);
        }
    }

    // ========================================================================
    // MAIN DEMONSTRATION METHOD
    // ========================================================================

    /**
     * Comprehensive demo exercising all CustomMaxHeap operations.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("         CUSTOM MAX HEAP COMPREHENSIVE DEMO           ");
        System.out.println("======================================================");

        // Instantiate heap with a small initial capacity to test dynamic resizing
        CustomMaxHeap maxHeap = new CustomMaxHeap(4);
        System.out.println("Initial heap created. Size: " + maxHeap.size() + ", isEmpty: " + maxHeap.isEmpty());
        System.out.println();

        // --------------------------------------------------------------------
        // 1. INSERTION DEMO
        // --------------------------------------------------------------------
        int[] valuesToInsert = {45, 20, 14, 12, 31, 7, 11, 13, 70, 99};
        System.out.println("--> Inserting values: " + Arrays.toString(valuesToInsert));

        for (int val : valuesToInsert) {
            maxHeap.insert(val);
            System.out.printf("Inserted %2d | Current Root (Max): %2d | Size: %2d%n",
                    val, maxHeap.peek(), maxHeap.size());
        }
        System.out.println();

        // --------------------------------------------------------------------
        // 2. DISPLAY HEAP STATE
        // --------------------------------------------------------------------
        System.out.println("--> Current Heap Structure after all insertions:");
        maxHeap.display();
        System.out.println();

        // --------------------------------------------------------------------
        // 3. PEEK DEMO
        // --------------------------------------------------------------------
        System.out.println("--> Peeking maximum element (without removal):");
        System.out.println("Current Max (Root): " + maxHeap.peek());
        System.out.println("Size remains unchanged: " + maxHeap.size());
        System.out.println();

        // --------------------------------------------------------------------
        // 4. EXTRACT-MAX DEMO (Simulating Heap Sort)
        // --------------------------------------------------------------------
        System.out.println("--> Extracting all elements one by one (Sorted Descending):");
        int originalSize = maxHeap.size();
        for (int step = 1; step <= originalSize; step++) {
            int extracted = maxHeap.extractMax();
            System.out.printf("Step %2d: Extracted Max = %2d | Remaining Size: %2d%n",
                    step, extracted, maxHeap.size());
        }
        System.out.println();

        // --------------------------------------------------------------------
        // 5. EDGE CASE TESTS: EMPTY HEAP
        // --------------------------------------------------------------------
        System.out.println("--> Testing edge cases on empty heap:");
        System.out.println("Is heap empty? " + maxHeap.isEmpty());
        System.out.println("Heap size: " + maxHeap.size());

        // Test peek on empty heap
        try {
            System.out.print("Attempting peek() on empty heap: ");
            maxHeap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION -> " + e.getMessage());
        }

        // Test extractMax on empty heap
        try {
            System.out.print("Attempting extractMax() on empty heap: ");
            maxHeap.extractMax();
        } catch (NoSuchElementException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION -> " + e.getMessage());
        }
        System.out.println();

        // --------------------------------------------------------------------
        // 6. RE-INSERTION AFTER EMPTIED
        // --------------------------------------------------------------------
        System.out.println("--> Re-inserting values after emptying heap:");
        maxHeap.insert(50);
        maxHeap.insert(100);
        maxHeap.insert(25);
        maxHeap.display();
        System.out.println("Current Max: " + maxHeap.peek());
        System.out.println();

        System.out.println("======================================================");
        System.out.println("              DEMO COMPLETED SUCCESSFULLY             ");
        System.out.println("======================================================");
    }
}
