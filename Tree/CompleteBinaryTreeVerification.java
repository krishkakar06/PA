import java.util.LinkedList;
import java.util.Queue;

/**
 * Solution to verify whether a given Binary Tree is a Complete Binary Tree (CBT).
 *
 * Definition of a Complete Binary Tree:
 * In a complete binary tree, every level, except possibly the last, is completely filled,
 * and all nodes in the last level are as far left as possible. It can have between 1 and
 * 2^h nodes at the last level h.
 *
 * Algorithm - BFS with Null-Check Trick:
 * 1. Perform a standard Level-Order Traversal (Breadth-First Search).
 * 2. Enqueue child nodes (both left and right), including null references.
 * 3. Maintain a boolean flag `seenNull` initialized to false.
 * 4. When polling nodes from the queue:
 *    - If a null node is polled, set `seenNull = true`.
 *    - If a non-null node is polled AFTER `seenNull` is already true, it implies there is
 *      a "gap" or missing node before this node in level order. Hence, the tree is NOT complete.
 * 5. If the queue is exhausted without violating this condition, the tree is a complete binary tree.
 *
 * Time Complexity: O(N) where N is the number of nodes in the binary tree, as each node is visited once.
 * Space Complexity: O(N) for the BFS queue to store nodes of the tree.
 */
public class CompleteBinaryTreeVerification {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Verifies if the given binary tree is a complete binary tree using BFS.
     *
     * @param root The root node of the binary tree.
     * @return true if the tree is a complete binary tree, false otherwise.
     */
    public boolean isComplete(TreeNode root) {
        // An empty tree is considered complete by definition.
        if (root == null) {
            return true;
        }

        // We use LinkedList as the Queue implementation because it supports null elements.
        // Note: ArrayDeque throws NullPointerException if null is offered.
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        // Flag to track whether a null child has been encountered during level order traversal
        boolean seenNull = false;

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            if (current == null) {
                // We encountered a null node. From this point forward, all subsequent nodes must also be null.
                seenNull = true;
            } else {
                // If we see a non-null node after having already seen a null node, the tree is not complete.
                if (seenNull) {
                    return false;
                }

                // Push both left and right children into the queue, even if they are null.
                queue.offer(current.left);
                queue.offer(current.right);
            }
        }

        // All nodes satisfied the complete binary tree property.
        return true;
    }

    /**
     * Main method providing diverse test cases to demonstrate and verify the functionality.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        CompleteBinaryTreeVerification verifier = new CompleteBinaryTreeVerification();

        // -------------------------------------------------------------
        // Test Case 1: Perfectly balanced and complete binary tree
        //         1
        //       /   \
        //      2     3
        //     / \   / \
        //    4   5 6   7
        // Expected: true
        // -------------------------------------------------------------
        TreeNode tree1 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        System.out.println("Test Case 1 (Perfect Binary Tree): " + verifier.isComplete(tree1) + " (Expected: true)");

        // -------------------------------------------------------------
        // Test Case 2: Complete binary tree with partially filled last level (left-aligned)
        //         1
        //       /   \
        //      2     3
        //     / \   /
        //    4   5 6
        // Expected: true
        // -------------------------------------------------------------
        TreeNode tree2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), null)
        );
        System.out.println("Test Case 2 (Complete Tree, left-aligned last level): " + verifier.isComplete(tree2) + " (Expected: true)");

        // -------------------------------------------------------------
        // Test Case 3: Incomplete binary tree with a gap on the last level
        //         1
        //       /   \
        //      2     3
        //     / \     \
        //    4   5     6
        // Node 3 is missing its left child, so node 6 creates a gap.
        // Expected: false
        // -------------------------------------------------------------
        TreeNode tree3 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, null, new TreeNode(6))
        );
        System.out.println("Test Case 3 (Incomplete Tree, gap on last level): " + verifier.isComplete(tree3) + " (Expected: false)");

        // -------------------------------------------------------------
        // Test Case 4: Incomplete tree where right child exists without left child
        //         1
        //        /
        //       2
        //        \
        //         3
        // Expected: false
        // -------------------------------------------------------------
        TreeNode tree4 = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(3)),
                null
        );
        System.out.println("Test Case 4 (Right child without left child): " + verifier.isComplete(tree4) + " (Expected: false)");

        // -------------------------------------------------------------
        // Test Case 5: Single node tree
        //         1
        // Expected: true
        // -------------------------------------------------------------
        TreeNode tree5 = new TreeNode(1);
        System.out.println("Test Case 5 (Single node tree): " + verifier.isComplete(tree5) + " (Expected: true)");

        // -------------------------------------------------------------
        // Test Case 6: Empty tree (null root)
        // Expected: true
        // -------------------------------------------------------------
        TreeNode tree6 = null;
        System.out.println("Test Case 6 (Empty tree): " + verifier.isComplete(tree6) + " (Expected: true)");
    }
}
