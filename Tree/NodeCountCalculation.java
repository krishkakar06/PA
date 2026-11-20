import java.util.LinkedList;
import java.util.Queue;

/**
 * NodeCountCalculation provides methods to count the total number of nodes 
 * in a binary tree using both recursive and iterative approaches.
 *
 * File: NodeCountCalculation.java
 */
public class NodeCountCalculation {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        /**
         * Constructor to initialize node with a value.
         * Left and right child references are set to null by default.
         *
         * @param val the integer value stored in the node
         */
        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        /**
         * Constructor to initialize node with value and left/right children.
         *
         * @param val   the integer value stored in the node
         * @param left  the left child node
         * @param right the right child node
         */
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Counts the total number of nodes in a binary tree using recursion (DFS).
     *
     * Approach:
     * - Base Case: If the current node (root) is null, the tree has 0 nodes.
     * - Recursive Step: Count the current node (1) plus all nodes in the left
     *   subtree and all nodes in the right subtree.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the total number of nodes in the tree.
     *   Each node is visited exactly once.
     * - Space Complexity: O(H), where H is the height of the tree.
     *   This space is occupied by the recursion call stack. In the worst case
     *   (skewed tree), H = N; in the best case (balanced tree), H = O(log N).
     *
     * @param root the root node of the binary tree
     * @return the total number of nodes in the binary tree
     */
    public int countNodes(TreeNode root) {
        // Base case: an empty tree has 0 nodes
        if (root == null) {
            return 0;
        }

        // Recursive case: 1 (current node) + nodes in left subtree + nodes in right subtree
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /**
     * Counts the total number of nodes in a binary tree using an iterative approach
     * (Breadth-First Search / Level-Order Traversal).
     *
     * Approach:
     * - Use a Queue to traverse the tree level by level.
     * - Dequeue each node, increment our node counter, and enqueue any existing children.
     * - Continue until the queue is empty.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the total number of nodes.
     *   Each node is enqueued and dequeued exactly once.
     * - Space Complexity: O(W), where W is the maximum width of the tree.
     *   In the worst case (complete binary tree), the queue holds up to ceil(N / 2) nodes,
     *   which is O(N).
     *
     * @param root the root node of the binary tree
     * @return the total number of nodes in the binary tree
     */
    public int countNodesIterative(TreeNode root) {
        // If the tree is empty, return 0
        if (root == null) {
            return 0;
        }

        int count = 0;
        Queue<TreeNode> queue = new LinkedList<>();

        // Start traversal with the root node
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            count++;

            // If left child exists, add it to the queue for traversal
            if (current.left != null) {
                queue.offer(current.left);
            }

            // If right child exists, add it to the queue for traversal
            if (current.right != null) {
                queue.offer(current.right);
            }
        }

        return count;
    }

    /**
     * Main method demonstrating the usage of both recursive and iterative
     * node counting methods with example binary trees.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        NodeCountCalculation calculator = new NodeCountCalculation();

        /*
         * Example 1: Constructing a binary tree
         *
         *            1
         *          /   \
         *         2     3
         *        / \   /
         *       4   5 6
         *
         * Total nodes = 6
         */
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right.left = new TreeNode(6);

        System.out.println("--- Test Case 1: Standard Binary Tree ---");
        int recursiveCount1 = calculator.countNodes(root1);
        int iterativeCount1 = calculator.countNodesIterative(root1);
        System.out.println("Expected Count  : 6");
        System.out.println("Recursive Count : " + recursiveCount1);
        System.out.println("Iterative Count : " + iterativeCount1);
        System.out.println("Match           : " + (recursiveCount1 == 6 && iterativeCount1 == 6));
        System.out.println();

        /*
         * Example 2: Empty tree (null root)
         * Total nodes = 0
         */
        TreeNode root2 = null;

        System.out.println("--- Test Case 2: Empty Tree ---");
        int recursiveCount2 = calculator.countNodes(root2);
        int iterativeCount2 = calculator.countNodesIterative(root2);
        System.out.println("Expected Count  : 0");
        System.out.println("Recursive Count : " + recursiveCount2);
        System.out.println("Iterative Count : " + iterativeCount2);
        System.out.println("Match           : " + (recursiveCount2 == 0 && iterativeCount2 == 0));
        System.out.println();

        /*
         * Example 3: Single-node tree
         *
         *         10
         *
         * Total nodes = 1
         */
        TreeNode root3 = new TreeNode(10);

        System.out.println("--- Test Case 3: Single Node Tree ---");
        int recursiveCount3 = calculator.countNodes(root3);
        int iterativeCount3 = calculator.countNodesIterative(root3);
        System.out.println("Expected Count  : 1");
        System.out.println("Recursive Count : " + recursiveCount3);
        System.out.println("Iterative Count : " + iterativeCount3);
        System.out.println("Match           : " + (recursiveCount3 == 1 && iterativeCount3 == 1));
        System.out.println();

        /*
         * Example 4: Skewed tree (linked list structure)
         *
         *       1
         *        \
         *         2
         *          \
         *           3
         *            \
         *             4
         *
         * Total nodes = 4
         */
        TreeNode root4 = new TreeNode(1);
        root4.right = new TreeNode(2);
        root4.right.right = new TreeNode(3);
        root4.right.right.right = new TreeNode(4);

        System.out.println("--- Test Case 4: Skewed Binary Tree ---");
        int recursiveCount4 = calculator.countNodes(root4);
        int iterativeCount4 = calculator.countNodesIterative(root4);
        System.out.println("Expected Count  : 4");
        System.out.println("Recursive Count : " + recursiveCount4);
        System.out.println("Iterative Count : " + iterativeCount4);
        System.out.println("Match           : " + (recursiveCount4 == 4 && iterativeCount4 == 4));
    }
}
