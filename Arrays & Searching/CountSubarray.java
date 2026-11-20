import java.util.*;
public class CountSubarray { // count subarrays with sum == k (prefix sum + hashmap)
    public static int count(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0, res = 0;
        for (int x : nums) {
            sum += x;
            res += map.getOrDefault(sum - k, 0);
            map.merge(sum, 1, Integer::sum);
        }
        return res;
    }
    public static void main(String[] a) { System.out.println(count(new int[]{1,1,1}, 2)); }
}
