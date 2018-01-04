# IpUtil介绍
该工具用于获取ip地址

## 1. getIpAddr(HttpServletRequest request)方法

该方法用于获取Ip地址,如果是采用localhost访问则返回：127.0.0.1

Usage:
```
IpUtil.getIpAddr(request);//return 192.168.15.80
```