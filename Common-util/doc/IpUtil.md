# IpUtil介绍
该工具用于获取ip地址

## 1. getIpAddr(HttpServletRequest request)方法

该方法用于获取Ip地址,如果是采用localhost访问则返回：127.0.0.1

Usage:
```
IpUtil.getIpAddr(request);//return 192.168.15.80
```
## 2. getLocalIPV4()方法

该方法用于返回服务器所在机器所有IPV4地址,map中的key是网卡名称，value是ip地址

Usage:
```
Map<String,String> ipsMap = IpUtil.getLocalIPV4();
```

## 3. getLocalIPV6()方法

该方法用于返回服务器所在机器所有IPV6地址,map中的key是网卡名称，value是ip地址

Usage:
```
Map<String,String> ipsMap = IpUtil.getLocalIPV6();
```