/**
 * SumNodesOnLongestPath.java
 *
 * Problem:
 * Given a binary tree, find the sum of all nodes on the longest path from the root
 * to any leaf node. If two or more paths have the same maximum length, the path with
 * the maximum sum of nodes is chosen.
 *
 * Algorithm:
 * We perform a Depth First Search (DFS) traversal starting from the root node.
 * At each step, we maintain:
 *  - currentLen: The number of nodes in the path from the root to the current node.
 *  - currentSum: The sum of values of all nodes on this path.
 *
 * When a leaf node (a node with no left and no right child) is reached:
 *  1. If currentLen > maxLen:
 *     We have found a strictly longer root-to-leaf path. We update maxLen to currentLen
 *     and maxSum to currentSum.
 *  2. If currentLen == maxLen:
 *     We have found another path with the same maximum length. We update maxSum to be
 *     Math.max(maxSum, currentSum).
 *
 * Time Complexity:  O(N), where N is the total number of nodes in the binary tree,
 *                   since each node is visited exactly once.
 * Space Complexity: O(H), where H is the height of the tree, due to the recursive
 *                   call stack (O(log N) for balanced trees, O(N) for skewed trees).
 */
public class SumNodesOnLongestPath {

    /**
     * Definition for a binary tree node.
     * Defined inside the SumNodesOnLongestPath class as requested.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        /**
         * Default constructor creating an empty node with value 0.
         */
        public TreeNode() {
        }

        /**
         * Constructor initializing node with a specific value.
         *
         * @param val the integer value of the node
         */
        public TreeNode(int val) {
            this.val = val;
        }

        /**
         * Constructor initializing node with a value and left/right children.
         *
         * @param val   the integer value of the node
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
     * Finds the sum of nodes on the longest path from root to leaf.
     * If multiple paths have the same maximum length, returns the maximum sum among them.
     *
     * @param root the root node of the binary tree
     * @return the maximum sum among all longest root-to-leaf paths, or 0 if tree is empty
     */
    public int sumOfLongestPath(TreeNode root) {
        // Base case: an empty tree has a sum of 0
        if (root == null) {
            return 0;
        }

        // maxTracker array is used to pass mutable state across recursive calls
        // maxTracker[0] stores the maximum path length (number of nodes) encountered so far
        // maxTracker[1] stores the maximum sum found among paths of maximum length
        // Initialized with length = 0 and sum = Integer.MIN_VALUE to properly support negative values
        int[] maxTracker = new int[]{0, Integer.MIN_VALUE};

        // Initiate DFS from root with path length 1 and sum equal to root.val
        dfs(root, 1, root.val, maxTracker);

        return maxTracker[1];
    }

    /**
     * Recursive helper method that explores root-to-leaf paths using Depth-First Search.
     *
     * @param node        the current tree node being visited
     * @param currentLen  the number of nodes on the path from root to current node
     * @param currentSum  the sum of node values along the path from root to current node
     * @param maxTracker  tracking array where maxTracker[0] is maxLen and maxTracker[1] is maxSum
     */
    private void dfs(TreeNode node, int currentLen, int currentSum, int[] maxTracker) {
        if (node == null) {
            return;
        }

        // A leaf node is defined as a node having neither left nor right child
        if (node.left == null && node.right == null) {
            // Case 1: Found a path strictly longer than previously seen longest paths
            if (currentLen > maxTracker[0]) {
                maxTracker[0] = currentLen;
                maxTracker[1] = currentSum;
            }
            // Case 2: Found a path with length equal to the maximum length; keep the greater sum
            else if (currentLen == maxTracker[0]) {
                maxTracker[1] = Math.max(maxTracker[1], currentSum);
            }
            return;
        }

        // Recurse on the left child if it exists
        if (node.left != null) {
            dfs(node.left, currentLen + 1, currentSum + node.left.val, maxTracker);
        }

        // Recurse on the right child if it exists
        if (node.right != null) {
            dfs(node.right, currentLen + 1, currentSum + node.right.val, maxTracker);
        }
    }

    /**
     * Main method demonstrating various test cases including standard trees,
     * competing paths of identical lengths, trees with negative values, and edge cases.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SumNodesOnLongestPath solver = new SumNodesOnLongestPath();

        // -------------------------------------------------------------
        // Example 1: Standard Tree
        //
        //            4
        //          /   \
        //         2     5
        //        / \   / \
        //       7   1 2   3
        //          /
        //         6
        //
        // Root-to-leaf paths:
        //  - 4 -> 2 -> 7      : length = 3, sum = 4 + 2 + 7 = 13
        //  - 4 -> 2 -> 1 -> 6 : length = 4, sum = 4 + 2 + 1 + 6 = 13 (LONGEST)
        //  - 4 -> 5 -> 2      : length = 3, sum = 4 + 5 + 2 = 11
        //  - 4 -> 5 -> 3      : length = 3, sum = 4 + 5 + 3 = 12
        //
        // Expected Output: 13 (path length is 4)
        // -------------------------------------------------------------
        TreeNode root1 = new TreeNode(4);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(5);

        root1.left.left = new TreeNode(7);
        root1.left.right = new TreeNode(1);
        root1.left.right.left = new TreeNode(6);

        root1.right.left = new TreeNode(2);
        root1.right.right = new TreeNode(3);

        int result1 = solver.sumOfLongestPath(root1);
        System.out.println("Example 1 Result: " + result1);
        System.out.println("Expected: 13\n");

        // -------------------------------------------------------------
        // Example 2: Competing Paths of Same Length with Different Sums
        //
        //             1
        //           /   \
        //          2     3
        //         /       \
        //        4         5
        //
        // Root-to-leaf paths:
        //  - 1 -> 2 -> 4 : length = 3, sum = 1 + 2 + 4 = 7
        //  - 1 -> 3 -> 5 : length = 3, sum = 1 + 3 + 5 = 9
        //
        // Both paths have the same maximum length (3), so the maximum sum (9) is returned.
        // Expected Output: 9
        // -------------------------------------------------------------
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.right.right = new TreeNode(5);

        int result2 = solver.sumOfLongestPath(root2);
        System.out.println("Example 2 Result: " + result2);
        System.out.println("Expected: 9\n");

        // -------------------------------------------------------------
        // Example 3: Tree with Negative Node Values
        //
        //            -1
        //           /  \
        //         -2    -3
        //         /      \
        //       -4       -5
        //
        // Root-to-leaf paths:
        //  - (-1) -> (-2) -> (-4) : length = 3, sum = -7
        //  - (-1) -> (-3) -> (-5) : length = 3, sum = -9
        //
        // Both paths have length 3. Max sum is Math.max(-7, -9) = -7.
        // Expected Output: -7
        // -------------------------------------------------------------
        TreeNode root3 = new TreeNode(-1);
        root3.left = new TreeNode(-2);
        root3.right = new TreeNode(-3);
        root3.left.left = new TreeNode(-4);
        root3.right.right = new TreeNode(-5);

        int result3 = solver.sumOfLongestPath(root3);
        System.out.println("Example 3 Result: " + result3);
        System.out.println("Expected: -7\n");

        // -------------------------------------------------------------
        // Example 4: Single Node Tree
        //
        //             10
        //
        // Root-to-leaf path:
        //  - 10 : length = 1, sum = 10
        //
        // Expected Output: 10
        // -------------------------------------------------------------
        TreeNode root4 = new TreeNode(10);
        int result4 = solver.sumOfLongestPath(root4);
        System.out.println("Example 4 (Single Node) Result: " + result4);
        System.out.println("Expected: 10\n");

        // -------------------------------------------------------------
        // Example 5: Empty Tree (null root)
        //
        // Expected Output: 0
        // -------------------------------------------------------------
        TreeNode root5 = null;
        int result5 = solver.sumOfLongestPath(root5);
        System.out.println("Example 5 (Empty Tree) Result: " + result5);
        System.out.println("Expected: 0");
    }
}
