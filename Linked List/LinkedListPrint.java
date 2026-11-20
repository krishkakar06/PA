import java.util.*;
public class LinkedListPrint {
    public static void print(ListNode h) { for (; h != null; h = h.next) System.out.print(h.val + (h.next != null ? " -> " : "\n")); }
    public static void main(String[] a) { print(build(1,2,3,4)); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
