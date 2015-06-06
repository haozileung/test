package com.haozileung.web.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

@WebServlet(urlPatterns = { "/index" }, loadOnStartup = 1)
public class IndexServlet extends VelocityLayoutServlet {

	/**
     *
     */
	private static final long serialVersionUID = -7189409201757362254L;

	// 处理请求
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		return getTemplate("/index.html");
	}
}
