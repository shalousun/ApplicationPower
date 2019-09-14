package com.power.common.util;

import java.util.UUID;

/**
 * @author yu 2018/10/14.
 */
public class UUIDUtil {


    /**
     * uuid
     *
     * @return string
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 32位uuid
     *
     * @return string
     */
    public static String getUuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
