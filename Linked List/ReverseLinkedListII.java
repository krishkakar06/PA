import java.util.*;
public class ReverseLinkedListII {
    // LC 92
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode d = new ListNode(0); d.next = head;
        ListNode pre = d;
        for (int i = 1; i < left; i++) pre = pre.next;
        ListNode cur = pre.next;
        for (int i = 0; i < right - left; i++) {
            ListNode nxt = cur.next; cur.next = nxt.next; nxt.next = pre.next; pre.next = nxt;
        }
        return d.next;
    }
    public static void main(String[] a) { System.out.println(str(reverseBetween(build(1,2,3,4,5), 2, 4))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
