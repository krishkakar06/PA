/**
 * Solution for LeetCode 98: Validate Binary Search Tree.
 *
 * Problem Description:
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST is defined as follows:
 * 1. The left subtree of a node contains only nodes with keys strictly less than the node's key.
 * 2. The right subtree of a node contains only nodes with keys strictly greater than the node's key.
 * 3. Both the left and right subtrees must also be binary search trees.
 *
 * Algorithm Approach: Min/Max Bounds (Recursive Traversal)
 * - A common pitfall is to only verify that a node is greater than its left child and smaller than
 *   its right child. However, every descendant in the left subtree must be smaller than the root,
 *   and every descendant in the right subtree must be greater than the root.
 * - To enforce this, we pass down valid lower and upper bounds (min, max) for each node.
 * - When traversing left, the current node's value becomes the new upper bound (high).
 * - When traversing right, the current node's value becomes the new lower bound (low).
 * - Using Integer wrapper objects (or Long) allows null to represent infinity (-inf, +inf)
 *   without suffering from 32-bit integer underflow or overflow when node values equal
 *   Integer.MIN_VALUE or Integer.MAX_VALUE.
 *
 * Complexity:
 * - Time Complexity: O(N), where N is the number of nodes in the binary tree. We visit each node once.
 * - Space Complexity: O(H), where H is the height of the binary tree, corresponding to the recursion stack.
 *   In the worst case (skewed tree), H = N; in the best/balanced case, H = log(N).
 */
public class BinaryTreeValidation {

    /**
     * Definition for a binary tree node.
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
     * Determines whether the given binary tree is a valid Binary Search Tree.
     *
     * @param root the root node of the binary tree
     * @return true if the tree is a valid BST, false otherwise
     */
    public boolean isValidBST(TreeNode root) {
        // Initial bounds are null, representing (-infinity, +infinity)
        return validate(root, null, null);
    }

    /**
     * Helper method to validate BST properties using min and max bounds.
     *
     * @param node the current node being evaluated
     * @param low  the exclusive lower bound for the current node's value (null if -infinity)
     * @param high the exclusive upper bound for the current node's value (null if +infinity)
     * @return true if the subtree rooted at 'node' is a valid BST within (low, high), false otherwise
     */
    private boolean validate(TreeNode node, Integer low, Integer high) {
        // An empty tree/node is a valid BST base case
        if (node == null) {
            return true;
        }

        // The current node's value must be strictly greater than 'low'
        if (low != null && node.val <= low) {
            return false;
        }

        // The current node's value must be strictly less than 'high'
        if (high != null && node.val >= high) {
            return false;
        }

        // Recursively validate left and right subtrees:
        // - Left child's value must be in range (low, node.val)
        // - Right child's value must be in range (node.val, high)
        return validate(node.left, low, node.val) && validate(node.right, node.val, high);
    }

    /**
     * Main method providing test cases and examples demonstrating the validation.
     */
    public static void main(String[] args) {
        BinaryTreeValidation validator = new BinaryTreeValidation();

        // -------------------------------------------------------------
        // Example 1: Valid BST
        //        2
        //       / \
        //      1   3
        // Expected: true
        // -------------------------------------------------------------
        TreeNode ex1 = new TreeNode(2,
                new TreeNode(1),
                new TreeNode(3)
        );
        boolean result1 = validator.isValidBST(ex1);
        System.out.println("Example 1: [2, 1, 3]");
        System.out.println("Is Valid BST? " + result1); // Expected: true
        System.out.println();

        // -------------------------------------------------------------
        // Example 2: Invalid BST (Right child violation)
        //        5
        //       / \
        //      1   4
        //         / \
        //        3   6
        // Root is 5, but its right child is 4 (4 < 5).
        // Expected: false
        // -------------------------------------------------------------
        TreeNode ex2 = new TreeNode(5,
                new TreeNode(1),
                new TreeNode(4,
                        new TreeNode(3),
                        new TreeNode(6)
                )
        );
        boolean result2 = validator.isValidBST(ex2);
        System.out.println("Example 2: [5, 1, 4, null, null, 3, 6]");
        System.out.println("Is Valid BST? " + result2); // Expected: false
        System.out.println();

        // -------------------------------------------------------------
        // Example 3: Subtree violation (Classic pitfall for local checks)
        //        5
        //       / \
        //      4   6
        //         / \
        //        3   7
        // Node 3 is in the right subtree of 5, but 3 < 5.
        // Even though 3 < 6 and 7 > 6 locally, globally 3 is invalid.
        // Expected: false
        // -------------------------------------------------------------
        TreeNode ex3 = new TreeNode(5,
                new TreeNode(4),
                new TreeNode(6,
                        new TreeNode(3),
                        new TreeNode(7)
                )
        );
        boolean result3 = validator.isValidBST(ex3);
        System.out.println("Example 3: [5, 4, 6, null, null, 3, 7]");
        System.out.println("Is Valid BST? " + result3); // Expected: false
        System.out.println();

        // -------------------------------------------------------------
        // Example 4: Integer Boundary Cases (Integer.MAX_VALUE / Integer.MIN_VALUE)
        //        Integer.MAX_VALUE
        // Expected: true
        // -------------------------------------------------------------
        TreeNode ex4 = new TreeNode(Integer.MAX_VALUE);
        boolean result4 = validator.isValidBST(ex4);
        System.out.println("Example 4: [Integer.MAX_VALUE]");
        System.out.println("Is Valid BST? " + result4); // Expected: true
        System.out.println();

        // -------------------------------------------------------------
        // Example 5: Duplicate values
        //        2
        //       / \
        //      2   2
        // BST keys must be strictly less / greater, duplicates violate this.
        // Expected: false
        // -------------------------------------------------------------
        TreeNode ex5 = new TreeNode(2,
                new TreeNode(2),
                new TreeNode(2)
        );
        boolean result5 = validator.isValidBST(ex5);
        System.out.println("Example 5: [2, 2, 2] (Duplicates)");
        System.out.println("Is Valid BST? " + result5); // Expected: false
    }
}