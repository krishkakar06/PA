import java.util.*;
public class DesignLinkedList {
    // LC 707
    static class MyLinkedList {
        ListNode dummy = new ListNode(0); int size = 0;
        int get(int i) { if (i < 0 || i >= size) return -1; ListNode c = dummy.next; while (i-- > 0) c = c.next; return c.val; }
        void addAtHead(int v) { addAtIndex(0, v); }
        void addAtTail(int v) { addAtIndex(size, v); }
        void addAtIndex(int i, int v) {
            if (i < 0 || i > size) return;
            ListNode p = dummy; while (i-- > 0) p = p.next;
            ListNode n = new ListNode(v); n.next = p.next; p.next = n; size++;
        }
        void deleteAtIndex(int i) {
            if (i < 0 || i >= size) return;
            ListNode p = dummy; while (i-- > 0) p = p.next;
            p.next = p.next.next; size--;
        }
    }
    public static void main(String[] a) {
        MyLinkedList l = new MyLinkedList();
        l.addAtHead(1); l.addAtTail(3); l.addAtIndex(1, 2);
        System.out.println(l.get(1)); l.deleteAtIndex(1); System.out.println(l.get(1));
    }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
