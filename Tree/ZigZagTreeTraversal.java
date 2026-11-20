import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * LeetCode 103: Binary Tree Zigzag Level Order Traversal
 *
 * Given the root of a binary tree, return the zigzag level order traversal of its
 * nodes' values (i.e., from left to right, then right to left for the next level
 * and alternate between).
 */
public class ZigZagTreeTraversal {

    /**
     * Definition for a binary tree node.
     * Static nested class as per problem specification.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        // Default constructor
        public TreeNode() {}

        // Constructor initializing node value
        public TreeNode(int val) {
            this.val = val;
        }

        // Constructor initializing node value, left child, and right child
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Returns the zigzag (spiral) level order traversal of a binary tree.
     *
     * Algorithm Explanation:
     * 1. Breadth-First Search (BFS) is performed level by level.
     * 2. A Deque<TreeNode> is used as the primary queue for level-order traversal.
     * 3. For each level, we determine the number of nodes at that level (`levelSize`).
     * 4. A Deque<Integer> (using LinkedList which implements both Deque and List) is used
     *    to collect values of the current level:
     *    - Left-to-Right: values are appended to the tail using addLast().
     *    - Right-to-Left: values are prepended to the head using addFirst().
     *    Both operations take O(1) amortized/constant time in a Deque.
     * 5. Child nodes (left then right) are pushed into the BFS queue for the next level.
     * 6. The direction flag `isLeftToRight` is toggled after each level.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the total number of nodes in the binary tree.
     *   Every node is processed exactly once, and insertions at both ends of the Deque
     *   take O(1) time.
     * - Space Complexity: O(N), where N is the number of nodes.
     *   The BFS queue holds at most the maximum width of the tree (up to N/2 nodes
     *   at the lowest level of a complete binary tree), and the result list stores all N values.
     *
     * @param root The root of the binary tree.
     * @return List of lists containing node values in zigzag level order.
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        // Base case: An empty tree has no levels to traverse
        if (root == null) {
            return result;
        }

        // Deque used as the queue for Breadth-First Search (BFS)
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);

        // Flag to track traversal direction:
        // true  -> Left to Right (0th level, 2nd level, ...)
        // false -> Right to Left (1st level, 3rd level, ...)
        boolean isLeftToRight = true;

        while (!nodeQueue.isEmpty()) {
            int levelSize = nodeQueue.size();

            // Deque used to store node values of the current level.
            // Using LinkedList as it implements Deque<Integer> for O(1) addFirst/addLast
            // and implements List<Integer> so it can be directly added to result.
            Deque<Integer> currentLevel = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = nodeQueue.poll();

                // Insert into current level deque based on the direction flag
                if (isLeftToRight) {
                    currentLevel.addLast(currentNode.val);
                } else {
                    currentLevel.addFirst(currentNode.val);
                }

                // Standard BFS child exploration: left child first, then right child
                if (currentNode.left != null) {
                    nodeQueue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    nodeQueue.offer(currentNode.right);
                }
            }

            // Append current level to the result list
            // Safe cast: LinkedList implements both Deque<Integer> and List<Integer>
            result.add((List<Integer>) currentLevel);

            // Flip the direction for the next level
            isLeftToRight = !isLeftToRight;
        }

        return result;
    }

    /**
     * Main method demonstrating the zigzag level order traversal with examples.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        ZigZagTreeTraversal solver = new ZigZagTreeTraversal();

        // -------------------------------------------------------------
        // Example 1: Standard Binary Tree (LeetCode Example 1)
        // Tree Structure:
        //          3
        //        /   \
        //       9     20
        //            /  \
        //           15   7
        //
        // Expected Traversal:
        // Level 0 (L -> R): [3]
        // Level 1 (R -> L): [20, 9]
        // Level 2 (L -> R): [15, 7]
        // Expected Output: [[3], [20, 9], [15, 7]]
        // -------------------------------------------------------------
        System.out.println("=== Example 1: Standard Binary Tree ===");
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);

        List<List<Integer>> result1 = solver.zigzagLevelOrder(root1);
        System.out.println("Input Tree : [3, 9, 20, null, null, 15, 7]");
        System.out.println("Output     : " + result1);
        System.out.println("Expected   : [[3], [20, 9], [15, 7]]");
        System.out.println();

        // -------------------------------------------------------------
        // Example 2: Deeper Binary Tree (4 Levels)
        // Tree Structure:
        //               1
        //            /     \
        //           2       3
        //          / \     / \
        //         4   5   6   7
        //        /             \
        //       8               9
        //
        // Expected Traversal:
        // Level 0 (L -> R): [1]
        // Level 1 (R -> L): [3, 2]
        // Level 2 (L -> R): [4, 5, 6, 7]
        // Level 3 (R -> L): [9, 8]
        // Expected Output: [[1], [3, 2], [4, 5, 6, 7], [9, 8]]
        // -------------------------------------------------------------
        System.out.println("=== Example 2: Multi-Level Binary Tree ===");
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.right = new TreeNode(5);
        root2.right.left = new TreeNode(6);
        root2.right.right = new TreeNode(7);
        root2.left.left.left = new TreeNode(8);
        root2.right.right.right = new TreeNode(9);

        List<List<Integer>> result2 = solver.zigzagLevelOrder(root2);
        System.out.println("Output     : " + result2);
        System.out.println("Expected   : [[1], [3, 2], [4, 5, 6, 7], [9, 8]]");
        System.out.println();

        // -------------------------------------------------------------
        // Example 3: Single Node Tree
        // Expected Output: [[1]]
        // -------------------------------------------------------------
        System.out.println("=== Example 3: Single Node Tree ===");
        TreeNode root3 = new TreeNode(1);
        List<List<Integer>> result3 = solver.zigzagLevelOrder(root3);
        System.out.println("Output     : " + result3);
        System.out.println("Expected   : [[1]]");
        System.out.println();

        // -------------------------------------------------------------
        // Example 4: Empty Tree (null root)
        // Expected Output: []
        // -------------------------------------------------------------
        System.out.println("=== Example 4: Empty Tree ===");
        TreeNode root4 = null;
        List<List<Integer>> result4 = solver.zigzagLevelOrder(root4);
        System.out.println("Output     : " + result4);
        System.out.println("Expected   : []");
    }
}