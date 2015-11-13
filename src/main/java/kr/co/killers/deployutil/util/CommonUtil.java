package kr.co.killers.deployutil.util;

public class CommonUtil {

    public static String arrayToString(Object[] a, String delimiter) {
        if (a == null)
            return "";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "";
        StringBuffer b = new StringBuffer();
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.toString();
            b.append(delimiter);
        }
    }

}
