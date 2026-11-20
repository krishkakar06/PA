import java.util.*;
public class LinkedListSearchMiddle {
    // LC 876 - middle node (second middle if even)
    public static ListNode middle(ListNode head) {
        ListNode s = head, f = head;
        while (f != null && f.next != null) { s = s.next; f = f.next.next; }
        return s;
    }
    public static void main(String[] a) { System.out.println(middle(build(1,2,3,4,5)).val + " " + middle(build(1,2,3,4)).val); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
