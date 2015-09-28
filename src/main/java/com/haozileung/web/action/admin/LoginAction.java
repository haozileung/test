package com.haozileung.web.action.admin;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginAction {
	public String get(HttpServletRequest request, HttpServletResponse response) {
		return "admin/login.html";
	}

	public Map<String, Object> post(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("email", request.getParameter("email"));
		result.put("password", request.getParameter("password"));
		return result;

	}
}
