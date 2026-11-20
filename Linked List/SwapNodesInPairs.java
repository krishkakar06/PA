import java.util.*;
public class SwapNodesInPairs {
    // LC 24
    public static ListNode swapPairs(ListNode head) {
        ListNode d = new ListNode(0); d.next = head;
        ListNode p = d;
        while (p.next != null && p.next.next != null) {
            ListNode a = p.next, b = a.next;
            a.next = b.next; b.next = a; p.next = b; p = a;
        }
        return d.next;
    }
    public static void main(String[] a) { System.out.println(str(swapPairs(build(1,2,3,4)))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
