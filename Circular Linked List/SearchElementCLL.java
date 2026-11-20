import java.util.*;
public class SearchElementCLL {
    // CLL Q2 - returns index or -1
    public static int search(Node head, int key) {
        if (head == null) return -1;
        Node c = head; int i = 0;
        do { if (c.val == key) return i; c = c.next; i++; } while (c != head);
        return -1;
    }
    public static void main(String[] a) { System.out.println(search(build(5,8,2,9), 2) + " " + search(build(5,8), 7)); }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
