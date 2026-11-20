import java.util.*;
public class InsertAfterDLL {
    // DLL Q1 - insert newVal after first node containing key
    public static Node insertAfter(Node head, int key, int newVal) {
        for (Node c = head; c != null; c = c.next) {
            if (c.val == key) {
                Node n = new Node(newVal);
                n.prev = c; n.next = c.next;
                if (c.next != null) c.next.prev = n;
                c.next = n;
                break;
            }
        }
        return head;
    }
    public static void main(String[] a) { System.out.println(str(insertAfter(build(1,2,4), 2, 3))); }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
