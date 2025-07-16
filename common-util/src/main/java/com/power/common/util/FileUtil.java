package com.power.common.util;

import com.power.common.model.FileInfo;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FileUtil
 * @apiNote File utility class
 * @javadoc
 * Created by yu on 2017/3/9.
 */
public class FileUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * make dir
     *
     * @param path file path
     * @return boolean
     */
    public static boolean mkdir(String path) {
        if (StringUtil.isEmpty(path)) {
            throw new NullPointerException("dir path can't null or empty");
        }
        File file = new File(path);
        return !file.exists() && file.mkdir();
    }

    /**
     * make dirs
     *
     * @param path file path
     * @return boolean
     */
    public static boolean mkdirs(String path) {
        if (StringUtil.isEmpty(path)) {
            throw new NullPointerException("dir path can't null or empty");
        }
        File file = new File(path);
        return !file.exists() && file.mkdirs();
    }

    /**
     * copy dir
     *
     * @param path     path
     * @param copyPath target path
     */
    public static void copyDir(String path, String copyPath) {
        File filePath = new File(path);
        if (filePath.isDirectory()) {
            File[] list = filePath.listFiles();
            if (Objects.isNull(list)) {
                list = new File[0];
            }
            for (File file : list) {
                String newPath = path + File.separator + file.getName();
                String newCopyPath = copyPath + File.separator + file.getName();
                File newFile = new File(copyPath);
                if (!newFile.exists()) {
                    newFile.mkdir();
                }
                nioTransferCopy(new File(newPath), new File(newCopyPath));
            }
        } else {
            throw new IllegalArgumentException(String.format("%s is not a directory", filePath.getAbsolutePath()));
        }
    }

    /**
     * use nio copy file
     *
     * @param source source file
     * @param target target file
     */
    public static void nioTransferCopy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inStream) {
                    inStream.close();
                }
                if (null != in) {
                    in.close();
                }
                if (null != outStream) {
                    outStream.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Convert InputStream to byte array
     *
     * @param inStream InputStream
     * @return byte array
     * @throws IOException io exception
     */
    public static byte[] copyToByteArray(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    /**
     * write string contents to file
     *
     * @param source   string contents
     * @param filePath local file path
     * @param append   append operate
     * @return boolean
     */
    public static boolean writeFile(String source, String filePath, boolean append) {
        return writeFile(source, filePath, append, DEFAULT_CHARSET);
    }

    /**
     * write string contents to file,overwrite any existing file
     *
     * @param source   string content
     * @param filePath file path
     * @return boolean
     */
    public static boolean writeFileNotAppend(String source, String filePath) {
        return writeFile(source, filePath, false, DEFAULT_CHARSET);
    }

    /**
     * write by OutPutStreamWriter
     *
     * @param source   String source
     * @param filePath File path
     * @param append   is append
     * @param encoding encoding
     * @return boolean
     */
    public static boolean writeFile(String source, String filePath, boolean append, String encoding) {
        boolean flag;
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(filePath, append), encoding);
            osw.write(source);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                if (Objects.nonNull(osw)) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * write by BufferedWriter
     *
     * @param source String source
     * @param file   File
     * @param append append operate
     * @return boolean
     */
    public static boolean writeFile(String source, File file, boolean append) {
        boolean flag = false;
        BufferedWriter output = null;
        try {
            if (file.createNewFile()) {
                output = new BufferedWriter(new FileWriter(file, append));
                output.write(source);
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(output)) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * get file content
     *
     * @param fileName file name
     * @return String
     */
    public static String getFileContent(String fileName) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(fileName);
            return getFileContent(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get String by input stream
     *
     * @param inputStream InputStream
     * @return String
     */
    public static String getFileContent(InputStream inputStream) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, DEFAULT_CHARSET));
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get files from folder
     *
     * @param folder folder
     * @return files array
     */
    public static File[] getResourceFolderFiles(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        if (null == url) {
            throw new RuntimeException("url is null");
        }
        String path = url.getPath();
        return new File(path).listFiles();
    }

    /**
     * Use nio write file
     *
     * @param contents string contents
     * @param filePath file path
     * @return boolean
     */
    public static boolean nioWriteFile(String contents, String filePath) {
        return nioWriteFile(filePath, contents, null);
    }

    /**
     * Appending The New Data To The Existing File
     *
     * @param contents string contents
     * @param filePath file path
     * @return boolean
     */
    public static boolean nioWriteAppendable(String contents, String filePath) {
        return nioWriteFile(filePath, contents, StandardOpenOption.APPEND);
    }

    /**
     * Use nio write file
     *
     * @param filePath   file path
     * @param contents   string contents
     * @param openOption open or create options
     * @return boolean
     */
    private static boolean nioWriteFile(String filePath, String contents, OpenOption openOption) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            if (null == openOption) {
                Files.write(path, contents.getBytes(DEFAULT_CHARSET));
            } else {
                Files.write(path, contents.getBytes(DEFAULT_CHARSET), openOption);
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * To Suffix
     * getFileExt
     *
     * @param fileName file name
     * @return String
     * @since 0.2
     */
    public static String toSuffix(String fileName) {
        String name = null;
        try {
            int index = fileName.lastIndexOf(".");
            name = fileName.substring(0, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * Get files from folder
     *
     * @param folder folder
     * @return List
     */
    public static List<FileInfo> getFilesFromFolder(String folder) {
        File file = new File(folder);
        File[] files = file.listFiles();
        if (Objects.isNull(files)) {
            return new ArrayList<>(0);
        }
        List<FileInfo> fileInfoList = new ArrayList<>(files.length);
        for (File f : files) {
            if (f.isFile()) {
                FileInfo fileInfo = FileInfo.builder()
                        .setFileName(f.getName())
                        .setPath(f.getPath())
                        .setCreateTime(DateTimeUtil.long2Str(f.lastModified(), DateTimeUtil.DATE_FORMAT_SECOND))
                        .setFileSize(f.length()).setMimeType(URLConnection.guessContentTypeFromName(f.getName()));
                fileInfoList.add(fileInfo);
            }
        }
        return fileInfoList;
    }

    /**
     * Copies bytes from an InputStream source to a file destination. The directories up to destination will be created if they don't already exist. destination will be overwritten if it already exists.
     *
     * @param source      the InputStream to copy bytes from, must not be null
     * @param destination the non-directory File to write bytes to (possibly overwriting), must not be null
     * @param copyOption  copy option
     * @return boolean
     */
    public static boolean copyInputStreamToFile(InputStream source, File destination, StandardCopyOption copyOption) {
        boolean flag = true;
        try {
            java.nio.file.Files.copy(
                    source,
                    destination.toPath(),
                    copyOption);
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                if (source != null) {
                    source.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
