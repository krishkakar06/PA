import java.util.*;
public class SecondLargestDLL {
    // DLL Q4 - second largest distinct value (null if none)
    public static Integer secondLargest(Node head) {
        Integer max = null, second = null;
        for (Node c = head; c != null; c = c.next) {
            if (max == null || c.val > max) { second = max; max = c.val; }
            else if (c.val < max && (second == null || c.val > second)) second = c.val;
        }
        return second;
    }
    public static void main(String[] a) { System.out.println(secondLargest(build(10,5,20,20,8))); }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
