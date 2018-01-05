package com.power.generator.builder;

import com.power.generator.constant.PackageConfig;
import com.power.generator.constant.ProjectConfig;
import com.power.generator.constant.SpringBootProjectConfig;
import com.power.generator.database.DbProvider;
import com.power.generator.factory.DbProviderFactory;

/**
 * Created by yu on 2016/12/10.
 */
public abstract class AbstractCodeWriter {

    /**
     * 数据库信息
     */
    private DbProvider dataBaseInfo;

    /**
     * 包配置
     */
    private PackageConfig packageConfig;

    /**
     * 工程配置
     */
    private ProjectConfig projectConfig;


    private SpringBootProjectConfig springBootProjectConfig;

    /**
     *
     */
    protected ConfigBuilder config;

    /**
     * 初始化配置
     */
    protected void initConfig() {
        if (null == config) {
            dataBaseInfo = new DbProviderFactory().getInstance();
            config = new ConfigBuilder(dataBaseInfo, packageConfig, projectConfig);

        }
    }

    /**
     * 初始化spring boot的配置
     */
    protected void initSpringBootConfig() {
        if (null == config) {
            dataBaseInfo = new DbProviderFactory().getInstance();
            config = new ConfigBuilder(dataBaseInfo, packageConfig, springBootProjectConfig);

        }
    }

    public DbProvider getDataBaseInfo() {
        return dataBaseInfo;
    }

    public void setDataBaseInfo(DbProvider dataBaseInfo) {
        this.dataBaseInfo = dataBaseInfo;
    }

    public PackageConfig getPackageConfig() {
        return packageConfig;
    }

    public void setPackageConfig(PackageConfig packageConfig) {
        this.packageConfig = packageConfig;
    }

    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public ConfigBuilder getConfig() {
        return config;
    }

    public void setConfig(ConfigBuilder config) {
        this.config = config;
    }

    public SpringBootProjectConfig getSpringBootProjectConfig() {
        return springBootProjectConfig;
    }

    public void setSpringBootProjectConfig(SpringBootProjectConfig springBootProjectConfig) {
        this.springBootProjectConfig = springBootProjectConfig;
    }
}
