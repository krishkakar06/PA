import java.util.ArrayDeque;
import java.util.Deque;

/**
 * DequeOperations demonstrates the core operations of the Double-Ended Queue (Deque)
 * interface using Java's ArrayDeque implementation.
 *
 * A Deque (pronounced "deck") is a linear collection that supports element insertion,
 * removal, and inspection at both ends (head and tail).
 *
 * ArrayDeque is a resizable-array implementation of the Deque interface:
 * - It has no capacity restrictions and grows dynamically as needed.
 * - It is generally faster than Stack when used as a stack (LIFO) and faster than
 *   LinkedList when used as a queue (FIFO), due to better cache locality and lower memory overhead.
 * - It does not allow null elements.
 * - It is not thread-safe without external synchronization.
 *
 * Operations demonstrated:
 * 1. addFirst(E e)   - Inserts element at the front.
 * 2. addLast(E e)    - Inserts element at the end.
 * 3. peekFirst()     - Inspects the first element without removing it.
 * 4. peekLast()      - Inspects the last element without removing it.
 * 5. removeFirst()   - Retrieves and removes the first element.
 * 6. removeLast()    - Retrieves and removes the last element.
 * 7. size()          - Returns the number of elements in the deque.
 * 8. isEmpty()       - Checks whether the deque contains any elements.
 */
public class DequeOperations {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       Java Deque Demonstration (ArrayDeque)      ");
        System.out.println("==================================================\n");

        // ---------------------------------------------------------------------
        // Initialization
        // ---------------------------------------------------------------------
        // We declare the reference variable using the Deque interface to adhere
        // to the principle of programming to an interface rather than implementation.
        Deque<String> deque = new ArrayDeque<>();
        System.out.println("[INITIAL STATE]");
        System.out.println("Created empty ArrayDeque: " + deque);

        // ---------------------------------------------------------------------
        // 1. isEmpty() and size() on an empty Deque
        // ---------------------------------------------------------------------
        // isEmpty() returns true if the collection contains no elements.
        // size() returns the total number of elements currently stored.
        System.out.println("\n--- Checking Initial Capacity & Emptiness ---");
        System.out.println("deque.isEmpty() -> " + deque.isEmpty()); // Expected: true
        System.out.println("deque.size()    -> " + deque.size());    // Expected: 0

        // ---------------------------------------------------------------------
        // 2. addFirst(E e) - Insertion at the front (head)
        // ---------------------------------------------------------------------
        // Inserts the specified element at the front of this deque.
        // Throws NullPointerException if the element is null.
        System.out.println("\n--- Demonstrating addFirst() ---");
        System.out.println("Adding 'Bravo' to front: deque.addFirst(\"Bravo\")");
        deque.addFirst("Bravo");
        System.out.println("Current Deque: " + deque);

        System.out.println("Adding 'Alpha' to front: deque.addFirst(\"Alpha\")");
        deque.addFirst("Alpha");
        System.out.println("Current Deque: " + deque);
        // Notice 'Alpha' was placed ahead of 'Bravo'.

        // ---------------------------------------------------------------------
        // 3. addLast(E e) - Insertion at the end (tail)
        // ---------------------------------------------------------------------
        // Inserts the specified element at the end of this deque.
        // This is functionally equivalent to add(E e).
        System.out.println("\n--- Demonstrating addLast() ---");
        System.out.println("Adding 'Charlie' to end: deque.addLast(\"Charlie\")");
        deque.addLast("Charlie");
        System.out.println("Current Deque: " + deque);

        System.out.println("Adding 'Delta' to end: deque.addLast(\"Delta\")");
        deque.addLast("Delta");
        System.out.println("Current Deque: " + deque);
        // Deque order from head to tail: [Alpha, Bravo, Charlie, Delta]

        // ---------------------------------------------------------------------
        // 4. size() and isEmpty() with populated Deque
        // ---------------------------------------------------------------------
        System.out.println("\n--- Checking Status After Additions ---");
        System.out.println("deque.isEmpty() -> " + deque.isEmpty()); // Expected: false
        System.out.println("deque.size()    -> " + deque.size());    // Expected: 4

        // ---------------------------------------------------------------------
        // 5. peekFirst() - Inspect the head without removal
        // ---------------------------------------------------------------------
        // Retrieves, but does not remove, the first element of this deque,
        // or returns null if this deque is empty.
        // (Contrast with getFirst(), which throws NoSuchElementException if empty).
        System.out.println("\n--- Demonstrating peekFirst() ---");
        String firstElement = deque.peekFirst();
        System.out.println("deque.peekFirst() returned: " + firstElement);
        System.out.println("Deque unchanged: " + deque);

        // ---------------------------------------------------------------------
        // 6. peekLast() - Inspect the tail without removal
        // ---------------------------------------------------------------------
        // Retrieves, but does not remove, the last element of this deque,
        // or returns null if this deque is empty.
        // (Contrast with getLast(), which throws NoSuchElementException if empty).
        System.out.println("\n--- Demonstrating peekLast() ---");
        String lastElement = deque.peekLast();
        System.out.println("deque.peekLast() returned: " + lastElement);
        System.out.println("Deque unchanged: " + deque);

        // ---------------------------------------------------------------------
        // 7. removeFirst() - Removal from the front (head)
        // ---------------------------------------------------------------------
        // Retrieves and removes the first element of this deque.
        // Throws NoSuchElementException if the deque is empty.
        // (Contrast with pollFirst(), which returns null if empty).
        System.out.println("\n--- Demonstrating removeFirst() ---");
        String removedFirst = deque.removeFirst();
        System.out.println("deque.removeFirst() removed: " + removedFirst);
        System.out.println("Current Deque: " + deque);

        // ---------------------------------------------------------------------
        // 8. removeLast() - Removal from the end (tail)
        // ---------------------------------------------------------------------
        // Retrieves and removes the last element of this deque.
        // Throws NoSuchElementException if the deque is empty.
        // (Contrast with pollLast(), which returns null if empty).
        System.out.println("\n--- Demonstrating removeLast() ---");
        String removedLast = deque.removeLast();
        System.out.println("deque.removeLast() removed: " + removedLast);
        System.out.println("Current Deque: " + deque);

        // ---------------------------------------------------------------------
        // Draining remaining elements to observe boundary state
        // ---------------------------------------------------------------------
        System.out.println("\n--- Emptying the Deque ---");
        System.out.println("Removing remaining elements:");
        System.out.println("deque.removeFirst() removed: " + deque.removeFirst());
        System.out.println("deque.removeLast() removed:  " + deque.removeLast());
        System.out.println("Current Deque: " + deque);

        // ---------------------------------------------------------------------
        // Final verification of empty state
        // ---------------------------------------------------------------------
        System.out.println("\n--- Final State ---");
        System.out.println("deque.isEmpty()   -> " + deque.isEmpty()); // Expected: true
        System.out.println("deque.size()      -> " + deque.size());    // Expected: 0
        System.out.println("deque.peekFirst() -> " + deque.peekFirst()); // Safe: returns null
        System.out.println("deque.peekLast()  -> " + deque.peekLast());  // Safe: returns null

        System.out.println("\n==================================================");
        System.out.println("             Demonstration Completed              ");
        System.out.println("==================================================");
    }
}
