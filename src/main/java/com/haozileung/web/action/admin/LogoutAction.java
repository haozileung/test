package com.haozileung.web.action.admin;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haozileung.infra.mvc.JsonServlet;

/**
 * Created by Haozi on 2015/9/28.
 */
@WebServlet(urlPatterns = "/admin/logout")
public class LogoutAction extends JsonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5139638775314301611L;

	public String get(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}
