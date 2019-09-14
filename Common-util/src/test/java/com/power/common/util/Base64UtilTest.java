package com.power.common.util;

import com.power.common.constants.Charset;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author yu 2019/1/19.
 */
public class Base64UtilTest {

    @Test
    public void test() throws Exception {

        String str = "F++/vWvvv73vv70gKO+/vQ9G77+9fuSAug==";

        String strBase = Base64Util.decryptToString(str);

        byte[] a = Base64Util.decryptBASE64(str);

        byte[] b = strBase.getBytes(Charset.DEFAULT_CHARSET);

        System.out.println(Arrays.equals(a, b));

    }
}
