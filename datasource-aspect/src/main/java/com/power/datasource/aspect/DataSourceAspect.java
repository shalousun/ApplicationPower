package com.power.datasource.aspect;


import com.power.datasource.annotations.TargetDataSource;
import com.power.datasource.database.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;


public class DataSourceAspect {
    protected static final ThreadLocal<String> preDatasourceHolder = new ThreadLocal<>();

    /**
     * @param clazz
     * @param name
     * @return
     */
    private static Method findUniqueMethod(Class<?> clazz, String name) {
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    @Pointcut("@annotation(com.power.datasource.annotations.TargetDataSource)")
    protected void datasourceAspect() {

    }

    /**
     * 根据@TargetDataSource的属性值设置不同的dataSourceKey,以供DynamicDataSource
     */
    @Before("datasourceAspect()")
    public void changeDataSourceBeforeMethodExecution(JoinPoint jp) {
        String key = determineDatasource(jp);
        if (key == null) {
            DataSourceContextHolder.setDatasourceType(null);
            return;
        }
        preDatasourceHolder.set(DataSourceContextHolder.getDatasourceType());
        DataSourceContextHolder.setDatasourceType(key);

    }

    /**
     * @param jp
     * @return
     */
    public String determineDatasource(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Class targetClass = jp.getSignature().getDeclaringType();
        String dataSourceForTargetClass = resolveDataSourceFromClass(targetClass);
        String dataSourceForTargetMethod = resolveDataSourceFromMethod(targetClass, methodName);
        String resultDS = determinateDataSource(dataSourceForTargetClass, dataSourceForTargetMethod);
        return resultDS;
    }

    /**
     *
     */
    @After("datasourceAspect()")
    public void restoreDataSourceAfterMethodExecution() {
        DataSourceContextHolder.setDatasourceType(preDatasourceHolder.get());
        preDatasourceHolder.remove();
    }

    /**
     * @param targetClass
     * @param methodName
     * @return
     */
    private String resolveDataSourceFromMethod(Class targetClass, String methodName) {
        Method m = findUniqueMethod(targetClass, methodName);
        if (m != null) {
            TargetDataSource choDs = m.getAnnotation(TargetDataSource.class);
            return resolveDataSourceName(choDs);
        }
        return null;
    }

    /**
     * @param classDS
     * @param methodDS
     * @return
     */
    private String determinateDataSource(String classDS, String methodDS) {
        return methodDS == null ? classDS : methodDS;
    }

    /**
     * @param targetClass
     * @return
     */
    private String resolveDataSourceFromClass(Class targetClass) {
        TargetDataSource classAnnotation = (TargetDataSource) targetClass.getAnnotation(TargetDataSource.class);
        return null != classAnnotation ? resolveDataSourceName(classAnnotation) : null;
    }

    /**
     * @param ds
     * @return
     */
    private String resolveDataSourceName(TargetDataSource ds) {
        return ds == null ? null : ds.value();
    }
}
