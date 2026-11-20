import java.util.*;
public class FindAllDuplicates { // LC 442
    public static List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int x : nums) {
            int i = Math.abs(x) - 1;
            if (nums[i] < 0) res.add(i + 1);
            else nums[i] = -nums[i];
        }
        return res;
    }
    public static void main(String[] a) { System.out.println(findDuplicates(new int[]{4,3,2,7,8,2,3,1})); }
}
