import java.util.*;
public class MergeSort {
    public static void sort(int[] a, int l, int r) {
        if (l >= r) return;
        int m = (l + r) / 2;
        sort(a, l, m); sort(a, m + 1, r);
        int[] t = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;
        while (i <= m && j <= r) t[k++] = a[i] <= a[j] ? a[i++] : a[j++];
        while (i <= m) t[k++] = a[i++];
        while (j <= r) t[k++] = a[j++];
        System.arraycopy(t, 0, a, l, t.length);
    }
    public static void main(String[] x) { int[] a = {5,2,9,1,6}; sort(a, 0, a.length - 1); System.out.println(Arrays.toString(a)); }
}
