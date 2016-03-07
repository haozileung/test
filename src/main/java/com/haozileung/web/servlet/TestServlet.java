/**
 * 
 */
package com.haozileung.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.haozileung.infra.web.BaseServlet;
import com.haozileung.web.service.IMyService;

/**
 * @author Haozi
 *
 */
@WebServlet(urlPatterns = "/test", loadOnStartup = 1)
public class TestServlet extends BaseServlet {
	@Inject
	private IMyService service;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4737169523928049554L;

	@Override
	public String get(HttpServletRequest req, HttpServletResponse resp) {
		service.test();
		return null;
	}

}
