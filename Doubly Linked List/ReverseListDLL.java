import java.util.*;
public class ReverseListDLL {
    // DLL Q3 - reverse by swapping node values with two pointers
    public static Node reverse(Node head) {
        if (head == null) return null;
        Node l = head, r = head; while (r.next != null) r = r.next;
        while (l != r && l.prev != r) { int t = l.val; l.val = r.val; r.val = t; l = l.next; r = r.prev; }
        return head;
    }
    public static void main(String[] a) { System.out.println(str(reverse(build(1,2,3,4,5)))); }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
