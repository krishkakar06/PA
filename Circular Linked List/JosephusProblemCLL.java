import java.util.*;
public class JosephusProblemCLL {
    // n people in a circle, every k-th is eliminated; returns survivor
    public static int josephus(int n, int k) {
        int[] arr = new int[n]; for (int i = 0; i < n; i++) arr[i] = i + 1;
        Node head = build(arr);
        Node prev = head; while (prev.next != head) prev = prev.next;
        Node cur = head;
        while (cur.next != cur) {
            for (int i = 1; i < k; i++) { prev = cur; cur = cur.next; }
            prev.next = cur.next; cur = prev.next;
        }
        return cur.val;
    }
    public static void main(String[] a) { System.out.println(josephus(5, 2) + " " + josephus(7, 3)); }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
