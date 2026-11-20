import java.util.*;
public class SubsetsII { // LC 90
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        bt(nums, 0, new ArrayList<>(), res);
        return res;
    }
    static void bt(int[] n, int start, List<Integer> cur, List<List<Integer>> res) {
        res.add(new ArrayList<>(cur));
        for (int i = start; i < n.length; i++) {
            if (i > start && n[i] == n[i-1]) continue;
            cur.add(n[i]); bt(n, i + 1, cur, res); cur.remove(cur.size() - 1);
        }
    }
    public static void main(String[] x) { System.out.println(subsetsWithDup(new int[]{1,2,2})); }
}
