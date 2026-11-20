import java.util.*;
public class PascalsTriangle { // LC 118
    public static List<List<Integer>> generate(int n) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++)
                row.add(j == 0 || j == i ? 1 : res.get(i-1).get(j-1) + res.get(i-1).get(j));
            res.add(row);
        }
        return res;
    }
    public static void main(String[] a) { System.out.println(generate(5)); }
}
