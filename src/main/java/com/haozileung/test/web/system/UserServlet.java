package com.haozileung.test.web.system;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

/**
 * Servlet implementation class Users
 */
@WebServlet(urlPatterns = { "/users" }, loadOnStartup = 1)
public class UserServlet extends VelocityLayoutServlet {
	private static final long serialVersionUID = 1L;

	// 处理请求
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		return getTemplate("/system/users.html");
	}

}
