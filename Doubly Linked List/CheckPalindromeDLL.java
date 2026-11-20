import java.util.*;
public class CheckPalindromeDLL {
    // DLL Q5
    public static boolean isPalindrome(Node head) {
        if (head == null) return true;
        Node r = head; while (r.next != null) r = r.next;
        for (Node l = head; l != r && l.prev != r; l = l.next, r = r.prev) if (l.val != r.val) return false;
        return true;
    }
    public static void main(String[] a) { System.out.println(isPalindrome(build(1,2,3,2,1)) + " " + isPalindrome(build(1,2,3))); }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
