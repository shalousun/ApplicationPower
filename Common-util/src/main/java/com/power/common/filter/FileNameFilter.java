package com.power.common.filter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * File filter
 *
 * @author sunyu
 */
public class FileNameFilter implements FilenameFilter {
    String extension = ".";

    public FileNameFilter(String fileExtensionNoDot) {
        extension += fileExtensionNoDot;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(extension);
    }

}
