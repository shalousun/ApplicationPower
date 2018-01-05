package com.power.generator.constant;

/**
 * Spring boot项目工程配置
 * Created by yu on 2017/4/29.
 */
public class SpringBootProjectConfig {

    private String resource = "src\\main\\resources";
    private String mybatisConfig = resource + "\\mybatis-config.xml";
    private String errorPath = resource + "\\static\\errors";
    private String html400 = errorPath + "\\400.html";
    private String html404 = errorPath + "\\404.html";
    private String html500 = errorPath + "\\500.html";
    private String pom = "pom.xml";
    private String log4j2 = resource + "\\log4j2.xml";
    private String applicationYml = resource + "\\application.yml";

    private String assemblyRoot = "src\\main\\assembly";
    private String assemblyBin = "src\\main\\assembly\\bin";
    private String assemblyCfg = "src\\main\\assembly\\config";
    private String log4j2Assembly = assemblyCfg + "\\log4j2.xml";
    private String applicationYmlAssembly = assemblyCfg + "\\application.yml";

    private static final String jpaPath = "config";


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
}
