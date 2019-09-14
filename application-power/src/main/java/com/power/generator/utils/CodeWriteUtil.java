package com.power.generator.utils;

import com.power.common.util.FileUtil;

import java.io.File;
import java.util.Map;

public class CodeWriteUtil {

    /**
     * 批量创建代码
     *
     * @param files
     */
    public static void writeFileNotAppend(Map<String, String> files) {
        for (Map.Entry<String, String> entry : files.entrySet()) {
            FileUtil.writeFileNotAppend(entry.getValue(), entry.getKey());
        }
    }

    public static void nioCopy(String source, String target) {
        String currentPath = Thread.currentThread().getContextClassLoader().getResource(source).getPath();
        FileUtil.nioTransferCopy(new File(currentPath), new File(target));
    }

    public static void nioCopyDir(String sourceFolder, String targetFolder) {
        String currentPath = Thread.currentThread().getContextClassLoader().getResource(sourceFolder).getPath();
        FileUtil.copyDir(currentPath, targetFolder);
    }
}
