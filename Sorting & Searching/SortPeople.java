import java.util.*;
public class SortPeople { // LC 2418
    public static String[] sortPeople(String[] names, int[] heights) {
        Integer[] idx = new Integer[names.length];
        for (int i = 0; i < idx.length; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> heights[b] - heights[a]);
        String[] res = new String[names.length];
        for (int i = 0; i < idx.length; i++) res[i] = names[idx[i]];
        return res;
    }
    public static void main(String[] x) {
        System.out.println(Arrays.toString(sortPeople(new String[]{"Mary","John","Emma"}, new int[]{180,165,170})));
    }
}
