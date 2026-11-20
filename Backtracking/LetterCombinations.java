import java.util.*;
public class LetterCombinations { // LC 17
    static final String[] MAP = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    public static List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.isEmpty()) return res;
        bt(digits, 0, new StringBuilder(), res);
        return res;
    }
    static void bt(String d, int i, StringBuilder sb, List<String> res) {
        if (i == d.length()) { res.add(sb.toString()); return; }
        for (char c : MAP[d.charAt(i) - '0'].toCharArray()) {
            sb.append(c); bt(d, i + 1, sb, res); sb.deleteCharAt(sb.length() - 1);
        }
    }
    public static void main(String[] x) { System.out.println(letterCombinations("23")); }
}
