# CollectionUtil方法介绍
CollectionUtil所属的包为com.boco.common.util,CollectionUtil工具包含一些对java 集合对象的判断方法。

## 1. isEmpty(Collection<T> c)方法

该方法用于判断集合是否为空

Usage:

```
List<String> list = new ArrayList<>(0);
CollectionUtil.isEmpty(list);//return true
```

## 2. isNotEmpty(Collection<T> c)方法

该方法用于判断集合是否为不为空，它和isEmpty方法的返回结果刚好相反。

Usage:

```
List<String> list = new ArrayList<>(1);
list.add("test");
CollectionUtil.isNotEmpty(list);//return true
```