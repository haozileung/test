package com.haozileung.test.web.system;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.haozileung.test.domain.system.User;
import com.haozileung.test.domain.system.repository.UserRepository;

/**
 * Created by YamchaL on 2015/4/19.
 */
@WebServlet(urlPatterns = { "/user/ban" }, loadOnStartup = 1)
public class UserBanServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uidStr = req.getParameter("uid");
		if (!Strings.isNullOrEmpty(uidStr)) {
			Long uid = Long.valueOf(uidStr);
			User user = UserRepository.getInstance().load(uid);
			if (user.getStatus()) {
				user.setStatus(Boolean.FALSE);
			} else {
				user.setStatus(Boolean.TRUE);
			}
			UserRepository.getInstance().update(user);
		}
	}
}
