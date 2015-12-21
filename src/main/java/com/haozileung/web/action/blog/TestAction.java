package com.haozileung.web.action.blog;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger logger = LoggerFactory.getLogger(TestAction.class);

	private static Random r = new Random(new Date().getTime());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int i = 0;
		while (true) {
			i = r.nextInt(40);
			if (i > 0) {
				break;
			}
		}
		logger.info("cal {} 's fi...", i);
		resp.getWriter().write(String.valueOf(fi(i)));
	}

}