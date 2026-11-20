import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Implementation of Postorder Traversal (Left -> Right -> Root) of a Binary Tree in Java.
 *
 * This class provides:
 * 1. An inner TreeNode definition.
 * 2. Recursive postorder traversal: postorder(TreeNode root, List<Integer> result)
 * 3. Iterative postorder traversal: postorder(TreeNode root) using the classic two-stack technique.
 * 4. A main method demonstrating both approaches on a sample binary tree.
 */
public class PostorderTraversal {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        // Default constructor
        public TreeNode() {
        }

        // Constructor initializing node value
        public TreeNode(int val) {
            this.val = val;
        }

        // Constructor initializing node value and children
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Recursive Postorder Traversal (Left -> Right -> Root).
     *
     * In postorder traversal, we recursively visit:
     * 1. The left subtree
     * 2. The right subtree
     * 3. The root node
     *
     * Time Complexity: O(N), where N is the number of nodes in the binary tree (each node is visited once).
     * Space Complexity: O(H), where H is the height of the binary tree (due to the recursive call stack).
     *                   In the worst case (skewed tree), H = N; in the best case (balanced tree), H = log(N).
     *
     * @param root   the root node of the binary tree/subtree
     * @param result the list to store the visited node values in postorder sequence
     */
    public static void postorder(TreeNode root, List<Integer> result) {
        // Base case: if the current node is null, return
        if (root == null) {
            return;
        }

        // Step 1: Recursively traverse the left subtree
        postorder(root.left, result);

        // Step 2: Recursively traverse the right subtree
        postorder(root.right, result);

        // Step 3: Visit the root node and add its value to the result list
        result.add(root.val);
    }

    /**
     * Iterative Postorder Traversal (Left -> Right -> Root) using the Two-Stack trick.
     *
     * How the Two-Stack trick works:
     * 1. Postorder sequence is: Left -> Right -> Root.
     * 2. The reverse of postorder sequence is: Root -> Right -> Left.
     * 3. We use the first stack (stack1) to traverse the tree in modified preorder (Root -> Left -> Right).
     *    When we pop a node from stack1, we push it immediately to the second stack (stack2).
     *    Then we push its left child, followed by its right child onto stack1.
     * 4. Because stack1 pops the right child before the left child, nodes enter stack2 in the order:
     *    Root -> Right -> Left.
     * 5. When all nodes have been processed, popping nodes from stack2 reverses the sequence,
     *    yielding: Left -> Right -> Root (exact postorder sequence).
     *
     * Time Complexity: O(N), where N is the number of nodes in the binary tree.
     * Space Complexity: O(N), for maintaining the two stacks.
     *
     * @param root the root node of the binary tree
     * @return a List containing the node values in postorder traversal sequence
     */
    public static List<Integer> postorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        // Corner case: if the tree is empty, return an empty list
        if (root == null) {
            return result;
        }

        // stack1 is used for traversal
        Deque<TreeNode> stack1 = new ArrayDeque<>();
        // stack2 stores nodes in reverse postorder sequence (Root -> Right -> Left)
        Deque<TreeNode> stack2 = new ArrayDeque<>();

        // Initialize stack1 with the root node
        stack1.push(root);

        // Run until stack1 is empty
        while (!stack1.isEmpty()) {
            TreeNode current = stack1.pop();

            // Push current node to stack2
            stack2.push(current);

            // Push left child first so right child is popped first from stack1
            if (current.left != null) {
                stack1.push(current.left);
            }

            // Push right child next so it is processed next in the loop
            if (current.right != null) {
                stack1.push(current.right);
            }
        }

        // Pop all elements from stack2 to obtain the postorder traversal (Left -> Right -> Root)
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }

        return result;
    }

    /**
     * Main method demonstrating recursive and iterative postorder traversal.
     *
     * Example Tree Structure:
     *            1
     *          /   \
     *         2     3
     *        / \   /
     *       4   5 6
     *
     * Expected Postorder Traversal (Left, Right, Root):
     * [4, 5, 2, 6, 3, 1]
     */
    public static void main(String[] args) {
        // Construct the sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);

        System.out.println("Binary Tree Structure:");
        System.out.println("         1");
        System.out.println("       /   \\");
        System.out.println("      2     3");
        System.out.println("     / \\   /");
        System.out.println("    4   5 6");
        System.out.println();

        // 1. Recursive Postorder Traversal
        List<Integer> recursiveResult = new ArrayList<>();
        postorder(root, recursiveResult);
        System.out.println("Recursive Postorder Traversal : " + recursiveResult);

        // 2. Iterative Postorder Traversal (Two-Stack Trick)
        List<Integer> iterativeResult = postorder(root);
        System.out.println("Iterative Postorder Traversal : " + iterativeResult);

        // Verify that both outputs match
        boolean match = recursiveResult.equals(iterativeResult);
        System.out.println("Traversals match: " + match);
    }
}
