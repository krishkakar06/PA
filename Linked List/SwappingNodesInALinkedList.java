import java.util.*;
public class SwappingNodesInALinkedList {
    // LC 1721 - swap values of kth from start and kth from end
    public static ListNode swapNodes(ListNode head, int k) {
        ListNode first = head;
        for (int i = 1; i < k; i++) first = first.next;
        ListNode f = first, second = head;
        while (f.next != null) { f = f.next; second = second.next; }
        int t = first.val; first.val = second.val; second.val = t;
        return head;
    }
    public static void main(String[] a) { System.out.println(str(swapNodes(build(1,2,3,4,5), 2))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
