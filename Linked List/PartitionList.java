import java.util.*;
public class PartitionList {
    // LC 86
    public static ListNode partition(ListNode head, int x) {
        ListNode lo = new ListNode(0), hi = new ListNode(0), l = lo, h = hi;
        for (; head != null; head = head.next) {
            if (head.val < x) { l.next = head; l = l.next; } else { h.next = head; h = h.next; }
        }
        h.next = null; l.next = hi.next;
        return lo.next;
    }
    public static void main(String[] a) { System.out.println(str(partition(build(1,4,3,2,5,2), 3))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
