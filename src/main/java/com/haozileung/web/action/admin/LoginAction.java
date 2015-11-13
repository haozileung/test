package com.haozileung.web.action.admin;

import com.google.common.collect.Maps;
import com.haozileung.infra.mvc.JsonServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

@WebServlet(urlPatterns = "/admin/login")
public class LoginAction extends JsonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1319560994298001675L;

	public Map<String, Object> post(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("email", request.getParameter("email"));
		result.put("password", request.getParameter("password"));
		return result;
	}
}
