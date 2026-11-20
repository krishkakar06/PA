import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * ServiceDeskQueue simulates a customer support / IT service desk ticketing system
 * using a standard FIFO (First-In, First-Out) Queue backed by a LinkedList.
 *
 * Key Feature: Lazy Cancellation
 * In a standard queue data structure, arbitrary removal from the middle of the queue
 * is not supported or violates FIFO queue mechanics. Instead, tickets can be cancelled
 * at any time by marking them in a set of cancelled IDs. During processing, the queue
 * is traversed in its natural order, automatically skipping any cancelled tickets.
 */
public class ServiceDeskQueue {

    /**
     * The primary FIFO queue holding incoming ticket IDs in the order they arrived.
     * Implemented using a LinkedList<String> as required.
     */
    private final LinkedList<String> queue;

    /**
     * A Set storing IDs of tickets that have been cancelled.
     * This provides O(1) average-time lookups to check if a dequeued ticket is cancelled.
     */
    private final Set<String> cancelledTickets;

    /**
     * Constructs a new, empty ServiceDeskQueue.
     */
    public ServiceDeskQueue() {
        this.queue = new LinkedList<>();
        this.cancelledTickets = new HashSet<>();
    }

    /**
     * Adds a new ticket to the rear (end) of the service desk queue.
     *
     * @param id The unique identifier of the ticket (e.g., "TICK-101").
     */
    public void addTicket(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("[ERROR] Cannot add ticket: Ticket ID must not be null or empty.");
            return;
        }

        // Add the ticket to the end of the FIFO queue
        queue.offer(id);

        // In case an ID is reused or re-submitted after a cancellation, ensure it is active
        cancelledTickets.remove(id);

        System.out.println("[ADDED] Ticket " + id + " has been added to the queue.");
    }

    /**
     * Cancels an existing ticket currently waiting in the queue.
     * Uses lazy cancellation: marks the ticket ID in the cancelled set so that
     * it will be skipped when reached during processing.
     *
     * @param id The unique identifier of the ticket to cancel.
     * @return true if the ticket was successfully marked as cancelled, false otherwise.
     */
    public boolean cancelTicket(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("[ERROR] Cannot cancel ticket: Ticket ID must not be null or empty.");
            return false;
        }

        // Check if the ticket is present in the waiting queue
        if (!queue.contains(id)) {
            System.out.println("[WARNING] Cannot cancel ticket " + id + ": Ticket not found in queue (may already be processed or never existed).");
            return false;
        }

        // Check if the ticket has already been marked as cancelled
        if (cancelledTickets.contains(id)) {
            System.out.println("[INFO] Ticket " + id + " is already marked as cancelled.");
            return false;
        }

        // Mark the ticket as cancelled
        cancelledTickets.add(id);
        System.out.println("[CANCELLED] Ticket " + id + " has been marked as cancelled.");
        return true;
    }

    /**
     * Processes the next available, valid ticket in the queue.
     * Traverses the queue in FIFO order, automatically skipping and discarding
     * any tickets that were previously cancelled.
     *
     * @return The ID of the processed ticket, or null if no valid tickets remain.
     */
    public String processNext() {
        while (!queue.isEmpty()) {
            // Retrieve and remove the head of the queue
            String nextTicket = queue.poll();

            // Check if this ticket was marked as cancelled
            if (cancelledTickets.contains(nextTicket)) {
                // Remove from the cancelled set to free memory
                cancelledTickets.remove(nextTicket);
                System.out.println("[SKIPPED] Ticket " + nextTicket + " was cancelled. Skipping to the next ticket...");
                // Continue loop to examine the next ticket in line
                continue;
            }

            // Valid active ticket found: process it
            System.out.println("[PROCESSED] Successfully processed ticket: " + nextTicket);
            return nextTicket;
        }

        // Reached when the queue is empty or all remaining tickets were cancelled
        System.out.println("[EMPTY] No tickets available to process in the queue.");
        return null;
    }

    /**
     * Displays the current state of the service desk queue from front to rear,
     * highlighting which tickets are active and which are cancelled.
     */
    public void displayQueue() {
        System.out.println("\n----------------- Service Desk Queue Status -----------------");
        if (queue.isEmpty()) {
            System.out.println("The queue is currently empty.");
            System.out.println("-------------------------------------------------------------");
            return;
        }

        System.out.println("Queue (Front to Rear) - Total items in queue: " + queue.size());
        int position = 1;
        int activeCount = 0;
        int cancelledCount = 0;

        for (String id : queue) {
            if (cancelledTickets.contains(id)) {
                System.out.printf("  %d. %-15s [CANCELLED - will be skipped]\n", position, id);
                cancelledCount++;
            } else {
                System.out.printf("  %d. %-15s [ACTIVE - waiting]\n", position, id);
                activeCount++;
            }
            position++;
        }
        System.out.println("Summary: " + activeCount + " Active, " + cancelledCount + " Cancelled");
        System.out.println("-------------------------------------------------------------\n");
    }

    /**
     * Returns the total number of items currently in the underlying queue
     * (including cancelled tickets awaiting lazy removal).
     *
     * @return Total queue size.
     */
    public int getQueueSize() {
        return queue.size();
    }

    /**
     * Checks whether the underlying queue has no items.
     *
     * @return true if queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Main method demonstrating realistic usage of the ServiceDeskQueue:
     * 1. Submitting incoming tickets
     * 2. Inspecting the queue state
     * 3. Processing tickets in FIFO order
     * 4. Handling ticket cancellations (including edge cases)
     * 5. Demonstrating automatic skipping of cancelled tickets during processing
     * 6. Handling an empty queue
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("       WELCOME TO SERVICE DESK TICKET MANAGEMENT SYSTEM      ");
        System.out.println("=============================================================");\n

        ServiceDeskQueue desk = new ServiceDeskQueue();

        // -------------------------------------------------------------
        // Step 1: Customers create and submit tickets
        // -------------------------------------------------------------
        System.out.println(">>> 1. Adding initial tickets to the queue:");
        desk.addTicket("TICK-101"); // Network connectivity issue
        desk.addTicket("TICK-102"); // Password reset request
        desk.addTicket("TICK-103"); // Software license renewal
        desk.addTicket("TICK-104"); // Hardware replacement
        desk.addTicket("TICK-105"); // VPN access setup

        // Display current queue state
        desk.displayQueue();

        // -------------------------------------------------------------
        // Step 2: Agent processes the first ticket (FIFO order)
        // -------------------------------------------------------------
        System.out.println(">>> 2. Agent processes the next ticket:");
        desk.processNext(); // Should process TICK-101

        desk.displayQueue();

        // -------------------------------------------------------------
        // Step 3: Customers cancel their requests before being served
        // -------------------------------------------------------------
        System.out.println(">>> 3. Users cancel tickets:");
        // TICK-102 self-resolved password issue
        desk.cancelTicket("TICK-102");

        // TICK-104 hardware issue was a false alarm
        desk.cancelTicket("TICK-104");

        // Edge case: Attempting to cancel already cancelled ticket
        System.out.println("\n>>> Testing Edge Case: Cancelling an already cancelled ticket:");
        desk.cancelTicket("TICK-102");

        // Edge case: Attempting to cancel an already processed ticket
        System.out.println("\n>>> Testing Edge Case: Cancelling an already processed ticket:");
        desk.cancelTicket("TICK-101");

        // Edge case: Attempting to cancel a non-existent ticket
        System.out.println("\n>>> Testing Edge Case: Cancelling a non-existent ticket:");
        desk.cancelTicket("TICK-999");

        // Edge case: Attempting to cancel with invalid ID
        System.out.println("\n>>> Testing Edge Case: Cancelling with null / blank ID:");
        desk.cancelTicket("");

        // Display queue reflecting cancellations
        desk.displayQueue();

        // -------------------------------------------------------------
        // Step 4: A new urgent ticket arrives
        // -------------------------------------------------------------
        System.out.println(">>> 4. A new ticket arrives at the service desk:");
        desk.addTicket("TICK-106"); // Urgent database outage

        desk.displayQueue();

        // -------------------------------------------------------------
        // Step 5: Agent processes all tickets in order, skipping cancelled ones
        // -------------------------------------------------------------
        System.out.println(">>> 5. Processing remaining tickets in order:");
        // Expected: TICK-102 is skipped, TICK-103 is processed
        System.out.println("\n--- Processing Step A ---");
        desk.processNext();

        // Expected: TICK-104 is skipped, TICK-105 is processed
        System.out.println("\n--- Processing Step B ---");
        desk.processNext();

        // Expected: TICK-106 is processed
        System.out.println("\n--- Processing Step C ---");
        desk.processNext();

        // -------------------------------------------------------------
        // Step 6: Attempting to process when queue is empty
        // -------------------------------------------------------------
        System.out.println("\n>>> 6. Attempting to process when queue is empty:");
        desk.processNext();

        desk.displayQueue();

        System.out.println("=============================================================");
        System.out.println("             SERVICE DESK SIMULATION COMPLETED               ");
        System.out.println("=============================================================");
    }
}
