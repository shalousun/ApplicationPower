package com.power.common.util;

import com.power.common.constants.AesPaddings;
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
    public void testEncodeByCBC() {
        String encodeStr = AESUtil.encodeByCBC("hello world", KEY, IV);

        System.out.println("After encode by CBC mode: " + encodeStr);

        String decodeStr = AESUtil.decodeByCBC(encodeStr, KEY, IV);

        System.out.println("After decode by CBC mode: " + decodeStr);
    }

    /**
     * 如果使用base64做双重加解密，使用的方法需要匹配
     */
    @Test
    public void testDecodeByCBCBase64() {

        String encodeStr = AESUtil.encodeByCBCBase64("hello world", KEY, IV, AesPaddings.AES_CBC_PKCS7);
        System.out.println("After encode by CBC mode: " + encodeStr);


        String decodeStr = AESUtil.decodeByCBCBase64(encodeStr, KEY, IV, AesPaddings.AES_CBC_PKCS7);
        System.out.println("After decode by CBC mode: " + decodeStr);
    }

    /**
     * 内部采用base64做了二次加密
     */
    @Test
    public void testEncodeByECB() {

        String encodeStr = AESUtil.encodeByECB("hello world", KEY);

        System.out.println("After encode by EBC mode: " + encodeStr);

        String decodeStr = AESUtil.decodeByECB(encodeStr, KEY);

        System.out.println("After decode by EBC mode: " + decodeStr);
    }

    /**
     * 基于base64加密key的解密测试
     */
    @Test
    public void testDecryptByECB() {

        //base 64加密后的key
        String base64Key = "F51riKYgKK8PRrt+5IC6CQ==";
        //Base64Util.encryptToString(KEY);

        byte[] key = Base64Util.decryptBASE64(base64Key);

        System.out.println("key:" + new String(key));

        byte[] content = "hello".getBytes();

        //加密
        byte[] encodeResult = AESUtil.encryptByECB(content, key);

        System.out.println("after encode:" + Base64Util.encryptToString(encodeResult));

        //解密
        byte[] result = AESUtil.decryptByECB(encodeResult, key);

        System.out.println("after decode:" + new String(result));

        String result2 = AESUtil.decodeByECB(Base64Util.encryptToString(encodeResult), new String(key));
        System.out.println(result2);
    }
}
