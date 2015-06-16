package com.haozileung.web.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.ext.servlet.ServletGroupTemplate;

import com.haozileung.infra.dao.persistence.Criteria;
import com.haozileung.infra.dao.persistence.JdbcDao;
import com.haozileung.infra.dao.persistence.JdbcDaoDbUtilsImpl;

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
		JdbcDao dao = new JdbcDaoDbUtilsImpl();
		List<User> users = dao.queryList(Criteria.create(User.class));
		req.setAttribute("users", users);
		ServletGroupTemplate.instance().render("/index.html", req, resp);
	}
}
