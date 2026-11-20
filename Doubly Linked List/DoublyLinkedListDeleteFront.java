import java.util.*;
public class DoublyLinkedListDeleteFront {
    public static Node deleteFront(Node head) {
        if (head == null) return null;
        head = head.next;
        if (head != null) head.prev = null;
        return head;
    }
    public static void main(String[] a) { System.out.println(str(deleteFront(build(1,2,3)))); }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
