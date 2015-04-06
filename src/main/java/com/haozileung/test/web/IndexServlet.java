package com.haozileung.test.web;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

import com.haozileung.test.domain.security.User;
import com.haozileung.test.infra.QueryHelper;
import com.haozileung.test.infra.cache.CacheHelper;
import com.haozileung.test.infra.cache.ICacheInvoker;

@WebServlet(name = "index", urlPatterns = { "/index" }, loadOnStartup = 1)
public class IndexServlet extends VelocityLayoutServlet {

	/**
     * 
     */
	private static final long serialVersionUID = -7189409201757362254L;

	// 处理请求
	@SuppressWarnings("unchecked")
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		List<User> list = (List<User>) CacheHelper.get("User", "alluser",
				new ICacheInvoker() {
					@Override
					public Object callback(Object... args) {
						return QueryHelper.query(User.class,
								"select username from t_user where status = ?",
								args);
					}
				}, "ENABLED");
		ctx.put("list", list);
		return getTemplate("/index.vm");
	}
}
