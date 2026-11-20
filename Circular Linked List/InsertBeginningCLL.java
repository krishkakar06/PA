import java.util.*;
public class InsertBeginningCLL {
    // CLL Q3
    public static Node insertBeginning(Node head, int v) {
        Node n = new Node(v);
        if (head == null) { n.next = n; return n; }
        Node tail = head; while (tail.next != head) tail = tail.next;
        n.next = head; tail.next = n;
        return n;
    }
    public static void main(String[] a) { System.out.println(str(insertBeginning(build(2,3,4), 1))); }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
