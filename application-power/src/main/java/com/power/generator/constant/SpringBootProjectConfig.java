package com.power.generator.constant;

/**
 * Spring boot项目工程配置
 * Created by yu on 2017/4/29.
 */
public class SpringBootProjectConfig {

    private static final String jpaPath = "config";
    private String resource = "src" + ConstVal.FILE_SEPARATOR + "main" + ConstVal.FILE_SEPARATOR + "resources";
    private String mybatisConfig = resource + ConstVal.FILE_SEPARATOR + "mybatis-config.xml";
    private String errorPath = resource + ConstVal.FILE_SEPARATOR + "static" + ConstVal.FILE_SEPARATOR + "error";
    private String html400 = errorPath + ConstVal.FILE_SEPARATOR + "400.html";
    private String html404 = errorPath + ConstVal.FILE_SEPARATOR + "404.html";
    private String html500 = errorPath + ConstVal.FILE_SEPARATOR + "500.html";
    private String pom = "pom.xml";
    private String log4j2 = resource + ConstVal.FILE_SEPARATOR + "log4j2.xml";
    private String applicationYml = resource + ConstVal.FILE_SEPARATOR + "application.yml";
    private String assemblyRoot = "src" + ConstVal.FILE_SEPARATOR + "main" + ConstVal.FILE_SEPARATOR + "assembly";
    private String assemblyBin = "src" + ConstVal.FILE_SEPARATOR + "main" + ConstVal.FILE_SEPARATOR + "assembly" + ConstVal.FILE_SEPARATOR + "bin";
    private String assemblyCfg = "src" + ConstVal.FILE_SEPARATOR + "main" + ConstVal.FILE_SEPARATOR + "assembly" + ConstVal.FILE_SEPARATOR + "config";
    private String log4j2Assembly = assemblyCfg + ConstVal.FILE_SEPARATOR + "log4j2.xml";
    private String applicationYmlAssembly = assemblyCfg + ConstVal.FILE_SEPARATOR + "application.yml";
    private String smartDocJson = resource + ConstVal.FILE_SEPARATOR + "smart-doc.json";
    private String readme = "README.md";

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getMybatisConfig() {
        return mybatisConfig;
    }

    public void setMybatisConfig(String mybatisConfig) {
        this.mybatisConfig = mybatisConfig;
    }

    public String getErrorPath() {
        return errorPath;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }

    public String getHtml400() {
        return html400;
    }

    public void setHtml400(String html400) {
        this.html400 = html400;
    }

    public String getHtml404() {
        return html404;
    }

    public void setHtml404(String html404) {
        this.html404 = html404;
    }

    public String getHtml500() {
        return html500;
    }

    public void setHtml500(String html500) {
        this.html500 = html500;
    }

    public String getPom() {
        return pom;
    }

    public void setPom(String pom) {
        this.pom = pom;
    }

    public String getLog4j2() {
        return log4j2;
    }

    public void setLog4j2(String log4j2) {
        this.log4j2 = log4j2;
    }

    public String getApplicationYml() {
        return applicationYml;
    }

    public void setApplicationYml(String applicationYml) {
        this.applicationYml = applicationYml;
    }

    public String getAssemblyRoot() {
        return assemblyRoot;
    }

    public String getAssemblyBin() {
        return assemblyBin;
    }

    public String getAssemblyCfg() {
        return assemblyCfg;
    }

    public String getLog4j2Assembly() {
        return log4j2Assembly;
    }

    public String getApplicationYmlAssembly() {
        return applicationYmlAssembly;
    }

    public String getSmartDocJson() {
        return smartDocJson;
    }

    public void setSmartDocJson(String smartDocJson) {
        this.smartDocJson = smartDocJson;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }
}
