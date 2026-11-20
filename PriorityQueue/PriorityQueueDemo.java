import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * PriorityQueueDemo.java
 *
 * A complete, comprehensive demonstration of Java's PriorityQueue class.
 *
 * Key Architectural & Algorithmic Concepts:
 * 1. Underlying Data Structure:
 *    - Java's PriorityQueue is an unbounded priority heap implemented as a balanced binary heap array.
 *    - For any element at array index k:
 *        - Left child is at (2 * k + 1)
 *        - Right child is at (2 * k + 2)
 *        - Parent is at ((k - 1) / 2)
 *    - By default, PriorityQueue orders elements according to their natural ordering (Min-Heap).
 *
 * 2. Time Complexities:
 *    - offer(E e) / add(E e) : O(log n) - Inserts element and sifts up to restore heap property.
 *    - poll() / remove()     : O(log n) - Removes root and sifts down to restore heap property.
 *    - peek() / element()    : O(1)     - Inspects root element at index 0 without removal.
 *    - remove(Object o)      : O(n)     - Linear scan to find the element, then O(log n) to reheapify.
 *    - contains(Object o)    : O(n)     - Linear scan of the backing array.
 *    - size() / isEmpty()    : O(1)     - Constant time field check.
 *
 * 3. Queue Method Contract Pairs (Special Value vs Exception):
 *    - Insert : offer(e) returns true (or false if bounded), whereas add(e) throws IllegalStateException.
 *               (Note: PriorityQueue is unbounded, so offer() will not return false).
 *    - Examine: peek() returns null if empty; element() throws NoSuchElementException.
 *    - Remove : poll() returns null if empty; remove() throws NoSuchElementException.
 *
 * 4. Important Subtleties:
 *    - Null Elements: PriorityQueue does NOT permit null elements (throws NullPointerException).
 *    - Iteration Order: An Iterator (or foreach loop) does NOT guarantee visiting elements in priority order.
 *      To retrieve elements strictly in priority order, poll() sequentially until empty.
 *    - Concurrency: PriorityQueue is not thread-safe. Use java.util.concurrent.PriorityBlockingQueue
 *      for concurrent access across multiple threads.
 */
public class PriorityQueueDemo {

    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("         JAVA PriorityQueue COMPREHENSIVE DEMONSTRATION         ");
        System.out.println("================================================================\n");

        demonstrateMinHeap();
        demonstrateMaxHeap();
        demonstrateCustomComparator();
        demonstrateRemovalOperations();
        demonstrateMethodDifferences();

        System.out.println("\n================================================================");
        System.out.println("                   DEMONSTRATION COMPLETE                       ");
        System.out.println("================================================================");
    }

    /**
     * Demonstrates standard Min-Heap behavior (Default in Java).
     * The smallest element (by natural ordering) is always at the head of the queue.
     */
    private static void demonstrateMinHeap() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("1. MIN-HEAP DEMONSTRATION (Natural Ordering - Default)");
        System.out.println("----------------------------------------------------------------");

        // By default, PriorityQueue orders elements using their natural order (Min-Heap).
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Inserting elements using offer():
        // Inserts the specified element into this priority queue.
        System.out.println("Inserting elements: 45, 12, 89, 3, 27, 64 using offer()...");
        int[] elementsToInsert = {45, 12, 89, 3, 27, 64};
        for (int val : elementsToInsert) {
            minHeap.offer(val);
            // peek() retrieves, but does not remove, the head of this queue.
            System.out.printf("  Offered %2d -> Current Head (peek): %2d, Heap Size: %d%n",
                    val, minHeap.peek(), minHeap.size());
        }

        System.out.println("\nInspecting head without removal (peek): " + minHeap.peek());

        // Note: Iterator traversal over a PriorityQueue traverses the underlying array,
        // which represents a heap tree, NOT a fully sorted array.
        System.out.println("Notice: Internal array order during iteration is NOT strictly sorted:");
        System.out.print("  Direct iteration: ");
        for (int val : minHeap) {
            System.out.print(val + " ");
        }
        System.out.println();

        // Polling elements: poll() retrieves and removes the head of the queue (or returns null if empty).
        System.out.println("\nExtracting all elements using poll() (guaranteed ascending order):");
        while (!minHeap.isEmpty()) {
            Integer polled = minHeap.poll();
            System.out.printf("  Polled: %2d | Remaining head: %-4s | Remaining size: %d%n",
                    polled, (minHeap.peek() == null ? "None" : String.valueOf(minHeap.peek())), minHeap.size());
        }

        // Demonstrating that poll() and peek() return null on an empty queue rather than throwing an exception.
        System.out.println("Calling poll() on empty queue: " + minHeap.poll());
        System.out.println("Calling peek() on empty queue: " + minHeap.peek());
        System.out.println();
    }

    /**
     * Demonstrates Max-Heap behavior using a reverse-order comparator.
     * The largest element is always at the head of the queue.
     */
    private static void demonstrateMaxHeap() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("2. MAX-HEAP DEMONSTRATION (Reverse Ordering)");
        System.out.println("----------------------------------------------------------------");

        // Supplying Collections.reverseOrder() reverses the natural ordering, turning it into a Max-Heap.
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        System.out.println("Inserting elements: 45, 12, 89, 3, 27, 64 using offer()...");
        int[] elementsToInsert = {45, 12, 89, 3, 27, 64};
        for (int val : elementsToInsert) {
            maxHeap.offer(val);
            System.out.printf("  Offered %2d -> Current Head (peek - Maximum): %2d%n",
                    val, maxHeap.peek());
        }

        System.out.println("\nExtracting all elements from Max-Heap using poll() (descending order):");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println("\n");
    }

    /**
     * Demonstrates a Custom Comparator.
     * Orders strings primarily by length (shortest first),
     * and secondarily by natural alphabetical order if lengths are identical.
     */
    private static void demonstrateCustomComparator() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("3. CUSTOM COMPARATOR DEMONSTRATION (By String Length, then Alpha)");
        System.out.println("----------------------------------------------------------------");

        // Constructing a custom comparator:
        // Rule 1: Compare by string length in ascending order.
        // Rule 2: If lengths are equal, tie-break alphabetically.
        Comparator<String> customComparator = Comparator
                .comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder());

        PriorityQueue<String> stringPq = new PriorityQueue<>(customComparator);

        String[] fruits = {
                "Watermelon", "Apple", "Fig", "Strawberry", "Kiwi", "Banana", "Plum", "Peach"
        };

        System.out.println("Offering words into Custom PriorityQueue:");
        for (String fruit : fruits) {
            stringPq.offer(fruit);
            System.out.printf("  Offered '%-10s' (len %2d) -> Current Head (peek): '%s' (len %d)%n",
                    fruit, fruit.length(), stringPq.peek(), stringPq.peek().length());
        }

        System.out.println("\nPolling words in order of priority (Shortest length first, ties broken alphabetically):");
        int rank = 1;
        while (!stringPq.isEmpty()) {
            String item = stringPq.poll();
            System.out.printf("  %d. %-12s (length: %d)%n", rank++, item, item.length());
        }
        System.out.println();
    }

    /**
     * Demonstrates removal operations:
     * - remove(Object o): removes a specific target element anywhere in the queue.
     * - remove(): retrieves and removes the head (throws exception if empty).
     */
    private static void demonstrateRemovalOperations() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("4. REMOVAL OPERATIONS DEMONSTRATION");
        System.out.println("----------------------------------------------------------------");

        PriorityQueue<String> taskQueue = new PriorityQueue<>();
        taskQueue.offer("Task-Low-40");
        taskQueue.offer("Task-High-10");
        taskQueue.offer("Task-Critical-01");
        taskQueue.offer("Task-Medium-20");
        taskQueue.offer("Task-Low-50");

        System.out.println("Initial Task Queue contents:");
        System.out.println("  Head element (peek): " + taskQueue.peek());
        System.out.println("  Queue size: " + taskQueue.size());

        // Demonstrating remove(Object o):
        // Performs a linear search (O(n)) through the backing array to find the item,
        // removes it, and runs a sift operation (O(log n)) to restore the heap property.
        String target = "Task-Medium-20";
        System.out.println("\nAttempting to remove specific element: '" + target + "' using remove(Object)...");
        boolean removed = taskQueue.remove(target);
        System.out.println("  Removal successful? " + removed);
        System.out.println("  Queue contains '" + target + "'? " + taskQueue.contains(target));
        System.out.println("  Current size after removal: " + taskQueue.size());

        // Attempting to remove non-existent element: returns false
        String nonExistent = "Task-NonExistent-99";
        System.out.println("\nAttempting to remove non-existent element: '" + nonExistent + "'...");
        boolean removedNonExistent = taskQueue.remove(nonExistent);
        System.out.println("  Removal successful? " + removedNonExistent);

        // Demonstrating remove():
        // Retrieves and removes the head of this queue.
        // Differs from poll() only in that it throws an exception if the queue is empty.
        System.out.println("\nRemoving head using remove():");
        String removedHead = taskQueue.remove();
        System.out.println("  Removed head: " + removedHead);
        System.out.println("  New head: " + taskQueue.peek());
        System.out.println();
    }

    /**
     * Demonstrates exception-handling differences between Queue API pairs:
     * - peek() vs element()
     * - poll() vs remove()
     * - offer() vs add()
     * - Null element rejection
     */
    private static void demonstrateMethodDifferences() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("5. QUEUE METHOD PAIRS (Special Value vs Exception)");
        System.out.println("----------------------------------------------------------------");

        PriorityQueue<Integer> emptyQueue = new PriorityQueue<>();

        System.out.println("Behavior on an EMPTY PriorityQueue:");

        // 1. peek() vs element()
        System.out.println("  peek() returns: " + emptyQueue.peek() + " (returns null, does not throw)");
        try {
            System.out.print("  element() returns: ");
            emptyQueue.element();
        } catch (NoSuchElementException e) {
            System.out.println("Threw NoSuchElementException as expected!");
        }

        // 2. poll() vs remove()
        System.out.println("  poll() returns: " + emptyQueue.poll() + " (returns null, does not throw)");
        try {
            System.out.print("  remove() returns: ");
            emptyQueue.remove();
        } catch (NoSuchElementException e) {
            System.out.println("Threw NoSuchElementException as expected!");
        }

        // 3. Null Elements
        // PriorityQueue does not permit null elements because comparator operations
        // on null would lead to undefined ordering or NullPointerExceptions.
        System.out.println("\nAttempting to insert null into PriorityQueue:");
        try {
            emptyQueue.offer(null);
        } catch (NullPointerException e) {
            System.out.println("  offer(null) threw NullPointerException (null elements not permitted)!");
        }
    }
}
