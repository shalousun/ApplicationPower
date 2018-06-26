# CollectionUtil方法介绍
CollectionUtil所属的包为com.power.common.util,CollectionUtil工具包含一些对java 集合对象的判断方法。

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
## 3. subList(List<T> source, int from, int to)方法
该方法相比使用List直接的subList，增加下标检测，避免输入的下标超出list的长度而报错
```
List<String> list = new ArrayList<>(5);
List listA = CollectionUtil.subList(list,0,3);
```
## 4. asList(T... a)方法
该方法调用Arrays.asList来完成，增加null判断，当传入的参数为null时，返回一个长度为0的list
```
Integer[] ints = {1,2,3,4,5};
List list = CollectionUtil.asList(ints);

```

## 5. mergeAndSwap(List<T> result1, List<T> result2)
该方法用于合并交换两个list中的各项值并返回合并后的list，只在特殊场景使用。
```
List<String> list = new ArrayList<>(2);
list.add("a");
list.add("c")

List<String> list2 = new ArrayList<>(1);
list2.add("b");
CollectionUtil.mergeAndSwap(list1,list2);

合并后的list打印输出为：a,b,c

```