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
import java.util.Objects;


/**
 *
 * AES Tools
 * @javadoc
 * @author yu 2018/06/08.
 */
public class AESUtil {


    /**
     * key
     */
    private static final String KEY_ALGORITHM = "AES";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * decrypt using cbc(default use AES/CBC/PKCS5Padding)
     *
     * @param content    byte array to be decrypted
     * @param key        key
     * @param initVector init vector
     * @return byte[]
     */
    public static byte[] decryptByCBC(byte[] content, byte[] key, byte[] initVector) {
        return decryptByCBC(content, key, initVector, AesPaddings.AES_CBC_PKCS5);
    }


    /**
     * Decrypt using CBC mode
     *
     * @param content    byte array to be decrypted
     * @param key        key
     * @param initVector init vector
     * @param padding    padding
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
     * Encrypt using CBC mode
     *
     * @param content    Content to be encrypted
     * @param key        key
     * @param initVector init vector
     * @return byte[]
     */
    public static byte[] encryptByCBC(byte[] content, byte[] key, byte[] initVector) {
        return encryptByCBC(content, key, initVector, AesPaddings.AES_CBC_PKCS5);
    }

    /**
     * Encrypt using CBC mode
     *
     * @param content    Content to be encrypted
     * @param key        key
     * @param initVector init vector
     * @param padding    padding
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
     * Decrypt with ECB(default use AES/ECB/PKCS5Padding)
     *
     * @param content byte array to be decrypted
     * @param key     key
     * @return byte[]
     */
    public static byte[] decryptByECB(byte[] content, byte[] key) {
        return decryptByECB(content, key, AesPaddings.AES_ECB_PKCS5);
    }

    /**
     * Decrypt with ECB
     *
     * @param content byte array to be decrypted
     * @param key     key
     * @param padding padding
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
     * Encrypt with ecb encryption mode
     *
     * @param content Content to be encrypted
     * @param key     key
     * @return byte[]
     */
    public static byte[] encryptByECB(byte[] content, byte[] key) {
        return encryptByECB(content, key, AesPaddings.AES_ECB_PKCS5);
    }


    /**
     * Encrypt with ecb encryption mode
     *
     * @param content Content to be encrypted
     * @param key     key
     * @param padding padding
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
     * Decrypt string using cbc mode
     *
     * @param content    character to be decrypted
     * @param key        key
     * @param initVector init vector
     * @return String
     */
    public static String decodeByCBC(String content, String key, String initVector) {
        return decodeByCBC(content, key, initVector, AesPaddings.AES_CBC_PKCS5);
    }

    /**
     * Decrypt with CBC
     *
     * @param content    String to be decrypted
     * @param key        key
     * @param initVector init vector
     * @param padding    padding
     * @return String
     */
    public static String decodeByCBC(String content, String key, String initVector, String padding) {
        AESUtil.checkParamsOfCBC(content, key, initVector);
        byte[] decryptFrom = HexUtil.hexStr2ByteArr(content);
        byte[] decryptResult = decryptByCBC(decryptFrom, key.getBytes(), initVector.getBytes(), padding);
        return new String(decryptResult);
    }

    /**
     * Decrypt a string encrypted with CBC and base64
     *
     * @param content    String processed with base64 and CBC
     * @param key        key
     * @param initVector init vector
     * @param padding    padding
     * @return String
     */
    public static String decodeByCBCBase64(String content, String key, String initVector, String padding) {
        AESUtil.checkParamsOfCBC(content, key, initVector);
        byte[] decryptFrom = Base64Util.decryptBASE64(content);
        byte[] decryptResult = decryptByCBC(decryptFrom, key.getBytes(), initVector.getBytes(), padding);
        return new String(decryptResult);
    }

    /**
     * Encrypt using cbc mode
     *
     * @param content    String to be encrypted
     * @param key        key
     * @param initVector init vector
     * @return String
     */
    public static String encodeByCBC(String content, String key, String initVector) {
        return encodeByCBC(content, key, initVector, AesPaddings.AES_CBC_PKCS5);
    }

    /**
     * Encrypt using cbc mode
     *
     * @param content    String to be encrypted
     * @param key        key
     * @param initVector init vector
     * @param padding    padding
     * @return String
     */
    public static String encodeByCBC(String content, String key, String initVector, String padding) {
        return encodeByCBC(content, key, initVector, padding, false);
    }

    /**
     * Use cbc mode encryption (add base64 encryption)
     *
     * @param content    Content to be encrypted
     * @param key        key
     * @param initVector init vector
     * @param padding    padding
     * @return String
     */
    public static String encodeByCBCBase64(String content, String key, String initVector, String padding) {
        return encodeByCBC(content, key, initVector, padding, true);
    }

    /**
     * Encrypt using ECB mode
     *
     * @param content Content to be encrypted
     * @param key     key
     * @return Returns encrypted data after Base64 transcoding
     */
    public static String encodeByECB(String content, String key) {
        return encodeByECB(content, key, AesPaddings.AES_ECB_PKCS5);
    }

    /**
     * Encrypt using ECB mode
     *
     * @param content Content to be encrypted
     * @param key     key
     * @param padding padding
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
     * decrypt using ecb mode
     *
     * @param content Content to be decrypted
     * @param key     key
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
     * ecb encryption and decryption
     *
     * @param content Byte array to be encrypted or decrypted
     * @param key     decrypt key
     * @param mode    Algorithmic mode
     * @param padding padding pattern
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
     * General processing, except AES ECB algorithm
     *
     * @param content    Byte array to be encrypted or decrypted
     * @param key        decrypt key
     * @param initVector init vector
     * @param mode       Algorithmic mode
     * @param padding    padding pattern
     * @return byte array
     * @throws GeneralSecurityException exception
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
     * Parameter check of CBC encryption and decryption mode
     *
     * @param content    String to be processed
     * @param key        key
     * @param initVector initial vector
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
        return base64 ? new String(Objects.requireNonNull(Base64.encodeBase64(encryptResult))) : HexUtil.byteArr2HexStr(Objects.requireNonNull(encryptResult));
    }

    /**
     * Detect encryption and decryption parameters
     *
     * @param content String to be processed
     * @param key     key
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
     * Generate Secret key
     *
     * @param key key
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
