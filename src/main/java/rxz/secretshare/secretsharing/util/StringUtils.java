package rxz.secretshare.secretsharing.util;

import java.util.List;

public class StringUtils {

    public static String vectorToString(List<? extends Number> vector) {
        String y = "(";
        for (int i = 0; i < vector.size() - 1; i++) {
            String value = vector.get(i).toString();
            y += value + ",";
        }
        y += vector.get(vector.size() - 1) + ")";
        return y;
    }

}
