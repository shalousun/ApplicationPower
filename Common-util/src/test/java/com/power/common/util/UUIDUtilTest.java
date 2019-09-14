package com.power.common.util;

import org.junit.Test;

/**
 * @author yu 2018/10/23.
 */
public class UUIDUtilTest {

    @Test
    public void testGetUUid() {
        System.out.println(UUIDUtil.getUuid());
    }

    @Test
    public void testGetUuid32() {
        System.out.println(UUIDUtil.getUuid32());
    }
}
