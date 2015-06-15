package com.haozileung.web.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.haozileung.infra.utils.DataSourceUtils;

/**
 * Servlet Filter implementation class CloseDBConnectionFilter
 */
@WebFilter(filterName = "CloseDBConnectionFilter", urlPatterns = "/*")
public class CloseDBConnectionFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
		} finally {
			DataSourceUtils.closeConnection();
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
	}
}
