import java.util.*;
public class FindMaxCLL {
    // CLL Q5
    public static int findMax(Node head) {
        int max = head.val; Node c = head.next;
        while (c != head) { max = Math.max(max, c.val); c = c.next; }
        return max;
    }
    public static void main(String[] a) { System.out.println(findMax(build(3,9,4,7))); }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
