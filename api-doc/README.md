
把懒当作一场习惯，而不是一时的热血！

# api-doc和其他工具对比

我懒的比，不要和我比懒。

# api-doc使用
## 1.导入api-doc工具的依赖包
```
<dependency>
    <groupId>com.github.shalousun</groupId>
    <artifactId>api-doc</artifactId>
    <version>0.2</version>
    <scope>test</scope>
</dependency>
```
## 2.编写一个单元测试类

妈妈再也不用担心我不会用了，So easy!

```
/**
 * Description:
 * ApiDoc测试
 *
 * @author yu 2018/06/11.
 */
public class ApiDocTest {

    /**
     * 简单型接口，不需要指定请求头，并且项目是maven的.
     *
     */
    @Test
    public void testBuilderControllersApiSimple(){
        //将生成的文档输出到d:\md目录下，严格模式下api-doc会检测Controller的接口注释
        ApiDocBuilder.builderControllersApi("d:\\md",true);
    }

    /**
     * 包括设置请求头，缺失注释的字段批量在文档生成期使用定义好的注释
     */
    @Test
    public void testBuilderControllersApi() {
        ApiConfig config = new ApiConfig();
        config.setStrict(true);
        config.setOutPath("d:\\md");
        //默认是src/main/java,maven项目可以不写
        config.setSourcePath("src/test/java");

        //设置请求头，如果没有请求头，可以不用设置
        config.setRequestHeaders(
                ApiReqHeader.header().setName("access_token").setType("string").setDesc("Basic auth credentials"),
                ApiReqHeader.header().setName("user_uuid").setType("string").setDesc("User Uuid key")
        );
        //对于外部jar的类，api-doc目前无法自动获取注释，
        //如果有这种场景，则自己添加字段和注释，api-doc后期遇到同名字段则直接给相应字段加注释
        config.setCustomResponseFields(
                CustomRespField.field().setName("success").setDesc("成功返回true,失败返回false"),
                CustomRespField.field().setName("message").setDesc("接口响应信息"),
                CustomRespField.field().setName("data").setDesc("接口响应数据"),
                CustomRespField.field().setName("code").setValue("00000").setDesc("响应代码")
        );
        
        //设置项目错误码列表，设置自动生成错误列表
        List<ApiErrorCode> errorCodeList = new ArrayList<>();
        for(ErrorCodeEnum codeEnum:ErrorCodeEnum.values()){
            ApiErrorCode errorCode = new ApiErrorCode();
            errorCode.setValue(codeEnum.getValue()).setDesc(codeEnum.getDesc());
            errorCodeList.add(errorCode);
        }
        //不是必须
        config.setErrorCodes(errorCodeList);

        ApiDocBuilder.builderControllersApi(config);
    }

}
```


# api-doc的缺点

万物有痕，所以美！


## 1.泛型推导不完美

目前api-doc在泛型的字段推导上是不完美的。例如：

**样例1**：
```
public class Teacher<T,M,K> {
    private T data;

    private Object data3
    
    private K data1;

    private M data2;
    
    /**
     * 年龄
     */
    private int age;
}
```
介于当前的推导算法。泛型字段属性的顺序和Teacher泛型参数顺序一致，否则api-doc推导的json数据会出现张冠李戴的情况。当然你只要调整一下属性的顺序就可以了。

**样例2**
```
public class Teacher<T,M,K> {
    private T data;

    private Object data3
    
    private K data1;

    private M data2;
    
    /**
     * 年龄
     */
    private int age;
}
```
上面这种在泛型属性中突然插入了一个Object的属性，因为泛型本身在编译后会被擦除，因此这种在中间插入的情况会导致目前算法推导出错，同样实现数据张冠李戴，因此推荐如果前面泛型的字段的前面字段不属于基本类型(String,int,Double等)的都移动到泛型后面去。


## 2.form data数据请求处理不完美

这并不是api-doc不具备能力来生成处理form data请求式接口文档，而是目前作者不知道怎么来做这个模板，尤其是请求示例数据的模板怎么提供。但是api-doc还是提供了基本类型请求参数的文档生成。

所以真诚的希望您可以提供一个form data请求式的模板，然后api-doc在后续的版本更新中完善form data请求参数的处理。
## 3. 对于继承的属性为推导到文档
由于api-doc目前采用qdoc来实现了代码的加载，由于qdoc在这方面没有提供直接支持，并且在泛型处理上也太好，考虑后期会去使用其他框架来作为api-doc
的底层实现，因此这项先搁浅，如果使用者遇到这样问题可以定义一个返回包装类解决。

## api-doc 测试demo

https://github.com/shalousun/api-doc-test

## api-doc 版本
版本小于1.0都属于使用版，正式1.0起始发布将会等到文中提到的问题解决后才发布。
### 版本号：0.1 
- 更新日期：2018-06-25  
- 更新内容：
	1. 手册将api-doc发布到中央仓库
### 版本号：0.2  
- 更新日期：2018-07-07
- 更新内容：
	1. 修改api-doc泛型推导的bug.
#### 版本号：0.3
- 更新日期：2018-07-10
- 更新内容：
	1. api-doc增加对jackson和fastjson注解的支持，可根据注解定义来生成返回信息。
#### 版本号：0.4
- 更新日期：2018-07-11
- 更新内容：
	1. 修改api-doc对类继承属性的支持。

# 问题反馈
由于api-doc属于第二次公开发行，目前已经能够支持许多复杂和返回类型和类结构推导，但是由于目前做采用的底层工具处理能力不是很好，所以有些功能做的不够彻底。
当然基于目前的版如果在使用中发现问题欢迎及时的提出反馈的建议。
