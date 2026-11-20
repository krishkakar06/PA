import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 145: Binary Tree Postorder Traversal
 *
 * Given the root of a binary tree, return the postorder traversal of its nodes' values.
 * Postorder Traversal visits nodes in the order: Left Subtree -> Right Subtree -> Root Node.
 */
public class PostorderTraversalList {

    /**
     * Definition for a binary tree node.
     * Defined inside the class as requested.
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
     * Primary entry point matching the standard LeetCode signature.
     * Computes the postorder traversal of the given binary tree.
     *
     * @param root The root node of the binary tree.
     * @return A list of node values ordered according to postorder traversal.
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        // Can delegate to either postorderTraversalRecursive or postorderTraversalIterative
        return postorderTraversalIterative(root);
    }

    /**
     * =========================================================================
     * 1. RECURSIVE IMPLEMENTATION
     * =========================================================================
     * Time Complexity: O(n), where n is the number of nodes in the tree,
     *                  since each node is visited exactly once.
     * Space Complexity: O(h), where h is the height of the tree (due to the
     *                   recursion call stack). In the worst case (skewed tree),
     *                   h = n. In the best/balanced case, h = O(log n).
     *
     * @param root The root node of the binary tree.
     * @return List of integers representing postorder traversal.
     */
    public List<Integer> postorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    /**
     * Helper method to perform the recursive postorder traversal (Left -> Right -> Root).
     *
     * @param node Current node in traversal.
     * @param result Accumulator list for node values.
     */
    private void postorderHelper(TreeNode node, List<Integer> result) {
        // Base case: if current node is null, return back up the call stack
        if (node == null) {
            return;
        }

        // 1. Traverse the left subtree
        postorderHelper(node.left, result);

        // 2. Traverse the right subtree
        postorderHelper(node.right, result);

        // 3. Visit the current (root) node
        result.add(node.val);
    }

    /**
     * =========================================================================
     * 2. ITERATIVE IMPLEMENTATION (Single Stack with Last-Visited Tracking)
     * =========================================================================
     * Standard 1-stack iterative postorder traversal that uses O(h) auxiliary memory.
     *
     * Traversal Logic:
     * - We traverse down the leftmost branch, pushing all nodes onto the stack.
     * - When we can go no further left (curr == null), we inspect the node at the top of the stack.
     * - If this node has a non-null right child that has NOT been visited yet, we must traverse
     *   into the right subtree before visiting this node (Left -> Right -> Root). Thus, curr = peekNode.right.
     * - If the node has no right child OR its right child was the last node processed
     *   (peekNode.right == lastVisited), it means both subtrees have been fully processed.
     *   We can pop the node, record its value, and update lastVisited = poppedNode.
     *
     * Time Complexity: O(n) - Each node is pushed to and popped from the stack at most once.
     * Space Complexity: O(h) - Auxiliary stack holds at most the height of the tree.
     *
     * @param root The root node of the binary tree.
     * @return List of integers representing postorder traversal.
     */
    public List<Integer> postorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        TreeNode lastVisited = null;

        while (curr != null || !stack.isEmpty()) {
            // Push all left children until reaching the leftmost null
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                TreeNode peekNode = stack.peek();
                // If right child exists and has not been processed yet, move to the right child
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    curr = peekNode.right;
                } else {
                    // Both left and right subtrees have been processed, visit peekNode
                    result.add(peekNode.val);
                    lastVisited = stack.pop();
                }
            }
        }

        return result;
    }

    /**
     * =========================================================================
     * 3. ALTERNATIVE ITERATIVE IMPLEMENTATION (Two Stacks / Modified Preorder)
     * =========================================================================
     * Postorder is Left -> Right -> Root.
     * Notice that the reverse of postorder is Root -> Right -> Left.
     * We can perform a preorder-like traversal visiting (Root -> Right -> Left)
     * and push each visited node onto an output stack. When finished, popping all
     * elements from the output stack yields the exact postorder sequence.
     *
     * Time Complexity: O(n) - Visits every node once.
     * Space Complexity: O(n) - Uses two stacks to store tree nodes and values.
     *
     * @param root The root node of the binary tree.
     * @return List of integers representing postorder traversal.
     */
    public List<Integer> postorderTraversalIterativeTwoStacks(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> output = new ArrayDeque<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.push(node.val);

            // Push left first, then right, so right child is popped and processed first
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        // Drain the output stack into the result list (Left -> Right -> Root)
        while (!output.isEmpty()) {
            result.add(output.pop());
        }

        return result;
    }

    /**
     * Test runner demonstrating correctness across multiple test cases.
     */
    public static void main(String[] args) {
        PostorderTraversalList solution = new PostorderTraversalList();

        // Example 1: Tree [1, null, 2, 3]
        //      1
        //       \
        //        2
        //       /
        //      3
        // Expected Postorder: [3, 2, 1]
        TreeNode root1 = new TreeNode(1);
        root1.right = new TreeNode(2);
        root1.right.left = new TreeNode(3);

        System.out.println("Test 1 (Recursive):          " + solution.postorderTraversalRecursive(root1));
        System.out.println("Test 1 (Iterative 1-Stack):  " + solution.postorderTraversalIterative(root1));
        System.out.println("Test 1 (Iterative 2-Stack):  " + solution.postorderTraversalIterativeTwoStacks(root1));

        // Example 2: Empty Tree -> Expected: []
        System.out.println("Test 2 (Empty Tree):         " + solution.postorderTraversal(null));

        // Example 3: Single Node [1] -> Expected: [1]
        TreeNode root3 = new TreeNode(1);
        System.out.println("Test 3 (Single Node):        " + solution.postorderTraversal(root3));

        // Example 4: Full Tree:
        //        4
        //      /   \
        //     2     5
        //    / \
        //   1   3
        // Expected Postorder: [1, 3, 2, 5, 4]
        TreeNode root4 = new TreeNode(4);
        root4.left = new TreeNode(2);
        root4.right = new TreeNode(5);
        root4.left.left = new TreeNode(1);
        root4.left.right = new TreeNode(3);

        System.out.println("Test 4 (Recursive):          " + solution.postorderTraversalRecursive(root4));
        System.out.println("Test 4 (Iterative 1-Stack):  " + solution.postorderTraversalIterative(root4));
        System.out.println("Test 4 (Iterative 2-Stack):  " + solution.postorderTraversalIterativeTwoStacks(root4));
    }
}
