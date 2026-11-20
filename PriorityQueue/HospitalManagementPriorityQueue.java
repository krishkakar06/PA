import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * HospitalManagementPriorityQueue
 * 
 * A Hospital Triage and Patient Management System implemented using Java's PriorityQueue.
 * In emergency triage, patients are prioritized based on medical urgency:
 * - Priority 1: Critical / Resuscitation (highest priority, immediate attention)
 * - Priority 2: Emergent
 * - Priority 3: Urgent
 * - Priority 4: Less Urgent
 * - Priority 5: Routine / Non-urgent (lowest priority)
 * 
 * When two patients have the same priority level, a First-In, First-Out (FIFO)
 * tie-breaker is applied using an arrival sequence counter.
 */
public class HospitalManagementPriorityQueue {

    /**
     * Represents a patient admitted to the hospital triage system.
     */
    public static class Patient implements Comparable<Patient> {
        private final String name;
        private final int priority; // 1 = Critical, 5 = Routine
        private final long arrivalOrder; // Monotonically increasing sequence for FIFO tie-breaking

        // Global sequence counter to assign sequential arrival order
        private static final AtomicLong SEQUENCE_GENERATOR = new AtomicLong(0);

        /**
         * Constructs a new Patient with the specified name and triage priority.
         *
         * @param name     the patient's name
         * @param priority the triage priority (1 to 5)
         */
        public Patient(String name, int priority) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Patient name cannot be null or empty.");
            }
            if (priority < 1 || priority > 5) {
                throw new IllegalArgumentException("Priority must be between 1 (Critical) and 5 (Routine).");
            }
            this.name = name.trim();
            this.priority = priority;
            this.arrivalOrder = SEQUENCE_GENERATOR.getAndIncrement();
        }

        public String getName() {
            return name;
        }

        public int getPriority() {
            return priority;
        }

        public long getArrivalOrder() {
            return arrivalOrder;
        }

        /**
         * Returns a descriptive string representing the triage severity level.
         */
        public String getPriorityDescription() {
            switch (priority) {
                case 1: return "Critical (Immediate)";
                case 2: return "Emergent (Very Urgent)";
                case 3: return "Urgent";
                case 4: return "Less Urgent (Standard)";
                case 5: return "Routine (Non-urgent)";
                default: return "Unknown";
            }
        }

        /**
         * Defines natural ordering for patients:
         * 1. Primary order: Numerical priority ascending (1 = Critical comes before 5 = Routine).
         * 2. Secondary order: Arrival order ascending (FIFO tie-breaker for identical priorities).
         */
        @Override
        public int compareTo(Patient other) {
            if (other == null) {
                return 1;
            }
            // Primary comparison: lower priority number indicates higher medical urgency
            int priorityComparison = Integer.compare(this.priority, other.priority);
            if (priorityComparison != 0) {
                return priorityComparison;
            }
            // Secondary comparison: earlier arrival gets treated first
            return Long.compare(this.arrivalOrder, other.arrivalOrder);
        }

        @Override
        public String toString() {
            return String.format("[ID #%03d] %-16s | Priority %d: %s", 
                    arrivalOrder + 1, name, priority, getPriorityDescription());
        }
    }

    // Min-heap PriorityQueue to store and manage patients by priority
    private final PriorityQueue<Patient> patientQueue;

    /**
     * Initializes an empty hospital patient priority queue.
     */
    public HospitalManagementPriorityQueue() {
        // PriorityQueue uses the natural ordering defined in Patient.compareTo()
        this.patientQueue = new PriorityQueue<>();
    }

    /**
     * Adds a new patient object to the priority queue.
     *
     * @param patient the patient to admit
     */
    public void addPatient(Patient patient) {
        if (patient == null) {
            System.out.println("[ERROR] Cannot admit a null patient record.");
            return;
        }
        patientQueue.offer(patient);
        System.out.println("[ADMITTED] " + patient);
    }

    /**
     * Overloaded convenience method to add a patient by name and priority.
     *
     * @param name     the patient's name
     * @param priority the triage priority (1 to 5)
     */
    public void addPatient(String name, int priority) {
        try {
            Patient patient = new Patient(name, priority);
            addPatient(patient);
        } catch (IllegalArgumentException ex) {
            System.out.println("[REJECTED] Failed to admit patient: " + ex.getMessage());
        }
    }

    /**
     * Retrieves and removes the next patient with the highest priority (lowest priority value).
     *
     * @return the next patient to be treated, or null if the queue is empty
     */
    public Patient getNextPatient() {
        if (patientQueue.isEmpty()) {
            System.out.println("[NOTICE] No patients currently waiting in the queue.");
            return null;
        }
        Patient nextPatient = patientQueue.poll();
        System.out.println("[TREATING] Processing: " + nextPatient);
        return nextPatient;
    }

    /**
     * Displays all patients currently waiting in the priority queue.
     * 
     * NOTE ON JAVA'S PRIORITY QUEUE:
     * Standard iteration (e.g. for-each) over a java.util.PriorityQueue visits elements 
     * in internal binary heap order, NOT in sorted priority order.
     * To display the exact treatment sequence without mutating the original queue,
     * this method creates a temporary copy of the queue and polls elements in order.
     */
    public void displayQueue() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("CURRENT WAITING QUEUE (Total Patients: " + patientQueue.size() + ")");
        System.out.println("--------------------------------------------------------------------------------");

        if (patientQueue.isEmpty()) {
            System.out.println("The waiting queue is currently empty.");
            System.out.println("--------------------------------------------------------------------------------");
            return;
        }

        // Create a shallow copy of the PriorityQueue to inspect in priority order
        PriorityQueue<Patient> copyQueue = new PriorityQueue<>(patientQueue);
        int position = 1;

        while (!copyQueue.isEmpty()) {
            Patient p = copyQueue.poll();
            System.out.printf(" %2d. %s%n", position++, p);
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Returns the number of patients currently waiting in the queue.
     *
     * @return count of waiting patients
     */
    public int getQueueSize() {
        return patientQueue.size();
    }

    /**
     * Checks if the queue has no waiting patients.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return patientQueue.isEmpty();
    }

    /**
     * Demonstrates the functionality of the Hospital Management Priority Queue.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("          HOSPITAL EMERGENCY ROOM TRIAGE SYSTEM (PRIORITY QUEUE)                ");
        System.out.println("================================================================================");
        System.out.println("Triage Severity Scale: 1 = Critical, 2 = Emergent, 3 = Urgent, 4 = Less Urgent, 5 = Routine\n");

        HospitalManagementPriorityQueue hospital = new HospitalManagementPriorityQueue();

        // 1. Admit patients in various arrival orders and priority levels
        System.out.println(">>> STEP 1: Admitting Patients to Triage Queue...\n");
        hospital.addPatient("John Doe", 5);       // Routine (e.g., minor scrape)
        hospital.addPatient("Alice Smith", 2);     // Emergent (e.g., severe asthma attack)
        hospital.addPatient("Robert Brown", 1);   // Critical (e.g., cardiac arrest)
        hospital.addPatient("Emily Davis", 4);    // Less Urgent (e.g., mild fever)
        hospital.addPatient("Michael Wilson", 3); // Urgent (e.g., bone fracture)
        hospital.addPatient("Sarah Connor", 1);   // Critical (e.g., severe trauma, tie-breaker test)
        hospital.addPatient("David Clark", 2);    // Emergent (tie-breaker test for priority 2)
        System.out.println();

        // 2. Display the waiting queue in priority order
        System.out.println(">>> STEP 2: Displaying Triage Queue in Treatment Priority Order...\n");
        hospital.displayQueue();
        System.out.println();

        // 3. Process/treat patients one by one according to priority
        System.out.println(">>> STEP 3: Doctors Treating Patients in Priority Order...\n");
        while (!hospital.isEmpty()) {
            hospital.getNextPatient();
        }
        System.out.println();

        // 4. Verify queue status after all patients are treated
        System.out.println(">>> STEP 4: Checking Queue Status After Processing...\n");
        hospital.displayQueue();
        System.out.println();

        // 5. Attempting to get the next patient from an empty queue
        System.out.println(">>> STEP 5: Testing Edge Case - Fetching From Empty Queue...\n");
        Patient p = hospital.getNextPatient();
        System.out.println("Returned patient reference: " + p);
        System.out.println();

        // 6. Testing input validation
        System.out.println(">>> STEP 6: Testing Input Validation...\n");
        hospital.addPatient("Invalid Patient", 7); // Out of bounds priority
        hospital.addPatient("", 3);                // Empty name
        System.out.println();

        System.out.println("================================================================================");
        System.out.println("                 DEMONSTRATION COMPLETED SUCCESSFULLY                          ");
        System.out.println("================================================================================");
    }
}