package com.power.doc.builer;

import com.power.common.util.JsonFormatUtil;
import com.power.common.util.StringUtil;
import com.power.doc.model.ApiDoc;
import com.power.doc.model.ApiMethodDoc;
import com.power.doc.utils.DocClassUtil;
import com.power.doc.utils.DocUtil;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.*;

import java.io.File;
import java.util.*;

public class SourceBuilder {

    private static final String GET_MAPPING = "GetMapping";

    private static final String POST_MAPPING = "PostMapping";

    private static final String REQUEST_MAPPING = "RequestMapping";

    private static final String REQUEST_BODY = "RequestBody";

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
     *
     * @param isStrict
     */
    public SourceBuilder(boolean isStrict) {
        loadJavaFiles(null);
        this.isStrict = isStrict;
    }

    /**
     * 加载项目的源代码
     *
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
     *
     * @param cls
     * @return
     */
    private int checkController(JavaClass cls) {
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
                List<ApiMethodDoc> apiMethodDocs = buildControllerMethod(cls);
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
     *
     * @param controller controller的名称
     * @return
     */
    public ApiDoc getSingleControllerApiData(String controller) {
        if (!javaFilesMap.containsKey(controller)) {
            throw new RuntimeException("Unable to find " + controller + " from your project");
        }
        JavaClass cls = builder.getClassByName(controller);
        int counter = checkController(cls);
        if (counter > 0) {
            String controllerName = cls.getName();
            List<ApiMethodDoc> apiMethodDocs = buildControllerMethod(cls);
            ApiDoc apiDoc = new ApiDoc();
            apiDoc.setList(apiMethodDocs);
            apiDoc.setName(controllerName);
            return apiDoc;
        } else {
            throw new RuntimeException(controller + " is not a Controller  in your project");
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
            if (StringUtil.isEmpty(method.getComment()) && isStrict) {
                throw new RuntimeException("Unable to find comment for  method " + method.getName() + " from " + cls.getName());
            }
            apiMethodDoc.setDesc(method.getComment());
            List<JavaAnnotation> annotations = method.getAnnotations();
            String url = null;
            String methodType = null;
            int methodCounter = 0;
            for (JavaAnnotation annotation : annotations) {
                String annotationName = annotation.getType().getName();
                if (REQUEST_MAPPING.equals(annotationName)) {
                    if (null == annotation.getNamedParameter("value")) {
                        throw new NullPointerException("Unable to find RequestMapping value for  method " + method.getName() + " from " + cls.getName());
                    }
                    url = annotation.getNamedParameter("value").toString();
                    if (url.contains("POST")) {
                        methodType = "POST";
                    } else {
                        methodType = "GET";
                    }
                    methodCounter++;
                } else if (GET_MAPPING.equals(annotationName)) {
                    if (null == annotation.getNamedParameter("value")) {
                        throw new NullPointerException("Unable to find GetMapping value for  method " + method.getName() + " from " + cls.getName());
                    }
                    url = annotation.getNamedParameter("value").toString();
                    methodType = "GET";
                    methodCounter++;
                } else if (POST_MAPPING.equals(annotationName)) {
                    if (null == annotation.getNamedParameter("value")) {
                        throw new NullPointerException("Unable to find PostMapping value for  method " + method.getName() + " from " + cls.getName());
                    }
                    url = annotation.getNamedParameter("value").toString();
                    methodType = "POST";
                    methodCounter++;
                }
            }
            if (methodCounter > 0) {
                url = url.replaceAll("\"", "").trim();
                apiMethodDoc.setType(methodType);
                apiMethodDoc.setUrl((baseUrl + "/" + url).replace("//", "/"));
                String comment = getCommentTag(method, "param", cls.getName());
                apiMethodDoc.setRequestParams(comment);
                String requestJson = buildReqJson(method);
                apiMethodDoc.setRequestUsage(JsonFormatUtil.formatJson(requestJson));
                System.out.println("requestJson:" + requestJson);
                apiMethodDoc.setResponseUsage(buildReturnJson(method));
                buildMethodReturn(method, apiMethodDoc);
                methodDocList.add(apiMethodDoc);
            }
        }
        return methodDocList;

    }

    public String buildMethodReturn(JavaMethod method, ApiMethodDoc apiMethodDoc) {
        String returnType = method.getReturnType().getGenericCanonicalName();
        System.out.println("returnType:" + returnType);
        String typeName = method.getReturnType().getFullyQualifiedName();
        System.out.println("simpleType:" + typeName);
        System.out.println("respJson:" + JsonFormatUtil.formatJson(buildJson(typeName, returnType)));
        if (StringUtil.isNotEmpty(returnType)) {
            String gicName = null;
            //反射存在
            StringBuilder params0 = new StringBuilder();
            if ("java.util.Map".equals(typeName)) {
                System.out.println("map，无法处理");
            } else if (returnType.contains("<")) {
                gicName = returnType.substring(returnType.indexOf("<") + 1, returnType.indexOf(">"));
                JavaClass cls = builder.getClassByName(gicName);
                List<JavaField> fields = cls.getFields();
                for (JavaField field : fields) {
                    if (!"serialVersionUID".equals(field.getName())) {
                        params0.append(field.getName()).append("|")
                                .append(field.getType().getSimpleName().toLowerCase()).append("|")
                                .append(field.getComment());
                    }
                }

            } else if (!PAGE_INFO.equals(typeName) && !COMMON_RESULT.equals(typeName)) {
                JavaClass cls = builder.getClassByName(typeName);
                List<JavaField> fields = cls.getFields();
                for (JavaField field : fields) {
                    if (!"serialVersionUID".equals(field.getName())) {
                        String typeSimpleName = field.getType().getSimpleName();
                        System.out.println("typeName:" + typeSimpleName);
                        params0.append(field.getName()).append("|")
                                .append(field.getType().getSimpleName().toLowerCase()).append("|")
                                .append(field.getComment()).append("");
                    }
                }
            }
            StringBuilder params = new StringBuilder();
            if ("java.util.List".equals(typeName)) {
                params.append("参数名称 | 参数类型|描述\n");
                params.append("---|---|---\n");
                params.append(params0.toString());
            } else if (COMMON_RESULT.equals(typeName)) {
                params.append("参数名称 | 参数类型|描述\n");
                params.append("---|---|---\n");
                params.append("code | int |错误编码，目前属于保留字段\n");
                params.append("message |string | 成功或者失败信息\n");
                params.append("success| boolean | 成功返回true,错误返回false\n");
                params.append("data| object | 查询操作success为true，data才有数据\n");
                params.append(params0.toString());
            } else if (PAGE_INFO.equals(typeName)) {
                params.append("参数名称 | 参数类型|描述\n");
                params.append("---|---|---\n");
                params.append("total | total |总记录数\n");
                params.append("pages |integer | 成功或者失败信息\n");
                params.append("list| array | 当前页的数据\n");
                params.append(params0.toString());
            } else {
                params.append("参数名称 | 参数类型|描述\n");
                params.append("---|---|---\n");
                params.append(params0.toString());
            }
            apiMethodDoc.setResponseParams(params.toString());
        }
        return null;
    }

    /**
     * 构建返回的json
     * @param method
     * @return
     */
    private String buildReturnJson(JavaMethod method){
        String returnType = method.getReturnType().getGenericCanonicalName();
        String typeName = method.getReturnType().getFullyQualifiedName();
        return JsonFormatUtil.formatJson(buildJson(typeName, returnType));
    }

    private String buildJson(String typeName, String genericCanonicalName) {
        if(DocClassUtil.isPrimitive(typeName)){
            return DocUtil.jsonValueByType(typeName).replace("\"","");
        }
        StringBuilder data0 = new StringBuilder();
        JavaClass cls = builder.getClassByName(typeName);
        data0.append("{");
        String[] globGicName = DocClassUtil.getSimpleGicName(genericCanonicalName);


        List<JavaField> fields = cls.getFields();
        int i = 0;
        for (JavaField field : fields) {
            if (!"serialVersionUID".equals(field.getName())) {
                String typeSimpleName = field.getType().getSimpleName();
                String subTypeName = field.getType().getFullyQualifiedName();
                data0.append("\"").append(field.getName()).append("\":");
                if (DocClassUtil.isPrimitive(typeSimpleName)) {
                    data0.append(DocUtil.jsonValueByType(typeSimpleName)).append(",");
                } else {
                    if ("java.util.List".equals(subTypeName)) {
                        String gNameTemp = field.getType().getGenericCanonicalName();
                        String gicName = DocClassUtil.getSimpleGicName(gNameTemp)[0];
                        data0.append("[").append(buildJson(gicName, gNameTemp)).append("]").append(",");
                    } else if ("java.util.Map".equals(subTypeName)) {
                        String gNameTemp = field.getType().getGenericCanonicalName();
                        String gicName = gNameTemp.substring(gNameTemp.indexOf(",") + 1, gNameTemp.indexOf(">"));
                        data0.append("{").append("\"mapKey\":").append(buildJson(gicName, gNameTemp)).append("},");
                    } else if (subTypeName.length() == 1) {
                        if (!typeName.equals(genericCanonicalName)) {
                            String gicName = globGicName[i];
                            if(gicName.contains("<")){
                                String simple = DocClassUtil.getSimpleName(gicName);
                                data0.append(buildJson(simple,gicName)).append(",");
                            }else{
                                data0.append(buildJson(gicName, genericCanonicalName)).append(",");
                            }
                        } else {
                            data0.append("{\"waring\":\"You may have used non-display generics.\"},");
                        }
                        i++;
                    } else if ("java.lang.Object".equals(subTypeName)) {
                        String gicName = globGicName[i];
                        if (!typeName.equals(genericCanonicalName)) {
                            data0.append(buildJson(gicName, genericCanonicalName)).append(",");
                        } else {
                            data0.append("{\"waring\":\"You may have used non-display generics.\"},");
                        }
                    } else {
                        data0.append(buildJson(subTypeName, genericCanonicalName)).append(",");
                    }
                }
            }
        }

        StringBuilder data = new StringBuilder();
        if ("java.util.List".equals(typeName)) {
            data.append("[");
            String gName = globGicName[0];
            if ("java.lang.Object".equals(gName)) {
                data.append("{\"waring\":\"You may use java.util.Object instead of display generics in the List\"}");
            } else if (DocClassUtil.isPrimitive(gName)) {
                data.append(DocUtil.jsonValueByType(gName)).append(",");
                data.append(DocUtil.jsonValueByType(gName));
            } else if(gName.contains("<")){
                String simple = DocClassUtil.getSimpleName(gName);
                String json = buildJson(simple, gName);
                data.append(json);
            }else {
                String json = buildJson(globGicName[0], globGicName[0]);
                data.append(json);
            }
            data.append("]");
            return data.toString();
        } else if ("java.util.Map".equals(typeName)) {
            String gNameTemp = genericCanonicalName;
            String[] getKeyValType = DocClassUtil.getMapKeyValueType(gNameTemp);
            if (!"java.lang.String".equals(getKeyValType[0])) {
                throw new RuntimeException("Map's key can only use String for json,but you use " + getKeyValType[0]);
            }
            String gicName = gNameTemp.substring(gNameTemp.indexOf(",") + 1, gNameTemp.lastIndexOf(">"));
            if ("java.lang.Object".equals(gicName)) {
                data.append("{").append("\"mapKey\":").append("{\"waring\":\"You may use java.util.Object for Map value; Api-doc can't be handle.\"}").append("}");
            } else if (DocClassUtil.isPrimitive(gicName)) {
                data.append("{").append("\"mapKey1\":").append(DocUtil.jsonValueByType(gicName)).append(",");
                data.append("\"mapKey2\":").append(DocUtil.jsonValueByType(gicName)).append("}");
            } else if(gicName.contains("<")){
                String simple = DocClassUtil.getSimpleName(gicName);
                String json = buildJson(simple, gicName);
                data.append("{").append("\"mapKey\":").append(json).append("}");
            }else {
                data.append("{").append("\"mapKey\":").append(buildJson(gicName, gNameTemp)).append("}");
            }
            return data.toString();
        } else {
            if ("java.lang.Object".equals(typeName)) {
                throw new RuntimeException("Please do not return java.lang.Object directly in api interface.");
            }
            data0.deleteCharAt(data0.lastIndexOf(","));
            data0.append("}");
            return data0.toString();
        }
    }

    private String buildReqJson(JavaMethod method) {
        List<JavaParameter> parameterList = method.getParameters();
        for (JavaParameter parameter : parameterList) {
            JavaType javaType = parameter.getType();
            String simpleTypeName = javaType.getValue();
            System.out.println("参数简单类型：" + simpleTypeName);
            String gicTypeName = javaType.getGenericCanonicalName();
            System.out.println("请求参数类型：" + gicTypeName);
            String typeName = javaType.getFullyQualifiedName();
            System.out.println("请求参数简单类型：" + typeName);
            String paraName = parameter.getName();
            System.out.println("参数名：" + paraName);
            if (!MODEL.equals(typeName) && !MODEL_VIEW.equals(typeName)) {
                List<JavaAnnotation> annotations = parameter.getAnnotations();
                for (JavaAnnotation annotation : annotations) {
                    String annotationName = annotation.getType().getName();
                    if (REQUEST_BODY.equals(annotationName)) {
                        if (DocClassUtil.isPrimitive(simpleTypeName)) {
                            StringBuilder builder = new StringBuilder();
                            builder.append("{\"").append(paraName).append("\":");
                            builder.append(DocUtil.jsonValueByType(simpleTypeName)).append("}");
                            return builder.toString();
                        } else {
                            return buildJson(typeName, gicTypeName);
                        }
                    }

                }

            }
        }
        return "Does not require any request parameters.";
    }

    private String getCommentTag(final JavaMethod javaMethod, final String tagName, final String className) {
        List<DocletTag> paramTags = javaMethod.getTagsByName(tagName);
        Map<String, String> paramTagMap = new HashMap<>();
        for (DocletTag docletTag : paramTags) {
            String value = docletTag.getValue();
            if (StringUtil.isEmpty(value)) {
                throw new RuntimeException("ERROR: #" + javaMethod.getName()
                        + "() - bad @param javadoc from " + className);
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
                                + parameter.getName() + "\" in method " + javaMethod.getName() + " from " + className);
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
