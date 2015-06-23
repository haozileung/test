package com.haozileung.web.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.infra.dao.exceptions.DaoException;
import com.haozileung.infra.utils.DataSourceUtil;

/**
 * Servlet Filter implementation class CloseDBConnectionFilter
 */
@WebFilter(filterName = "CloseDBConnectionFilter", urlPatterns = "/*")
public class CloseDBConnectionFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(CloseDBConnectionFilter.class);

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			DataSourceUtil.closeConnection(true);
		} finally {
			DataSourceUtil.closeConnection(false);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
	}
}
