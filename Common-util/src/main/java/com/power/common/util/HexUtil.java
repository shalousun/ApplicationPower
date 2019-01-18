package com.power.common.util;

/**
 * @author yu 2018/11/30.
 */
public class HexUtil {

    /**
     * 将byte数组转换成16进制字符串
     *
     * @param buf byte数组
     * @return String
     */
    public static String byteArr2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串转换为byte数组
     *
     * @param hexStr 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStr2ByteArr(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() >> 1];
        for (int i = 0; i < hexStr.length() >> 1; i++) {
            int high = Integer.parseInt(hexStr.substring(i << 1, (i << 1) + 1), 16);
            int low = Integer.parseInt(hexStr.substring((i << 1) + 1, (i + 1) << 1), 16);
            result[i] = (byte) ((high << 4) + low);
        }
        return result;
    }
}
