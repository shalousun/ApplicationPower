package com.power.generator;

import com.power.doc.builer.ApiDocBuilder;
import com.power.doc.model.ApiDoc;
import org.junit.Test;

/**
 * Description:
 * ApiDoc测试
 *
 * @author yu 2018/06/11.
 */
public class ApiDocTest {

    @Test
    public void testBuilderControllersApi(){
        ApiDocBuilder.builderControllersApi("d:\\md",true);
    }
}
