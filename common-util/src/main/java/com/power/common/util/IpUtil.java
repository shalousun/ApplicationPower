package com.power.common.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for working with IP addresses, providing methods to retrieve
 * the local server IP and lists of local IPv4 and IPv6 addresses.
 */
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
     * Gets a map of local IPv4 addresses,
     * @apiNote where the key represents the network interface name
     * and the value is the corresponding IP address.
     *
     * @return A map containing network interfaces and their associated IPv4 addresses.
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
     * Retrieves a map of local IPv6 addresses
     * @apiNote Where the key represents the network interface name
     * and the value is the corresponding IP address.
     *
     * @return A map containing network interfaces and their associated IPv6 addresses.
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
