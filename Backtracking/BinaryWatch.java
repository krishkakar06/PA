import java.util.*;
public class BinaryWatch { // LC 401
    public static List<String> readBinaryWatch(int turnedOn) {
        List<String> res = new ArrayList<>();
        for (int h = 0; h < 12; h++)
            for (int m = 0; m < 60; m++)
                if (Integer.bitCount(h) + Integer.bitCount(m) == turnedOn)
                    res.add(h + ":" + (m < 10 ? "0" : "") + m);
        return res;
    }
    public static void main(String[] x) { System.out.println(readBinaryWatch(1)); }
}
