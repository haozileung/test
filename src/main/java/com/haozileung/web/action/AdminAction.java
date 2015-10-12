package com.haozileung.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminAction {
	public String get(HttpServletRequest request, HttpServletResponse response) {
        return "admin/admin.html";
    }
}
