import java.util.*;
public class Subsets { // LC 78
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        bt(nums, 0, new ArrayList<>(), res);
        return res;
    }
    static void bt(int[] n, int start, List<Integer> cur, List<List<Integer>> res) {
        res.add(new ArrayList<>(cur));
        for (int i = start; i < n.length; i++) { cur.add(n[i]); bt(n, i + 1, cur, res); cur.remove(cur.size() - 1); }
    }
    public static void main(String[] x) { System.out.println(subsets(new int[]{1,2,3})); }
}
