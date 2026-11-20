import java.util.*;
public class PermutationsII { // LC 47
    public static List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        bt(nums, new boolean[nums.length], new ArrayList<>(), res);
        return res;
    }
    static void bt(int[] n, boolean[] used, List<Integer> cur, List<List<Integer>> res) {
        if (cur.size() == n.length) { res.add(new ArrayList<>(cur)); return; }
        for (int i = 0; i < n.length; i++) {
            if (used[i] || (i > 0 && n[i] == n[i-1] && !used[i-1])) continue;
            used[i] = true; cur.add(n[i]); bt(n, used, cur, res); cur.remove(cur.size() - 1); used[i] = false;
        }
    }
    public static void main(String[] x) { System.out.println(permuteUnique(new int[]{1,1,2})); }
}
