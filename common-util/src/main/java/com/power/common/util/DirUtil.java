package com.power.common.util;

import com.power.common.file.CopyDirVisitor;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Objects;

/**
 * DirUtil
 * @apiNote This class is designed to be a utility class, hence it is marked as final to prevent inheritance.
 * @javadoc
 * @author yu 2018/06/03.
 */
public class DirUtil {

    private DirUtil() {

    }

    /**
     * Copies a directory tree from one location to another.
     * @apiNote This method recursively copies all files and subdirectories under the source directory to the target location.
     *
     * @param from The path of the source directory, which should exist and be a directory.
     * @param to   The path of the target directory, where the source directory will be copied to.
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
     * Validates the provided directory paths to ensure they are non-null and exist.
     * @apiNote Throws an IllegalArgumentException if any of the paths are null or do not represent an existing directory.
     *
     * @param paths An array of Path objects representing the directories to be validated.
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
