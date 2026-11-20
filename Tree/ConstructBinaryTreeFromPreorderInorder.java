import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 105: Construct Binary Tree from Preorder and Inorder Traversal
 *
 * Problem Description:
 * Given two integer arrays preorder and inorder where preorder is the preorder traversal
 * of a binary tree and inorder is the inorder traversal of the same tree, construct and
 * return the binary tree.
 *
 * Approach:
 * 1. In a preorder traversal (Root -> Left -> Right), the first element is always the root of the current subtree.
 * 2. In an inorder traversal (Left -> Root -> Right), all elements to the left of the root belong to its left subtree,
 *    and all elements to the right belong to its right subtree.
 * 3. We can precompute the indices of elements in the inorder traversal using a HashMap to achieve O(1) index lookups.
 * 4. By knowing the position of the root in the inorder array, we can determine the size of the left subtree.
 *    This allows us to partition both the preorder and inorder arrays into left and right subtrees and recursively
 *    construct the binary tree.
 *
 * Complexity Analysis:
 * - Time Complexity: O(N), where N is the number of nodes in the tree.
 *   Building the HashMap takes O(N) time. Each node is visited and constructed once in O(1) time.
 * - Space Complexity: O(N).
 *   The HashMap stores N entries, consuming O(N) space. The recursion call stack takes O(H) space,
 *   where H is the height of the tree (O(log N) for balanced trees, O(N) in the worst case for skewed trees).
 */
public class ConstructBinaryTreeFromPreorderInorder {

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
     * Map storing value-to-index mappings of elements in the inorder array
     * to facilitate O(1) position lookup during tree reconstruction.
     */
    private Map<Integer, Integer> inorderIndexMap;

    /**
     * Constructs a binary tree from given preorder and inorder traversal arrays.
     *
     * @param preorder preorder traversal array
     * @param inorder  inorder traversal array
     * @return the root node of the constructed binary tree, or null if input arrays are empty/null
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Edge cases check: return null if either array is null or empty
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        // Initialize and populate the map with inorder values and their respective indices
        inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        // Delegate to the recursive helper function covering the full array ranges
        return buildTreeHelper(preorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    /**
     * Recursive helper method that constructs subtrees by partitioning preorder and inorder ranges.
     *
     * @param preorder preorder traversal array
     * @param preStart start index of current subtree in preorder array
     * @param preEnd   end index of current subtree in preorder array
     * @param inStart  start index of current subtree in inorder array
     * @param inEnd    end index of current subtree in inorder array
     * @return the root node of the subtree constructed within the specified index boundaries
     */
    private TreeNode buildTreeHelper(int[] preorder, int preStart, int preEnd, int inStart, int inEnd) {
        // Base case: if either range is invalid, no nodes exist in this subtree
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // The first node in preorder range is always the root of current subtree
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // Retrieve the index of the current root in the inorder traversal
        int inRootIndex = inorderIndexMap.get(rootVal);

        // Calculate the number of nodes present in the left subtree
        int leftSubtreeSize = inRootIndex - inStart;

        // Recursively construct the left subtree:
        // - Preorder range: begins immediately after root (preStart + 1) and ends after leftSubtreeSize elements
        // - Inorder range: begins at inStart and ends just before root (inRootIndex - 1)
        root.left = buildTreeHelper(preorder, preStart + 1, preStart + leftSubtreeSize, inStart, inRootIndex - 1);

        // Recursively construct the right subtree:
        // - Preorder range: begins immediately after left subtree elements (preStart + leftSubtreeSize + 1) to preEnd
        // - Inorder range: begins right after root (inRootIndex + 1) to inEnd
        root.right = buildTreeHelper(preorder, preStart + leftSubtreeSize + 1, preEnd, inRootIndex + 1, inEnd);

        return root;
    }
}
