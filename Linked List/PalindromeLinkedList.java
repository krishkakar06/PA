import java.util.*;
public class PalindromeLinkedList {
    // LC 234
    public static boolean isPalindrome(ListNode head) {
        ListNode s = head, f = head;
        while (f != null && f.next != null) { s = s.next; f = f.next.next; }
        ListNode prev = null;
        while (s != null) { ListNode n = s.next; s.next = prev; prev = s; s = n; }
        for (ListNode l = head, r = prev; r != null; l = l.next, r = r.next) if (l.val != r.val) return false;
        return true;
    }
    public static void main(String[] a) { System.out.println(isPalindrome(build(1,2,2,1)) + " " + isPalindrome(build(1,2,3))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
