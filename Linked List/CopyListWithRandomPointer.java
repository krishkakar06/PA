public class CopyListWithRandomPointer { // LC 138
    static class Node { int val; Node next, random; Node(int v) { val = v; } }
    public static Node copyRandomList(Node head) {
        if (head == null) return null;
        for (Node c = head; c != null; c = c.next.next) { Node n = new Node(c.val); n.next = c.next; c.next = n; }
        for (Node c = head; c != null; c = c.next.next) if (c.random != null) c.next.random = c.random.next;
        Node newHead = head.next;
        for (Node c = head; c != null; c = c.next) {
            Node copy = c.next; c.next = copy.next;
            copy.next = copy.next != null ? copy.next.next : null;
        }
        return newHead;
    }
    public static void main(String[] a) {
        Node n1 = new Node(7), n2 = new Node(13), n3 = new Node(11);
        n1.next = n2; n2.next = n3; n2.random = n1; n3.random = n1;
        Node c = copyRandomList(n1);
        for (; c != null; c = c.next) System.out.println(c.val + " random=" + (c.random == null ? "null" : c.random.val));
    }
}
