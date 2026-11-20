import java.util.*;
public class FindAllNumbersDisappeared { // LC 448
    public static List<Integer> findDisappeared(int[] nums) {
        for (int x : nums) {
            int i = Math.abs(x) - 1;
            if (nums[i] > 0) nums[i] = -nums[i];
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) if (nums[i] > 0) res.add(i + 1);
        return res;
    }
    public static void main(String[] a) { System.out.println(findDisappeared(new int[]{4,3,2,7,8,2,3,1})); }
}
