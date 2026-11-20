import java.util.*;
public class NthNodeFromEnd {
    public static int nthFromEnd(ListNode head, int n) {
        ListNode f = head, s = head;
        for (int i = 0; i < n; i++) { if (f == null) return -1; f = f.next; }
        while (f != null) { f = f.next; s = s.next; }
        return s.val;
    }
    public static void main(String[] a) { System.out.println(nthFromEnd(build(1,2,3,4,5), 2)); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
