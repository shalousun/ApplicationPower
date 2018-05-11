package com.boco.common.util;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/3/9.
 */
public class FileUtil {

    /**
     * 创建目录
     *
     * @param path
     */
    public static void mkdir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * 创建多级目录
     *
     * @param path
     */
    public static void mkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 复制文件到目标目录
     *
     * @param source
     * @param target
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
                inStream.close();
                in.close();
                outStream.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //将输入流转byte
    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * 写文件
     *
     * @param source
     * @param filePath
     * @param append
     * @return
     */
    public static boolean writeFile(String source, String filePath, boolean append) {
        return writeFile(source, filePath, append, "utf-8");
    }

    /**
     * @param source
     * @param filePath
     * @return
     */
    public static boolean writeFileNotAppend(String source, String filePath) {
        return writeFile(source, filePath, false, "utf-8");
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
        boolean flag = false;
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
                osw.close();
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
     * @return boolean
     */
    public static boolean writeFile(String source, File file) {
        boolean flag = true;
        BufferedWriter output = null;
        try {
            file.createNewFile();
            output = new BufferedWriter(new FileWriter(file, true));
            output.write(source);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                output.close();
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
        BufferedReader reader = null;
        StringBuilder fileContent = new StringBuilder();
        try {
            File f = new File(fileName);
            reader = new BufferedReader(new FileReader(f));
            String line = "";
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
                fileContent.append("\n");
            }

        } catch (Exception e) {
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
        return fileContent.toString();
    }

    /**
     * Get String by input stream
     *
     * @param is InputStream
     * @return String
     */
    public static String getFileContent(InputStream is) {
        BufferedReader reader = null;
        StringBuilder fileContent = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
                fileContent.append("\n");
            }

        } catch (Exception e) {
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
        return fileContent.toString();
    }

    /**
     * 获取文件加下的文件
     * @param folder
     * @return
     */
    public static File[] getResourceFolderFiles (String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        return new File(path).listFiles();
    }

    /**
     * Use nio write file
     * @param filePath file path
     * @param contents
     * @return
     */
    public static boolean nioWriteFile(String filePath,String contents){
        return nioWriteFile(filePath,contents,null);
    }

    /**
     * Appending The New Data To The Existing File
     * @param filePath
     * @param contents
     * @return
     */
    public static boolean nioWriteAppendable(String filePath,String contents){
        return nioWriteFile(filePath,contents, StandardOpenOption.APPEND);
    }

    /**
     * Use nio write file
     * @param filePath
     * @param contents
     * @param openOption
     * @return
     */
    public static boolean nioWriteFile(String filePath, String contents, OpenOption openOption){
        Path path = Paths.get(filePath);
        try{
            Files.createDirectories(path.getParent());
            if( !Files.exists(path)){
                Files.createFile(path);
            }
            if(null == openOption){
                Files.write(path, contents.getBytes("utf-8"));
            }else{
                Files.write(path, contents.getBytes("utf-8"),openOption);
            }
            return true;
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return false;
    }
}
