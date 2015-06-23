package com.haozileung.web.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.ext.servlet.ServletGroupTemplate;

import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.cache.ICacheInvoker;
import com.haozileung.infra.dao.persistence.Criteria;
import com.haozileung.infra.dao.persistence.JdbcDaoUtil;

@WebServlet(urlPatterns = { "/index" }, loadOnStartup = 1)
public class IndexServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -840470989620299816L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		List<User> users = CacheHelper.get("User", "allUser",
				new ICacheInvoker<List<User>>() {
					@Override
					public List<User> callback() {
						List<User> users = JdbcDaoUtil.getInstance().queryList(
								Criteria.create(User.class));
						return users;
					}
				});
		req.setAttribute("users", users);
		ServletGroupTemplate.instance().render("/index.html", req, resp);
	}
}
