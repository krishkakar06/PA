import java.util.*;
public class DoublyLinkedListTraversal {
    public static void main(String[] a) {
        Node head = build(1,2,3,4);
        System.out.print("Forward : "); Node tail = null;
        for (Node c = head; c != null; c = c.next) { System.out.print(c.val + " "); tail = c; }
        System.out.print("\nBackward: ");
        for (Node c = tail; c != null; c = c.prev) System.out.print(c.val + " ");
        System.out.println();
    }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
