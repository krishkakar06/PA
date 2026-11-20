import java.util.*;
public class SortColors { // LC 75 - Dutch national flag
    public static void sortColors(int[] a) {
        int lo = 0, mid = 0, hi = a.length - 1;
        while (mid <= hi) {
            if (a[mid] == 0) { int t = a[lo]; a[lo++] = a[mid]; a[mid++] = t; }
            else if (a[mid] == 1) mid++;
            else { int t = a[hi]; a[hi--] = a[mid]; a[mid] = t; }
        }
    }
    public static void main(String[] x) { int[] a = {2,0,2,1,1,0}; sortColors(a); System.out.println(Arrays.toString(a)); }
}
