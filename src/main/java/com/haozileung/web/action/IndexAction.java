package com.haozileung.web.action;

import com.haozileung.infra.dao.persistence.Criteria;
import com.haozileung.infra.dao.persistence.JdbcDaoUtil;
import com.haozileung.web.domain.blog.Post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IndexAction {
    public String get(HttpServletRequest request, HttpServletResponse response) {
        List<Post> posts = JdbcDaoUtil.getInstance().queryList(Criteria.create(Post.class));
        request.setAttribute("page", posts);
        return "/index.html";
    }
}
