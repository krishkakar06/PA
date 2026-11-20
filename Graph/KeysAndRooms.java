import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * LeetCode 841: Keys and Rooms
 * 
 * Problem Description:
 * There are n rooms labeled from 0 to n - 1 and all the rooms are locked, except for room 0.
 * Your goal is to visit all the rooms. However, you cannot enter a locked room without having its key.
 * When you visit a room, you may find a set of distinct keys in it. Each key has a number on it,
 * denoting which room it unlocks, and you can take all of them with you to unlock the other rooms.
 * Given an array `rooms` where `rooms[i]` is the list of keys that you can obtain if you visited room `i`,
 * return true if you can visit all the rooms, or false otherwise.
 */
public class KeysAndRooms {

    /**
     * Determines if all rooms can be visited starting from room 0.
     * 
     * Algorithmic Approach (Breadth-First Search - BFS):
     * 1. Modeling as a Graph:
     *    - Each room represents a node in a directed graph.
     *    - Each key found inside room `u` to room `v` represents a directed edge `u -> v`.
     *    - The problem reduces to determining if all nodes are reachable from source node 0.
     * 
     * 2. Traversal Mechanism:
     *    - Start at room 0, marking it as visited and pushing it into a FIFO queue.
     *    - Maintain a count of visited rooms (initialized to 1 for room 0).
     *    - While the queue is not empty:
     *      a. Dequeue the current room.
     *      b. For each key in the current room, check if the corresponding destination room
     *         has already been visited.
     *      c. If unvisited:
     *         - Mark the room as visited immediately (to avoid duplicate enqueueing).
     *         - Increment the count of visited rooms.
     *         - Enqueue the destination room.
     * 
     * 3. Termination & Verification:
     *    - If at any point visitedCount reaches n, we can even early-exit with true.
     *    - Once the traversal finishes, return true if visitedCount == n, else false.
     * 
     * Complexity Analysis:
     * - Time Complexity: O(N + K), where N is the number of rooms and K is the total number of keys
     *   across all rooms (K = sum of rooms[i].length). Each room is visited at most once, and each
     *   key is inspected at most once.
     * - Space Complexity: O(N) auxiliary space for the `visited` array and the BFS `queue`.
     * 
     * @param rooms A list of lists of integers where rooms.get(i) is the list of keys in room i.
     * @return true if every room can be visited; false otherwise.
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        // Base validation: An empty or single-room building is trivially visitable.
        if (rooms == null || rooms.isEmpty()) {
            return false;
        }

        int n = rooms.size();
        if (n == 1) {
            return true;
        }

        // boolean array to track visited rooms and prevent processing cycles
        boolean[] visited = new boolean[n];

        // Queue for Breadth-First Search traversal
        Queue<Integer> queue = new ArrayDeque<>();

        // Start BFS traversal from room 0
        visited[0] = true;
        queue.offer(0);

        // Counter to track the total number of distinct rooms visited
        int count = 1;

        while (!queue.isEmpty()) {
            int currentRoom = queue.poll();

            // Inspect all keys available in the current room
            List<Integer> keys = rooms.get(currentRoom);
            for (int key : keys) {
                // If the key opens an unvisited room, unlock and visit it
                if (!visited[key]) {
                    visited[key] = true;
                    count++;

                    // Optimization: Early exit if all rooms are already visited
                    if (count == n) {
                        return true;
                    }

                    queue.offer(key);
                }
            }
        }

        // Return true if every room from 0 to n - 1 was visited
        return count == n;
    }

    /**
     * Alternative DFS implementation (Iterative using an explicit Stack).
     * Provided for reference and comparison with the BFS approach.
     * 
     * Time Complexity: O(N + K)
     * Space Complexity: O(N)
     */
    public boolean canVisitAllRoomsDFS(List<List<Integer>> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return false;
        }

        int n = rooms.size();
        boolean[] visited = new boolean[n];
        java.util.Deque<Integer> stack = new ArrayDeque<>();

        visited[0] = true;
        stack.push(0);
        int count = 1;

        while (!stack.isEmpty()) {
            int currentRoom = stack.pop();
            for (int key : rooms.get(currentRoom)) {
                if (!visited[key]) {
                    visited[key] = true;
                    count++;
                    if (count == n) {
                        return true;
                    }
                    stack.push(key);
                }
            }
        }

        return count == n;
    }
}
