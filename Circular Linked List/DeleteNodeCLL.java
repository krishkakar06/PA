import java.util.*;
public class DeleteNodeCLL {
    // CLL Q4 - delete first node with the given key
    public static Node delete(Node head, int key) {
        if (head == null) return null;
        Node tail = head; while (tail.next != head) tail = tail.next;
        if (head.val == key) {
            if (head == tail) return null;
            tail.next = head.next; return head.next;
        }
        Node prev = head;
        while (prev.next != head && prev.next.val != key) prev = prev.next;
        if (prev.next != head) prev.next = prev.next.next;
        return head;
    }
    public static void main(String[] a) { System.out.println(str(delete(build(1,2,3,4), 3)) + "\n" + str(delete(build(1,2,3,4), 1))); }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
