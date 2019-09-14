package com.power.common.util;

import org.junit.Test;

import java.security.KeyPair;
import java.util.Map;

/**
 * Description:
 * RSAUtil单元测试
 *
 * @author yu 2018/06/09.
 */
public class RSAUtilTest {

    @Test
    public void testGenerateKeyPair() throws Exception {
        RSAUtil.generateKeyPair(1024);

    }

    @Test
    public void testEncryptString() throws Exception {

        Map<String, String> keyMap = RSAUtil.createKeys(2048);
        String privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
        System.out.println("私钥：" + privateKey);
        String publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
        System.out.println("公钥：" + publicKey);
        String encodedData = RSAUtil.encryptString("hello", publicKey);
        System.out.println("密文：\n" + encodedData);
        String decodedData = RSAUtil.decryptString(encodedData, privateKey);
        System.out.println("解密后文字: \r\n" + decodedData);

    }

    @Test
    public void testSaveKeyPair() throws Exception {
        KeyPair kp = RSAUtil.generateKeyPair(1024);
        RSAUtil.saveKeyPair(kp, "e:\\RSAKey.txt");
    }

    @Test
    public void testGetPublicKey() throws Exception {
        KeyPair kp = RSAUtil.getKeyPair("e:\\RSAKey.txt");
        String publicKey = RSAUtil.getPublicKey(kp);//return base64转码后的字符串公钥
        System.out.println("公钥：" + publicKey);
    }

    @Test
    public void testGetPrivateKey() throws Exception {
        KeyPair kp = RSAUtil.getKeyPair("e:\\RSAKey.txt");
        String privateKey = RSAUtil.getPrivateKey(kp);//return base64转码后的字符串私钥
        System.out.println("私钥：" + privateKey);
    }

    /**
     * 测试使用文件存储的秘钥进行加解密
     *
     * @throws Exception Exception
     */
    @Test
    public void testUseSoreKeyEncryptString() throws Exception {
        KeyPair kp = RSAUtil.getKeyPair("e:\\RSAKey.txt");
        String privateKey = RSAUtil.getPrivateKey(kp);
        String publicKey = RSAUtil.getPublicKey(kp);

        String encodedData = RSAUtil.encryptString("hello", publicKey);
        System.out.println("密文：\n" + encodedData);
        String decodedData = RSAUtil.decryptString(encodedData, privateKey);
        System.out.println("解密后文字: \r\n" + decodedData);
    }

}
