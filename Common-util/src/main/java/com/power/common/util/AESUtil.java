package com.power.common.util;

import com.power.common.constants.AesPaddings;
import com.power.common.constants.Charset;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;


/**
 * Description:
 * aes加密工具
 *
 * @author yu 2018/06/08.
 */
public class AESUtil {


    /**
     * key 算法
     */
    private static final String KEY_ALGORITHM = "AES";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 使用cbc加密解密(default use AES/CBC/PKCS5Padding)
     *
     * @param content    待解密字节数组
     * @param key        解密的密匙
     * @param initVector 初始向量
     * @return byte[]
     */
    public static byte[] decryptByCBC(byte[] content, byte[] key, byte[] initVector) {
        return decryptByCBC(content, key, initVector, AesPaddings.AES_CBC_PKCS5);
    }


    /**
     * 使用cbc加密解密
     *
     * @param content    待解密字节数组
     * @param key        解密的密匙
     * @param initVector 初始向量
     * @param padding    填充模式
     * @return byte[]
     */
    public static byte[] decryptByCBC(byte[] content, byte[] key, byte[] initVector, String padding) {
        try {
            return doFinal(content, key, initVector, Cipher.DECRYPT_MODE, padding);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("AES CBC decrypt error");
        }
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
        return encryptByCBC(content, key, initVector, AesPaddings.AES_CBC_PKCS5);
    }

    /**
     * 使用cbc加密模式加密
     *
     * @param content    待加密的内容
     * @param key        加密key
     * @param initVector 初始向量
     * @param padding    填充模式
     * @return byte array
     */
    public static byte[] encryptByCBC(byte[] content, byte[] key, byte[] initVector, String padding) {
        try {
            return doFinal(content, key, initVector, Cipher.ENCRYPT_MODE, padding);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("AES CBC encrypt error");
        }
    }

    /**
     * 使用ecb解密(default use AES/ECB/PKCS5Padding)
     *
     * @param content 待解密字节数组
     * @param key     解密的密匙
     * @return byte[]
     */
    public static byte[] decryptByECB(byte[] content, byte[] key) {
        return decryptByECB(content, key, AesPaddings.AES_ECB_PKCS5);
    }

    /**
     * 使用ECB解密
     *
     * @param content 待解密字节数组
     * @param key     解密的密匙
     * @param padding 填充模式
     * @return byte array
     */
    public static byte[] decryptByECB(byte[] content, byte[] key, String padding) {
        try {
            return doFinalECB(content, key, Cipher.DECRYPT_MODE, padding);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Aes decrypt error");
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
        return encryptByECB(content, key, AesPaddings.AES_ECB_PKCS5);
    }


    /**
     * 使用ecb加密模式加密
     *
     * @param content 待加密的内容
     * @param key     加密key
     * @param padding 填充模式
     * @return byte[]
     */
    public static byte[] encryptByECB(byte[] content, byte[] key, String padding) {
        try {
            return doFinalECB(content, key, Cipher.ENCRYPT_MODE, padding);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Aes encrypt error");
        }
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
        return decodeByCBC(content, key, initVector, AesPaddings.AES_CBC_PKCS5);
    }

    /**
     * 使用CBC解密
     *
     * @param content    待解密的字符串
     * @param key        解密的密匙
     * @param initVector 初始向量
     * @param padding    填充模式
     * @return String
     */
    public static String decodeByCBC(String content, String key, String initVector, String padding) {
        AESUtil.checkParamsOfCBC(content, key, initVector);
        byte[] decryptFrom = HexUtil.hexStr2ByteArr(content);
        byte[] decryptResult = decryptByCBC(decryptFrom, key.getBytes(), initVector.getBytes(), padding);
        return new String(decryptResult);
    }

    /**
     * 解密使用CBC和base64双重加密的字符串
     *
     * @param content    经过base64和CBC的字串
     * @param key        解密的密匙
     * @param initVector 初始向量
     * @param padding    填充模式
     * @return String
     */
    public static String decodeByCBCBase64(String content, String key, String initVector, String padding) {
        AESUtil.checkParamsOfCBC(content, key, initVector);
        byte[] decryptFrom = Base64Util.decryptBASE64(content);
        byte[] decryptResult = decryptByCBC(decryptFrom, key.getBytes(), initVector.getBytes(), padding);
        return new String(decryptResult);
    }

    /**
     * 使用cbc模式加密
     *
     * @param content    待加密字节数组
     * @param key        密匙
     * @param initVector 初始向量
     * @return String
     */
    public static String encodeByCBC(String content, String key, String initVector) {
        return encodeByCBC(content, key, initVector, AesPaddings.AES_CBC_PKCS5);
    }

    /**
     * 使用cbc模式加密
     *
     * @param content    待加密字符串
     * @param key        密匙
     * @param initVector 初始向量
     * @param padding    填充模式
     * @return String
     */
    public static String encodeByCBC(String content, String key, String initVector, String padding) {
        return encodeByCBC(content, key, initVector, padding, false);
    }

    /**
     * 使用cbc模式加密(增加base64加密)
     *
     * @param content    待加密字符串
     * @param key        密匙
     * @param initVector 初始向量
     * @param padding    填充模式
     * @return String
     */
    public static String encodeByCBCBase64(String content, String key, String initVector, String padding) {
        return encodeByCBC(content, key, initVector, padding, true);
    }

    /**
     * 使用ecb模式加密
     *
     * @param content 待加密的内容
     * @param key     加密key
     * @return 返回Base64转码后的加密数据
     */
    public static String encodeByECB(String content, String key) {
        return encodeByECB(content, key, AesPaddings.AES_ECB_PKCS5);
    }

    /**
     * 使用ECB模式加密
     *
     * @param content 待加密的内容
     * @param key     加密的key
     * @param padding 填充模式
     * @return String
     */
    public static String encodeByECB(String content, String key, String padding) {
        AESUtil.checkContentAndKey(content, key);
        try {
            byte[] encrypted = encryptByECB(content.getBytes(Charset.DEFAULT_CHARSET), key.getBytes(Charset.DEFAULT_CHARSET), padding);
            //BASE64 is used here as a transcoding function, which can also play a role of 2 times encryption
            return Base64Util.encryptToString(encrypted);
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
            byte[] result = decryptByECB(Base64Util.decryptBASE64(content), key.getBytes(Charset.DEFAULT_CHARSET));
            return new String(result, Charset.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * ecb加解密
     *
     * @param content 待解密字节数组
     * @param key     待解密key
     * @param mode    算法模式(加密或者是解密)
     * @param padding 填充模式
     * @return byte array
     * @throws GeneralSecurityException
     */
    private static byte[] doFinalECB(byte[] content, byte[] key, int mode, String padding) throws GeneralSecurityException {
        SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(padding);
        cipher.init(mode, keySpec);
        return cipher.doFinal(content);
    }

    /**
     * 除AES ECB算法外的其他算法公用
     *
     * @param content    待解密字节数组
     * @param key        待解密key
     * @param initVector 加解密向量
     * @param mode       算法模式(加密或者是解密)
     * @param padding    填充模式
     * @return byte array
     * @throws GeneralSecurityException
     */
    private static byte[] doFinal(byte[] content, byte[] key, byte[] initVector, int mode, String padding)
            throws GeneralSecurityException {
        SecretKeySpec keySpec = new SecretKeySpec(key, KEY_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(initVector);
        Cipher cipher = Cipher.getInstance(padding);
        cipher.init(mode, keySpec, iv);
        return cipher.doFinal(content);
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

    private static String encodeByCBC(String content, String key, String initVector, String padding, boolean base64) {
        AESUtil.checkParamsOfCBC(content, key, initVector);
        byte[] encryptResult = null;
        try {
            encryptResult = encryptByCBC(content.getBytes(Charset.DEFAULT_CHARSET), key.getBytes(), initVector.getBytes(), padding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64 ? new String(Base64.encodeBase64(encryptResult)) : HexUtil.byteArr2HexStr(encryptResult);
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
            return new SecretKeySpec(key.getBytes(Charset.DEFAULT_CHARSET), KEY_ALGORITHM);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
