package com.power.generator.model;

/**
 * Created by yu on 2017/4/29.
 */
public class ProjectPath {

    /**
     * 项目根据路径
     */
    private String basePath;

    /**
     * src/main/java
     */
    private String javaSrcPath;

    /**
     * src/test/java
     */
    private String testJavaSrcPath;

    /**
     * src/main/resources
     */
    private String resourceDir;


    //getters and setters
    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getJavaSrcPath() {
        return javaSrcPath;
    }

    public void setJavaSrcPath(String javaSrcPath) {
        this.javaSrcPath = javaSrcPath;
    }

    public String getTestJavaSrcPath() {
        return testJavaSrcPath;
    }

    public void setTestJavaSrcPath(String testJavaSrcPath) {
        this.testJavaSrcPath = testJavaSrcPath;
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
    }


}
