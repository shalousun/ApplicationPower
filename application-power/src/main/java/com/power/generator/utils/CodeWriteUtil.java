package com.power.generator.utils;

import com.boco.common.util.FileUtil;

import java.util.Map;

public class CodeWriteUtil {

    /**
     * 批量创建代码
     * @param files
     */
    public static void writeFileNotAppend(Map<String,String> files){
        for(Map.Entry<String,String> entry:files.entrySet()){
            FileUtil.writeFileNotAppend(entry.getValue(),entry.getKey());
        }
    }
}
