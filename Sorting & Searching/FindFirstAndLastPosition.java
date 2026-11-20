import java.util.*;
public class FindFirstAndLastPosition { // LC 34
    static int bound(int[] a, int t, boolean first) {
        int lo = 0, hi = a.length - 1, ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == t) { ans = mid; if (first) hi = mid - 1; else lo = mid + 1; }
            else if (a[mid] < t) lo = mid + 1; else hi = mid - 1;
        }
        return ans;
    }
    public static int[] searchRange(int[] nums, int target) {
        return new int[]{bound(nums, target, true), bound(nums, target, false)};
    }
    public static void main(String[] x) { System.out.println(Arrays.toString(searchRange(new int[]{5,7,7,8,8,10}, 8))); }
}
