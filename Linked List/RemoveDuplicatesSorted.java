import java.util.*;
public class RemoveDuplicatesSorted {
    // LC 83
    public static ListNode deleteDuplicates(ListNode head) {
        for (ListNode c = head; c != null && c.next != null; ) {
            if (c.val == c.next.val) c.next = c.next.next; else c = c.next;
        }
        return head;
    }
    public static void main(String[] a) { System.out.println(str(deleteDuplicates(build(1,1,2,3,3)))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
