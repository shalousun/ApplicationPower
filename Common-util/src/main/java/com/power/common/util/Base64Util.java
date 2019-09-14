package com.power.common.util;


import com.power.common.constants.Charset;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * base64工具类
 *
 * @author yu 2018/12/08.
 */
public class Base64Util {

    /**
     * BASE64解密
     *
     * @param encryptedData 已加密的字节数组
     * @return byte array
     */
    public static byte[] decryptBASE64(byte[] encryptedData) {
        return Base64.getDecoder().decode(encryptedData);
    }

    /**
     * BASE64解密
     *
     * @param encryptedData 已加密的字符串
     * @return a byte array
     */
    public static byte[] decryptBASE64(String encryptedData) {
        try {
            return decryptBASE64(encryptedData.getBytes(Charset.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码方式：" + e);
        }
    }

    /**
     * BASE64解密
     *
     * @param encryptedData 已加密的字节数组
     * @return String
     */
    public static String decryptToString(String encryptedData) {
        try {
            return decryptToString(encryptedData.getBytes(Charset.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码方式：" + e);
        }
    }

    /**
     * BASE64解密
     *
     * @param encryptedData 已加密的字节数组
     * @return String
     */
    public static String decryptToString(byte[] encryptedData) {
        return new String(decryptBASE64(encryptedData));
    }

    /**
     * BASE64加密
     *
     * @param decryptedData 已加密的字节数组
     * @return a byte array
     */
    public static byte[] encryptBASE64(byte[] decryptedData) {
        return Base64.getEncoder().encode(decryptedData);
    }

    /**
     * base64加密
     *
     * @param plaintext 待加密字符串
     * @return a byte array
     */
    public static byte[] encryptBASE64(String plaintext) {
        try {
            return encryptBASE64(plaintext.getBytes(Charset.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码方式：" + e);
        }
    }

    /**
     * base64加密
     *
     * @param plaintext 待加密字符串
     * @return String
     */
    public static String encryptToString(String plaintext) {
        try {
            return encryptToString(plaintext.getBytes(Charset.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码方式：" + e);
        }
    }

    /**
     * base64加密
     *
     * @param plainByteArr 待加密的字节数组
     * @return String
     */
    public static String encryptToString(byte[] plainByteArr) {
        return new String(encryptBASE64(plainByteArr));
    }

}
