package com.power.common.util;

/**
 * @author yolanda0608
 * @since JDK 1.7+
 */
public class ObjectUtil {
    /**
     * isNotEmpty:(检查对象是否为空，不为空返回true)
     *
     * @param object Object
     * @return boolean
     */
    public static boolean isNotEmpty(Object object) {
        if (object != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * isNotEmpty:(检查对象是否为空，为空返回true)
     *
     * @param object Object
     * @return boolean
     */
    public static boolean isEmpty(Object object) {
        return !isNotEmpty(object);
    }
}
