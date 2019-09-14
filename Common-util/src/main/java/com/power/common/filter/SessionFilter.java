package com.power.common.filter;


import com.power.common.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 通用的session拦截过滤器
 */
public class SessionFilter extends AbstractUrlMatcher implements Filter {

    private String sessionKey;
    private Set<String> exceptUrlPattern = null;//配置例外url
    private String forwardUrl;//服务器转发地址,预留
    private String redirectUrl;//重定向

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        sessionKey = cfg.getInitParameter("sessionKey");
        //配置拦击
        String exceptUrlString = cfg.getInitParameter("exceptUrlPattern");
        if (StringUtil.isNotEmpty(exceptUrlString)) {
            exceptUrlPattern = Collections.unmodifiableSet(
                    new HashSet<>(Arrays.asList(exceptUrlString.split(";", 0))));
        } else {
            exceptUrlPattern = Collections.emptySet();
        }

        forwardUrl = cfg.getInitParameter("forwardUrl");
        redirectUrl = cfg.getInitParameter("redirectUrl");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //没有配置sessionKey则直接放行
        if (StringUtil.isEmpty(sessionKey)) {
            chain.doFilter(req, resp);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();

        // 如果请求的路径与forwardUrl相同，或请求的路径是排除的URL时，则直接放行
        if (servletPath.equals(redirectUrl) || isMatches(exceptUrlPattern, servletPath)) {
            chain.doFilter(req, resp);
            return;
        }
        Object sessionObj = request.getSession().getAttribute(sessionKey);
        if (null == sessionObj) {
            boolean isAjaxRequest = isAjaxRequest(request);
            if (isAjaxRequest) {
                response.setCharacterEncoding("UTF-8");
                response.sendError(401, "您已经太长时间没有操作,请刷新页面");
                return;
            } else {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + redirectUrl);
            }
        } else {
            chain.doFilter(req, resp);
            return;
        }

    }

    @Override
    public void destroy() {

    }

    /**
     * 判断是否是ajax异步
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)) {
            return true;
        } else {
            return false;
        }
    }
}
