import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Class: PreorderTraversal
 * 
 * Demonstrates Preorder Traversal of a Binary Tree using both:
 * 1. Recursive approach: preorder(TreeNode root, List<Integer> result)
 * 2. Iterative approach: preorder(TreeNode root)
 * 
 * Traversal Order (Root -> Left -> Right):
 * 1. Visit the current node (Root).
 * 2. Recursively / sequentially traverse the Left subtree.
 * 3. Recursively / sequentially traverse the Right subtree.
 * 
 * Complexity Analysis:
 * - Time Complexity: O(N) for both approaches, where N is the total number of nodes in the binary tree.
 *   Every node is visited exactly once.
 * - Auxiliary Space Complexity: O(H) where H is the height of the binary tree.
 *   - Worst case (skewed tree): O(N)
 *   - Best/Average case (balanced tree): O(log N)
 *   For the recursive method, space is consumed by the call stack.
 *   For the iterative method, space is consumed by the explicit Deque/Stack.
 */
public class PreorderTraversal {

    /**
     * Definition for a binary tree node.
     * Encapsulates the node's value and references to its left and right children.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        // Default constructor
        public TreeNode() {}

        // Constructor initializing node with a value
        public TreeNode(int val) {
            this.val = val;
        }

        // Constructor initializing node with value and child pointers
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Recursive implementation of Preorder Traversal.
     * 
     * Algorithm:
     * 1. Base case: If the current node (root) is null, return immediately.
     * 2. Process / visit the current node's value (Root).
     * 3. Make a recursive call on the left child (Left).
     * 4. Make a recursive call on the right child (Right).
     * 
     * @param root   The current node in the binary tree being visited.
     * @param result The list accumulating the values of visited nodes in preorder.
     */
    public static void preorder(TreeNode root, List<Integer> result) {
        // Base case: an empty subtree requires no processing
        if (root == null) {
            return;
        }

        // Step 1: Visit Root
        result.add(root.val);

        // Step 2: Traverse Left Subtree
        preorder(root.left, result);

        // Step 3: Traverse Right Subtree
        preorder(root.right, result);
    }

    /**
     * Iterative implementation of Preorder Traversal using an explicit stack.
     * 
     * Algorithm:
     * 1. If the root is null, return an empty result list.
     * 2. Initialize an explicit LIFO stack (using Deque) and push the root node.
     * 3. While the stack is not empty:
     *    a. Pop the top node from the stack.
     *    b. Add its value to the result list (Root is processed).
     *    c. Push the RIGHT child to the stack (if not null).
     *    d. Push the LEFT child to the stack (if not null).
     *       * Note: The right child is pushed BEFORE the left child so that the
     *         left child is popped and processed first, adhering to LIFO order.
     * 
     * @param root The root node of the binary tree.
     * @return List of integers representing nodes in preorder traversal.
     */
    public static List<Integer> preorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        // Handle edge case of an empty tree
        if (root == null) {
            return result;
        }

        // Use Deque as a standard, high-performance stack implementation in Java
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        // Process nodes until stack is exhausted
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();

            // Visit the current node
            result.add(current.val);

            // Push right child first so left child is popped and processed first
            if (current.right != null) {
                stack.push(current.right);
            }

            // Push left child next
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return result;
    }

    /**
     * Main method demonstrating the usage of both recursive and iterative preorder traversals.
     * 
     * Constructed Binary Tree:
     *            1
     *          /   \
     *         2     3
     *        / \     \
     *       4   5     6
     * 
     * Expected Preorder Traversal Order (Root -> Left -> Right):
     * [1, 2, 4, 5, 3, 6]
     */
    public static void main(String[] args) {
        // Construct the sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        // 1. Test Recursive Preorder Traversal
        List<Integer> recursiveResult = new ArrayList<>();
        preorder(root, recursiveResult);
        System.out.println("Recursive Preorder Traversal: " + recursiveResult);

        // 2. Test Iterative Preorder Traversal
        List<Integer> iterativeResult = preorder(root);
        System.out.println("Iterative Preorder Traversal: " + iterativeResult);

        // Verify that both approaches produce the identical expected result
        List<Integer> expected = List.of(1, 2, 4, 5, 3, 6);
        boolean matches = recursiveResult.equals(expected) && iterativeResult.equals(expected);
        System.out.println("Both traversals match expected output: " + matches);
    }
}