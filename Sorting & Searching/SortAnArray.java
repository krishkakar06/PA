import java.util.*;
public class SortAnArray { // LC 912 - merge sort, O(n log n)
    public static int[] sortArray(int[] nums) { mergeSort(nums, 0, nums.length - 1, new int[nums.length]); return nums; }
    static void mergeSort(int[] a, int l, int r, int[] t) {
        if (l >= r) return;
        int m = (l + r) / 2;
        mergeSort(a, l, m, t); mergeSort(a, m + 1, r, t);
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r) t[k++] = a[i] <= a[j] ? a[i++] : a[j++];
        while (i <= m) t[k++] = a[i++];
        while (j <= r) t[k++] = a[j++];
        for (k = l; k <= r; k++) a[k] = t[k];
    }
    public static void main(String[] x) { System.out.println(Arrays.toString(sortArray(new int[]{5,2,3,1}))); }
}
