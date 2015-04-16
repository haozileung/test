package com.haozileung.test.web.system;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityViewServlet;

import com.google.common.base.Strings;
import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.Page;
import com.haozileung.test.infra.QueryHelper;

@WebServlet(name = "user", urlPatterns = { "/userdata" }, loadOnStartup = 1)
public class UserDataServlet extends VelocityViewServlet {

	/**
     * 
     */
	private static final long serialVersionUID = -7189409201757362254L;

	// 处理请求
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		String p = request.getParameter("pageNo");
		String s = request.getParameter("pageSize");
		Integer pageNo = 1;
		Integer pageSize = 25;
		if (!Strings.isNullOrEmpty(p) && StringUtils.isNumeric(p)) {
			pageNo = Integer.valueOf(p);
		}
		if (!Strings.isNullOrEmpty(s) && StringUtils.isNumeric(s)) {
			pageSize = Integer.valueOf(s);
		}
		List<User> list = QueryHelper.query_slice(User.class,
				"select * from sys_user", pageNo, pageSize);
		Long count = QueryHelper.stat("select count(*) from sys_user");
		Page<User> page = new Page<User>(pageSize);
		page.setPageNo(pageNo);
		page.setTotalCount(count);
		page.setResult(list);
		ctx.put("page", page);
		return getTemplate("/system/userdata.vm");
	}
}
