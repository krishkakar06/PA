import java.util.ArrayDeque;
import java.util.Queue;

/**
 * TreeHeightComputation
 *
 * This class provides implementations to compute the height (maximum depth)
 * of a Binary Tree using two different algorithmic approaches:
 *   1. Recursive Depth-First Search (DFS)
 *   2. Iterative Breadth-First Search (BFS / Level-Order Traversal)
 *
 * Definition of Height used here:
 *   - The number of nodes along the longest path from the root down to the farthest leaf node.
 *   - An empty tree (root == null) has height 0.
 *   - A tree with only the root node has height 1.
 *   (Note: If height is defined by the number of edges, subtract 1 from the result for non-empty trees).
 */
public class TreeHeightComputation {

    /**
     * Definition of a binary tree node.
     * Defined as a static nested class inside TreeHeightComputation.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        // Default constructor
        public TreeNode() {
        }

        // Constructor initializing value
        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        // Constructor initializing value and child pointers
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Approach 1: Recursive Depth-First Search (DFS)
     *
     * Computes the height of the binary tree recursively.
     * The height of any node is: 1 + max(height(left subtree), height(right subtree)).
     *
     * Algorithm:
     *   1. Base Case: If the root is null, return 0 (height of an empty tree).
     *   2. Recursive Case:
     *      a. Compute the height of the left subtree recursively.
     *      b. Compute the height of the right subtree recursively.
     *      c. Return 1 plus the maximum of left and right heights.
     *
     * Complexity Analysis:
     *   - Time Complexity: O(N), where N is the total number of nodes in the binary tree.
     *     Every node is visited exactly once.
     *   - Space Complexity: O(H), where H is the height of the binary tree.
     *     This corresponds to the maximum depth of the call stack:
     *       - Best/Average Case (Balanced Tree): O(log N)
     *       - Worst Case (Skewed Tree): O(N)
     *
     * @param root The root node of the binary tree.
     * @return The height (number of nodes along the longest path) of the tree.
     */
    public int height(TreeNode root) {
        // Base case: empty tree has height 0
        if (root == null) {
            return 0;
        }

        // Recursively find the height of left and right subtrees
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        // Current height is 1 (for the current node) plus the max of subtrees
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Approach 2: Iterative Breadth-First Search (BFS / Level-Order Traversal)
     *
     * Computes the height of the binary tree iteratively level by level using a Queue.
     *
     * Algorithm:
     *   1. If the root is null, return 0.
     *   2. Initialize a queue and add the root node. Initialize height = 0.
     *   3. While the queue is not empty:
     *      a. Increment height by 1 (we are processing a new level).
     *      b. Determine the number of nodes at the current level (`levelSize = queue.size()`).
     *      c. Poll all nodes at this level from the queue and enqueue their non-null children.
     *   4. Return the computed height once the queue is empty.
     *
     * Complexity Analysis:
     *   - Time Complexity: O(N), where N is the number of nodes in the binary tree.
     *     Each node is enqueued and dequeued once.
     *   - Space Complexity: O(W), where W is the maximum width (maximum number of nodes
     *     at any level) of the binary tree.
     *     In the worst case (full binary tree), the last level contains ceil(N / 2) nodes,
     *     yielding O(N) space.
     *
     * @param root The root node of the binary tree.
     * @return The height (number of nodes along the longest path) of the tree.
     */
    public int heightIterative(TreeNode root) {
        // Base case: empty tree has height 0
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int treeHeight = 0;

        // Traverse the tree level by level
        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            // Process all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }

            // Increment height after completing one full level
            treeHeight++;
        }

        return treeHeight;
    }

    /**
     * Main method demonstrating the computation of binary tree height
     * using both recursive DFS and iterative BFS methods.
     */
    public static void main(String[] args) {
        TreeHeightComputation solver = new TreeHeightComputation();

        /*
         * Constructing the following sample binary tree:
         *
         *                1
         *              /   \
         *             2     3
         *            / \     \
         *           4   5     6
         *              /
         *             7
         *
         * Longest path: 1 -> 2 -> 5 -> 7 (4 nodes, 3 edges)
         * Expected Height (by node count) = 4
         * Expected Height (by edge count) = 3
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        root.right.right = new TreeNode(6);

        root.left.right.left = new TreeNode(7);

        // Test 1: General Binary Tree
        System.out.println("--- Test Case 1: General Binary Tree ---");
        int dfsHeight1 = solver.height(root);
        int bfsHeight1 = solver.heightIterative(root);
        System.out.println("Height (Recursive DFS): " + dfsHeight1);
        System.out.println("Height (Iterative BFS): " + bfsHeight1);

        // Test 2: Single Node Tree
        // Tree: [10] -> Height = 1
        System.out.println("\n--- Test Case 2: Single Node Tree ---");
        TreeNode singleNode = new TreeNode(10);
        System.out.println("Height (Recursive DFS): " + solver.height(singleNode));
        System.out.println("Height (Iterative BFS): " + solver.heightIterative(singleNode));

        // Test 3: Empty Tree (null root)
        // Tree: null -> Height = 0
        System.out.println("\n--- Test Case 3: Empty Tree ---");
        TreeNode emptyRoot = null;
        System.out.println("Height (Recursive DFS): " + solver.height(emptyRoot));
        System.out.println("Height (Iterative BFS): " + solver.heightIterative(emptyRoot));
    }
}
