package org.simpleframework.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ValidationUtil {
    // 对collections是否为null 或者size为0的判断
    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(String obj) {
        return obj == null || "".equals(obj);
    }

    public static boolean isEmpty(List<?> obj) {
        return obj == null || obj.isEmpty();
    }

}
