import java.util.*;
public class DesignLinkedListDLL {
    // LC 707 using a doubly linked list with sentinels
    static class MyLinkedList {
        Node head = new Node(0), tail = new Node(0); int size = 0;
        MyLinkedList() { head.next = tail; tail.prev = head; }
        Node at(int i) {
            Node c;
            if (i < size / 2) { c = head.next; while (i-- > 0) c = c.next; }
            else { c = tail; for (int k = size - i; k > 0; k--) c = c.prev; }
            return c;
        }
        int get(int i) { return (i < 0 || i >= size) ? -1 : at(i).val; }
        void addAtHead(int v) { addAtIndex(0, v); }
        void addAtTail(int v) { addAtIndex(size, v); }
        void addAtIndex(int i, int v) {
            if (i < 0 || i > size) return;
            Node nxt = (i == size) ? tail : at(i), n = new Node(v);
            n.prev = nxt.prev; n.next = nxt; nxt.prev.next = n; nxt.prev = n; size++;
        }
        void deleteAtIndex(int i) {
            if (i < 0 || i >= size) return;
            Node n = at(i); n.prev.next = n.next; n.next.prev = n.prev; size--;
        }
    }
    public static void main(String[] a) {
        MyLinkedList l = new MyLinkedList();
        l.addAtHead(1); l.addAtTail(3); l.addAtIndex(1, 2);
        System.out.println(l.get(1)); l.deleteAtIndex(1); System.out.println(l.get(1));
    }

    static class Node { int val; Node prev, next; Node(int v) { val = v; } }
    static Node build(int... a) { Node h = null, t = null; for (int x : a) { Node n = new Node(x); if (h == null) h = t = n; else { t.next = n; n.prev = t; t = n; } } return h; }
    static String str(Node h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? " <-> " : ""); return sb.toString(); }

}
