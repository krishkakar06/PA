import java.util.*;
public class ReorderList {
    // LC 143
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        ListNode s = head, f = head;
        while (f.next != null && f.next.next != null) { s = s.next; f = f.next.next; }
        ListNode second = s.next, prev = null; s.next = null;
        while (second != null) { ListNode n = second.next; second.next = prev; prev = second; second = n; }
        ListNode a = head, b = prev;
        while (b != null) { ListNode an = a.next, bn = b.next; a.next = b; b.next = an; a = an; b = bn; }
    }
    public static void main(String[] a) { ListNode h = build(1,2,3,4,5); reorderList(h); System.out.println(str(h)); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
