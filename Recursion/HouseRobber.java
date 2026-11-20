public class HouseRobber { // LC 198
    public static int rob(int[] nums) {
        int prev = 0, cur = 0;
        for (int x : nums) { int t = Math.max(cur, prev + x); prev = cur; cur = t; }
        return cur;
    }
    public static void main(String[] a) { System.out.println(rob(new int[]{2,7,9,3,1})); }
}
