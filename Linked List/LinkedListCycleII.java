import java.util.*;
public class LinkedListCycleII {
    // LC 142 - returns node where cycle begins
    public static ListNode detectCycle(ListNode head) {
        ListNode s = head, f = head;
        while (f != null && f.next != null) {
            s = s.next; f = f.next.next;
            if (s == f) {
                s = head;
                while (s != f) { s = s.next; f = f.next; }
                return s;
            }
        }
        return null;
    }
    public static void main(String[] a) {
        ListNode h = build(3,2,0,-4); h.next.next.next.next = h.next;
        System.out.println(detectCycle(h).val);
    }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
