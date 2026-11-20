import java.util.*;
public class InsertionSortList {
    // LC 147
    public static ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        while (head != null) {
            ListNode next = head.next, p = dummy;
            while (p.next != null && p.next.val < head.val) p = p.next;
            head.next = p.next; p.next = head; head = next;
        }
        return dummy.next;
    }
    public static void main(String[] a) { System.out.println(str(insertionSortList(build(4,2,1,3)))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
