import java.util.*;
public class CombinationSumII { // LC 40
    public static List<List<Integer>> combinationSum2(int[] c, int target) {
        Arrays.sort(c);
        List<List<Integer>> res = new ArrayList<>();
        bt(c, target, 0, new ArrayList<>(), res);
        return res;
    }
    static void bt(int[] c, int rem, int start, List<Integer> cur, List<List<Integer>> res) {
        if (rem == 0) { res.add(new ArrayList<>(cur)); return; }
        for (int i = start; i < c.length && c[i] <= rem; i++) {
            if (i > start && c[i] == c[i-1]) continue;
            cur.add(c[i]); bt(c, rem - c[i], i + 1, cur, res); cur.remove(cur.size() - 1);
        }
    }
    public static void main(String[] x) { System.out.println(combinationSum2(new int[]{10,1,2,7,6,1,5}, 8)); }
}
