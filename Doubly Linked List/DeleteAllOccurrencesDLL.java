import java.util.*;
public class DeleteAllOccurrencesDLL {
    // DLL Q2
    public static Node deleteAll(Node head, int key) {
        Node cur = head;
        while (cur != null) {
            Node nxt = cur.next;
            if (cur.val == key) {
                if (cur.prev != null) cur.prev.next = cur.next; else head = cur.next;
                if (cur.next != null) cur.next.prev = cur.prev;
            }
            cur = nxt;
        }
        return head;
    }
    public static void main(String[] a) { System.out.println(str(deleteAll(build(2,1,2,3,2), 2))); }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
