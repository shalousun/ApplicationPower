api-doc是一个基于注解的文档解释器。是现在网上公开的rest api文档中，极少数根据源代码
来推导出项目接口文档的工具，拥有强大的java泛型嵌套推导能力，目前api-doc能够完成应用开发中
至少95%的接口返回泛型推导。如果你已经讨厌了swagger等文档工具的注解和代码侵入性，api-doc一定是
一款值得你去尝试的rest-api接口文档生成工具。

# api-doc和swagger对比
## 1.界面提供
关于是真的是否需要提供api文档界面，对于非公开的项目是完全不需要的web界面来显示api文档的，如果真的需要显示，那么swagger的界面还不足作为产品的公开接口文档。

- api-doc目前版本没有界面支持，未来也不会提供界面。如果使用者真的需要界面，可以自己开发一个解析markdown的web接口界面，
- swagger有界面，但是界面很丑。

## 2. 代码侵入性
- api-doc是完全零侵入的文档自动生成工具，完全基于java原生注释来自动完成文档的生成，而且工具周期仅仅是测试级的，编写一个单元测试即可完成文档的生成。
- swagger侵入性很强，必须在应用中编写swagger配置启动，包括编写接口写一堆注解。
## 3. 强大的返回结构推导
- api-doc可以自动完成常见开发中95%以上的泛型接口返回自推导，推导出参数化类型的字段名和相关注释。
- swagger只用使用注解，注解太多而且复杂。

## 4. 自推导请求参数示例
- api-doc能够推导spring web开发中json请求的数据请求示例，减少开发者和测试者拼装json数据的痛苦，开发者或者测试者拿到自动生成的json请求实例只需要根据自己的业务修改字段的值或者是删除业务请求不需要的字段即可，并且能够对一些常用字段名做智能赋初始值。
- swagger不具备这样的能力。
## 5. json返回数据推导

- api-doc能够根据controller的接口返回类型自推导出json数据，定义完接口你就能是api-doc来在测试推导出json数据和返回字段说明，并且强大的树形字段说明，让字段依赖一目了然，当然推导的数据都是随机值。
- swagger需要在界面请求来实现。

# api-doc使用
## 1.导入api-doc工具的依赖包
```
<dependency>
    <groupId>com.github.shalousun</groupId>
    <artifactId>api-doc</artifactId>
    <version>0.1</version>
    <scope>test</scope>
</dependency>
```
## 2.编写一个单元测试类

这个单元测试类仅仅就是用于生成rest ful接口文档

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
# api-doc之不完美

**接受我的好时，也请接受我的不完美！**

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

# 问题反馈
由于api-doc属于第一次公开发行，目前测试的接口场景还比较少，对于一些接口的推导支持可能不全或者是api-doc直接报错了，在这里真诚的希望您能反馈出问题，以便更好的推动api-doc的完成和更加的智能化。
