public class SearchInRotatedSortedArray { // LC 33
    public static int search(int[] a, int t) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == t) return mid;
            if (a[lo] <= a[mid]) {
                if (a[lo] <= t && t < a[mid]) hi = mid - 1; else lo = mid + 1;
            } else {
                if (a[mid] < t && t <= a[hi]) lo = mid + 1; else hi = mid - 1;
            }
        }
        return -1;
    }
    public static void main(String[] x) { System.out.println(search(new int[]{4,5,6,7,0,1,2}, 0)); }
}
