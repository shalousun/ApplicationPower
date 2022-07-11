package com.power.common.util;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA TOOls
 */
public class RSAUtil {

    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    public static final String CHARSET = "UTF-8";
    public static final int KEY_SIZE = 2048;
    private static final String RSA_ECB_PADDING = "RSA/ECB/PKCS1Padding";
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * generate key pair
     *
     * @param keySize key size
     * @return KeyPair key pair
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(keySize, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        System.out.println("privateKey:" + keyPair.getPrivate());
        System.out.println("publicKey:" + keyPair.getPublic());
        return keyPair;

    }

    /**
     *
     * create key
     * @param keySize size of key
     * @return public key and private key
     */
    public static Map<String, String> createKeys(int keySize) {
        try {
            KeyPair keyPair = RSAUtil.generateKeyPair(keySize);
            Key publicKey = keyPair.getPublic();
            String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
            Key privateKey = keyPair.getPrivate();
            String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
            Map<String, String> keyPairMap = new HashMap<>();
            keyPairMap.put(PUBLIC_KEY, publicKeyStr);
            keyPairMap.put(PRIVATE_KEY, privateKeyStr);
            return keyPairMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get public key
     *
     * @param keyPair KeyPair
     * @return String
     */
    public static String getPublicKey(KeyPair keyPair) {
        Key publicKey = keyPair.getPublic();
        return Base64.encodeBase64URLSafeString(publicKey.getEncoded());
    }

    /**
     * Get private key
     *
     * @param keyPair KeyPair
     * @return String
     */
    public static String getPrivateKey(KeyPair keyPair) {
        Key privateKey = keyPair.getPrivate();
        return Base64.encodeBase64URLSafeString(privateKey.getEncoded());
    }

    /**
     * Get key pair from file
     *
     * @param filePath file
     * @return KeyPair KeyPair
     * @throws Exception Exception
     */
    public static KeyPair getKeyPair(String filePath) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream oos = new ObjectInputStream(fis);
        KeyPair kp = (KeyPair) oos.readObject();
        oos.close();
        fis.close();
        return kp;
    }

    /**
     * Save key pair into file
     *
     * @param kp       key pair
     * @param filePath file
     * @throws Exception Exception
     */
    public static void saveKeyPair(KeyPair kp, String filePath) throws Exception {
        File file = new File(filePath);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        // write key pair
        oos.writeObject(kp);
        oos.close();
        fos.close();
    }

    /**
     * Decrypt with private key
     *
     * @param plaintext  plain text
     * @param privateKey private key
     * @return decrypted string
     */
    public static String decryptString(String plaintext, String privateKey) {
        return RSAUtil.privateDecrypt(plaintext, RSAUtil.getPrivateKey(privateKey));
    }


    /**
     * Decrypt with private key
     *
     * @param plaintext plaintext
     * @param publicKey public key
     * @return String
     */
    public static String encryptString(String plaintext, String publicKey) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        return RSAUtil.publicEncrypt(plaintext, RSAUtil.getPublicKey(publicKey));
    }


    /**
     * Get public key
     *
     * @param publicKey key string (base64 encoded)
     * @return RSAPublicKey
     */
    public static RSAPublicKey getPublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get private key
     *
     * @param privateKey key string (base64 encoded)
     * @return RSAPrivateKey
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Use public key encryption
     *
     * @param plaintext String to be encrypted
     * @param publicKey public key
     * @return String
     */
    public static String publicEncrypt(String plaintext, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, plaintext.getBytes(CHARSET),
                    publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred while encrypting the string [" + plaintext + "]", e);
        }
    }

    /**
     * Decrypt with private key
     *
     * @param plaintext  plaintext
     * @param privateKey private key
     * @return String
     */

    public static String privateDecrypt(String plaintext, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(plaintext),
                    privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred while decrypting the string [" + plaintext + "]", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred when the encryption and decryption threshold was [" + maxBlock + "]", e);
        }
        byte[] resultDates = out.toByteArray();
        try {
            if (null != out) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultDates;
    }

}
