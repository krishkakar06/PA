public class ClimbingStairs { // LC 70
    public static int climbStairs(int n) {
        int a = 1, b = 1;
        for (int i = 2; i <= n; i++) { int c = a + b; a = b; b = c; }
        return b;
    }
    public static void main(String[] x) { System.out.println(climbStairs(5)); }
}
