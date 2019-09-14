package com.power.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * md6算法
 *
 * @author yu 2018/06/09.
 */
public class MD6Util {

    private static final String CHS_STR = "0123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * MD6字符串
     *
     * @param content 待加密字符串
     * @return String
     */
    public static String md6(String content) {
        Random rd = new Random();
        int rd1 = rd.nextInt(35);
        rd = new Random();
        int rd2 = rd.nextInt(35);
        String rdStr = String.valueOf(CHS_STR.charAt(rd1)) + String.valueOf(CHS_STR.charAt(rd2));
        String str = DigestUtils.md5Hex(rdStr + content);
        str = rdStr + str.substring(0, 30);
        return str;
    }

    /**
     * 比较待加密的字符串加密后和加密串是否相同
     *
     * @param md6Str  md6加密串
     * @param content 待加密串
     * @return boolean
     */
    public static boolean equal(String md6Str, String content) {
        if (md6Str == null || md6Str.length() != 32) {
            return false;
        }
        String rdStr = md6Str.substring(0, 2);
        String str = DigestUtils.md5Hex(rdStr + content);
        str = rdStr + str.substring(0, 30);
        if (str.equals(md6Str)) {
            return true;
        }
        return false;
    }


}
