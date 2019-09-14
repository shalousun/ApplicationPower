package com.power.common.util;

import org.junit.Test;

/**
 * Description:
 * MD6Util的单元测试
 *
 * @author yu 2018/06/09.
 */
public class MD6UtilTest {

    @Test
    public void testMd6() {
        String content = "helloi";
        String encodeStr = MD6Util.md6(content);
        System.out.println("After md6 encode: " + encodeStr);
        boolean decodeStr = MD6Util.equal(encodeStr, content);
        System.out.println("is equal:" + decodeStr);
    }
}
