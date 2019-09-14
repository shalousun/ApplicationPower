package com.power.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 开放接口签名生成工具，服务方根据接口调用方的接口参数中
 * 的appId参数查询出对应的appKey秘钥(秘钥需要保持每个用户唯一性)，
 * 然后根据再根据接口中的nonceStr，timestamp，appId和查询到的appKey计算出签名，
 * 最后将计算出的签名与调用方传输回来的signature签名对比，如果相同的通过验证
 * 不相同则不通过，服务方最好对timestamp的时间验证，时间差超过5分钟的认为被劫持，
 * 时间越长风险越高，返回接口认证失败，当然如果想更细力度的控制最好加上请求的url也做为
 * 签名生成的条件
 * <p>
 * Created by yu on 2017/7/27.
 */
public class ApiSignUtil {

    /**
     * 调用方根据自己的用户数据行生成签名返回给接口层
     * 接口层将接口设置及的四个参数值设置到请求参数中发送给接口服务方
     *
     * @param appID    应用id
     * @param SECRET   密钥
     * @param nonceStr 无意义的字符
     * @return hash map
     */
    public static Map<String, Object> sign(String appID, String SECRET, String nonceStr) {
        Map<String, Object> ret = new HashMap<>();
        long timestamp = System.currentTimeMillis() / 1000L;
        String signature = ApiSignUtil.getSignature(appID, SECRET, nonceStr, timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", appID);
        return ret;
    }

    /**
     * 服务方根据调用方生成的接口常用参数生成签名
     *
     * @param appID     应用ID
     * @param SECRET    密钥
     * @param nonceStr  无意义的字符
     * @param timestamp 接口请求时间
     * @return string
     */
    public static String getSignature(String appID, String SECRET, String nonceStr, long timestamp) {
        String string1;
        String signature = "";
        string1 = "app_id=" + appID + "&app_key" + SECRET + "&noncestr=" + nonceStr
                + "&timestamp=" + timestamp;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return signature;
    }

    /**
     * byte转到16进制
     *
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
