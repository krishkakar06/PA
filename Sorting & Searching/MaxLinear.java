public class MaxLinear { // maximum element via linear scan
    public static int max(int[] a) {
        int m = a[0];
        for (int x : a) if (x > m) m = x;
        return m;
    }
    public static void main(String[] x) { System.out.println(max(new int[]{4,2,9,7})); }
}
