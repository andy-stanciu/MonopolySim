package util;

import lot.Property;

import java.util.Set;

public class Util {
    public static String formatSet(Set<Property> set) {
        if (set.isEmpty()) {
            return "None";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Property property : set) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(property);
            i++;
        }
        return sb.toString();
    }
}
