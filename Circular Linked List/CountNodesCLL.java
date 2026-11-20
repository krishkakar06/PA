import java.util.*;
public class CountNodesCLL {
    // CLL Q1
    public static int count(Node head) {
        if (head == null) return 0;
        int c = 0; Node cur = head;
        do { c++; cur = cur.next; } while (cur != head);
        return c;
    }
    public static void main(String[] a) { System.out.println(count(build(1,2,3,4))); }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
