package com.power.common.util;


import com.power.common.constants.Charset;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64Uti
 * @javadoc
 * @author yu 2018/12/08.
 */
public class Base64Util {

    /**
     * BASE64 decryption
     *
     * @param encryptedData encrypted byte array
     * @return byte array
     */
    public static byte[] decryptBASE64(byte[] encryptedData) {
        return Base64.getDecoder().decode(encryptedData);
    }

    /**
     * BASE64 decryption
     *
     * @param encryptedData encrypted string
     * @return a byte array
     */
    public static byte[] decryptBASE64(String encryptedData) {
        try {
            return decryptBASE64(encryptedData.getBytes(Charset.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding:" + e);
        }
    }

    /**
     * BASE64 decryption
     *
     * @param encryptedData encrypted string
     * @return String
     */
    public static String decryptToString(String encryptedData) {
        try {
            return decryptToString(encryptedData.getBytes(Charset.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding:" + e);
        }
    }

    /**
     * BASE64 decryption
     *
     * @param encryptedData encrypted byte array
     * @return String
     */
    public static String decryptToString(byte[] encryptedData) {
        return new String(decryptBASE64(encryptedData));
    }

    /**
     * BASE64 encryption
     *
     * @param decryptedData byte array to be encrypted
     * @return a byte array
     */
    public static byte[] encryptBASE64(byte[] decryptedData) {
        return Base64.getEncoder().encode(decryptedData);
    }

    /**
     * BASE64 encryption
     *
     * @param plaintext String to be encrypted
     * @return a byte array
     */
    public static byte[] encryptBASE64(String plaintext) {
        try {
            return encryptBASE64(plaintext.getBytes(Charset.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding:" + e);
        }
    }

    /**
     * BASE64 encryption
     *
     * @param plaintext String to be encrypted
     * @return String
     */
    public static String encryptToString(String plaintext) {
        try {
            return encryptToString(plaintext.getBytes(Charset.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding:" + e);
        }
    }

    /**
     * BASE64 encryption
     *
     * @param plainByteArr byte array to be encrypted
     * @return String
     */
    public static String encryptToString(byte[] plainByteArr) {
        return new String(encryptBASE64(plainByteArr));
    }

}
