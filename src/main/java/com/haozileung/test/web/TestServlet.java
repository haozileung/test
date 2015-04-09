package com.haozileung.test.web;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.QueryHelper;

@WebServlet(name = "test", urlPatterns = { "/test" }, loadOnStartup = 1)
public class TestServlet extends VelocityLayoutServlet {

	/**
     * 
     */
	private static final long serialVersionUID = -7189409201757362254L;

	// 处理请求
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		List<User> list = null;
		list = QueryHelper.query(User.class, "select username from qx_users");
		ctx.put("list", list);
		return getTemplate("/index.vm");
	}
}
