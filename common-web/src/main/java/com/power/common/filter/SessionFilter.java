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
 * General session filter
 */
public class SessionFilter extends AbstractUrlMatcher implements Filter {

    private String sessionKey;
    private Set<String> exceptUrlPattern = null;
    private String forwardUrl;
    private String redirectUrl;

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        sessionKey = cfg.getInitParameter("sessionKey");
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
        if (StringUtil.isEmpty(sessionKey)) {
            chain.doFilter(req, resp);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();

        if (servletPath.equals(redirectUrl) || isMatches(exceptUrlPattern, servletPath)) {
            chain.doFilter(req, resp);
            return;
        }
        Object sessionObj = request.getSession().getAttribute(sessionKey);
        if (null == sessionObj) {
            boolean isAjaxRequest = isAjaxRequest(request);
            if (isAjaxRequest) {
                response.setCharacterEncoding("UTF-8");
                response.sendError(401, "You have not operated for too long, please refresh the page");
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
     * Is it an ajax asynchronous request
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
