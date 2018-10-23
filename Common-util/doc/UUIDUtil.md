# UUIDUtil方法说明
UUIDUtil方法只封装了两个简单的方法，一个用于获取36位的UUID，一个用于获取32位去掉'-'的UUID。

## 1. getUuid()方法
该方法用于获取36位的uuid。

Usage:

```
UUIDUtil.getUuid();//return "108e78b8-cb04-46e6-a1c5-0d535896abb9"

```

## 2. getUuid32()方法
该方法用于获取32位的uuid。

Usage:

```
UUIDUtil.getUuid32();//return "b90b1d855b094bf68115069488c899dc"
```