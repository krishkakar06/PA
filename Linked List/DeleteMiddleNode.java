import java.util.*;
public class DeleteMiddleNode {
    public static ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) return null;
        ListNode slow = head, fast = head.next.next;
        while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
        slow.next = slow.next.next;
        return head;
    }
    public static void main(String[] a) { System.out.println(str(deleteMiddle(build(1,3,4,7,1,2,6)))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
