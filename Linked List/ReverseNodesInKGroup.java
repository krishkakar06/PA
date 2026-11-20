import java.util.*;
public class ReverseNodesInKGroup {
    // LC 25
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode d = new ListNode(0); d.next = head;
        ListNode prev = d;
        while (true) {
            ListNode end = prev;
            for (int i = 0; i < k && end != null; i++) end = end.next;
            if (end == null) break;
            ListNode start = prev.next, next = end.next;
            end.next = null;
            ListNode p = null, c = start;
            while (c != null) { ListNode n = c.next; c.next = p; p = c; c = n; }
            prev.next = p; start.next = next; prev = start;
        }
        return d.next;
    }
    public static void main(String[] a) { System.out.println(str(reverseKGroup(build(1,2,3,4,5), 2))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
