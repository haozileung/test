package com.haozileung.test.web.system;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.haozileung.test.domain.system.User;
import com.haozileung.test.domain.system.repository.UserRepository;

/**
 * Created by YamchaL on 2015/4/19.
 */
@WebServlet(urlPatterns = { "/user/save" }, loadOnStartup = 1)
public class UserSaveServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String[]> requestMap = req.getParameterMap();
		Preconditions.checkArgument(requestMap.containsKey("id"));
		User user = UserRepository.getInstance().load(
				Long.valueOf(requestMap.get("id")[0]));
		try {
			BeanUtils.populate(user, requestMap);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		user.setPassword(Hashing.sha512()
				.hashString(user.getPassword(), Charsets.UTF_8).toString());
		UserRepository.getInstance().update(user);
		resp.getWriter().write("Success!");
	}
}
