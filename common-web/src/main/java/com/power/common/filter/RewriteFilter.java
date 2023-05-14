package com.power.common.filter;

import com.power.common.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * be used to rewrite vue router
 *
 * @author yu on 2017-11-22 19:47:23.
 */
public class RewriteFilter extends AbstractUrlMatcher implements Filter {

    /**
     * Need to rewrite to the destination url
     */
    public static final String REWRITE_TO = "rewriteToUrl";

    /**
     * Intercepted url, separated by English semicolon before url wildcard
     */
    public static final String REWRITE_PATTERNS = "urlPatterns";

    private Set<String> urlPatterns = null;

    private String rewriteTo = null;

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        rewriteTo = cfg.getInitParameter(REWRITE_TO);
        String exceptUrlString = cfg.getInitParameter(REWRITE_PATTERNS);
        if (StringUtil.isNotEmpty(exceptUrlString)) {
            urlPatterns = Collections.unmodifiableSet(
                    new HashSet<>(Arrays.asList(exceptUrlString.split(";", 0))));
        } else {
            urlPatterns = Collections.emptySet();
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String servletPath = request.getServletPath();
        String context = request.getContextPath();
        if (isMatches(urlPatterns, servletPath)) {
            req.getRequestDispatcher(context + "/" + rewriteTo).forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }

}
