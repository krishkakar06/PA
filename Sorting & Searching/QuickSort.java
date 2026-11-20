import java.util.*;
public class QuickSort {
    public static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = a[hi], i = lo;
        for (int j = lo; j < hi; j++) if (a[j] < p) { int t = a[i]; a[i] = a[j]; a[j] = t; i++; }
        int t = a[i]; a[i] = a[hi]; a[hi] = t;
        sort(a, lo, i - 1); sort(a, i + 1, hi);
    }
    public static void main(String[] x) { int[] a = {7,2,1,6,8,5,3,4}; sort(a, 0, a.length - 1); System.out.println(Arrays.toString(a)); }
}
