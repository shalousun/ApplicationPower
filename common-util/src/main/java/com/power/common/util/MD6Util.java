package com.power.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;
import java.util.Random;

/**
 * MD6Util
 * @apiNote md6 decrypt
 * @javadoc
 * @author yu 2018/06/09.
 */
public class MD6Util {

    private static final String CHS_STR = "0123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * md6 encryption
     *
     * @param content plaintext
     * @return String
     */
    public static String md6(String content) {
        Random rd = new Random();
        int rd1 = rd.nextInt(35);
        rd = new Random();
        int rd2 = rd.nextInt(35);
        String rdStr = CHS_STR.charAt(rd1) + String.valueOf(CHS_STR.charAt(rd2));
        String str = DigestUtils.md5Hex(rdStr + content);
        str = rdStr + str.substring(0, 30);
        return str;
    }

    /**
     * md6 checksums
     *
     * @param md6Str    md6 checksums
     * @param plaintext plaintext
     * @return boolean
     */
    public static boolean equal(String md6Str, String plaintext) {
        if (Objects.isNull(md6Str) || md6Str.length() != 32) {
            return false;
        }
        String rdStr = md6Str.substring(0, 2);
        String str = DigestUtils.md5Hex(rdStr + plaintext);
        str = rdStr + str.substring(0, 30);
        return str.equals(md6Str);
    }


}
