import java.util.*;
public class SplitHalvesCLL {
    // splits into two circular lists; first half gets the extra node when odd
    public static Node[] split(Node head) {
        if (head == null || head.next == head) return new Node[]{head, null};
        Node slow = head, fast = head;
        while (fast.next != head && fast.next.next != head) { slow = slow.next; fast = fast.next.next; }
        if (fast.next.next == head) fast = fast.next;   // fast = tail
        Node head2 = slow.next;
        fast.next = head2; slow.next = head;
        return new Node[]{head, head2};
    }
    public static void main(String[] a) {
        for (int n : new int[]{4, 5}) {
            int[] arr = new int[n]; for (int i = 0; i < n; i++) arr[i] = i + 1;
            Node[] r = split(build(arr));
            System.out.println(str(r[0]) + "   |   " + str(r[1]));
        }
    }

    static class Node { int val; Node next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; t = n; } } if (t != null) t.next = h; return h; }
    static String str(Node h) { if (h == null) return "(empty)"; StringBuilder sb = new StringBuilder(); Node c = h; do { sb.append(c.val).append(c.next != h ? " -> " : " -> (head)"); c = c.next; } while (c != h); return sb.toString(); }

}
