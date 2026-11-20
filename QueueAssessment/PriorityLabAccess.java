import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * PriorityLabAccess
 * 
 * Manages access to a computer lab based on role-based priority.
 * Priority hierarchy: Faculty > Graduate > Undergraduate.
 * For individuals with the same role priority, First-Come, First-Served (FCFS)
 * order is preserved using an arrival sequence counter.
 */
public class PriorityLabAccess {

    /**
     * Inner class representing a lab access applicant/student.
     */
    public static class Student {
        private final String name;
        private final String role;
        private final int priorityLevel; // Higher numerical value indicates higher priority
        private final long arrivalOrder;  // Sequence number used as a tie-breaker for FIFO behavior

        /**
         * Constructs a new Student instance.
         *
         * @param name         Name of the individual
         * @param role         Role ("Faculty", "Graduate", "Undergraduate")
         * @param arrivalOrder Unique sequence number indicating time of arrival
         */
        public Student(String name, String role, long arrivalOrder) {
            this.name = name;
            this.role = role;
            this.arrivalOrder = arrivalOrder;
            this.priorityLevel = assignPriority(role);
        }

        /**
         * Maps role name to an integer priority level.
         * Faculty = 3 (Highest)
         * Graduate = 2
         * Undergraduate = 1 (Lowest)
         * Other/Unknown = 0
         *
         * @param role The role string
         * @return Integer priority level
         */
        private int assignPriority(String role) {
            if (role == null) {
                return 0;
            }
            switch (role.trim().toLowerCase()) {
                case "faculty":
                    return 3;
                case "graduate":
                    return 2;
                case "undergraduate":
                    return 1;
                default:
                    return 0;
            }
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public int getPriorityLevel() {
            return priorityLevel;
        }

        public long getArrivalOrder() {
            return arrivalOrder;
        }

        @Override
        public String toString() {
            return String.format("%s (%s, Priority Level: %d, Arrival Order: #%d)",
                    name, role, priorityLevel, arrivalOrder);
        }
    }

    // Underlying priority queue holding the waiting access requests
    private final PriorityQueue<Student> accessQueue;
    // Counter to assign monotonic arrival sequence IDs for tie-breaking
    private long sequenceCounter;

    /**
     * Constructor for PriorityLabAccess.
     * Initializes the PriorityQueue with a custom Comparator.
     */
    public PriorityLabAccess() {
        this.sequenceCounter = 0;

        /*
         * Custom Comparator:
         * 1. Higher priorityLevel comes first (descending order of priorityLevel).
         * 2. If priorityLevels are equal, earlier arrivalOrder comes first (ascending order of arrivalOrder).
         */
        Comparator<Student> labAccessComparator = new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                // Compare priority levels in descending order (higher priority value served first)
                int priorityComparison = Integer.compare(s2.getPriorityLevel(), s1.getPriorityLevel());
                if (priorityComparison != 0) {
                    return priorityComparison;
                }
                // Tie-breaker: Compare arrival order in ascending order (earlier arrival served first)
                return Long.compare(s1.getArrivalOrder(), s2.getArrivalOrder());
            }
        };

        this.accessQueue = new PriorityQueue<>(labAccessComparator);
    }

    /**
     * Enqueues an access request for a student or faculty member.
     *
     * @param name Name of the person requesting access
     * @param role Role of the person ("Faculty", "Graduate", "Undergraduate")
     */
    public void requestAccess(String name, String role) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("[Error] Cannot request access: Student name cannot be null or empty.");
            return;
        }
        if (role == null || role.trim().isEmpty()) {
            System.out.println("[Error] Cannot request access: Role cannot be null or empty.");
            return;
        }

        Student student = new Student(name.trim(), role.trim(), ++sequenceCounter);
        accessQueue.offer(student);
        System.out.printf("[Request Received] %s registered for lab access.%n", student);
    }

    /**
     * Grants access to the highest-priority applicant currently waiting in the queue.
     *
     * @return The Student granted access, or null if the queue is empty
     */
    public Student grantAccess() {
        if (accessQueue.isEmpty()) {
            System.out.println("[Access Denied] Queue is empty. No pending access requests to grant.");
            return null;
        }

        Student granted = accessQueue.poll();
        System.out.printf("[Access Granted] >> %s is granted entry to the computer lab.%n", granted);
        return granted;
    }

    /**
     * Displays all applicants currently waiting in the queue in exact priority order.
     * Note: Standard iteration over a PriorityQueue does not guarantee sorted order,
     * so elements are sorted by the queue's comparator for display purposes.
     */
    public void displayQueue() {
        System.out.println("\n----------------- Current Lab Access Queue -----------------");
        if (accessQueue.isEmpty()) {
            System.out.println("No students currently waiting in the queue.");
            System.out.println("------------------------------------------------------------\n");
            return;
        }

        // Copy elements to a list and sort with the queue's comparator to view in serving order
        List<Student> sortedList = new ArrayList<>(accessQueue);
        sortedList.sort(accessQueue.comparator());

        int rank = 1;
        for (Student student : sortedList) {
            System.out.printf("  %d. %s%n", rank++, student);
        }
        System.out.printf("Total pending requests: %d%n", accessQueue.size());
        System.out.println("------------------------------------------------------------\n");
    }

    /**
     * Returns the number of students currently waiting in the queue.
     *
     * @return Number of queued applicants
     */
    public int getQueueSize() {
        return accessQueue.size();
    }

    /**
     * Checks if the queue has any pending requests.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return accessQueue.isEmpty();
    }

    /**
     * Main method demonstrating the functionality of PriorityLabAccess.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("       PRIORITY-BASED LAB ACCESS SYSTEM SIMULATION          ");
        System.out.println(" Priority: Faculty (High) > Graduate (Medium) > Undergraduate (Low)");
        System.out.println("============================================================\n");

        PriorityLabAccess lab = new PriorityLabAccess();

        // 1. Multiple requests submitted in varying arrival order
        System.out.println("--- Submitting Initial Access Requests ---");
        lab.requestAccess("Alice Johnson", "Undergraduate");
        lab.requestAccess("Bob Smith", "Graduate");
        lab.requestAccess("Dr. Charles Xavier", "Faculty");
        lab.requestAccess("David Lee", "Undergraduate"); // Arrived after Alice, same priority
        lab.requestAccess("Elena Rostova", "Graduate");   // Arrived after Bob, same priority
        lab.requestAccess("Prof. Minerva McGonagall", "Faculty"); // Arrived after Dr. Charles

        // 2. Display initial queue state
        lab.displayQueue();

        // 3. Grant access to top 3 applicants (Expect both Faculty first, then first Graduate)
        System.out.println("--- Granting Access (Batch 1) ---");
        lab.grantAccess(); // Expected: Dr. Charles Xavier (Faculty, #3)
        lab.grantAccess(); // Expected: Prof. Minerva McGonagall (Faculty, #6)
        lab.grantAccess(); // Expected: Bob Smith (Graduate, #2)

        // 4. Display remaining queue state
        lab.displayQueue();

        // 5. New requests arrive while queue is active
        System.out.println("--- New High-Priority and Standard Requests Arrive ---");
        lab.requestAccess("Dean Thomas", "Undergraduate");
        lab.requestAccess("Dr. Alan Turing", "Faculty"); // Higher priority than existing waiting members

        // 6. Display queue to demonstrate dynamic reordering
        lab.displayQueue();

        // 7. Grant access to all remaining students
        System.out.println("--- Granting Access to All Remaining Applicants ---");
        while (!lab.isEmpty()) {
            lab.grantAccess();
        }

        // 8. Attempt granting access when queue is empty (Edge case test)
        System.out.println("\n--- Testing Edge Case: Grant Access on Empty Queue ---");
        lab.grantAccess();

        // Final queue display
        lab.displayQueue();
        System.out.println("Simulation complete.");
    }
}