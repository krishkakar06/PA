import java.util.*;
public class LinkedListCount {
    public static int count(ListNode h) { int c = 0; for (; h != null; h = h.next) c++; return c; }
    public static void main(String[] a) { System.out.println(count(build(1,2,3,4))); }

    static class ListNode { int val; ListNode next; ListNode(int v) { val = v; } }
    static ListNode build(int... a) { ListNode d = new ListNode(0), t = d; for (int x : a) { t.next = new ListNode(x); t = t.next; } return d.next; }
    static String str(ListNode h) { StringBuilder sb = new StringBuilder(); for (; h != null; h = h.next) sb.append(h.val).append(h.next != null ? "->" : ""); return sb.toString(); }

}
