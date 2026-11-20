import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * ============================================================================
 * CLASS: InorderTraversalIterative
 * ============================================================================
 * 
 * Demonstrates an iterative approach to performing an Inorder Traversal
 * (Left -> Root -> Right) on a Binary Tree using an explicit Stack.
 * 
 * ----------------------------------------------------------------------------
 * BACKGROUND & MOTIVATION:
 * ----------------------------------------------------------------------------
 * In recursive traversal, the runtime environment manages call frames on the
 * system call stack. For a deep or degenerate (skewed) tree, recursion may
 * lead to a java.lang.StackOverflowError.
 * 
 * An iterative implementation replaces the implicit call stack with an explicit
 * data structure (e.g., Deque / ArrayDeque in Java) stored on the heap, giving
 * full control over memory and traversal flow.
 * 
 * ----------------------------------------------------------------------------
 * ALGORITHM STEP-BY-STEP BREAKDOWN:
 * ----------------------------------------------------------------------------
 * 1. Initialize:
 *    - An empty result list to collect visited node values.
 *    - An explicit stack (ArrayDeque) to store node references during descent.
 *    - A pointer 'curr' initialized to the root of the tree.
 * 
 * 2. Loop Condition:
 *    - Continue as long as 'curr != null' OR 'stack is not empty'.
 *      * 'curr != null' means there are nodes further down along the current path.
 *      * '!stack.isEmpty()' means there are previously pushed parent/ancestor
 *        nodes awaiting processing after their left subtrees are finished.
 * 
 * 3. Step A - Traverse Left:
 *    - While 'curr != null':
 *        a. Push 'curr' onto the stack (save ancestor to visit after left subtree).
 *        b. Move 'curr' to its left child: curr = curr.left.
 * 
 * 4. Step B - Backtrack & Visit:
 *    - When 'curr' becomes null, we have reached past the leftmost node.
 *    - Pop the topmost node from the stack and assign it to 'curr'.
 *    - Visit this node: add 'curr.val' to the result list.
 * 
 * 5. Step C - Traverse Right:
 *    - Move 'curr' to its right child: curr = curr.right.
 *    - If 'curr' has a right child, the next iteration will push it and explore
 *      its leftmost descendant.
 *    - If 'curr.right' is null, the next iteration will skip Step A and pop the
 *      next ancestor from the stack in Step B.
 * 
 * 6. Termination:
 *    - When both 'curr' is null and the stack is empty, all nodes have been visited.
 *    - Return the result list.
 * 
 * ----------------------------------------------------------------------------
 * COMPLEXITY ANALYSIS:
 * ----------------------------------------------------------------------------
 * - Time Complexity:  O(N), where N is the total number of nodes in the tree.
 *                     Every node is pushed onto the stack exactly once and
 *                     popped from the stack exactly once.
 * - Space Complexity: O(H), where H is the height of the tree.
 *                     In the worst case (skewed tree), H = N -> O(N).
 *                     In the best/average case (balanced tree), H = log2(N) -> O(log N).
 *                     The space is allocated on the heap for the stack.
 * ============================================================================
 */
public class InorderTraversalIterative {

    /**
     * Definition for a binary tree node.
     * Enclosed as an inner static class within InorderTraversalIterative.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        /**
         * Default constructor initializing with default field values.
         */
        public TreeNode() {
        }

        /**
         * Constructor initializing node with a specific value.
         *
         * @param val the integer value to store in this node
         */
        public TreeNode(int val) {
            this.val = val;
        }

        /**
         * Constructor initializing node with value and child pointers.
         *
         * @param val   the integer value to store in this node
         * @param left  reference to the left child
         * @param right reference to the right child
         */
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Performs an iterative inorder traversal (Left -> Node -> Right)
     * using an explicit stack.
     *
     * @param root the root node of the binary tree
     * @return a List of integers containing node values in inorder sequence
     */
    public List<Integer> inorderIterative(TreeNode root) {
        // Result list to store the values in inorder sequence
        List<Integer> result = new ArrayList<>();

        // If the tree is empty, return an empty list immediately
        if (root == null) {
            return result;
        }

        // Explicit stack to simulate recursion.
        // Deque (ArrayDeque) is preferred over java.util.Stack in modern Java
        // because it is unsynchronized and offers faster LIFO operations.
        Deque<TreeNode> stack = new ArrayDeque<>();

        // Pointer to track the current node being explored
        TreeNode curr = root;

        // The outer loop runs while:
        // 1. curr != null: we are exploring down a path (seeking the leftmost node), OR
        // 2. !stack.isEmpty(): we have ancestors on the stack whose right subtrees
        //    remain to be explored.
        while (curr != null || !stack.isEmpty()) {

            // -----------------------------------------------------------------
            // STEP 1: Traverse all the way to the leftmost node of current subtree.
            // Push each ancestor node onto the stack so we can return to it later.
            // -----------------------------------------------------------------
            while (curr != null) {
                stack.push(curr);      // Save current node on stack
                curr = curr.left;       // Move deeper to left child
            }

            // -----------------------------------------------------------------
            // STEP 2: curr is now null (we hit the bottom of the left branch).
            // Backtrack by popping the most recently pushed ancestor from the stack.
            // -----------------------------------------------------------------
            curr = stack.pop();

            // -----------------------------------------------------------------
            // STEP 3: "Visit" the node.
            // Since all nodes in its left subtree have already been processed,
            // it is now this node's turn to be recorded.
            // -----------------------------------------------------------------
            result.add(curr.val);

            // -----------------------------------------------------------------
            // STEP 4: Move to the right subtree.
            // In the next iteration:
            // - If curr.right != null, STEP 1 will push curr.right and explore its left.
            // - If curr.right == null, STEP 1 is skipped, and STEP 2 pops the parent.
            // -----------------------------------------------------------------
            curr = curr.right;
        }

        return result;
    }

    /**
     * Main method providing example test cases to demonstrate and verify the
     * iterative inorder traversal implementation.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        InorderTraversalIterative traversal = new InorderTraversalIterative();

        /*
         * --------------------------------------------------------------------
         * Example 1: Standard Binary Tree
         * --------------------------------------------------------------------
         * Tree structure:
         *             1
         *           /   \
         *          2     3
         *         / \   /
         *        4   5 6
         *
         * Expected Inorder Traversal: [4, 2, 5, 1, 6, 3]
         *
         * Step-by-step trace of Example 1:
         * 1. Push 1, push 2, push 4. curr becomes null.
         * 2. Pop 4 -> visit 4. curr = 4.right (null).
         * 3. Pop 2 -> visit 2. curr = 2.right (5).
         * 4. Push 5. curr becomes null.
         * 5. Pop 5 -> visit 5. curr = 5.right (null).
         * 6. Pop 1 -> visit 1. curr = 1.right (3).
         * 7. Push 3, push 6. curr becomes null.
         * 8. Pop 6 -> visit 6. curr = 6.right (null).
         * 9. Pop 3 -> visit 3. curr = 3.right (null).
         * 10. Stack is empty and curr is null -> loop terminates.
         */
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right.left = new TreeNode(6);

        List<Integer> result1 = traversal.inorderIterative(root1);
        System.out.println("Example 1 Inorder Traversal: " + result1);
        // Expected Output: [4, 2, 5, 1, 6, 3]

        /*
         * --------------------------------------------------------------------
         * Example 2: Skewed Tree (Right-skewed)
         * --------------------------------------------------------------------
         * Tree structure:
         *        10
         *          \
         *           20
         *             \
         *              30
         *
         * Expected Inorder Traversal: [10, 20, 30]
         */
        TreeNode root2 = new TreeNode(10);
        root2.right = new TreeNode(20);
        root2.right.right = new TreeNode(30);

        List<Integer> result2 = traversal.inorderIterative(root2);
        System.out.println("Example 2 (Right-skewed)   : " + result2);
        // Expected Output: [10, 20, 30]

        /*
         * --------------------------------------------------------------------
         * Example 3: Empty Tree
         * --------------------------------------------------------------------
         * Tree structure: null
         *
         * Expected Inorder Traversal: []
         */
        TreeNode root3 = null;
        List<Integer> result3 = traversal.inorderIterative(root3);
        System.out.println("Example 3 (Empty tree)     : " + result3);
        // Expected Output: []

        /*
         * --------------------------------------------------------------------
         * Example 4: Single Node Tree
         * --------------------------------------------------------------------
         * Tree structure: 42
         *
         * Expected Inorder Traversal: [42]
         */
        TreeNode root4 = new TreeNode(42);
        List<Integer> result4 = traversal.inorderIterative(root4);
        System.out.println("Example 4 (Single node)    : " + result4);
        // Expected Output: [42]
    }
}
