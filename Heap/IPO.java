import java.util.PriorityQueue;

/**
 * LeetCode 502 - IPO
 * 
 * Problem Statement:
 * Given n projects where profits[i] is the pure profit of the i-th project and
 * capital[i] is the minimum capital required to start it. Initially, you have
 * capital w. When you finish a project, you obtain its pure profit, which is
 * added to your total capital. You want to finish at most k distinct projects
 * to maximize your total capital.
 *
 * Approach:
 * Greedy strategy using Two Priority Queues (Heaps):
 * 1. Min-Heap ordered by capital:
 *    Stores all projects that are not yet affordable, sorted in ascending order
 *    of their required capital.
 * 2. Max-Heap ordered by profit:
 *    Stores all currently affordable projects (capital <= w), sorted in descending
 *    order of their profits.
 *
 * In each of up to k iterations:
 * - Transfer all projects whose required capital <= w from the min-heap to the max-heap.
 * - If the max-heap is empty, we cannot afford any further projects, so we break early.
 * - Greedily select the project with the highest profit from the max-heap and add its
 *   profit to our current capital w.
 *
 * Complexity Analysis:
 * - Time Complexity: O(N log N + K log N)
 *   - Inserting all N projects into the min-heap takes O(N log N).
 *   - Each project is pushed to and popped from the max-heap at most once: O(N log N).
 *   - We perform at most K extractions from the max-heap: O(K log N).
 *   - Total Time: O(N log N + K log N), which easily fits within constraints (N, K <= 10^5).
 * - Space Complexity: O(N)
 *   - O(N) to store the projects in the priority queues.
 */
public class IPO {

    /**
     * Inner class representing a project with required capital and expected profit.
     */
    private static class Project {
        final int capital;
        final int profit;

        Project(int capital, int profit) {
            this.capital = capital;
            this.profit = profit;
        }
    }

    /**
     * Finds the maximized capital after completing at most k distinct projects.
     *
     * @param k       The maximum number of distinct projects that can be finished.
     * @param w       The initial capital.
     * @param profits An array representing the pure profit of each project.
     * @param capital An array representing the minimum capital needed for each project.
     * @return The maximized total capital after finishing at most k projects.
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;

        // Min-heap ordered by capital (ascending) to keep track of unstarted projects.
        PriorityQueue<Project> minCapitalHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.capital, b.capital)
        );

        // Max-heap ordered by profit (descending) to greedily pick the most profitable affordable project.
        PriorityQueue<Project> maxProfitHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.profit, a.profit)
        );

        // Add all available projects to the min-heap.
        for (int i = 0; i < n; i++) {
            minCapitalHeap.offer(new Project(capital[i], profits[i]));
        }

        // We can complete at most k projects.
        for (int i = 0; i < k; i++) {
            // Unlock all projects that can be afforded with the current capital w.
            while (!minCapitalHeap.isEmpty() && minCapitalHeap.peek().capital <= w) {
                maxProfitHeap.offer(minCapitalHeap.poll());
            }

            // If no projects can be afforded, stop and return the current capital.
            if (maxProfitHeap.isEmpty()) {
                break;
            }

            // Greedily pick the project with the highest profit among all affordable projects.
            Project bestProject = maxProfitHeap.poll();
            w += bestProject.profit;
        }

        return w;
    }
}
