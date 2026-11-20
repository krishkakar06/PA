import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Definition for singly-linked list node.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

/**
 * LeetCode 23: Merge k Sorted Lists
 *
 * Problem Description:
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Approach:
 * - We use a Min-Heap (implemented via Java's PriorityQueue) to keep track of the smallest
 *   node among the heads of all currently non-exhausted linked lists.
 * - First, we insert the head node of each non-empty list into the PriorityQueue.
 * - At each iteration:
 *     1. Extract the node with the minimum value from the PriorityQueue (`poll`).
 *     2. Append this node to the merged list.
 *     3. If the extracted node has a next node (`next != null`), offer that next node into the PriorityQueue.
 * - This process continues until the PriorityQueue is empty, meaning all nodes across all k lists
 *   have been processed in ascending order.
 *
 * Complexity:
 * - Time Complexity: O(N * log(k))
 *     - k is the number of linked lists.
 *     - N is the total number of nodes across all k lists.
 *     - The PriorityQueue contains at most k elements at any time.
 *     - Each insertion and removal operation takes O(log k) time.
 *     - Each of the N nodes is added to and removed from the PriorityQueue exactly once.
 * - Space Complexity: O(k)
 *     - The PriorityQueue stores at most k elements simultaneously.
 *     - Nodes are re-linked in place, requiring O(1) additional list storage.
 */
public class MergeKSortedLists {

    /**
     * Merges k sorted linked lists into one sorted linked list using a Min-Heap.
     *
     * @param lists Array of heads of k sorted linked lists.
     * @return Head of the single merged sorted linked list, or null if input is empty or lists are empty.
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // Base case: check if the input array is null or has zero length
        if (lists == null || lists.length == 0) {
            return null;
        }

        // Initialize a PriorityQueue (min-heap) ordered by node value.
        // We use Integer.compare(a.val, b.val) to safely prevent potential integer underflow/overflow.
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
            lists.length,
            (a, b) -> Integer.compare(a.val, b.val)
        );

        // Add the head node of each non-empty list to the priority queue
        for (ListNode listHead : lists) {
            if (listHead != null) {
                minHeap.offer(listHead);
            }
        }

        // Dummy head simplifies list operations by eliminating special checks for the head node
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // Continuously extract the minimum node and advance
        while (!minHeap.isEmpty()) {
            // Extract the smallest node among the current heads
            ListNode smallestNode = minHeap.poll();

            // Link the smallest node to the merged list
            current.next = smallestNode;
            current = current.next;

            // If the extracted node has a successor, push it into the priority queue
            if (smallestNode.next != null) {
                minHeap.offer(smallestNode.next);
            }
        }

        // Return the merged list starting from the node following dummy
        return dummy.next;
    }

    /**
     * Main method demonstrating usage and verification of the solution.
     */
    public static void main(String[] args) {
        MergeKSortedLists solution = new MergeKSortedLists();

        // Example 1: lists = [[1,4,5],[1,3,4],[2,6]]
        ListNode l1 = buildList(new int[]{1, 4, 5});
        ListNode l2 = buildList(new int[]{1, 3, 4});
        ListNode l3 = buildList(new int[]{2, 6});

        ListNode[] lists = new ListNode[]{l1, l2, l3};

        System.out.println("--- Test Case 1: Standard lists ---");
        System.out.print("Input lists:\n");
        printList("List 1", l1);
        printList("List 2", l2);
        printList("List 3", l3);

        ListNode merged = solution.mergeKLists(lists);
        System.out.print("Merged Result: ");
        printList("Output", merged);

        // Example 2: lists = []
        System.out.println("\n--- Test Case 2: Empty array ---");
        ListNode mergedEmpty = solution.mergeKLists(new ListNode[]{});
        System.out.print("Merged Result: ");
        printList("Output", mergedEmpty);

        // Example 3: lists = [[]]
        System.out.println("\n--- Test Case 3: Array with empty list ---");
        ListNode mergedNulls = solution.mergeKLists(new ListNode[]{null});
        System.out.print("Merged Result: ");
        printList("Output", mergedNulls);
    }

    /**
     * Helper utility to build a linked list from an integer array.
     */
    private static ListNode buildList(int[] values) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }

    /**
     * Helper utility to print the linked list in a readable arrow-separated format.
     */
    private static void printList(String label, ListNode head) {
        System.out.print(label + ": ");
        if (head == null) {
            System.out.println("[] (null)");
            return;
        }
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + (curr.next != null ? " -> " : ""));
            curr = curr.next;
        }
        System.out.println();
    }
}