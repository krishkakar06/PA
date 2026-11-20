import java.util.LinkedList;
import java.util.Queue;

/**
 * HeightOfBinaryTree
 *
 * This class demonstrates two fundamental approaches to compute the height
 * (or maximum depth) of a binary tree:
 * 1. Recursive Depth-First Search (DFS)
 * 2. Iterative Breadth-First Search (BFS) / Level-Order Traversal
 *
 * Height Definition Used:
 * - The height is defined as the number of nodes along the longest path from
 *   the root node down to the farthest leaf node.
 *   - Empty tree (null root): Height = 0
 *   - Single-node tree: Height = 1
 *   (Note: If height is defined in terms of edges, edgeHeight = nodeHeight - 1 for non-empty trees).
 */
public class HeightOfBinaryTree {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        /**
         * Constructor for a leaf node with a value.
         *
         * @param val the integer value to store in the node
         */
        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        /**
         * Constructor with value and child pointers.
         *
         * @param val the integer value to store in the node
         * @param left left child reference
         * @param right right child reference
         */
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Computes the height of a binary tree recursively using Depth-First Search (DFS).
     *
     * Algorithm:
     * - Base Case: If root is null, the tree is empty; return 0.
     * - Recursive Case: Recursively compute the height of the left and right subtrees.
     *   The height of the current node is 1 + max(height(left), height(right)).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the total number of nodes in the binary tree.
     *   Each node is visited exactly once.
     * - Space Complexity: O(H), where H is the height of the tree, corresponding to the
     *   maximum depth of the call stack.
     *   - In the best/average case (balanced tree): O(log N).
     *   - In the worst case (skewed tree): O(N).
     *
     * @param root the root node of the binary tree
     * @return the maximum height (depth in terms of nodes) of the tree
     */
    public static int height(TreeNode root) {
        // Base case: an empty tree has height 0
        if (root == null) {
            return 0;
        }

        // Recursively find the height of the left and right subtrees
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        // Height of tree is 1 (current node) + maximum of left and right subtree heights
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Computes the height of a binary tree iteratively using Breadth-First Search (BFS).
     *
     * Algorithm:
     * - Uses a queue to traverse the tree level by level.
     * - For each level:
     *   1. Determine the number of nodes at this level (size of queue).
     *   2. Dequeue each node at this level and enqueue their non-null children.
     *   3. Increment the height counter after processing the entire level.
     * - When the queue becomes empty, all levels have been processed.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the total number of nodes in the binary tree.
     *   Each node is enqueued and dequeued exactly once.
     * - Space Complexity: O(W), where W is the maximum width (maximum number of nodes
     *   at any single level) of the binary tree.
     *   - In the worst case (complete binary tree): O(N/2) = O(N).
     *   - In the best case (skewed tree): O(1).
     *
     * @param root the root node of the binary tree
     * @return the maximum height (depth in terms of nodes) of the tree
     */
    public static int heightIterative(TreeNode root) {
        // Base case: an empty tree has height 0
        if (root == null) {
            return 0;
        }

        // Queue for level-order traversal (BFS)
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int treeHeight = 0;

        // Process level by level
        while (!queue.isEmpty()) {
            // Number of nodes at the current level
            int levelSize = queue.size();

            // Dequeue all nodes at the current level and enqueue their children
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }

            // After finishing one level, increment the height
            treeHeight++;
        }

        return treeHeight;
    }

    /**
     * Main method demonstrating height computation on diverse tree shapes:
     * 1. Empty Tree
     * 2. Single Node Tree
     * 3. Balanced / Complete Binary Tree
     * 4. Left-Skewed Tree (Degenerate)
     * 5. Right-Skewed Tree (Degenerate)
     * 6. Asymmetrical / Unbalanced Tree
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  Binary Tree Height Calculation Demonstrations  ");
        System.out.println("=================================================\n");

        // ---------------------------------------------------------------------
        // Case 1: Empty Tree (null)
        // Structure:
        //   (empty)
        // ---------------------------------------------------------------------
        TreeNode emptyTree = null;
        testTree("1. Empty Tree", emptyTree, 0);

        // ---------------------------------------------------------------------
        // Case 2: Single Node Tree
        // Structure:
        //      [10]
        // ---------------------------------------------------------------------
        TreeNode singleNodeTree = new TreeNode(10);
        testTree("2. Single Node Tree", singleNodeTree, 1);

        // ---------------------------------------------------------------------
        // Case 3: Balanced / Complete Binary Tree
        // Structure:
        //          1
        //        /   \
        //       2     3
        //      / \   / \
        //     4   5 6   7
        // Expected Height: 3
        // ---------------------------------------------------------------------
        TreeNode balancedTree = new TreeNode(1,
            new TreeNode(2, new TreeNode(4), new TreeNode(5)),
            new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        testTree("3. Balanced / Complete Binary Tree", balancedTree, 3);

        // ---------------------------------------------------------------------
        // Case 4: Left-Skewed Binary Tree (Degenerate / Linked-list like)
        // Structure:
        //        1
        //       /
        //      2
        //     /
        //    3
        //   /
        //  4
        // Expected Height: 4
        // ---------------------------------------------------------------------
        TreeNode leftSkewedTree = new TreeNode(1,
            new TreeNode(2,
                new TreeNode(3,
                    new TreeNode(4),
                    null),
                null),
            null
        );
        testTree("4. Left-Skewed Binary Tree", leftSkewedTree, 4);

        // ---------------------------------------------------------------------
        // Case 5: Right-Skewed Binary Tree (Degenerate / Linked-list like)
        // Structure:
        //  1
        //   \
        //    2
        //     \
        //      3
        // Expected Height: 3
        // ---------------------------------------------------------------------
        TreeNode rightSkewedTree = new TreeNode(1,
            null,
            new TreeNode(2,
                null,
                new TreeNode(3))
        );
        testTree("5. Right-Skewed Binary Tree", rightSkewedTree, 3);

        // ---------------------------------------------------------------------
        // Case 6: Asymmetric / Unbalanced Binary Tree
        // Structure:
        //        10
        //       /  \
        //      5    15
        //     /       \
        //    2         20
        //   /
        //  1
        // /
        //0
        // Expected Height: 5
        // ---------------------------------------------------------------------
        TreeNode asymmetricTree = new TreeNode(10,
            new TreeNode(5,
                new TreeNode(2,
                    new TreeNode(1,
                        new TreeNode(0),
                        null),
                    null),
                null),
            new TreeNode(15,
                null,
                new TreeNode(20))
        );
        testTree("6. Asymmetrical Tree (Left Height 5, Right Height 3)", asymmetricTree, 5);
    }

    /**
     * Helper method to run both height calculation algorithms, print outputs,
     * and verify correctness against the expected value.
     *
     * @param testName name of the test scenario
     * @param root root of the binary tree
     * @param expectedHeight the expected height of the tree
     */
    private static void testTree(String testName, TreeNode root, int expectedHeight) {
        int recHeight = height(root);
        int iterHeight = heightIterative(root);

        boolean isCorrect = (recHeight == expectedHeight) && (iterHeight == expectedHeight);

        System.out.println("Test Case: " + testName);
        System.out.println("  - Recursive Height : " + recHeight);
        System.out.println("  - Iterative Height : " + iterHeight);
        System.out.println("  - Expected Height  : " + expectedHeight);
        System.out.println("  - Status           : " + (isCorrect ? "PASSED [OK]" : "FAILED [MISMATCH]"));
        System.out.println();
    }
}