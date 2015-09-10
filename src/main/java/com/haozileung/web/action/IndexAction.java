package com.haozileung.web.action;

import com.haozileung.infra.dao.persistence.JdbcDaoUtil;
import com.haozileung.infra.utils.RequestUtil;
import com.haozileung.web.domain.blog.Post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexAction {

	public String get(HttpServletRequest request, HttpServletResponse response) {
		Post post = RequestUtil.getBean(request, Post.class, null);
		request.setAttribute("post", post);
		JdbcDaoUtil.getInstance().save(post);
		return "/index.html";
	}
}
