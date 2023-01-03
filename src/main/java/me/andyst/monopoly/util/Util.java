package me.andyst.monopoly.util;

import java.util.Collection;

public class Util {
    public static String formatCollection(Collection<?> coll) {
        if (coll.isEmpty()) {
            return "None";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Object object : coll) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(object);
            i++;
        }
        return sb.toString();
    }
}
