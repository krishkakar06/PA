import java.util.LinkedList;
import java.util.Queue;

/**
 * File: LevelOrderTraversalBFS.java
 * 
 * Description:
 * Implementation of Breadth-First Search (BFS) / Level Order Traversal on a Binary Tree.
 * Nodes at each level are printed on a new line by keeping track of the queue size
 * at each level boundary.
 *
 * Complexity:
 * - Time Complexity: O(N), where N is the total number of nodes in the binary tree.
 *   Each node is enqueued and dequeued exactly once.
 * - Space Complexity: O(W), where W is the maximum width (maximum number of nodes at any level)
 *   of the binary tree. In the worst case (full binary tree), W can be up to ceil(N / 2),
 *   which is O(N).
 */
public class LevelOrderTraversalBFS {

    /**
     * Definition of the Binary Tree Node.
     * Defined as a static nested class inside LevelOrderTraversalBFS.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        // Constructor for node with value and no children
        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        // Constructor for node with value and child pointers
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Traverses the binary tree level by level using a Queue (BFS)
     * and prints the node values of each level on a distinct line.
     *
     * @param root The root node of the binary tree.
     */
    public void printLevelOrder(TreeNode root) {
        // Base case: If tree is empty, nothing to print
        if (root == null) {
            return;
        }

        // Initialize a First-In-First-Out (FIFO) queue for BFS traversal
        Queue<TreeNode> queue = new LinkedList<>();

        // Enqueue the root node as the starting point (Level 0 / Level 1)
        queue.offer(root);

        // Process levels until the queue becomes empty
        while (!queue.isEmpty()) {
            // Number of nodes present at the current level
            int levelSize = queue.size();

            // Iterate through all nodes of the current level
            for (int i = 0; i < levelSize; i++) {
                // Remove the front node from the queue
                TreeNode currentNode = queue.poll();

                // Print the current node's value separated by space
                System.out.print(currentNode.val);
                if (i < levelSize - 1) {
                    System.out.print(" ");
                }

                // Enqueue left child if it exists
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }

                // Enqueue right child if it exists
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            // Move to the next line after completing the current level
            System.out.println();
        }
    }

    /**
     * Main method demonstrating Level Order Traversal on an example binary tree.
     *
     * Example Tree Structure:
     *            1
     *          /   \
     *         2     3
     *        / \     \
     *       4   5     6
     *          /
     *         7
     *
     * Expected Output:
     * 1
     * 2 3
     * 4 5 6
     * 7
     */
    public static void main(String[] args) {
        LevelOrderTraversalBFS traverser = new LevelOrderTraversalBFS();

        // Construct the example binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        root.left.right.left = new TreeNode(7);

        // Execute level order traversal
        System.out.println("Level Order Traversal (BFS) - Level by level:");
        traverser.printLevelOrder(root);
    }
}
