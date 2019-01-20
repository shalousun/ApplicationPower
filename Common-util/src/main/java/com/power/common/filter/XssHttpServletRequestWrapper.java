package com.power.common.filter;

import com.power.common.exception.XssException;
import com.power.common.util.StringUtil;
import com.power.common.util.ValidateUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * xss过滤
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    boolean isUpData = false;//判断是否是上传 上传忽略

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        String contentType = servletRequest.getContentType();
        if (null != contentType)
            isUpData = contentType.startsWith("multipart");
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            if (ValidateUtil.isContainsForbiddenCharacter(values[i])) {
                throw new XssException("Contains illegal characters[From getParameterValues method]:" + values[i]);
            }
            encodedValues[i] = StringUtil.cleanXSS(values[i]);

        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (ValidateUtil.isContainsForbiddenCharacter(value)) {
            throw new XssException("Contains illegal characters[From getParameter method]：" + value);
        }
        if (value == null) {
            return null;
        }
        return StringUtil.cleanXSS(value);
    }

    /**
     * 获取request的属性时，做xss过滤
     */
    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
//        if (null != value && value instanceof String) {
//            if(ValidateUtil.isContainsForbiddenCharacter(String.valueOf(value))){
//              throw  new RuntimeException("From getAttribute->参数包含非法字符："+String.valueOf(value));
//            }
//            value = cleanXSS((String) value);
//        }
        return value;
    }

    @Override
    public String getHeader(String name) {

        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isUpData) {
            return super.getInputStream();
        } else {

            final ByteArrayInputStream bais = new ByteArrayInputStream(inputHandlers(super.getInputStream()).getBytes("utf-8"));

            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }

    }

    public String inputHandlers(ServletInputStream servletInputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (ValidateUtil.isContainsForbiddenCharacter(sb.toString())) {
            throw new XssException("Contains illegal characters[From getInputStream method]：" + sb.toString());
        }
        return StringUtil.cleanXSS(sb.toString());
    }

    private String cleanXSS(String value) {
        if (null == value) {
            return value;
        } else {
            value = value.replaceAll("\\+", "&#43;");
            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("%", "&#37;");
            // value = value.replaceAll("\"","&quot;");
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
            value = value.replaceAll("'", "&#39;");
            value = value.replaceAll("eval\\((.*)\\)", "非法字符");
            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            value = value.replaceAll("script", " ");
        }
        return value;
    }
}