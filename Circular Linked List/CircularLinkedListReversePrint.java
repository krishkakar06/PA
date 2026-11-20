import java.util.*;
public class CircularLinkedListReversePrint {
    static void printReverse(Node n, Node head) {
        if (n.next != head) printReverse(n.next, head);
        System.out.print(n.val + " ");
    }
    public static void main(String[] a) { Node h = build(1,2,3,4,5); printReverse(h, h); System.out.println(); }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
