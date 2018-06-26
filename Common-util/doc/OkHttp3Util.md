# OkHttp3Util介绍
OkHttp3Util是针对okhttp3的网络请求框架的封装，目前基于okhttp3封装了同步异步的http post或者是get
的网络调用。

**注意：** 该工具基于okhttp3的异步回调目前测试中还存在问题。


## 1. syncGet(String url)方法
该方法是http get方式的请求封装。

参数 | 描述
---|---
url| 请求地址

 
Usage:

```
String url = "http://www.baidu.com";
String response = OkHttp3Util.syncGet(url);
```

## 2. syncGet(String baseUrl, Map<String, String> params)方法
该方法是http get带参数方式的请求。第一种get请求同样也可以携带参数，但是对于参数过多时，
手动拼装请求url是非常麻烦的,所以提供map来封装请求参数。

参数 | 描述
---|---
baseUrl| get请求根url
params| 请求参数

 
Usage:

```
String url = "http://www.baidu.com";
Map<String,String> params = new HashMap<>();
params.put("ok","ok");
String response = OkHttp3Util.syncGet(url,params);
```
## 3. syncGet(String baseUrl, Map<String, String> params, Map<String, String> headers)方法
该方法是okhttp3 get带参数方式和请求头的请求的封装。

参数 | 描述
---|---
baseUrl| get请求根url
params| 请求参数
headers| 请求头
 
Usage:

```
String url = "http://www.baidu.com";
Map<String,String> params = new HashMap<>();
params.put("ok","ok");

Map<String,String> headers = new HashMap<>();
headers.put("accessToken","ok");

String response = OkHttp3Util.syncGet(url,params,headers);
```
## 4. syncPost(String url, Map<String, String> params)方法
该方法是http post带参数方式的请求。

参数 | 描述
---|---
url| 请求url
params| 请求参数

 
Usage:

```
String url = "http://www.baidu.com";
Map<String,String> params = new HashMap<>();
params.put("ok","ok");
String response = OkHttp3Util.syncPost(url,params);
```
## 5. syncPost(String url, Map<String, String> params, Map<String, String> headers)方法
该方法是okhttp3 post带参数方式和请求头的请求的封装。

参数 | 描述
---|---
url| 请求url
params| 请求参数
headers| 请求头
 
Usage:

```
String url = "http://www.baidu.com";
Map<String,String> params = new HashMap<>();
params.put("ok","ok");

Map<String,String> headers = new HashMap<>();
headers.put("accessToken","ok");

String response = OkHttp3Util.syncPost(url,params,headers);
```
## 6. syncPostJson(String url, String json)方法
该方法用于发送json参数。

参数 | 描述
---|---
url| 请求url
params| 请求参数
 
Usage:

```
String url = "http://www.baidu.com";
String json = "{\"name\":\"list\"}";

String response = OkHttp3Util.syncPostJson(url,json);
```

## 7. syncPostJson(String url, String json, Map<String, String> headersMap)方法

该方法用于发送json参数。

参数 | 描述
---|---
url| 请求url
params| 请求参数
headers| 请求头
 
Usage:

```
String url = "http://www.baidu.com";
String json = "{\"name\":\"list\"}";

Map<String,String> headers = new HashMap<>();
headers.put("accessToken","ok");
String response = OkHttp3Util.syncPostJson(url,json,headers);
```