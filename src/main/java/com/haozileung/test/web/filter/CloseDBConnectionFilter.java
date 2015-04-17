package com.haozileung.test.web.filter;

import com.haozileung.test.infra.DataSourceProvider;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Servlet Filter implementation class CloseDBConnectionFilter
 */
@WebFilter(filterName = "CloseDBConnectionFilter", urlPatterns = "/*")
public class CloseDBConnectionFilter implements Filter {

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            DataSourceProvider.closeConnection();
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }
}
