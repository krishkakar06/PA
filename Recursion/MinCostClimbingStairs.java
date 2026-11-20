public class MinCostClimbingStairs { // LC 746
    public static int minCostClimbingStairs(int[] cost) {
        int a = 0, b = 0;
        for (int i = 2; i <= cost.length; i++) {
            int c = Math.min(b + cost[i-1], a + cost[i-2]);
            a = b; b = c;
        }
        return b;
    }
    public static void main(String[] x) { System.out.println(minCostClimbingStairs(new int[]{10,15,20})); }
}
