import java.util.*;
public class CircularLinkedListMiddle {
    // second middle for even length
    public static Node middle(Node head) {
        if (head == null) return null;
        Node slow = head, fast = head;
        do { slow = slow.next; fast = fast.next.next; } while (fast != head && fast.next != head);
        return slow;
    }
    public static void main(String[] a) { System.out.println(middle(build(1,2,3,4,5)).val + " " + middle(build(1,2,3,4)).val); }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
