/**
 * 
 */
package com.haozileung.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.haozileung.web.service.IMyService;

/**
 * @author Haozi
 *
 */
public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737169523928049554L;

	private IMyService service;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	public IMyService getService() {
		return service;
	}

	@Inject
	public void setService(IMyService service) {
		this.service = service;
	}

}
