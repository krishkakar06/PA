import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 71 - Simplify Path
 *
 * Problem Description:
 * Given an absolute path for a Unix-style file system (starting with a slash '/'),
 * transform this path into its simplified canonical path.
 *
 * Rules:
 * 1. A single period '.' refers to the current directory and should be ignored.
 * 2. A double period '..' refers to the parent directory and moves up one directory level.
 * 3. Multiple consecutive slashes '//' or '///' are treated as a single slash '/'.
 * 4. Any other sequence of periods (such as '...') is treated as a valid file/directory name.
 * 5. The canonical path must start with a single slash '/', segments separated by a single '/',
 *    cannot end with a trailing '/', and must contain only the valid directories.
 *
 * Approach:
 * - Use a Deque (Double-Ended Queue) as a stack.
 * - Split the input string by "/" into directory/file components.
 * - Iterate through each component:
 *     - If the component is empty ("") or ".", ignore it.
 *     - If the component is "..", pop the top directory from the stack (if not empty).
 *     - Otherwise, push the valid directory name onto the stack.
 * - Reconstruct the canonical path by joining the components from the bottom of the stack to the top.
 * - Return "/" if the stack is empty, otherwise return the reconstructed path.
 *
 * Complexity:
 * - Time Complexity: O(N), where N is the length of the path string.
 *   Splitting the string and processing each token takes linear time.
 * - Space Complexity: O(N), to store the directory components in the stack and StringBuilder.
 */
public class SimplifyPath {

    /**
     * Simplifies a given Unix-style absolute path to its canonical form.
     *
     * @param path The absolute path to simplify.
     * @return The simplified canonical path.
     */
    public String simplifyPath(String path) {
        // Handle edge case for null or empty string
        if (path == null || path.isEmpty()) {
            return "/";
        }

        // Use Deque as a LIFO stack to store directory names
        Deque<String> stack = new ArrayDeque<>();

        // Split the path by the delimiter "/"
        // This splits consecutive slashes "//" into empty strings ""
        String[] components = path.split("/");

        // Process each component from the split path
        for (String component : components) {
            // Case 1: Empty string (from consecutive slashes or leading/trailing slashes)
            // or "." (current directory reference) -> skip
            if (component.isEmpty() || component.equals(".")) {
                continue;
            }

            // Case 2: ".." (parent directory reference) -> move up one level
            if (component.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                // Case 3: Valid directory or file name (e.g., "home", "foo", "...")
                stack.push(component);
            }
        }

        // If no directory names remain in the stack, we are at the root "/"
        if (stack.isEmpty()) {
            return "/";
        }

        // Reconstruct the canonical path.
        // Elements were pushed onto the stack (LIFO: newest on top / at front).
        // The root directory is at the bottom of the stack (at the end/last).
        // Therefore, we traverse from bottom to top using pollLast() or descendingIterator().
        StringBuilder canonicalPath = new StringBuilder();
        while (!stack.isEmpty()) {
            canonicalPath.append("/").append(stack.pollLast());
        }

        return canonicalPath.toString();
    }

    /**
     * Main method to test and demonstrate the simplifyPath functionality.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SimplifyPath solver = new SimplifyPath();

        // Test cases
        String[] testPaths = {
            "/home/",
            "/../",
            "/home//foo/",
            "/a/./b/../../c/",
            "/a/../../b/../c//.//",
            "/a//b////c/d//././/..",
            "/..."
        };

        for (String testPath : testPaths) {
            System.out.println("Original:  " + testPath);
            System.out.println("Canonical: " + solver.simplifyPath(testPath));
            System.out.println();
        }
    }
}
