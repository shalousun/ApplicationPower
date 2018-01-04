package com.power.doc.builer;

import com.boco.common.util.StringUtil;
import com.power.doc.model.ApiDoc;
import com.power.doc.model.ApiMethodDoc;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.*;

import java.io.File;
import java.util.*;

public class SourceBuilder {

    private static final String GET_MAPPING = "GetMapping";

    private static final String POST_MAPPING = "PostMapping";

    private static final String REQUEST_MAPPING = "RequestMapping";

    private static final String MODEL_VIEW = "org.springframework.web.servlet.ModelAndView";

    private static final String MODEL = "org.springframework.ui.Model";

    private static final String PAGE_INFO = "com.github.pagehelper.PageInfo";

    private static final String COMMON_RESULT = "com.boco.common.model.CommonResult";

    private Map<String, String> javaFilesMap = new HashMap<>();

    private JavaProjectBuilder builder;

    private Collection<JavaClass> javaClasses;

    private boolean isStrict = false;//严格模式

    /**
     * if isStrict value is true,it while check all method
     * @param isStrict
     */
    public SourceBuilder(boolean isStrict) {
        loadJavaFiles(null);
        this.isStrict = isStrict;
    }

    /**
     * 加载项目的源代码
     * @param path
     */
    private void loadJavaFiles(String path) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File("src/main/java"));
        this.builder = builder;
        this.javaClasses = builder.getClasses();
        for (JavaClass cls : javaClasses) {
            javaFilesMap.put(cls.getName(), cls.getName());
        }
    }

    /**
     * 检测controller上的注解
     * @param cls
     * @return
     */
    private int checkController(JavaClass cls){
        int counter = 0;
        List<JavaAnnotation> classAnnotations = cls.getAnnotations();
        for (JavaAnnotation annotation : classAnnotations) {
            String annotationName = annotation.getType().getName();
            if ("Controller".equals(annotationName) || "RestController".equals(annotationName)) {
                counter++;
            }
        }
        return counter;
    }
    public List<ApiDoc> getControllerApiData() {
        List<ApiDoc> apiDocList = new ArrayList<>();
        for (JavaClass cls : javaClasses) {
            int counter = checkController(cls);
            if (counter > 0) {
                String controllerName = cls.getName();
                List<ApiMethodDoc> apiMethodDocs= buildControllerMethod(cls);
                ApiDoc apiDoc = new ApiDoc();
                apiDoc.setList(apiMethodDocs);
                apiDoc.setName(controllerName);

                apiDocList.add(apiDoc);
            }
        }
        return apiDocList;
    }


    /**
     * 包括包名
     * @param controller controller的名称
     * @return
     */
    public ApiDoc getSingleControllerApiData(String controller){
        if(!javaFilesMap.containsKey(controller)){
            throw new RuntimeException("Unable to find "+controller+" from your project");
        }
        JavaClass cls = builder.getClassByName(controller);
        int counter = checkController(cls);
        if (counter > 0) {
            String controllerName = cls.getName();
            List<ApiMethodDoc> apiMethodDocs= buildControllerMethod(cls);
            ApiDoc apiDoc = new ApiDoc();
            apiDoc.setList(apiMethodDocs);
            apiDoc.setName(controllerName);
           return  apiDoc;
        }else{
            throw new RuntimeException(controller+" is not a Controller  in your project");
        }
    }

    public List<ApiMethodDoc> buildControllerMethod(final JavaClass cls) {
        List<JavaAnnotation> classAnnotations = cls.getAnnotations();
        String baseUrl = null;
        for (JavaAnnotation annotation : classAnnotations) {
            String annotationName = annotation.getType().getName();
            if (REQUEST_MAPPING.equals(annotationName)) {
                baseUrl = annotation.getNamedParameter("value").toString();
                baseUrl = baseUrl.replaceAll("\"", "");
            }
        }
        List<JavaMethod> methods = cls.getMethods();
        List<ApiMethodDoc> methodDocList = new ArrayList<>(methods.size());
        for (JavaMethod method : methods) {
            ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
            if(StringUtil.isEmpty(method.getComment())&&isStrict){
                throw new RuntimeException("Unable to find comment for  method " + method.getName()+" from "+cls.getName());
            }
            apiMethodDoc.setDesc(method.getComment());
            List<JavaAnnotation> annotations = method.getAnnotations();
            String url = null;
            String methodType = null;
            int methodCounter = 0;
            for (JavaAnnotation annotation : annotations) {
                String annotationName = annotation.getType().getName();
                if (REQUEST_MAPPING.equals(annotationName)) {
                    url = annotation.getNamedParameter("value").toString();
                    if (url.contains("POST")) {
                        methodType = "POST";
                    } else {
                        methodType = "GET";
                    }
                    methodCounter ++;
                } else if (GET_MAPPING.equals(annotationName)) {
                    url = annotation.getNamedParameter("value").toString();
                    methodType = "GET";
                    methodCounter ++;
                } else if (POST_MAPPING.equals(annotationName)) {
                    url = annotation.getNamedParameter("value").toString();
                    methodType = "POST";
                    methodCounter ++;
                }
            }
            if(methodCounter>0){
                url = url.replaceAll("\"", "").trim();
                apiMethodDoc.setType(methodType);
                apiMethodDoc.setUrl((baseUrl + "/" + url).replace("//", "/"));
                String comment = getCommentTag(method, "param",cls.getName());
                apiMethodDoc.setRequestParams(comment);
                buildMethodReturn(method, apiMethodDoc);
                methodDocList.add(apiMethodDoc);
            }
        }
        return methodDocList;

    }

    public String buildMethodReturn(JavaMethod method, ApiMethodDoc apiMethodDoc) {
        String returnType = method.getReturnType().getGenericCanonicalName();
        String typeName = method.getReturnType().getFullyQualifiedName();
        if (StringUtil.isNotEmpty(returnType)) {
            String gicName = null;
            //反射存在
            StringBuilder params0 = new StringBuilder();
            StringBuilder data0 = new StringBuilder();
            if (returnType.contains("<")) {
                gicName = returnType.substring(returnType.indexOf("<") + 1, returnType.indexOf(">"));
                JavaClass cls = builder.getClassByName(gicName);
                data0.append("{\n");
                List<JavaField> fields = cls.getFields();
                for (JavaField field : fields) {
                    if (!"serialVersionUID".equals(field.getName())) {
                        data0.append("    \"").append(field.getName()).append("\":").append(" ").append(",\n");
                        params0.append(field.getName()).append("|")
                                .append(field.getType().getSimpleName().toLowerCase()).append("|")
                                .append(field.getComment()).append("\n");
                    }
                }
                data0.deleteCharAt(data0.lastIndexOf(","));
                data0.append("  }");

            } else if (!PAGE_INFO.equals(typeName)&&!COMMON_RESULT.equals(typeName)) {
                JavaClass cls = builder.getClassByName(typeName);
                data0.append("{\n");
                List<JavaField> fields = cls.getFields();
                for (JavaField field : fields) {
                    if (!"serialVersionUID".equals(field.getName())) {
                        data0.append("    \"").append(field.getName()).append("\":").append(" ").append(",\n");
                        params0.append(field.getName()).append("|")
                                .append(field.getType().getSimpleName().toLowerCase()).append("|")
                                .append(field.getComment()).append("\n");
                    }
                }
                data0.deleteCharAt(data0.lastIndexOf(","));
                data0.append("  }");
            }
            StringBuilder data = new StringBuilder();
            StringBuilder params = new StringBuilder();
            if ("java.util.List".equals(typeName)) {
                data.append("[");
                data.append(data0.toString()).append("]\n");
                params.append("参数名称 | 参数类型|描述\n");
                params.append("---|---|---\n");
                params.append(params0.toString());
            } else if ("java.util.Map".equals(typeName)) {

            } else if (COMMON_RESULT.equals(typeName)) {
                data.append("{\n");
                data.append("  \"code\": 0,\n");
                data.append("  \"message\": \"操作成功\",\n");
                data.append("  \"success\": true,\n");
                if (data0.length() > 0) {
                    data.append("  \"data\":").append(data0.toString());
                } else {
                    data.append("  \"data\":").append("null\n");
                }
                data.append("\n}");

                params.append("参数名称 | 参数类型|描述\n");
                params.append("---|---|---\n");
                params.append("code | int |错误编码，目前属于保留字段\n");
                params.append("message |string | 成功或者失败信息\n");
                params.append("success| boolean | 成功返回true,错误返回false\n");
                params.append("data| object | 查询操作success为true，data才有数据\n");
                params.append(params0.toString());
            } else if (PAGE_INFO.equals(typeName)) {
                data.append("{\n");
                data.append("  \"total\": 0,\n");
                data.append("  \"pages\": 0,\n");
                if (data0.length() > 0) {
                    data.append("  \"list\":[").append(data0.toString()).append("]");
                } else {
                    data.append("  \"list\":[").append("").append("]\n");
                }
                data.append("\n}");

                params.append("参数名称 | 参数类型|描述\n");
                params.append("---|---|---\n");
                params.append("total | total |总记录数\n");
                params.append("pages |integer | 成功或者失败信息\n");
                params.append("list| array | 当前页的数据\n");
                params.append(params0.toString());
            }
            apiMethodDoc.setResponseUsage(data.toString());
            apiMethodDoc.setResponseParams(params.toString());
        }
        return null;
    }

    private String getCommentTag(final JavaMethod javaMethod, final String tagName,final String className) {
        List<DocletTag> paramTags = javaMethod.getTagsByName(tagName);
        Map<String, String> paramTagMap = new HashMap<>();
        for (DocletTag docletTag : paramTags) {
            String value = docletTag.getValue();
            if (StringUtil.isEmpty(value)) {
                throw new RuntimeException("ERROR: #" + javaMethod.getName()
                        + "() - bad @param javadoc from "+className);
            }
            String pName;
            String pValue;
            int idx = value.indexOf("\n");
            //如果存在换行
            if (idx > -1) {
                pName = value.substring(0, idx);
                pValue = value.substring(idx + 1);
            } else {
                pName = (value.indexOf(" ") > -1) ? value.substring(0, value.indexOf(" ")) : value;
                pValue = value.indexOf(" ") > -1 ? value.substring(value.indexOf(' ') + 1) : "No Comment";
            }
            paramTagMap.put(pName, pValue);
        }

        List<JavaParameter> parameterList = javaMethod.getParameters();
        if (parameterList.size() > 0) {
            StringBuilder params = new StringBuilder();
            params.append("参数名称 | 参数类型|描述|是否必填\n");
            params.append("---|---|---|---\n");
            for (JavaParameter parameter : parameterList) {
                String typeName = parameter.getType().getGenericCanonicalName();
                if (!MODEL.equals(typeName) && !MODEL_VIEW.equals(typeName)) {
                    if (!paramTagMap.containsKey(parameter.getName())) {
                        throw new RuntimeException("Unable to find javadoc @param for actual param \""
                                + parameter.getName() + "\" in method " + javaMethod.getName()+" from "+className);
                    }
                    params.append(parameter.getName()).append("|")
                            .append(parameter.getType().getValue().toLowerCase()).append("|")
                            .append(paramTagMap.get(parameter.getName())).append("|\n");
                }
            }
            return params.toString();
        }
        return null;
    }



}
