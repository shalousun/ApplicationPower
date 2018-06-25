package com.power.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Description:
 * aes加密工具
 *
 * @author yu 2018/06/08.
 */
public class AESUtil {

    private static final String DEFAULT_CHARSET = "utf-8";

    /**
     * key 算法
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 使用AES/ECB/PKCS5Padding
     */
    private static final String AES_ECB_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * 使用AES/CBC/PKCS5Padding
     */
    private static final String AES_CBC_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 使用cbc加密解密
     *
     * @param content    待解密字节数组
     * @param key        解密的密匙
     * @param initVector 初始向量
     * @return byte[]
     */
    public static byte[] decryptByCBC(byte[] content, byte[] key, byte[] initVector) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(initVector);
            Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用cbc加密模式解密
     *
     * @param content    待加密的内容
     * @param key        加密key
     * @param initVector 初始向量
     * @return byte[]
     */
    public static byte[] encryptByCBC(byte[] content, byte[] key, byte[] initVector) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(initVector);
            Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用ecb解密
     *
     * @param content 待解密字节数组
     * @param key     解密的密匙
     * @return byte[]
     */
    public static byte[] decryptByECB(byte[] content, byte[] key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ECB_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用ecb加密模式加密
     *
     * @param content 待加密的内容
     * @param key     加密key
     * @return byte[]
     */
    public static byte[] encryptByECB(byte[] content, byte[] key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ECB_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用cbc模式解密字符串
     *
     * @param content    带解密字节数组
     * @param key        解密的密匙
     * @param initVector 初始向量
     * @return String
     */
    public static String decodeByCBC(String content, String key, String initVector) {
        AESUtil.checkParamsOfCBC(content, key, initVector);
        byte[] decryptFrom = parseHexStr2Byte(content);
        byte[] decryptResult = decryptByCBC(decryptFrom, key.getBytes(), initVector.getBytes());
        return new String(decryptResult);
    }

    /**
     * 使用cbc模式接啊
     *
     * @param content    带解密字节数组
     * @param key        解密的密匙
     * @param initVector 初始向量
     * @return String
     */
    public static String encodeByCBC(String content, String key, String initVector) {
        AESUtil.checkParamsOfCBC(content, key, initVector);
        byte[] encryptResult = null;
        try {
            encryptResult = encryptByCBC(content.getBytes(DEFAULT_CHARSET), key.getBytes(), initVector.getBytes());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encryptResultStr = parseByte2HexStr(encryptResult);
        return encryptResultStr;
    }

    /**
     * 使用ecb模式加密
     *
     * @param content 待加密的内容
     * @param key     加密key
     * @return 返回Base64转码后的加密数据
     */
    public static String encodeByECB(String content, String key) {
        AESUtil.checkContentAndKey(content, key);
        try {
            byte[] encrypted = encryptByECB(content.getBytes(DEFAULT_CHARSET), key.getBytes(DEFAULT_CHARSET));
            //BASE64 is used here as a transcoding function, which can also play a role of 2 times encryption
            return new Base64().encodeToString(encrypted);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 使用ecb模式解密
     *
     * @param content 待加密的内容
     * @param key     加密key
     * @return String
     */
    public static String decodeByECB(String content, String key) {
        AESUtil.checkContentAndKey(content, key);
        try {
            byte[] result = decryptByECB(Base64.decodeBase64(content), key.getBytes(DEFAULT_CHARSET));
            return new String(result, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 将byte数组转换成16进制
     *
     * @param buf byte数组
     * @return String
     */
    public static String parseByte2HexStr(byte buf[]) {
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
    public static byte[] parseHexStr2Byte(String hexStr) {
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


    /**
     * CBC加解密模式的参数检查
     *
     * @param content    带解密字节数组
     * @param key        解密的密匙
     * @param initVector 初始向量
     */
    private static void checkParamsOfCBC(String content, String key, String initVector) {
        AESUtil.checkContentAndKey(content, key);
        if (StringUtil.isEmpty(initVector)) {
            throw new NullPointerException("The init Vector can't be null or empty.");
        }
    }

    /**
     * 检测加解密的参数
     *
     * @param content 带解密字节数组
     * @param key     解密的密匙
     */
    private static void checkContentAndKey(String content, String key) {
        if (StringUtil.isEmpty(content)) {
            throw new NullPointerException("The string to be encrypted cannot be null.");
        }
        if (StringUtil.isEmpty(key)) {
            throw new NullPointerException("The key can't be null or empty.");
        }

        if (key.length() != 16) {
            throw new RuntimeException("The length of key must be 16 while use AES CBC mode.");
        }
    }

    /**
     * 生成加密秘钥
     *
     * @param key 加密key
     * @return SecretKeySpec
     */
    private static SecretKeySpec getSecretKey(final String key) {
        try {
            return new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), KEY_ALGORITHM);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
