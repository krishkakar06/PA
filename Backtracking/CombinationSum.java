import java.util.*;
public class CombinationSum { // LC 39
    public static List<List<Integer>> combinationSum(int[] c, int target) {
        List<List<Integer>> res = new ArrayList<>();
        bt(c, target, 0, new ArrayList<>(), res);
        return res;
    }
    static void bt(int[] c, int rem, int start, List<Integer> cur, List<List<Integer>> res) {
        if (rem == 0) { res.add(new ArrayList<>(cur)); return; }
        for (int i = start; i < c.length; i++) {
            if (c[i] > rem) continue;
            cur.add(c[i]); bt(c, rem - c[i], i, cur, res); cur.remove(cur.size() - 1);
        }
    }
    public static void main(String[] x) { System.out.println(combinationSum(new int[]{2,3,6,7}, 7)); }
}
