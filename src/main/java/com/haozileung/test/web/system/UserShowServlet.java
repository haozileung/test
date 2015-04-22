package com.haozileung.test.web.system;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityViewServlet;

import com.google.common.base.Strings;
import com.haozileung.test.domain.system.User;
import com.haozileung.test.domain.system.repository.UserRepository;

/**
 * Created by YamchaL on 2015/4/18.
 */
@WebServlet(urlPatterns = "/user/show", loadOnStartup = 1)
public class UserShowServlet extends VelocityViewServlet {
	// 处理请求
	@Override
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {
		String uids = request.getParameter("uid");
		if (!Strings.isNullOrEmpty(uids) && StringUtils.isNumeric(uids)) {
			Long uid = Long.valueOf(uids);
			User user = UserRepository.getInstance().load(uid);
			ctx.put("user", user);
		}
		return getTemplate("/system/user_show.html");
	}
}
