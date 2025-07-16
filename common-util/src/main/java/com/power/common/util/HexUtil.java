package com.power.common.util;

/**
 * HexUtil
 * @javadoc
 * @author yu 2018/11/30.
 */
public class HexUtil {

    /**
     * Convert byte array to hex string
     *
     * @param buf byte array
     * @return String
     */
    public static String byteArr2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * Convert hex string to byte array
     *
     * @param hexStr hex string
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
