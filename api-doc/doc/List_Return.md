关于list结构的返回json数据测试

# List<Map<String,String>>结构

```
/**
 * 
 * @return
 */
@GetMapping(value = "/map/Primitive")
public List<Map<String,String>> testMap() {
    return null;
}
```
api-doc生成的响应数据
```
[{
	"mapKey1": "o9mibj",
	"mapKey2": "3dnnrn"
}]
```

# List<Map<String,T>>结构

```
@GetMapping(value = "/map/Primitive")
public List<Map<String,Student>> testMap() {
  return null;
}

```
相应数据省略

# 测试List<T>结构

```
/**
 * 测试List<T>结构
 * @return
 */
@GetMapping(value = "/map/Primitive")
public List<Teacher> testMap() {
    return null;
}
```

# List<T<M,N>>结构
```
/**
 * 测试List<T<M,N>>结构
 * @return
 */
@GetMapping(value = "/map/Primitive")
public List<Teacher<User,User>> testMap() {
    return null;
}
```
# List<Map<M,N<P,k>>>超复杂结构
```
/**
 * 测试List<Map<M,N<P,k>>>超复杂结构
 * @return
 */
@GetMapping(value = "/map/Primitive")
public List<Map<String,Teacher<User,User>>> testMap() {
    return null;
}
```
api-doc自动返回的数据
```
[{
	"mapKey": {
		"data": {
			"userName": "lxh2yi",
			"userAddress": "6jfp3h",
			"userAge": 741
		},
		"data1": {
			"userName": "1wp54g",
			"userAddress": "8ul6m4",
			"userAge": 550
		},
		"age": 10
	}
}]
```