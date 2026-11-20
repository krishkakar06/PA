import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 94: Binary Tree Inorder Traversal
 * 
 * Problem Description:
 * Given the root of a binary tree, return the inorder traversal of its nodes' values.
 * Inorder traversal visits nodes in the sequence: Left Subtree -> Root Node -> Right Subtree.
 * 
 * This class provides:
 * 1. Recursive Approach (DFS)
 * 2. Iterative Approach (Using an explicit Stack / Deque)
 * 3. Morris Traversal Approach (Threaded Binary Tree for O(1) auxiliary space)
 */
public class InorderTraversalList {

    /**
     * Definition for a binary tree node.
     * Defined as a static nested class inside InorderTraversalList.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {}

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Primary entry point as specified by LeetCode problem signature.
     * Default implementation delegates to the iterative stack-based approach.
     *
     * @param root The root node of the binary tree.
     * @return List of node values visited in inorder sequence.
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        return inorderTraversalIterative(root);
    }

    // =========================================================================
    // Approach 1: Recursive Traversal
    // =========================================================================

    /**
     * Traverses the binary tree inorder using recursion (system call stack).
     *
     * Algorithm:
     * 1. Recursively traverse the left subtree.
     * 2. Process / visit the current node (add val to result).
     * 3. Recursively traverse the right subtree.
     *
     * Time Complexity: O(n), where n is the number of nodes in the binary tree.
     *                  Every node is visited exactly once.
     * Space Complexity: O(h), where h is the height of the tree, representing the
     *                   depth of the recursion call stack.
     *                   - Worst case (skewed tree): O(n)
     *                   - Best / Average case (balanced tree): O(log n)
     *
     * @param root The root of the binary tree.
     * @return List containing inorder node values.
     */
    public List<Integer> inorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderRecursiveHelper(root, result);
        return result;
    }

    /**
     * Helper method to recursively perform inorder traversal.
     *
     * @param node   The current tree node being visited.
     * @param result The list accumulating node values.
     */
    private void inorderRecursiveHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }

        // 1. Visit left subtree
        inorderRecursiveHelper(node.left, result);

        // 2. Visit current node
        result.add(node.val);

        // 3. Visit right subtree
        inorderRecursiveHelper(node.right, result);
    }

    // =========================================================================
    // Approach 2: Iterative Traversal (Using Explicit Stack)
    // =========================================================================

    /**
     * Traverses the binary tree inorder iteratively using an explicit stack.
     * Simulates the recursive call stack to prevent StackOverflowError on deep trees.
     *
     * Algorithm:
     * 1. Initialize an empty stack (Deque) and set current pointer to root.
     * 2. While current is not null or the stack is not empty:
     *    a. Traverse as far left as possible, pushing each node onto the stack.
     *    b. Pop the top node from the stack, record its value.
     *    c. Move current pointer to the popped node's right child.
     *
     * Time Complexity: O(n), where n is the number of nodes. Each node is pushed
     *                  and popped from the stack at most once.
     * Space Complexity: O(h), where h is the height of the tree for the explicit stack.
     *                   - Worst case (skewed tree): O(n)
     *                   - Best / Average case (balanced tree): O(log n)
     *
     * @param root The root of the binary tree.
     * @return List containing inorder node values.
     */
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            // Reach the leftmost node of the current subtree
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Current must be null at this point, pop the most recently pushed node
            current = stack.pop();
            result.add(current.val);

            // We have visited the node and its left subtree; now move to right subtree
            current = current.right;
        }

        return result;
    }

    // =========================================================================
    // Approach 3: Morris Inorder Traversal (Threaded Binary Tree)
    // =========================================================================

    /**
     * Traverses the binary tree inorder with O(1) auxiliary space using Morris Traversal.
     * Based on the concept of Threaded Binary Trees.
     *
     * Algorithm:
     * 1. Initialize current as root.
     * 2. While current is not null:
     *    a. If current has no left child:
     *       - Add current.val to result.
     *       - Move to current.right.
     *    b. Else (current has a left child):
     *       - Find the inorder predecessor of current (rightmost node in left subtree).
     *       - If predecessor.right == null:
     *           Establish temporary thread: predecessor.right = current.
     *           Move current to current.left.
     *       - Else (predecessor.right == current, thread already exists):
     *           Remove temporary thread: predecessor.right = null (restore original tree structure).
     *           Add current.val to result.
     *           Move current to current.right.
     *
     * Time Complexity: O(n), where n is the number of nodes.
     *                  Although edges are traversed multiple times, no edge is traversed
     *                  more than 3 times, giving amortized linear time O(n).
     * Space Complexity: O(1) auxiliary space (excluding the output list),
     *                   as no stack or recursion call stack is used.
     *
     * @param root The root of the binary tree.
     * @return List containing inorder node values.
     */
    public List<Integer> inorderTraversalMorris(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;

        while (current != null) {
            if (current.left == null) {
                // No left child: visit current node and move to right child
                result.add(current.val);
                current = current.right;
            } else {
                // Find the inorder predecessor of current
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Create temporary thread back to current
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Thread already exists: revert modification to restore tree
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }

        return result;
    }
}
