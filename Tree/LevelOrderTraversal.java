import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LevelOrderTraversal
 * 
 * This class provides a complete implementation of Breadth-First Search (BFS) /
 * Level Order Traversal on a binary tree.
 * 
 * In a level-order traversal, the nodes are visited level by level from top to bottom
 * (root to leaves), and from left to right at each level.
 */
public class LevelOrderTraversal {

    /**
     * Definition for a binary tree node.
     * Defined as a static nested class inside LevelOrderTraversal.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        // Default constructor
        public TreeNode() {}

        // Constructor with value
        public TreeNode(int val) {
            this.val = val;
        }

        // Constructor with value, left child, and right child
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Performs level order traversal of a binary tree.
     *
     * Algorithm (BFS using a Queue):
     * 1. Check if the root is null. If so, return an empty list.
     * 2. Initialize a Queue of TreeNode objects and enqueue the root node.
     * 3. Loop while the queue is not empty:
     *    a. Determine the number of nodes at the current level (levelSize = queue.size()).
     *    b. Create a sublist to hold the integer values of nodes at this level.
     *    c. Iterate levelSize times:
     *       i. Dequeue a node.
     *       ii. Add its value to the current level list.
     *       iii. If the node has a left child, enqueue it.
     *       iv. If the node has a right child, enqueue it.
     *    d. Add the current level list to the overall result list.
     * 4. Return the result list.
     *
     * Time Complexity: O(N), where N is the number of nodes in the binary tree,
     *                  as each node is enqueued and dequeued exactly once.
     * Space Complexity: O(W), where W is the maximum width of the tree (maximum number of nodes at any level).
     *                   In the worst case (e.g., complete binary tree), W = O(N).
     *
     * @param root the root node of the binary tree
     * @return a list of lists of integers representing node values at each level
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        // Base case: if the tree is empty, return an empty list
        if (root == null) {
            return result;
        }

        // Initialize a Queue to facilitate breadth-first traversal
        Queue<TreeNode> queue = new LinkedList<>();

        // Enqueue the root node to start traversal
        queue.offer(root);

        // Process nodes level by level
        while (!queue.isEmpty()) {
            // Number of nodes present in the current level
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>(levelSize);

            // Process all nodes of the current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                currentLevel.add(currentNode.val);

                // If left child exists, enqueue it for the next level
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }

                // If right child exists, enqueue it for the next level
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            // Append the processed level to the final result
            result.add(currentLevel);
        }

        return result;
    }

    /**
     * Main method to demonstrate and test the levelOrder traversal with an example tree.
     *
     * Example Tree Structure:
     *          3
     *        /   \
     *       9     20
     *            /  \
     *           15   7
     *
     * Expected Level Order Traversal:
     * Level 0: [3]
     * Level 1: [9, 20]
     * Level 2: [15, 7]
     * Overall Result: [[3], [9, 20], [15, 7]]
     */
    public static void main(String[] args) {
        // Construct the example binary tree:
        //        3
        //      /   \
        //     9     20
        //          /  \
        //         15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        // Instantiate the solution class
        LevelOrderTraversal traversal = new LevelOrderTraversal();

        // Perform level order traversal
        List<List<Integer>> levels = traversal.levelOrder(root);

        // Print results
        System.out.println("Level Order Traversal Result:");
        System.out.println(levels);

        System.out.println("\nDetailed Level-by-Level Output:");
        for (int i = 0; i < levels.size(); i++) {
            System.out.println("Level " + i + ": " + levels.get(i));
        }

        // Test with an empty tree (null root)
        System.out.println("\nEmpty Tree Test:");
        List<List<Integer>> emptyResult = traversal.levelOrder(null);
        System.out.println("Result for null root: " + emptyResult);
    }
}