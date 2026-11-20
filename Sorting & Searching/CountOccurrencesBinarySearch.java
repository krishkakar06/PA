public class CountOccurrencesBinarySearch {
    static int bound(int[] a, int t, boolean first) {
        int lo = 0, hi = a.length - 1, ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == t) { ans = mid; if (first) hi = mid - 1; else lo = mid + 1; }
            else if (a[mid] < t) lo = mid + 1; else hi = mid - 1;
        }
        return ans;
    }
    public static int count(int[] a, int t) {
        int f = bound(a, t, true);
        return f == -1 ? 0 : bound(a, t, false) - f + 1;
    }
    public static void main(String[] x) { System.out.println(count(new int[]{1,2,2,2,3,4}, 2)); }
}
