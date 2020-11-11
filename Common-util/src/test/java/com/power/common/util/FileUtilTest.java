package com.power.common.util;

import com.power.common.model.FileInfo;
import org.junit.Test;

import java.util.List;

/**
 * Description:
 * FileUtil单元测试
 *
 * @author yu 2018/06/09.
 */
public class FileUtilTest {

    @Test
    public void testToSuffix() {
        System.out.println(FileUtil.toSuffix("me.java"));
    }

    @Test
    public void testGetResourceFolderFiles() {
        List<FileInfo> files = FileUtil.getFilesFromFolder("D:\\data\\data-mysql\\student");
        for (FileInfo file : files) {
            System.out.println(file.toString());
        }
    }
}
