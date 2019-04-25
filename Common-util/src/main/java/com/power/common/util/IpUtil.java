package com.power.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class IpUtil {

    private static String serverIp;

    static {
        InetAddress ia = null;
        try {
            ia = ia.getLocalHost();
            serverIp = ia.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取ip地址
     *
     * @param request HttpServletRequest
     * @return string
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //cast localhost
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = getServerIp();
        }
        return ip;
    }


    /**
     * 获取服务器的ip地址
     *
     * @return String
     */
    public static String getServerIp() {
        return serverIp;
    }

    /**
     * 获取所有的网卡的ip v4地址,key为网卡地址，value为ip地址
     *
     * @return hash map
     */
    public static Map<String, String> getLocalIPV4() {
        Map<String, String> map = new HashMap<>();
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (ip instanceof Inet4Address) {
                        map.put(ni.getName(), ip.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取所有ipv6地址
     *
     * @return hash map
     */
    public static Map<String, String> getLocalIPV6() {
        Map<String, String> map = new HashMap<>();
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (ip instanceof Inet6Address) {
                        map.put(ni.getName(), ip.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
