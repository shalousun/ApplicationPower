package com.power.doc;

import com.power.doc.builer.ApiDocBuilder;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiReqHeader;
import com.power.doc.model.CustomRespField;
import org.junit.Test;

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


        ApiDocBuilder.builderControllersApi(config);
    }

}
