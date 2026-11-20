import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Problem: LeetCode 1700 - Number of Students Unable to Eat Lunch
 * 
 * Description:
 * The school cafeteria offers circular and square sandwiches at lunch break,
 * represented by 0 and 1, respectively. Students are queued up with their preferences.
 * Sandwiches are stacked such that sandwiches[0] is at the top of the stack.
 *
 * Simulation Rules:
 * 1. If the student at the front of the queue prefers the sandwich at the top of the stack,
 *    they take it and leave the queue. The sandwich is removed from the stack.
 * 2. If not, the student leaves the sandwich and moves to the rear of the queue.
 * 3. The process continues until none of the students remaining in the queue want the
 *    sandwich on top of the stack.
 * 
 * Approach:
 * - Use a Queue<Integer> to represent the line of students (FIFO).
 * - Use a Stack<Integer> to represent the stack of sandwiches (LIFO).
 *   Since sandwiches[0] represents the top of the stack, we push the elements from
 *   the end of the array (sandwiches.length - 1 down to 0) into the stack so that
 *   sandwiches[0] is popped first.
 * - Track the number of consecutive students who cannot eat the current top sandwich.
 *   If the number of consecutive rotations equals the queue's current size, it indicates
 *   that a full cycle has completed without anyone taking the top sandwich. At this point,
 *   deadlock is reached and no further students can ever eat.
 * 
 * Time Complexity: O(N^2) in the worst case for this simulation (each sandwich can cause up to N rotations).
 * Space Complexity: O(N) to store students in the Queue and sandwiches in the Stack.
 */
public class NumberOfStudentsUnableToEatLunch {

    /**
     * Calculates the number of students unable to eat lunch.
     *
     * @param students   Array representing student preferences (0 = circular, 1 = square).
     * @param sandwiches Array representing the sandwiches in stack order (sandwiches[0] is the top).
     * @return The number of students who cannot eat lunch.
     */
    public int countStudents(int[] students, int[] sandwiches) {
        // Queue to maintain the order of students waiting for lunch (FIFO).
        Queue<Integer> studentQueue = new LinkedList<>();
        for (int student : students) {
            studentQueue.offer(student);
        }

        // Stack to maintain the sandwiches (LIFO).
        // sandwiches[0] is at the top of the stack, so we push elements in reverse order
        // (from sandwiches.length - 1 down to 0) so that sandwiches[0] is at the top.
        Stack<Integer> sandwichStack = new Stack<>();
        for (int i = sandwiches.length - 1; i >= 0; i--) {
            sandwichStack.push(sandwiches[i]);
        }

        // Tracks how many consecutive students have examined the top sandwich and declined it.
        int consecutiveRefusals = 0;

        // Continue simulation as long as:
        // 1. There are students still waiting in the queue.
        // 2. Not all current students in the queue have consecutively refused the top sandwich.
        while (!studentQueue.isEmpty() && consecutiveRefusals < studentQueue.size()) {
            // Check if the student at the front of the queue wants the top sandwich.
            if (studentQueue.peek().equals(sandwichStack.peek())) {
                // The student takes the sandwich and leaves the queue.
                studentQueue.poll();
                sandwichStack.pop();

                // Reset the refusal counter since the sandwich stack changed
                // and everyone remaining gets another chance to check the new top sandwich.
                consecutiveRefusals = 0;
            } else {
                // The student dislikes the top sandwich and moves to the back of the queue.
                studentQueue.offer(studentQueue.poll());
                consecutiveRefusals++;
            }
        }

        // The remaining size of the queue is the number of students unable to eat.
        return studentQueue.size();
    }

    /**
     * Main method providing test cases to verify the implementation.
     */
    public static void main(String[] args) {
        NumberOfStudentsUnableToEatLunch solver = new NumberOfStudentsUnableToEatLunch();

        // Test Case 1:
        // students = [1, 1, 0, 0], sandwiches = [0, 1, 0, 1]
        // Expected Output: 0
        int[] students1 = {1, 1, 0, 0};
        int[] sandwiches1 = {0, 1, 0, 1};
        int result1 = solver.countStudents(students1, sandwiches1);
        System.out.println("Test Case 1 Result: " + result1 + " (Expected: 0)");

        // Test Case 2:
        // students = [1, 1, 1, 0, 0, 1], sandwiches = [1, 0, 0, 0, 1, 1]
        // Expected Output: 3
        int[] students2 = {1, 1, 1, 0, 0, 1};
        int[] sandwiches2 = {1, 0, 0, 0, 1, 1};
        int result2 = solver.countStudents(students2, sandwiches2);
        System.out.println("Test Case 2 Result: " + result2 + " (Expected: 3)");
    }
}
