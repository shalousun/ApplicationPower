package com.power.common.model;

/**
 * @author yu 2020/11/11.
 */
public class FileInfo {

    /**
     * file name
     */
    private String fileName;

    /**
     * file create time
     */
    private String createTime;

    /**
     * file path
     */
    private String path;

    /**
     * file size
     */
    private long fileSize;

    /**
     * file mimeType
     */
    private String mimeType;

    public static FileInfo builder(){
        return new FileInfo();
    }

    public String getFileName() {
        return fileName;
    }

    public FileInfo setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public long getFileSize() {
        return fileSize;
    }

    public FileInfo setFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public FileInfo setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getPath() {
        return path;
    }

    public FileInfo setPath(String path) {
        this.path = path;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public FileInfo setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"fileName\":\"")
                .append(fileName).append('\"');
        sb.append(",\"createTime\":\"")
                .append(createTime).append('\"');
        sb.append(",\"path\":\"")
                .append(path).append('\"');
        sb.append(",\"fileSize\":")
                .append(fileSize);
        sb.append(",\"mimeType\":\"")
                .append(mimeType).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
