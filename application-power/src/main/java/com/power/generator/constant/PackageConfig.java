package com.power.generator.constant;

/**
 * Created by yu on 2016/12/9.
 */
public class PackageConfig {

    private String service = "service";

    private String serviceImpl = "service.impl";

    private String entity = "model";

    private String controller = "controller";

    private String dao = "dao";

    private String mapper = "mappers";

    private String converter = "convert";

    private String utils = "utils";

    private String config = "config";

    private String aspect = "aspect";

    private String constants = "constants";

    private String restError = "error";

    private String docs = "docs";

    public String getService() {
        return service;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public String getEntity() {
        return entity;
    }

    public String getController() {
        return controller;
    }

    public String getMapper() {
        return mapper;
    }

    public String getDao() {
        return dao;
    }

    public String getConverter() {
        return converter;
    }

    public String getUtils() {
        return utils;
    }

    public String getConfig() {
        return config;
    }

    public String getConstants() {
        return constants;
    }

    public String getAspect() {
        return aspect;
    }

    public String getRestError() {
        return restError;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }
}
