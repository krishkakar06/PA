import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 144: Binary Tree Preorder Traversal
 *
 * Problem Description:
 * Given the root of a binary tree, return the preorder traversal of its nodes' values.
 * Preorder traversal visits nodes in the order: Root -> Left Subtree -> Right Subtree.
 *
 * This class provides:
 * 1. The required LeetCode method signature: public List<Integer> preorderTraversal(TreeNode root)
 * 2. An explicit Recursive Approach (using helper method)
 * 3. An explicit Iterative Approach (using an explicit Deque/Stack)
 * 4. A nested TreeNode definition
 * 5. A main method demonstrating both approaches on a sample binary tree
 */
public class PreorderTraversalList {

    /**
     * Definition for a binary tree node.
     * Defined statically within the class as requested.
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
     * Primary entry point matching the LeetCode 144 method signature.
     * By default, this delegates to the iterative implementation.
     *
     * @param root The root node of the binary tree.
     * @return List of integers representing the preorder traversal.
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        // Can call either preorderTraversalIterative(root) or preorderTraversalRecursive(root)
        return preorderTraversalIterative(root);
    }

    // =========================================================================
    // 1. RECURSIVE IMPLEMENTATION
    // =========================================================================

    /**
     * Preorder Traversal using Depth-First Search (DFS) / Recursion.
     *
     * Algorithm:
     * 1. If current node is null, return.
     * 2. Process/visit current node (add node.val to result list).
     * 3. Recursively traverse left subtree.
     * 4. Recursively traverse right subtree.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of nodes in the tree.
     *   Each node is visited exactly once.
     * - Space Complexity: O(H), where H is the height of the tree, representing
     *   the recursion call stack depth.
     *   - Worst-case (skewed tree): O(N).
     *   - Best-case (balanced tree): O(log N).
     *
     * @param root The root of the binary tree.
     * @return List of node values in preorder.
     */
    public List<Integer> preorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfsPreorder(root, result);
        return result;
    }

    /**
     * Helper method to perform the recursive preorder traversal.
     *
     * @param node   The current node being visited.
     * @param result The accumulating list of values.
     */
    private void dfsPreorder(TreeNode node, List<Integer> result) {
        // Base case: if node is null, backtrack
        if (node == null) {
            return;
        }

        // 1. Visit Root
        result.add(node.val);

        // 2. Traverse Left Subtree
        dfsPreorder(node.left, result);

        // 3. Traverse Right Subtree
        dfsPreorder(node.right, result);
    }

    // =========================================================================
    // 2. ITERATIVE IMPLEMENTATION
    // =========================================================================

    /**
     * Preorder Traversal using an explicit Stack (ArrayDeque).
     *
     * Algorithm:
     * 1. If root is null, return an empty list.
     * 2. Initialize an explicit stack (using Deque) and push the root onto the stack.
     * 3. While the stack is not empty:
     *    a. Pop the top node from the stack.
     *    b. Add the popped node's value to the result list.
     *    c. Push right child onto the stack (if non-null).
     *       *Note: Right child is pushed FIRST so that the left child is popped and processed FIRST (LIFO).
     *    d. Push left child onto the stack (if non-null).
     * 4. Return the result list.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of nodes in the binary tree.
     *   Every node is pushed and popped from the stack exactly once.
     * - Space Complexity: O(H), where H is the height of the binary tree.
     *   The stack stores at most nodes proportional to the tree height.
     *   - Worst-case (skewed tree): O(N).
     *   - Best-case (balanced tree): O(log N).
     *
     * @param root The root of the binary tree.
     * @return List of node values in preorder.
     */
    public List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        // Corner case: empty tree
        if (root == null) {
            return result;
        }

        // ArrayDeque is preferred over legacy java.util.Stack for performance and standard API practices
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();

            // 1. Process Root
            result.add(current.val);

            // 2. Push Right child first, so Left child is popped and processed next
            if (current.right != null) {
                stack.push(current.right);
            }

            // 3. Push Left child
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return result;
    }

    // =========================================================================
    // 3. MAIN METHOD (DEMO / VERIFICATION)
    // =========================================================================

    /**
     * Main method to test and demonstrate the functionality of both approaches.
     *
     * Constructs the following tree:
     *         1
     *          \
     *           2
     *          /
     *         3
     *
     * Expected Preorder Traversal: [1, 2, 3]
     */
    public static void main(String[] args) {
        PreorderTraversalList solution = new PreorderTraversalList();

        // Build sample binary tree: [1, null, 2, 3]
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);

        // Test primary method (delegates to iterative)
        List<Integer> defaultResult = solution.preorderTraversal(root);
        System.out.println("Default preorderTraversal result:   " + defaultResult);

        // Test recursive implementation
        List<Integer> recursiveResult = solution.preorderTraversalRecursive(root);
        System.out.println("Recursive preorderTraversal result: " + recursiveResult);

        // Test iterative implementation
        List<Integer> iterativeResult = solution.preorderTraversalIterative(root);
        System.out.println("Iterative preorderTraversal result: " + iterativeResult);
    }
}