package com.power.common.util;

import org.junit.Test;

import java.util.Map;

/**
 * Description:
 * RSAUtil单元测试
 *
 * @author yu 2018/06/09.
 */
public class RSAUtilTest {

    @Test
    public void testGenerateKeyPair() throws Exception{
        RSAUtil.generateKeyPair(1024);

    }

    @Test
    public void testEncryptString() throws Exception{

        Map<String,String> keyMap = RSAUtil.createKeys(2048);
        String privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
        System.out.println("私钥："+privateKey);
        String publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
        System.out.println("公钥："+publicKey);
        String encodedData = RSAUtil.encryptString("hello", publicKey);
        System.out.println("密文：\n" + encodedData);
        String decodedData = RSAUtil.decryptString(encodedData, privateKey);
        System.out.println("解密后文字: \r\n" + decodedData);

    }

}
