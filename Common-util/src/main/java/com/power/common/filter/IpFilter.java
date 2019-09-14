package com.power.common.filter;

import com.power.common.exception.IPException;
import com.power.common.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yu 2018/9/28.
 */

/**
 * @author yu 2018/9/28.
 */
public class IpFilter implements Filter {

    /**
     * ip白名单
     */
    public static final String ALLOW = "allow";
    /**
     * ip黑名单
     */
    public static final String DENY = "deny";
    /**
     * 响应信息
     */
    public static final String MSG = "msg";
    private static final Logger LOGGER = LoggerFactory.getLogger(IpFilter.class);
    private Set<String> allowSet = null;

    private Set<String> denySet = null;

    private String msg = null;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        String allowString = fConfig.getInitParameter(ALLOW);
        if (allowString != null) {
            allowSet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(allowString.split(";", 0))));
        }
        String denyString = fConfig.getInitParameter(DENY);
        if (denyString != null) {
            denySet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(denyString.split(";", 0))));
        }

        msg = fConfig.getInitParameter(MSG);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //get client ip
        HttpServletRequest req = (HttpServletRequest) request;
        String ip = IpUtil.getIpAddr(req);
        if (null != denySet) {
            if (denySet.contains(ip)) {
                handleMsg(response, ip);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (allowSet != null) {
                if (allowSet.contains(ip)) {
                    chain.doFilter(request, response);
                } else {
                    handleMsg(response, ip);
                }
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private void handleMsg(ServletResponse response, String ip) throws IOException {
        if (null != msg) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().println(msg);
        } else {
            LOGGER.error("This ip can't all access [IP]: {}", ip);
            throw new IPException("This ip can't all access,IP: " + ip);
        }

    }
}
