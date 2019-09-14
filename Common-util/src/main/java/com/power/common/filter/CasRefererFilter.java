package com.power.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 单点登录过滤器
 * Created by yu on 2017/8/1.
 */
public class CasRefererFilter implements Filter {
    private Set<String> includeServices = null;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        String excludedString = fConfig.getInitParameter("includeServices");
        if (excludedString != null) {
            includeServices = Collections.unmodifiableSet(
                    new HashSet<>(Arrays.asList(excludedString.split(",", 0))));
        } else {
            includeServices = Collections.emptySet();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (isExcluded(req)) {
            chain.doFilter(request, response);
            return;
        } else {
            req.getRequestDispatcher("/hhhhhhhhh").forward(request, response);
        }

    }


    private boolean isExcluded(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if (null == referer) {
            return true;
        }
        return includeServices.contains(referer);
    }
}
