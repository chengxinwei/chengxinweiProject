package com.howbuy.cc.basic.boot.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/9/7.
 */
public class TestFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter");
        filterChain.doFilter(servletRequest , servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
