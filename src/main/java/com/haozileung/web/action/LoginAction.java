package com.haozileung.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction {
	public String get(HttpServletRequest request, HttpServletResponse response) {
		return "/login.html";
	}
}
