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
 * referer过滤器
 * Created by yu on 2017/7/23.
 */
public class RefererFilter implements Filter {

    public static final String IGNORES = "ignores";
    private static final long serialVersionUID = 1L;
    private Set<String> excluded = null;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        String excludedString = fConfig.getInitParameter(IGNORES);
        if (excludedString != null) {
            excluded = Collections.unmodifiableSet(
                    new HashSet<>(Arrays.asList(excludedString.split(";", 0))));
        } else {
            excluded = Collections.emptySet();
        }
    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        // 链接来源地址
        if (isExcluded(request)) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/aa").forward(request, response);
        }
    }

    public void destroy() {

    }

    /**
     * 判断是否是例外referer
     *
     * @param request
     * @return
     */
    private boolean isExcluded(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if (StringUtil.isEmpty(referer)) {
            return true;
        }
        String severName = request.getServerName();
        if (referer.contains(severName)) {
            return true;
        }
        for (String url : excluded) {
            if (referer.contains(url)) {
                return true;
            }
        }
        return false;
    }
}
