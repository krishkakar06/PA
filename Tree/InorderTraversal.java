import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * ============================================================================
 * Class: InorderTraversal
 * ============================================================================
 * Description:
 * This class provides a complete, robust Java implementation of Inorder 
 * Traversal for a Binary Tree using both Recursive and Iterative approaches.
 *
 * Inorder Traversal Order:
 * 1. Traverse the left subtree.
 * 2. Visit / process the current node (Root).
 * 3. Traverse the right subtree.
 * (Left -> Root -> Right)
 *
 * Note: For a Binary Search Tree (BST), an inorder traversal visits nodes 
 * in strictly non-decreasing (sorted) order.
 * ============================================================================
 */
public class InorderTraversal {

    /**
     * Definition for a binary tree node.
     * Defined as a static nested class inside InorderTraversal.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        /**
         * Default constructor initializing an empty node.
         */
        public TreeNode() {}

        /**
         * Constructor initializing node with a value.
         *
         * @param val The integer value to store in this node.
         */
        public TreeNode(int val) {
            this.val = val;
        }

        /**
         * Constructor initializing node with a value, left child, and right child.
         *
         * @param val   The integer value to store in this node.
         * @param left  Reference to the left child node.
         * @param right Reference to the right child node.
         */
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Recursive Inorder Traversal (Left -> Root -> Right).
     *
     * Algorithm:
     * 1. Base Case: If the current node (root) is null, simply return.
     * 2. Recurse on the left subtree: inorder(root.left, result).
     * 3. Process the current root node: result.add(root.val).
     * 4. Recurse on the right subtree: inorder(root.right, result).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Every node in the tree is visited exactly once, where N is the total 
     *   number of nodes.
     * - Space Complexity: O(H)
     *   Space taken by the implicit call stack during recursion, where H is the 
     *   height of the tree.
     *   - Worst case (skewed / degenerate tree): O(N)
     *   - Best / Average case (balanced tree): O(log N)
     *
     * @param root   The root node of the binary tree (or current subtree).
     * @param result The list accumulating the values of the visited nodes.
     */
    public static void inorder(TreeNode root, List<Integer> result) {
        // Base case: null node represents an empty subtree
        if (root == null) {
            return;
        }

        // Step 1: Recursively visit the left subtree
        inorder(root.left, result);

        // Step 2: Visit and record the root node value
        result.add(root.val);

        // Step 3: Recursively visit the right subtree
        inorder(root.right, result);
    }

    /**
     * Iterative Inorder Traversal (Left -> Root -> Right) using an explicit Stack.
     *
     * Algorithm:
     * 1. Initialize an empty list 'result' and an explicit stack (ArrayDeque) 
     *    to simulate the function call stack.
     * 2. Set a pointer 'current' to the root node.
     * 3. Loop while 'current' is not null OR the stack is not empty:
     *    a. Traverse down to the leftmost node of the current subtree, 
     *       pushing each node onto the stack as we go:
     *       while (current != null) {
     *           stack.push(current);
     *           current = current.left;
     *       }
     *    b. When 'current' is null, the top of the stack is the leftmost 
     *       unvisited node. Pop it from the stack.
     *    c. Add the popped node's value to 'result'.
     *    d. Move 'current' to the popped node's right child (current = popped.right)
     *       to traverse its right subtree in subsequent iterations.
     * 4. Return 'result'.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Each node is pushed onto the stack once and popped from the stack once.
     * - Space Complexity: O(H)
     *   The stack holds at most H nodes at any point, where H is tree height.
     *   - Worst case (skewed tree): O(N)
     *   - Best / Average case (balanced tree): O(log N)
     *
     * @param root The root node of the binary tree.
     * @return A list containing node values in inorder sequence.
     */
    public static List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        // Deque is preferred over java.util.Stack for LIFO stack operations
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;

        // Continue processing while there are nodes to explore or nodes waiting in stack
        while (current != null || !stack.isEmpty()) {
            // Traverse as far left as possible, pushing nodes onto the stack
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // At this point, current is null; pop the topmost node
            current = stack.pop();

            // Visit the node
            result.add(current.val);

            // Turn to the right subtree
            current = current.right;
        }

        return result;
    }

    /**
     * Helper overload for recursive inorder traversal that allocates and 
     * returns a new result list.
     *
     * @param root The root node of the binary tree.
     * @return A list containing node values in inorder sequence.
     */
    public static List<Integer> inorderRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    /**
     * Main method demonstrating both recursive and iterative inorder traversals.
     *
     * Example 1 Tree Structure (General Binary Tree):
     *            1
     *          /   \
     *         2     3
     *        / \   /
     *       4   5 6
     *
     * Expected Inorder Traversal Order (Left, Root, Right):
     * - Left subtree of 1:
     *     - Left of 2: 4
     *     - Root: 2
     *     - Right of 2: 5
     *   => [4, 2, 5]
     * - Root: 1
     * - Right subtree of 1:
     *     - Left of 3: 6
     *     - Root: 3
     *   => [6, 3]
     * - Full Order: [4, 2, 5, 1, 6, 3]
     *
     * Example 2 Tree Structure (Binary Search Tree - BST):
     *            4
     *          /   \
     *         2     6
     *        / \   / \
     *       1   3 5   7
     *
     * Expected BST Inorder Order: [1, 2, 3, 4, 5, 6, 7] (Sorted)
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       BINARY TREE INORDER TRAVERSAL DEMO         ");
        System.out.println("==================================================");

        // ----------------------------------------------------
        // Example 1: General Binary Tree
        // ----------------------------------------------------
        // Constructing Tree:
        //            1
        //          /   \
        //         2     3
        //        / \   /
        //       4   5 6
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right.left = new TreeNode(6);

        System.out.println("\n--- Example 1: General Binary Tree ---");
        System.out.println("Tree Structure:");
        System.out.println("       1      ");
        System.out.println("     /   \\   ");
        System.out.println("    2     3   ");
        System.out.println("   / \\   /   ");
        System.out.println("  4   5 6     ");

        // 1. Recursive Traversal
        List<Integer> recursiveResult1 = new ArrayList<>();
        inorder(root1, recursiveResult1);
        System.out.println("Recursive Inorder : " + recursiveResult1);

        // 2. Iterative Traversal
        List<Integer> iterativeResult1 = inorder(root1);
        System.out.println("Iterative Inorder : " + iterativeResult1);

        // Verification
        boolean match1 = recursiveResult1.equals(iterativeResult1);
        System.out.println("Outputs Match?    : " + match1);

        // ----------------------------------------------------
        // Example 2: Binary Search Tree (BST)
        // ----------------------------------------------------
        // Constructing BST:
        //            4
        //          /   \
        //         2     6
        //        / \   / \
        //       1   3 5   7
        TreeNode root2 = new TreeNode(4);
        root2.left = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        root2.right = new TreeNode(6, new TreeNode(5), new TreeNode(7));

        System.out.println("\n--- Example 2: Binary Search Tree (BST) ---");
        System.out.println("Tree Structure:");
        System.out.println("       4      ");
        System.out.println("     /   \\   ");
        System.out.println("    2     6   ");
        System.out.println("   / \\   / \\ ");
        System.out.println("  1   3 5   7 ");

        List<Integer> recursiveResult2 = new ArrayList<>();
        inorder(root2, recursiveResult2);
        List<Integer> iterativeResult2 = inorder(root2);

        System.out.println("Recursive Inorder : " + recursiveResult2);
        System.out.println("Iterative Inorder : " + iterativeResult2);
        System.out.println("Notice: For a BST, inorder traversal produces sorted values!");
        System.out.println("Outputs Match?    : " + recursiveResult2.equals(iterativeResult2));

        // ----------------------------------------------------
        // Example 3: Edge Cases (Single Node & Null Tree)
        // ----------------------------------------------------
        System.out.println("\n--- Example 3: Edge Cases ---");

        // Single-node tree
        TreeNode singleNode = new TreeNode(42);
        System.out.println("Single Node (42)  : " + inorder(singleNode));

        // Empty tree (null root)
        TreeNode nullRoot = null;
        List<Integer> emptyRecursive = new ArrayList<>();
        inorder(nullRoot, emptyRecursive);
        List<Integer> emptyIterative = inorder(nullRoot);

        System.out.println("Empty Tree (Rec)  : " + emptyRecursive);
        System.out.println("Empty Tree (Iter) : " + emptyIterative);
        System.out.println("Outputs Match?    : " + emptyRecursive.equals(emptyIterative));

        System.out.println("\n==================================================");
        System.out.println("             DEMO COMPLETED SUCCESSFULLY          ");
        System.out.println("==================================================");
    }
}