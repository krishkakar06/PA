import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * File Name: IterativePreorderTraversal.java
 *
 * Implementation of Iterative Preorder Traversal on a Binary Tree using an explicit Stack.
 *
 * Algorithm Overview:
 * -------------------
 * Preorder traversal visits nodes in the order: Root -> Left Subtree -> Right Subtree.
 *
 * In a recursive implementation, the call stack implicitly tracks the nodes.
 * To achieve this iteratively, we simulate the recursion using an explicit stack (e.g., Deque).
 *
 * Key Steps:
 * 1. Base Case: If the root is null, return an empty list immediately.
 * 2. Initialization:
 *    - Create an empty list to store the traversal results.
 *    - Initialize an explicit stack (using Deque / ArrayDeque for performance).
 *    - Push the root node onto the stack.
 * 3. Iteration (while the stack is not empty):
 *    - Pop the top node from the stack.
 *    - Visit/process the popped node: add its value to the result list.
 *    - Push the RIGHT child to the stack (if it exists).
 *    - Push the LEFT child to the stack (if it exists).
 *
 * Why Push Right Child First?
 * ---------------------------
 * A stack follows Last-In, First-Out (LIFO) order. By pushing the right child
 * first followed by the left child, the left child will sit on top of the stack.
 * Thus, the left child will be popped and processed in the next iteration before
 * the right child, preserving the correct preorder sequence: Root -> Left -> Right.
 *
 * Complexity Analysis:
 * --------------------
 * Time Complexity:  O(N), where N is the total number of nodes in the binary tree.
 *                   Each node is pushed onto and popped from the stack exactly once,
 *                   and each list insertion operation takes O(1) amortized time.
 * Space Complexity: O(H), where H is the height of the binary tree.
 *                   In the worst-case (skewed tree), the stack may store up to N nodes (O(N)).
 *                   In the best-case (balanced tree), the stack stores at most O(log N) nodes.
 */
public class IterativePreorderTraversal {

    /**
     * Definition of a Binary Tree Node.
     * Defined statically inside the outer class for encapsulation and convenience.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        // Default constructor
        public TreeNode() {
        }

        // Value constructor
        public TreeNode(int val) {
            this.val = val;
        }

        // Full constructor
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Performs an iterative preorder traversal of a binary tree using an explicit stack.
     *
     * @param root The root node of the binary tree
     * @return List of node values in preorder sequence (Root -> Left -> Right)
     */
    public List<Integer> preorder(TreeNode root) {
        // List to hold the visited node values in preorder sequence
        List<Integer> result = new ArrayList<>();

        // Edge case: if tree is empty, return the empty result list
        if (root == null) {
            return result;
        }

        // Use Deque as a modern, efficient explicit stack (preferred over legacy java.util.Stack)
        Deque<TreeNode> stack = new ArrayDeque<>();

        // Step 1: Push the root node to kick off the traversal
        stack.push(root);

        // Step 2: Loop until there are no more nodes to process
        while (!stack.isEmpty()) {
            // Step 2a: Pop the most recently added node from the top of the stack
            TreeNode current = stack.pop();

            // Step 2b: Process the current node (Root step of Root -> Left -> Right)
            result.add(current.val);

            // Step 2c: Push the RIGHT child first (if present).
            // Because the stack is LIFO, pushing right first ensures that the
            // right child will be processed AFTER the left child.
            if (current.right != null) {
                stack.push(current.right);
            }

            // Step 2d: Push the LEFT child next (if present).
            // Pushing left after right places the left child at the top of the stack,
            // so it will be popped and processed in the very next iteration.
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        // Return the collected preorder traversal result
        return result;
    }

    /**
     * Main method demonstrating the iterative preorder traversal with an example tree.
     *
     * Example Tree Structure:
     *           1
     *         /   \
     *        2     3
     *       / \     \
     *      4   5     6
     *
     * Expected Preorder Traversal:
     * Visit 1 -> Visit Left Subtree (2 -> 4 -> 5) -> Visit Right Subtree (3 -> 6)
     * Sequence: [1, 2, 4, 5, 3, 6]
     */
    public static void main(String[] args) {
        IterativePreorderTraversal traverser = new IterativePreorderTraversal();

        // 1. Construct the example binary tree
        //
        //          1
        //        /   \
        //       2     3
        //      / \     \
        //     4   5     6
        //
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        // 2. Execute preorder traversal
        List<Integer> traversalResult = traverser.preorder(root);

        // 3. Print the results
        System.out.println("Tree Structure:");
        System.out.println("         1      ");
        System.out.println("       /   \\    ");
        System.out.println("      2     3   ");
        System.out.println("     / \\     \\  ");
        System.out.println("    4   5     6 ");
        System.out.println();
        System.out.println("Preorder Traversal Result (Root -> Left -> Right):");
        System.out.println(traversalResult);

        // 4. Test edge cases
        // Test with a single node tree
        TreeNode singleNode = new TreeNode(42);
        System.out.println("\nSingle node tree traversal: " + traverser.preorder(singleNode));

        // Test with an empty tree (null root)
        System.out.println("Empty tree traversal: " + traverser.preorder(null));
    }
}
