package com.power.common.util;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * Description:
 * AESUtil单元测试
 *
 * @author yu 2018/06/09.
 */
public class AESUtilTest {

    private static final String KEY = "aespower98765432";

    /**
     * 非ECB加密模式的向量
     */
    private static final String IV = "7201084316056726";


    @Test
    public void testEncodeByCBC(){
        String encodeStr = AESUtil.encodeByCBC("hello world",KEY,IV);

        System.out.println("After encode by CBC mode: "+encodeStr);

        String decodeStr = AESUtil.decodeByCBC(encodeStr,KEY,IV);

        System.out.println("After decode by CBC mode: "+decodeStr);
    }

    @Test
    public void testEncodeByECB(){

        String encodeStr = AESUtil.encodeByECB("hello world",KEY);

        System.out.println("After encode by EBC mode: "+encodeStr);

        String decodeStr = AESUtil.decodeByECB(encodeStr,KEY);

        System.out.println("After decode by EBC mode: "+decodeStr);
    }

    /**
     * 基于base64加密key的解密测试
     */
    @Test
    public void testDecryptByECB(){

        byte[] key = Base64.decodeBase64("T2+PfV1qoSpkSUI6Yu1ZsQ==");

        byte[] content = Base64.encodeBase64("hello".getBytes());

        //加密
        byte[] encodeResult = AESUtil.encryptByECB(content,key);

        //解密
        byte[] result = AESUtil.decryptByECB(encodeResult,key);

        System.out.println(new String(Base64.decodeBase64(result)));
    }
}
