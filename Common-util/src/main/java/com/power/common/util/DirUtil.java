package com.power.common.util;

import com.power.common.file.CopyDirVisitor;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Objects;

/**
 * @author yu 2018/06/03.
 */
public class DirUtil {

    private DirUtil() {

    }

    /**
     * Copies a directory tree
     *
     * @param from from
     * @param to   to
     */
    public static void copy(Path from, Path to) {
        validate(from);
        try {
            Files.walkFileTree(from, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new CopyDirVisitor(from, to));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * validate path
     *
     * @param paths array of path
     */
    private static void validate(Path... paths) {
        for (Path path : paths) {
            Objects.requireNonNull(path);
            if (!Files.isDirectory(path)) {
                throw new IllegalArgumentException(String.format("%s is not a directory", path.toString()));
            }
        }
    }

}
