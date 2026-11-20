import java.util.*;
public class LinkedListSearch {
    public static int search(ListNode h, int key) {
        for (int i = 0; h != null; h = h.next, i++) if (h.val == key) return i;
        return -1;
    }
    public static void main(String[] a) { System.out.println(search(build(5,8,2,9), 2)); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
