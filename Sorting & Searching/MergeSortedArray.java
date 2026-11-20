import java.util.*;
public class MergeSortedArray { // LC 88
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (j >= 0) nums1[k--] = (i >= 0 && nums1[i] > nums2[j]) ? nums1[i--] : nums2[j--];
    }
    public static void main(String[] x) {
        int[] a = {1,2,3,0,0,0}; merge(a, 3, new int[]{2,5,6}, 3); System.out.println(Arrays.toString(a));
    }
}
