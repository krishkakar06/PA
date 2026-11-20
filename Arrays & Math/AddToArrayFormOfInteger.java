import java.util.*;
public class AddToArrayFormOfInteger { // LC 989
    public static List<Integer> addToArrayForm(int[] num, int k) {
        LinkedList<Integer> res = new LinkedList<>();
        int i = num.length - 1, carry = 0;
        while (i >= 0 || k > 0 || carry > 0) {
            int sum = carry + (i >= 0 ? num[i--] : 0) + k % 10;
            k /= 10;
            res.addFirst(sum % 10);
            carry = sum / 10;
        }
        return res;
    }
    public static void main(String[] a) { System.out.println(addToArrayForm(new int[]{1,2,0,0}, 34)); }
}
