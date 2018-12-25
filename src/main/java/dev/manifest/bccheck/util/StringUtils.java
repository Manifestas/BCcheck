package dev.manifest.bccheck.util;

import java.util.List;

public class StringUtils {

    public static String getStringFromList(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String s : stringList) {
            builder.append(s);
        }
        return builder.toString();
    }
}
