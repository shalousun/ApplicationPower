# ObjectUtil方法介绍
ObjectUtil所属的包为com.power.common.util,ObjectUtil工具包含一些对java对象的判断方法。

## 1. isEmpty(Object object)方法

该方法用于判断对象是否为空

Usage:

```
ObjectUtil.isEmpty(null);//return true
```

## 2. isNotEmpty(Object object)方法

该方法用于对象是否不为空，它和isEmpty方法的返回结果刚好相反。

Usage:

```
ObjectUtil.isNotEmpty(null);//return false
```