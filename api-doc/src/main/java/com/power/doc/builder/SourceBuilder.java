package com.power.doc.builder;

import com.power.common.util.CollectionUtil;
import com.power.common.util.JsonFormatUtil;
import com.power.common.util.StringUtil;
import com.power.doc.model.*;
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

    private static final String REQUEST_PARAM = "RequestParam";

    private static final String SERVLET_REQUEST = "javax.servlet.http.HttpServletRequest";

    private static final String MODEL_VIEW = "org.springframework.web.servlet.ModelAndView";

    private static final String MODEL = "org.springframework.ui.Model";

    private static final String BINDING_RESULT = "org.springframework.validation.BindingResult";

    private static final String JSON_CONTENT_TYPE = "application/json";

    public Map<String, String> javaFilesMap = new HashMap<>();

    private JavaProjectBuilder builder;

    private Collection<JavaClass> javaClasses;

    private boolean isStrict = false;//严格模式

    private List<ApiReqHeader> headers;


    public Map<String, CustomRespField> fieldMap = new HashMap<>();

    /**
     * if isStrict value is true,it while check all method
     *
     * @param isStrict strict flag
     */
    public SourceBuilder(boolean isStrict) {
        loadJavaFiles(null);
        this.isStrict = isStrict;
    }

    /**
     * use custom config
     *
     * @param config config
     */
    public SourceBuilder(ApiConfig config) {
        if (null == config) {
            throw new NullPointerException("ApiConfig can't be null.");
        }
        loadJavaFiles(config.getSourcePath());
        this.headers = config.getRequestHeaders();
        if (CollectionUtil.isNotEmpty(config.getCustomResponseFields())) {
            for (CustomRespField field : config.getCustomResponseFields()) {
                fieldMap.put(field.getName(), field);
            }
        }
    }

    /**
     * 加载项目的源代码
     *
     * @param path
     */
    private void loadJavaFiles(String path) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        if (StringUtil.isEmpty(path)) {
            builder.addSourceTree(new File("src/main/java"));
        } else {
            builder.addSourceTree(new File(path));
        }
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
                apiDoc.setDesc(cls.getComment());
                apiDoc.setName(controllerName);
                apiDoc.setList(apiMethodDocs);

                apiDocList.add(apiDoc);
            }
        }
        return apiDocList;
    }


    /**
     * 包括包名
     *
     * @param controller controller的名称
     * @return ApiDoc
     */
    public ApiDoc getSingleControllerApiData(String controller) {
        if (!javaFilesMap.containsKey(controller)) {
            throw new RuntimeException("Unable to find " + controller + " in your project");
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
            throw new RuntimeException(controller + " is not a Controller in your project");
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
            if (StringUtil.isEmpty(method.getComment()) && isStrict) {
                throw new RuntimeException("Unable to find comment for method " + method.getName() + " in " + cls.getName());
            }
            ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
            apiMethodDoc.setDesc(method.getComment());
            List<JavaAnnotation> annotations = method.getAnnotations();
            String url = null;
            String methodType = null;
            int methodCounter = 0;
            for (JavaAnnotation annotation : annotations) {
                String annotationName = annotation.getType().getName();
                if (REQUEST_MAPPING.equals(annotationName)) {
                    if (null == annotation.getNamedParameter("value")) {
                        throw new NullPointerException("Unable to find RequestMapping value for  method " + method.getName() + " in " + cls.getName());
                    }
                    url = annotation.getNamedParameter("value").toString();
                    if (url.contains("POST")) {
                        methodType = "post";
                    } else {
                        methodType = "get";
                    }
                    methodCounter++;
                } else if (GET_MAPPING.equals(annotationName)) {
                    if (null == annotation.getNamedParameter("value")) {
                        throw new NullPointerException("Unable to find GetMapping value for method " + method.getName() + " in " + cls.getName());
                    }
                    url = annotation.getNamedParameter("value").toString();
                    methodType = "get";
                    methodCounter++;
                } else if (POST_MAPPING.equals(annotationName)) {
                    if (null == annotation.getNamedParameter("value")) {
                        throw new NullPointerException("Unable to find PostMapping value for method " + method.getName() + " in " + cls.getName());
                    }
                    url = annotation.getNamedParameter("value").toString();
                    methodType = "post";
                    methodCounter++;
                }
            }
            if (methodCounter > 0) {
                if ("void".equals(method.getReturnType().getFullyQualifiedName())) {
                    throw new RuntimeException(method.getName() + " method in " + cls.getName() + " can't be  return type 'void'");
                }
                url = url.replaceAll("\"", "").trim();
                apiMethodDoc.setType(methodType);
                if (StringUtil.isNotEmpty(baseUrl)) {
                    apiMethodDoc.setUrl((baseUrl + "/" + url).replace("//", "/"));
                } else {
                    apiMethodDoc.setUrl((url).replace("//", "/"));
                }
                String comment = getCommentTag(method, "param", cls.getName());
                apiMethodDoc.setRequestParams(comment);
                String requestJson = buildReqJson(method, apiMethodDoc);
                apiMethodDoc.setRequestUsage(JsonFormatUtil.formatJson(requestJson));
                apiMethodDoc.setResponseUsage(buildReturnJson(method, this.fieldMap));
                String str = buildMethodReturn(method, apiMethodDoc);
                apiMethodDoc.setResponseParams(str);
                apiMethodDoc.setHeaders(createHeaders(this.headers));
                methodDocList.add(apiMethodDoc);
            }
        }
        return methodDocList;

    }

    /**
     * create request headers
     *
     * @param headers
     * @return
     */
    private String createHeaders(List<ApiReqHeader> headers) {
        StringBuilder builder = new StringBuilder();
        if (CollectionUtil.isEmpty(headers)) {
            headers = new ArrayList<>(0);
        }
        for (ApiReqHeader header : headers) {
            builder.append(header.getName()).append("|");
            builder.append(header.getType()).append("|");
            builder.append(header.getDesc()).append("\n");
        }
        return builder.toString();
    }

    public String buildMethodReturn(JavaMethod method, ApiMethodDoc apiMethodDoc) {
        String returnType = method.getReturnType().getGenericCanonicalName();
        String typeName = method.getReturnType().getFullyQualifiedName();
        if (DocClassUtil.isPrimitive(typeName)) {
            return primitiveReturnRespComment(DocClassUtil.processTypeNameForParams(typeName));
        }
        if (DocClassUtil.isCollection(typeName)) {
            String gicName = returnType.substring(returnType.indexOf("<") + 1, returnType.lastIndexOf(">"));
            if (DocClassUtil.isPrimitive(gicName)) {
                return primitiveReturnRespComment("array of " + DocClassUtil.processTypeNameForParams(gicName));
            }
            String param = buildParams(gicName, "", 0, null, fieldMap);
            return param;
        }
        if (DocClassUtil.isMap(typeName)) {
            String[] keyValue = DocClassUtil.getMapKeyValueType(returnType);
            if (DocClassUtil.isPrimitive(keyValue[1])) {
                return primitiveReturnRespComment("key value");
            }
            String param = buildParams(keyValue[1], "", 0, null, fieldMap);
            return param;
        }
        if (StringUtil.isNotEmpty(returnType)) {
            String param = buildParams(returnType, "", 0, null, fieldMap);
            return param;
        }
        return null;
    }

    public String buildParams(String className, String pre, int i, String isRequired,
                              Map<String, CustomRespField> responseFieldMap) {
        StringBuilder params0 = new StringBuilder();
        String simpleName = DocClassUtil.getSimpleName(className);


        String[] globGicName = DocClassUtil.getSimpleGicName(className);
        JavaClass cls = builder.getClassByName(simpleName);
        List<JavaField> fields = cls.getFields();
        int n = 0;
        for (JavaField field : fields) {
            if (!"serialVersionUID".equals(field.getName())) {
                String typeSimpleName = field.getType().getSimpleName();
                String subTypeName = field.getType().getFullyQualifiedName();
                String fieldGicName = field.getType().getGenericCanonicalName();
                //cover comment
                CustomRespField customResponseField = responseFieldMap.get(field.getName());
                String comment;
                if (null != customResponseField) {
                    comment = customResponseField.getDesc();
                } else {
                    comment = field.getComment();
                }
                if (DocClassUtil.isPrimitive(subTypeName)) {
                    params0.append(pre);
                    params0.append(field.getName()).append("|")
                            .append(DocClassUtil.processTypeNameForParams(typeSimpleName.toLowerCase())).append("|");

                    if (StringUtil.isNotEmpty(comment)) {
                        if (StringUtil.isEmpty(isRequired)) {
                            params0.append(comment).append("\n");
                        } else {
                            params0.append(comment).append("|").append(isRequired).append("\n");
                        }
                    } else {
                        if (StringUtil.isEmpty(isRequired)) {
                            params0.append("no comment.").append("\n");
                        } else {
                            params0.append("no comment.").append("|").append(isRequired).append("\n");
                        }
                    }
                } else {
                    params0.append(pre);
                    params0.append(field.getName()).append("|")
                            .append(DocClassUtil.processTypeNameForParams(typeSimpleName.toLowerCase())).append("|");
                    if (StringUtil.isNotEmpty(comment)) {
                        if (StringUtil.isEmpty(isRequired)) {
                            params0.append(comment).append("\n");
                        } else {
                            params0.append(comment).append("|").append(isRequired).append("\n");
                        }
                    } else {
                        params0.append("no comment.").append("\n");
                    }
                    StringBuilder preBuilder = new StringBuilder();
                    for (int j = 0; j < i; j++) {
                        preBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    }
                    preBuilder.append("└─");
                    if (DocClassUtil.isMap(subTypeName)) {
                        String gNameTemp = field.getType().getGenericCanonicalName();
                        String valType = DocClassUtil.getMapKeyValueType(gNameTemp)[1];
                        if (!DocClassUtil.isPrimitive(valType)) {
                            params0.append(buildParams(valType, preBuilder.toString(), i + 1, isRequired, responseFieldMap));
                        }
                    } else if (DocClassUtil.isCollection(subTypeName)) {
                        String gNameTemp = field.getType().getGenericCanonicalName();
                        String gName = DocClassUtil.getSimpleGicName(gNameTemp)[0];
                        if (!DocClassUtil.isPrimitive(gName)) {
                            params0.append(buildParams(gName, preBuilder.toString(), i + 1, isRequired, responseFieldMap));
                        }
                    } else if (subTypeName.length() == 1 || "java.lang.Object".equals(subTypeName)) {
                        if (!simpleName.equals(className)) {
                            if (n < globGicName.length) {
                                String gicName = globGicName[n];
                                String simple = DocClassUtil.getSimpleName(gicName);
                                if (DocClassUtil.isPrimitive(simple)) {
                                    //do nothing
                                } else if (gicName.contains("<")) {
                                    if (DocClassUtil.isCollection(simple)) {
                                        String gName = DocClassUtil.getSimpleGicName(gicName)[0];
                                        if (!DocClassUtil.isPrimitive(gName)) {
                                            params0.append(buildParams(gName, preBuilder.toString(), i + 1, isRequired, responseFieldMap));
                                        }
                                    } else if (DocClassUtil.isMap(simple)) {
                                        String valType = DocClassUtil.getMapKeyValueType(gicName)[1];
                                        if (!DocClassUtil.isPrimitive(valType)) {
                                            params0.append(buildParams(valType, preBuilder.toString(), i + 1, isRequired, responseFieldMap));
                                        }
                                    } else {
                                        params0.append(buildParams(gicName, preBuilder.toString(), i + 1, isRequired, responseFieldMap));
                                    }
                                } else {
                                    params0.append(buildParams(gicName, preBuilder.toString(), i + 1, isRequired, responseFieldMap));
                                }
                            } else {
                                params0.append(buildParams(subTypeName, preBuilder.toString(), i + 1, isRequired, responseFieldMap));
                            }
                        }
                        n++;
                    } else {
                        params0.append(buildParams(fieldGicName, preBuilder.toString(), i + 1, isRequired, responseFieldMap));
                    }
                }

            }
        }
        return params0.toString();

    }


    private String primitiveReturnRespComment(String typeName) {
        StringBuilder comments = new StringBuilder();
        comments.append("no param name|").append(typeName).append("|").append("接口直接返回").append(typeName).append("类型值\n");
        return comments.toString();
    }

    /**
     * 构建返回的json
     *
     * @param method
     * @return
     */
    private String buildReturnJson(JavaMethod method, Map<String, CustomRespField> responseFieldMap) {
        String returnType = method.getReturnType().getGenericCanonicalName();
        String typeName = method.getReturnType().getFullyQualifiedName();
        int circularRef = 0;
        return JsonFormatUtil.formatJson(buildJson(typeName, returnType, responseFieldMap, 0));
    }

    public String buildJson(String typeName, String genericCanonicalName, Map<String, CustomRespField> responseFieldMap, int circularRef) {
        System.out.println("typeName:" + typeName);
        System.out.println("genericCanonicalName:" + genericCanonicalName);
        if (DocClassUtil.isPrimitive(typeName)) {
            return DocUtil.jsonValueByType(typeName).replace("\"", "");
        }
        StringBuilder data0 = new StringBuilder();
        JavaClass cls = builder.getClassByName(typeName);
        data0.append("{");
        String[] globGicName = DocClassUtil.getSimpleGicName(genericCanonicalName);
        StringBuilder data = new StringBuilder();
        if (DocClassUtil.isCollection(typeName)) {
            data.append("[");
            String gName = globGicName[0];
            if ("java.lang.Object".equals(gName)) {
                data.append("{\"waring\":\"You may use java.util.Object instead of display generics in the List\"}");
            } else if (DocClassUtil.isPrimitive(gName)) {
                data.append(DocUtil.jsonValueByType(gName)).append(",");
                data.append(DocUtil.jsonValueByType(gName));
            } else if (gName.contains("<")) {
                String simple = DocClassUtil.getSimpleName(gName);
                String json = buildJson(simple, gName, responseFieldMap, 0);
                data.append(json);
            } else {
                String json = buildJson(globGicName[0], globGicName[0], responseFieldMap, 0);
                data.append(json);
            }
            data.append("]");
            return data.toString();
        } else if (DocClassUtil.isMap(typeName)) {
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
            } else if (gicName.contains("<")) {
                String simple = DocClassUtil.getSimpleName(gicName);
                String json = buildJson(simple, gicName, responseFieldMap, 0);
                data.append("{").append("\"mapKey\":").append(json).append("}");
            } else {
                data.append("{").append("\"mapKey\":").append(buildJson(gicName, gNameTemp, responseFieldMap, 0)).append("}");
            }
            return data.toString();
        } else if ("java.lang.Object".equals(typeName)) {
            if ("java.lang.Object".equals(typeName)) {
                throw new RuntimeException("Please do not return java.lang.Object directly in api interface.");
            }
        } else {
            List<JavaField> fields = cls.getFields();
            int i = 0;
            for (JavaField field : fields) {
                if (!"serialVersionUID".equals(field.getName())) {
                    String typeSimpleName = field.getType().getSimpleName();
                    String subTypeName = field.getType().getFullyQualifiedName();
                    String fieldGicName = field.getType().getGenericCanonicalName();

                    data0.append("\"").append(field.getName()).append("\":");
                    if (DocClassUtil.isPrimitive(typeSimpleName)) {
                        CustomRespField customResponseField = responseFieldMap.get(field.getName());
                        if (null != customResponseField) {
                            Object val = customResponseField.getValue();
                            if (null != val) {
                                if ("String".equals(typeSimpleName)) {
                                    data0.append("\"").append(val).append("\",");
                                } else {
                                    data0.append(val).append(",");
                                }
                            } else {
                                data0.append(DocUtil.getValByTypeAndFieldName(typeSimpleName, field.getName())).append(",");
                            }
                        } else {
                            data0.append(DocUtil.getValByTypeAndFieldName(typeSimpleName, field.getName())).append(",");
                        }
                    } else {
                        if (DocClassUtil.isCollection(subTypeName)) {
                            String gicName = DocClassUtil.getSimpleGicName(fieldGicName)[0];

                            if ("java.lang.String".equals(gicName)) {
                                data0.append("[").append("\"").append(buildJson(gicName, fieldGicName, responseFieldMap, 0)).append("\"]").append(",");
                            } else {
                                data0.append("[").append(buildJson(gicName, fieldGicName, responseFieldMap, 0)).append("]").append(",");
                            }
                        } else if (DocClassUtil.isMap(subTypeName)) {
                            String gicName = fieldGicName.substring(fieldGicName.indexOf(",") + 1, fieldGicName.indexOf(">"));
                            data0.append("{").append("\"mapKey\":").append(buildJson(gicName, fieldGicName, responseFieldMap, 0)).append("},");
                        } else if (subTypeName.length() == 1) {
                            if (!typeName.equals(genericCanonicalName)) {
                                String gicName = globGicName[i];
                                if (gicName.contains("<")) {
                                    String simple = DocClassUtil.getSimpleName(gicName);
                                    data0.append(buildJson(simple, gicName, responseFieldMap, circularRef + 1)).append(",");
                                } else {
                                    if (DocClassUtil.isPrimitive(gicName)) {
                                        data0.append(DocUtil.jsonValueByType(gicName)).append(",");
                                    } else {
                                        data0.append(buildJson(gicName, gicName, responseFieldMap, circularRef + 1)).append(",");
                                    }

                                }

                            } else {
                                data0.append("{\"waring\":\"You may have used non-display generics.\"},");
                            }

                            i++;
                        } else if ("java.lang.Object".equals(subTypeName)) {
                            if (i < globGicName.length) {
                                String gicName = globGicName[i];
                                if (!typeName.equals(genericCanonicalName)) {
                                    if(DocClassUtil.isPrimitive(gicName)){
                                        data0.append("\"").append(buildJson(gicName, genericCanonicalName, responseFieldMap, 0)).append("\",");
                                    }else{
                                        data0.append(buildJson(gicName, gicName, responseFieldMap, 0)).append(",");
                                    }
                                } else {
                                    data0.append("{\"waring\":\"You may have used non-display generics.\"},");
                                }
                            } else {
                                data0.append("{\"waring\":\"You may have used non-display generics.\"},");
                            }
                        } else {
                            //
                            data0.append(buildJson(subTypeName, fieldGicName, responseFieldMap, 0)).append(",");
                        }
                    }
                }
            }
        }
        data0.deleteCharAt(data0.lastIndexOf(","));
        data0.append("}");
        return data0.toString();
    }

    private String buildReqJson(JavaMethod method, ApiMethodDoc apiMethodDoc) {
        List<JavaParameter> parameterList = method.getParameters();
        for (JavaParameter parameter : parameterList) {
            JavaType javaType = parameter.getType();
            String simpleTypeName = javaType.getValue();
            String gicTypeName = javaType.getGenericCanonicalName();
            String typeName = javaType.getFullyQualifiedName();
            String paraName = parameter.getName();
            if (!MODEL.equals(typeName) && !MODEL_VIEW.equals(typeName) &&
                    !SERVLET_REQUEST.equals(typeName) && !BINDING_RESULT.equals(typeName)) {
                List<JavaAnnotation> annotations = parameter.getAnnotations();
                for (JavaAnnotation annotation : annotations) {
                    String annotationName = annotation.getType().getName();
                    if (REQUEST_BODY.equals(annotationName)) {
                        apiMethodDoc.setContentType(JSON_CONTENT_TYPE);
                        if (DocClassUtil.isPrimitive(simpleTypeName)) {
                            StringBuilder builder = new StringBuilder();
                            builder.append("{\"").append(paraName).append("\":");
                            builder.append(DocUtil.jsonValueByType(simpleTypeName)).append("}");
                            return builder.toString();
                        } else {
                            return buildJson(typeName, gicTypeName, this.fieldMap, 0);
                        }
                    } else {
                        //非json请求，当前不做处理
                    }

                }

            }
        }
        return "Does not require any request parameters.";
    }

    private String getCommentTag(final JavaMethod javaMethod, final String tagName, final String className) {
        //对请求参数的注释目前不做任何修复
        Map<String, CustomRespField> responseFieldMap = new HashMap<>();
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
            int requestBodyCounter = 0;
            StringBuilder reqBodyParams = new StringBuilder();
            StringBuilder reqParam = new StringBuilder();
            for (JavaParameter parameter : parameterList) {
                String typeName = parameter.getType().getGenericCanonicalName();
                String simpleName = parameter.getType().getValue().toLowerCase();
                String fullTypeName = parameter.getType().getFullyQualifiedName();
                if (!MODEL.equals(typeName) && !MODEL_VIEW.equals(typeName) &&
                        !SERVLET_REQUEST.equals(typeName) && !BINDING_RESULT.equals(typeName)) {
                    if (!paramTagMap.containsKey(parameter.getName())) {
                        throw new RuntimeException("Unable to find javadoc @param for actual param \""
                                + parameter.getName() + "\" in method " + javaMethod.getName() + " from " + className);
                    }
                    List<JavaAnnotation> annotations = parameter.getAnnotations();
                    if (annotations.size() == 0) {
                        //default set required is true
                        reqParam.append(parameter.getName()).append("|")
                                .append(DocClassUtil.processTypeNameForParams(simpleName)).append("|")
                                .append(paramTagMap.get(parameter.getName())).append("|true\n");
                    }
                    for (JavaAnnotation annotation : annotations) {
                        String annotationName = annotation.getType().getName();
                        if (REQUEST_BODY.equals(annotationName)) {
                            if (requestBodyCounter > 0) {
                                throw new RuntimeException("You have use @RequestBody Passing multiple variables  for method "
                                        + javaMethod.getName() + " in " + className + ",@RequestBody annotation could only bind one variables.");
                            }
                            if (DocClassUtil.isPrimitive(fullTypeName)) {
                                reqBodyParams.append(parameter.getName()).append("|")
                                        .append(DocClassUtil.processTypeNameForParams(simpleName)).append("|")
                                        .append(paramTagMap.get(parameter.getName())).append("|true\n");
                            } else {
                                if (DocClassUtil.isCollection(fullTypeName)) {
                                    String[] gicNameArr = DocClassUtil.getSimpleGicName(typeName);
                                    if (DocClassUtil.isPrimitive(gicNameArr[0])) {
                                        reqBodyParams.append(parameter.getName()).append("|")
                                                .append(DocClassUtil.processTypeNameForParams(simpleName)).append("|")
                                                .append(paramTagMap.get(parameter.getName())).append("|true\n");
                                    } else {
                                        String strPrams = buildParams(gicNameArr[0], "", 0, "true", responseFieldMap);
                                        reqBodyParams.append(strPrams);
                                    }

                                } else if (DocClassUtil.isMap(fullTypeName)) {
                                    String[] gicNameArr = DocClassUtil.getSimpleGicName(typeName);
                                    String strPrams = buildParams(gicNameArr[1], "", 0, "true", responseFieldMap);
                                    reqBodyParams.append(strPrams);
                                } else {
                                    reqBodyParams.append(buildParams(typeName, "", 0, "true", responseFieldMap));
                                }
                            }
                            requestBodyCounter++;
                        } else {
                            String required = "true";
                            if (null != annotation.getProperty("required")) {
                                required = annotation.getProperty("required").toString();
                            }
                            reqParam.append(parameter.getName()).append("|")
                                    .append(DocClassUtil.processTypeNameForParams(simpleName)).append("|")
                                    .append(paramTagMap.get(parameter.getName())).append("|")
                                    .append(required).append("\n");
                        }
                    }

                }
            }
            if (requestBodyCounter > 0) {
                params.append(reqBodyParams);
                return params.toString();
            }
            params.append(reqParam);
            return params.toString();
        }
        return null;
    }

    public static void main(String[] args) {
        SourceBuilder sourceBuilder = new SourceBuilder(true);
//        else if(DocClassUtil.isArray(typeSimpleName)){
//                            typeSimpleName = typeSimpleName.substring(0,typeSimpleName.indexOf("["));
//                            subTypeName = subTypeName.substring(0,subTypeName.indexOf("["));
//                            fieldGicName = fieldGicName.substring(0,fieldGicName.indexOf("["));
//                            String gicName = DocClassUtil.getSimpleGicName(fieldGicName)[0];
//                            System.out.println("simpleTypeName:"+typeSimpleName);
//                            System.out.println("subTypeName:"+subTypeName);
//                            System.out.println("fieldGicName:"+fieldGicName);
//                            System.out.println("gicName:"+fieldGicName);
//                            data0.append("[").append(buildJson(gicName, fieldGicName, responseFieldMap)).append("]").append(",");

//        }
    }
}
