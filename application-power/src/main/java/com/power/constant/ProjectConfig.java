package com.power.constant;

/**
 * Created by yu on 2016/12/9.
 */
public class ProjectConfig {

    private String resource = "src\\main\\resources";
    private String springMvc = resource + "\\spring-mvc.xml";
    private String springMybatis = resource + "\\spring-mybatis.xml";
    private String mybatisConfig = resource + "\\mybatis-config.xml";
    private String errorPath = "src\\main\\webapp\\error";
    private String html400 = errorPath + "\\400.html";
    private String html404 = errorPath + "\\404.html";
    private String html500 = errorPath + "\\500.html";
    private String pom = "pom.xml";
    private String log4j = resource + "\\log4j.properties";
    private String log4j2 = resource + "\\log4j2.xml";

    private String jdbc = resource + "\\jdbc.properties";
    private String webAppPath = "src\\main\\webapp";
    private String webInfoPath = "src\\main\\webapp\\WEB-INF";
    private String webXml = webInfoPath + "\\web.xml";

    public String getResource() {
        return resource;
    }

    public String getSpringMvc() {
        return springMvc;
    }

    public String getSpringMybatis() {
        return springMybatis;
    }

    public String getErrorPath() {
        return errorPath;
    }

    public String getHtml404() {
        return html404;
    }

    public String getHtml500() {
        return html500;
    }

    public String getPom() {
        return pom;
    }

    public String getLog4j() {
        return log4j;
    }

    public String getJdbc() {
        return jdbc;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public String getWebInfoPath() {
        return webInfoPath;
    }

    public String getWebXml() {
        return webXml;
    }

    public String getMybatisConfig() {
        return mybatisConfig;
    }

    public String getHtml400() {
        return html400;
    }

    public String getLog4j2() {
        return log4j2;
    }

    public void setLog4j2(String log4j2) {
        this.log4j2 = log4j2;
    }
}
