package com.power.common.util;

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
        InetAddress ia;
        try {
            ia = InetAddress.getLocalHost();
            serverIp = ia.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get local server ip address
     *
     * @return String
     */
    public static String getServerIp() {
        return serverIp;
    }

    /**
     * Get IPV4 address
     *
     * @return hash map,key is net interface, value is ip address
     */
    public static Map<String, String> getLocalIPV4() {
        Map<String, String> map = new HashMap<>();
        InetAddress ip;
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
     * Get IPV6 address
     *
     * @return hash map,key is net interface, value is ip address
     */
    public static Map<String, String> getLocalIPV6() {
        Map<String, String> map = new HashMap<>();
        InetAddress ip;
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
