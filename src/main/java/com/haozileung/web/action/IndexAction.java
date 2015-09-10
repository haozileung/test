package com.haozileung.web.action;

import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.cache.ICacheInvoker;
import com.haozileung.infra.dao.persistence.Criteria;
import com.haozileung.infra.dao.persistence.JdbcDaoUtil;
import com.haozileung.web.domain.blog.Post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IndexAction {
    private static ICacheInvoker<List<Post>> callback = new ICacheInvoker<List<Post>>() {
        @Override
        public List<Post> callback() {
            return JdbcDaoUtil.getInstance().queryList(Criteria.create(Post.class).desc("createTime").limit(10));
        }
    };

    public String get(HttpServletRequest request, HttpServletResponse response) {
        List<Post> page = CacheHelper.get("Post", "index", callback);
        request.setAttribute("page", page);
        return "/index.html";
    }
}
