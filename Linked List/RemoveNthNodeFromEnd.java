import java.util.*;
public class RemoveNthNodeFromEnd {
    // LC 19
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode d = new ListNode(0); d.next = head;
        ListNode f = d, s = d;
        for (int i = 0; i <= n; i++) f = f.next;
        while (f != null) { f = f.next; s = s.next; }
        s.next = s.next.next;
        return d.next;
    }
    public static void main(String[] a) { System.out.println(str(removeNthFromEnd(build(1,2,3,4,5), 2))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
