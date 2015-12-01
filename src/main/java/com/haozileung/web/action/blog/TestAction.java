package com.haozileung.web.action.blog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = { "/fi" }, loadOnStartup = 1)
public class TestAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6184133246938545225L;

	private int fi(int n) {
		if (n <= 2) {
			return 1;
		} else {
			return fi(n - 1) + fi(n - 2);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write(String.valueOf(fi(40)));
	}

}