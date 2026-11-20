import java.util.*;
public class DoublyLinkedListReverse {
    // swap prev/next pointers of every node
    public static Node reverse(Node head) {
        Node cur = head, newHead = null;
        while (cur != null) {
            Node t = cur.next; cur.next = cur.prev; cur.prev = t;
            newHead = cur; cur = t;
        }
        return newHead;
    }
    public static void main(String[] a) { System.out.println(str(reverse(build(1,2,3,4)))); }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
