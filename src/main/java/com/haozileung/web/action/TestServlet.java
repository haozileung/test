/**
 * 
 */
package com.haozileung.web.action;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haozileung.infra.servlet.BaseServlet;
import com.haozileung.web.init.AppInitializer;
import com.haozileung.web.service.IMyService;

/**
 * @author Haozi
 *
 */
@WebServlet(urlPatterns = "/test")
public class TestServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737169523928049554L;

	private IMyService service;

	public void init() {
		this.service = AppInitializer.getInjector().getInstance(IMyService.class);
	}

	@Override
	public Object get(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("num", service.add(1, 4));
		return "num.html";
	}

}
