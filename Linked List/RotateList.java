import java.util.*;
public class RotateList {
    // LC 61
    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;
        int len = 1; ListNode tail = head;
        while (tail.next != null) { tail = tail.next; len++; }
        k %= len; if (k == 0) return head;
        ListNode p = head;
        for (int i = 0; i < len - k - 1; i++) p = p.next;
        ListNode nh = p.next; p.next = null; tail.next = head;
        return nh;
    }
    public static void main(String[] a) { System.out.println(str(rotateRight(build(1,2,3,4,5), 2))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
