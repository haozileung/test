package com.haozileung.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexAction {

	public String get(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("code", 0);
		request.setAttribute("message", "測試test");
		return "/index.html";
	}
}
