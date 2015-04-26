package com.haozileung.test.web.system;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.Page;
import com.haozileung.test.infra.QueryHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityViewServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(urlPatterns = { "/user/list" }, loadOnStartup = 1)
public class UserListServlet extends VelocityViewServlet {

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
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		Integer pageNo = 1;
		Integer pageSize = 25;
		if (!Strings.isNullOrEmpty(p) && StringUtils.isNumeric(p)) {
			pageNo = Integer.valueOf(p);
		}
		if (!Strings.isNullOrEmpty(s) && StringUtils.isNumeric(s)) {
			pageSize = Integer.valueOf(s);
		}
		List<Object> params = Lists.newArrayList();
		StringBuffer where = new StringBuffer(" where 1 = 1 ");
		if (!Strings.isNullOrEmpty(name)) {
			where.append(" and name = ? ");
			params.add(name);
		}
		if (!Strings.isNullOrEmpty(email)) {
			where.append(" and email = ?");
			params.add(email);
		}
		StringBuffer query = new StringBuffer("select * from user");
		StringBuffer count = new StringBuffer("select count(*) from user");
		query.append(where);
		count.append(where);
		List<User> list = QueryHelper.query_slice(User.class, query.toString(),
				pageNo, pageSize, params.toArray());
		Long totalCount = QueryHelper.stat(count.toString(), params.toArray());
		Page<User> page = new Page<>(pageSize);
		page.setPageNo(pageNo);
		page.setTotalCount(totalCount);
		page.setResult(list);
		ctx.put("page", page);
		return getTemplate("/system/user_list.html");
	}
}
