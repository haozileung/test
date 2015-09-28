package com.haozileung.web.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction {
	public String get(HttpServletRequest request, HttpServletResponse response) {
		return "admin/login.html";
	}
}
